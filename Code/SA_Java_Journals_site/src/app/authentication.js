define(["knockout", "knockout-mapping", 'app/app.config', "crossroads", "hasher"], function (ko, komapping, APP, crossroads, hasher) {

  //TEST1: BASIC Authentication with sessionstorage
  //TEST2: No BASIC, custome filter
  //TEST3: JWT with jax-rs


  return {
    loadUserData: function () {
      var json;
      var jsonStr = localStorage.getItem('authData');
      try {
        json = JSON.parse(jsonStr);
      } catch (e) {
        return null;
      }
      return json;
    },
    storeUserData: function (authData) {
      if (authData) {
        localStorage.setItem("authData", JSON.stringify(authData));
      }
    },
    login: function (loginData, oldRoute, authModel) {
      $.ajax({
        method: "POST",
        url: APP.SERVER + APP.REST_PATH + 'auth/login',
        data: ko.toJSON(loginData()),
        contentType: 'application/json',
        dataType: 'json'
      }).done(function (data) {
        if (data && data.success) {
          localStorage.setItem("authData", JSON.stringify(data));
          komapping.fromJS(data, {}, authModel);
          var landingPage = oldRoute();
          if (oldRoute() === '') {
            switch (data.role) {
              case 'PUBLISHER':
                landingPage = 'journals';
                break;
              case 'PUBLIC':
                landingPage = 'subscriptions';
                break;
              default:
                landingPage = oldRoute();
                break;
            }
          }
          oldRoute('reseted');
          hasher.setHash(landingPage);
          crossroads.parse(landingPage);
        } else {
          alert("error login");
        }
      });
    },
    logout: function (authData) {
      localStorage.removeItem("authData");
      authData.success(false);
      authData.role('');
      authData.userName('');
      authData.token('');
      hasher.setHash('');
      crossroads.parse('');
    }
  };
});