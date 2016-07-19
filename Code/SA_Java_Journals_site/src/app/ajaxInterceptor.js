define(["growl", "jquery-binarytransport", "app/app.config", "crossroads", "hasher", "app/authentication"], function (growl, binarytransport, APP, crossroads, hasher, auth) {

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
        $('.modal').each(function () {
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

  var ajaxCall = function (conf, doneCallback, errorCallback) {
    if (auth.loadUserData()) {
      var token = auth.loadUserData().token;
      conf['beforeSend'] = function (xhr) {
        xhr.setRequestHeader("Authorization", "Basic " + token);
      };
      $.ajax(conf)
        .done(doneCallback)
        .fail(errorCallback, defaultErrorCallback);
    } else {
      hasher.setHash('error/403');
      crossroads.parse('error/403');
    }
  }



  return {
    sendAjax: function (method, data, contentType, dataType, restPath, doneCallback, errorCallback, fileUpload) {
      var conf = {
        method: method,
        url: APP.SERVER + APP.REST_PATH + restPath,
        data: data,
        contentType: contentType,
        dataType: dataType
      };
      if (fileUpload) {
        conf['cache'] = false;
        conf['processData'] = false;// Don't process the files
        conf['contentType'] = false;// Set content type to false as jQuery will tell the server its a query string request
      }
      ajaxCall(conf, doneCallback, errorCallback);
    },
    downloadFile: function (method, restPath, fileName) {
      if (auth.loadUserData()) {
        var conf = {
          method: method,
          url: APP.SERVER + APP.REST_PATH + restPath,
          dataType: 'binary'
        };
        ajaxCall(conf, function (result) {
          var windowUrl = window.URL || window.webkitURL;
          var url = windowUrl.createObjectURL(result);
          //var url = URL.createObjectURL(result);
          var $a = $('<a />', {
            'href': url,
            'download': fileName,
            'text': "click"
          }).hide().appendTo("body")[0].click();
          setTimeout(function () {
            windowUrl.revokeObjectURL(url);
          }, 10000);
        });
      } else {
        hasher.setHash('error/403');
        crossroads.parse('error/403');
      }
    }
  };
});