define(["knockout", "crossroads", "hasher"], function (ko, crossroads, hasher) {
 
  return new Router({
    routes: [
      {url: '', params: {page: 'home-view', label: 'Home'}},
      {url: 'journals', params: {page: 'journals-view', label: 'Journals'}},
      {url: 'subscriptions', params: {page: 'subscriptions-view', label: 'Subscriptions'}}
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