define(["knockout", "crossroads", "hasher"], function (ko, crossroads, hasher) {
 
  return new Router({
    routes: [
      {url: '', params: {page: 'home-view', label: 'Home', inMainMenu: true}},
      {url: 'journals', params: {page: 'journals-view', label: 'Journals', inMainMenu: true}},
      {url: 'journals/{idJournal}/publications', params: {page: 'journal-publications-view', label: 'Journal Publications', inMainMenu: false}},
      {url: 'subscriptions', params: {page: 'subscriptions-view', label: 'Subscriptions', inMainMenu: true}}
    ]
  });

  function Router(config) {
    var currentRoute = this.currentRoute = ko.observable({});
    this.routesArr = ko.observable(config.routes);

    ko.utils.arrayForEach(config.routes, function (route) {
      crossroads.addRoute(route.url, function (requestParams) {
        currentRoute(ko.utils.extend(requestParams, route.params));
      });
    });

    activateCrossroads();
  }

  function activateCrossroads() {
    function parseHash(newHash, oldHash) {
      crossroads.parse(newHash);
    }
    crossroads.normalizeFn = crossroads.NORM_AS_OBJECT;
    hasher.initialized.add(parseHash);
    hasher.changed.add(parseHash);
    hasher.init();
  }
});