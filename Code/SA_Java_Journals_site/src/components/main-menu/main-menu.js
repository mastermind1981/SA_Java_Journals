define(['knockout', 'text!./main-menu.html'], function(ko, template) {

  function MainMenuViewModel(params) {

    // This viewmodel doesn't do anything except pass through the 'route' parameter to the view.
    // You could remove this viewmodel entirely, and define 'nav-bar' as a template-only component.
    // But in most apps, you'll want some viewmodel logic to determine what navigation options appear.
    var self = this;
    self.routerObj = params.routerObj;
  }

  return { viewModel: MainMenuViewModel, template: template };
});
