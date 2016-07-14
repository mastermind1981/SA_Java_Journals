define(['jquery', 'knockout', 'knockout-mapping', './router', './authentication', 'bootstrap', 'knockout-projections'], function ($, ko, komapping, router, auth) {

  // Components can be packaged as AMD modules, such as the following:
  ko.components.register('home-view', {require: 'views/home-view/home-view'});
  ko.components.register('journals-view', {require: 'views/journals-view/journals-view'});
  ko.components.register('journal-publications-view', {require: 'views/journal-publications-view/journal-publications-view'});
  ko.components.register('subscriptions-view', {require: 'views/subscriptions-view/subscriptions-view'});
  ko.components.register('error-view', {require: 'views/error-view/error-view'});

  ko.components.register('about-page', {
    template: {require: 'text!components/about-page/about.html'}
  });

  ko.components.register('main-menu', {require: 'components/main-menu/main-menu'});
  // [Scaffolded component registrations will be inserted here. To retain this feature, don't remove this comment.]

  // Start the application
  var AuthModelObj = {
    success: ko.observable(false),
    role: ko.observable(),
    token: ko.observable(),
    userName: ko.observable()
  };

  var json = auth.loadUserData();
  if (json) {
    // Map to set authModel from JSON
    komapping.fromJS(json, {}, AuthModelObj);
  }
  ko.applyBindings({routerObj: router, authModelObj: AuthModelObj});
  
  
  $(function () {
    $("[data-hide]").on("click", function (event) {
      event.preventDefault();
      $(this).closest("." + $(this).attr("data-hide")).hide();
    });
  });
});
