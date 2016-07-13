define(["knockout", "app/ajaxInterceptor", "text!./journals-list.html", "knockout-mapping"], function (ko, ajaxInterceptor, journalsListTemplate, komapping) {

  function SearchJournalsViewModel(params) {
    var self = this;
    self.journalMappings = {
      'observe': [""]
    };
    self.journals = params.journals;
    self.authModel = params.authModel;
  }

  return {viewModel: SearchJournalsViewModel, template: journalsListTemplate};

});
