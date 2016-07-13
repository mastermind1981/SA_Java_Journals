define(['knockout', "knockout-mapping", 'app/authentication', 'text!./main-menu.html'], function(ko, komapping, auth, template) {

  function MainMenuViewModel(params) {

    var self = this;
    self.routerObj = params.routerObj;
    //check the current user
    self.authModel = params.authModel;
  }
  
  MainMenuViewModel.prototype.logout = function () {
    var self = this;
    auth.logout(self.authModel);
  };
  
  return { viewModel: MainMenuViewModel, template: template };
});
