define(["growl", "app/app.config", "crossroads", "hasher", "app/authentication"], function (growl, APP, crossroads, hasher, auth) {

  var navToError = function (error) {
    hasher.setHash('error/' + error);
    crossroads.parse('error/' + error);
  };

  var afterCloseModal = function (event) {
    $('.modal').off('hidden.bs.modal', afterCloseModal);
    navToError(event.data.error);

  };

  var defaultErrorCallback = function (jqXHR, textStatus) {
    var error = jqXHR.status;
    switch (jqXHR.status) {
      case 0:
      case 403:
      case 401:
        if (error === 0) {
          error = 403;
        }
        var modalOpen = false;
        $('.modal').each(function() {
          var data = $(this).data('bs.modal');
          if (data && data.isShown) {
            modalOpen = true;
          }
        });
        if (modalOpen) {
          $('.modal').on('hidden.bs.modal', {error: error}, afterCloseModal);
          $('.modal').modal('hide');
        } else {
          navToError(error);
        }
        break;
      default:
        //throws error
        $.bootstrapGrowl("An error has occuer in the server, try again!", {type: 'danger', delay: 5000});
        break;
    }

  };



  return {
    sendAjax: function (method, data, contentType, dataType, restPath, doneCallback, errorCallback) {
      if (auth.loadUserData()) {
        var token = auth.loadUserData().token;
        $.ajax({
          method: method,
          url: APP.SERVER + APP.REST_PATH + restPath,
          data: data,
          contentType: contentType,
          dataType: dataType,
          beforeSend: function (xhr) {
            xhr.setRequestHeader("Authorization", "Basic " + token);
          }
        })
          .done(doneCallback)
          .fail(errorCallback, defaultErrorCallback);
      } else {
        hasher.setHash('error/403');
        crossroads.parse('error/403');
      }
    }
  };
});