define(['jquery', 'knockout', './router', 'bootstrap', 'knockout-projections'], function($, ko, router) {

  // Components can be packaged as AMD modules, such as the following:
  ko.components.register('main-menu', { require: 'components/main-menu/main-menu' });
  ko.components.register('home-view', { require: 'views/home-view/home-view' });
  ko.components.register('journals-view', { require: 'views/journals-view/journals-view' });

  ko.components.register('about-page', {
    template: { require: 'text!components/about-page/about.html' }
  });

  // [Scaffolded component registrations will be inserted here. To retain this feature, don't remove this comment.]

  // Start the application
  ko.applyBindings({ routerObj: router });
});
