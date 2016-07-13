define(["knockout", "knockout-mapping", "crossroads", "hasher", "app/authentication"], function (ko, komapping, crossroads, hasher, auth) {

  return new Router({
    routes: [
      {url: '', params: {page: 'home-view', label: 'Home', inMainMenu: true}, roles: []},
      {url: 'error/{codeError}', params: {page: 'error-view', label: 'Error', inMainMenu: false}},
      {url: 'journals', params: {page: 'journals-view', label: 'Journals', inMainMenu: true}, roles: ['PUBLISHER']},
      {url: 'journals/{idJournal}/publications', params: {page: 'journal-publications-view', label: 'Journal Publications', inMainMenu: false}, roles: ['PUBLISHER']},
      {url: 'subscriptions/{idJournal}/publications', params: {page: 'journal-publications-view', label: 'Journal Publications', inMainMenu: false}, roles: ['PUBLISHER','PUBLIC']},
      {url: 'subscriptions', params: {page: 'subscriptions-view', label: 'Subscriptions', inMainMenu: true}, roles: ['PUBLIC']}
    ]
  });

  function Router(config) {
    var self = this;
    var currentRoute = this.currentRoute = ko.observable({});
    this.routesArr = ko.observable(config.routes);
    self.oldRoute = ko.observable('');
    hasher.prependHash = '';
    ko.utils.arrayForEach(config.routes, function (route) {
      crossroads.addRoute(route.url, function (requestParams) {
        manageAuthentication(requestParams, route, config.routes, currentRoute, self.oldRoute);
      });
    });

    activateCrossroads();
  }

  function manageAuthentication(requestParams, route, routes, currentRoute, oldRoute) {
    var self = this;
    var json = auth.loadUserData();
    var fail = true;
    var hasPermission = false;
    if (json) {//validate if user data is valid
      if (json.role) {
        fail = false;
        if (!route.roles || route.roles.length === 0) {
          hasPermission = true;
        } else {
          ko.utils.arrayForEach(route.roles, function (role) {
            if (role === json.role) {
              hasPermission = true;
            }
          });
        }
        if (hasPermission) {
          //navigate to requested view
          currentRoute(ko.utils.extend(requestParams, route.params));
        } else {
          //go to error page
          hasher.setHash('error/403');
          crossroads.parse('error/403');
        }
      }
    }
    if (route.url === 'error') {
      currentRoute(ko.utils.extend(requestParams, route.params));
      fail = false;
    }
    if (fail) {
      currentRoute(ko.utils.extend(requestParams, routes[0].params));
      oldRoute(hasher.getHash());
    }

  }
  ;

  function activateCrossroads() {
    function parseHash(newHash, oldHash) {
      crossroads.ignoreState = true;
      crossroads.parse(newHash);
    }
    crossroads.normalizeFn = crossroads.NORM_AS_OBJECT;
    hasher.initialized.add(parseHash);
    hasher.changed.add(parseHash);
    hasher.init();
  }
});