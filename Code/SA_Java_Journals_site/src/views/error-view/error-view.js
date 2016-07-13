define(['knockout', "knockout-mapping", 'text!./error-view.html'], function(ko, komapping, template) {

  function errorViewModel(params) {

    // This viewmodel doesn't do anything except pass through the 'route' parameter to the view.
    // You could remove this viewmodel entirely, and define 'nav-bar' as a template-only component.
    // But in most apps, you'll want some viewmodel logic to determine what navigation options appear.
    var self = this;
    self.routerObj = params.router;
    //check the current user
    self.authModel = params.authModel;
    self.errorCode = params.router.currentRoute().codeError;
  }

  return { viewModel: errorViewModel, template: template };
});
