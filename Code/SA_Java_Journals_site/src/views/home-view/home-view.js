define(["knockout", "knockout-mapping", "shalib", "base64", "app/app.config", "text!./home-view.html", "crossroads", "hasher", "app/authentication"], function (ko, komapping, jsSHA, B64, APP, homeTemplate, crossroads, hasher, auth) {

  var loginData = function () {
    var self = this;
    self.userName = ko.observable('mguevara');
    self.userPassword = ko.observable('ajedrez21');
  };

  function HomeViewModel(params) {
    var self = this;
    self.loginData = ko.observable(new loginData());
    self.oldRoute = params.router.oldRoute;
    //check the current user
    self.authModel = params.authModel;

  }

  HomeViewModel.prototype.doLogin = function () {
    var self = this;
    var tempPassword = self.loginData().userPassword();
    //creating SHA and bas64 of the password
    var shaObj = new jsSHA("SHA-256", "TEXT");
    shaObj.update(self.loginData().userPassword());
    var hash = shaObj.getHash("B64");
    self.loginData().userPassword(hash);
    auth.login(self.loginData, self.oldRoute, self.authModel);
  };
  
  HomeViewModel.prototype.doLogout = function () {
    var self = this;
     auth.logout(self.authModel);
  };

  return {viewModel: HomeViewModel, template: homeTemplate};

});
