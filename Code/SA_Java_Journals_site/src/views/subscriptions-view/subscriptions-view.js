define(["knockout", "app/app.config", "app/ajaxInterceptor", "text!./subscriptions-view.html", "knockout-mapping"], function (ko, APP, ajaxInterceptor, supbscriptionsTemplate, komapping) {

  var JournalSearchCriterias = function () {
    var self = this;
    self.name = ko.observable();
    self.tags = ko.observable();
    self.active = true;
  };
  
  var subscriptionsSearchCriterias = function () {
    var self = this;
    self.journalName = ko.observable();
    self.dateIni = ko.observable();
    self.dateEnd = ko.observable();
  };

  function SubscriptionsViewModel(params) {
    var self = this;

    self.subscriptionMappings = {
      'observe': [""]
    };
    self.journalMappings = {
      'observe': [""]
    };
    self.authModel = params.authModel;
    self.currentSubscription = ko.observable({});
    self.subscriptionSelected = false;
    self.searchCriterias = new subscriptionsSearchCriterias();
    self.subscriptions = ko.observableArray([]);
    self.journalSearchCriterias = new JournalSearchCriterias();
    self.journals = ko.observableArray([]);
  }

  /**
   * Method to search subscriptions
   */
  SubscriptionsViewModel.prototype.doSearch = function () {
    var self = this;
    ajaxInterceptor.sendAjax('GET', ko.toJS(this.searchCriterias),
      'application/json', 'json', 'subscription/search',
      function (data) {
        komapping.fromJS(data, self.subscriptionMappings, self.subscriptions);
      });
  };
  
   /**
   * Method to search journals
   */
  SubscriptionsViewModel.prototype.doSearchJournals = function () {
    var self = this;
    ajaxInterceptor.sendAjax('GET', ko.toJS(this.journalSearchCriterias),
      'application/json', 'json', 'journal/search',
      function (data) {
        komapping.fromJS(data, self.journalMappings, self.journals);
      });
  };
  
//
//  /**
//   * Method to select the journal to edit
//   * @param {Journal} journal
//   */
//  SubscriptionsViewModel.prototype.selectJournalToEdit = function (journal) {
//    var self = this;
//    self.currentSubscription(ko.toJS(journal));//copying to avoid changes directly in the object
//    self.subscriptionSelected = true;
//  };
//
//  /**
//   * Method to select the journal to delete
//   * @param {Journal} journal
//   */
//  SubscriptionsViewModel.prototype.selectJournalToDelete = function (journal) {
//    var self = this;
//    self.currentSubscription(journal);
//    self.subscriptionSelected = true;
//  };
//
//  /**
//   * Method to save the changes on an existing journal or save a new journal
//   */
//  SubscriptionsViewModel.prototype.saveJournal = function () {
//    var self = this;
//    if (!self.subscriptionSelected) {
//      ajaxInterceptor.sendAjax('POST', ko.toJSON(self.currentSubscription()),
//        'application/json', 'json', 'journal/add',
//        function (data) {
//          self.resetSelection();
//          $('#edit').modal('hide');
//          self.doSearch();
//        });
//    } else {
//      var journalId = self.currentSubscription().idJournal;
//      ajaxInterceptor.sendAjax('PUT', ko.toJSON(self.currentSubscription()),
//        'application/json', 'json', 'journal/' + journalId,
//        function (updatedJournal) {
//          var currentUpdated;
//          komapping.fromJS(updatedJournal, self.subscriptionMappings, currentUpdated);
//          self.journals.replace(self.currentSubscription, currentUpdated);
//          self.resetSelection();
//          $('#edit').modal('hide');
//          self.doSearch();
//        });
//    }
//  };
//
//  /**
//   * Method to delete the selected journal
//   */
//  SubscriptionsViewModel.prototype.deleteJournal = function () {
//    var self = this;
//    var journalId = self.currentSubscription().idJournal;
//    if (self.subscriptionSelected) {
//      ajaxInterceptor.sendAjax('DELETE', null,
//        'application/json', 'json', 'journal/' + journalId,
//        function () {
//          self.journals.remove(self.currentSubscription());
//          self.resetSelection();
//          $('#delete').modal('hide');
//        });
//    }
//  };
//
//  /**
//   * Method to open the new journal modal cleaning previous data
//   */
//  SubscriptionsViewModel.prototype.openNewJournalModal = function () {
//    var self = this;
//    self.resetSelection();
//    self.modalTitle("New journal");
//    $('#edit').modal('show');
//  };
//
//  /**
//   * Method to open the edito journal modal cleaning previous data
//   * @param {type} journal
//   * @returns {undefined}
//   */
//  SubscriptionsViewModel.prototype.openEditJournalModal = function (journal) {
//    var self = this;
//    self.modalTitle("Edit the journal");
//    self.selectJournalToEdit(journal);
//    $('#edit').modal('show');
//  };
//
//  /**
//   * Method to reset the selection
//   */
//  SubscriptionsViewModel.prototype.resetSelection = function () {
//    var self = this;
//    self.currentSubscription({});
//    self.subscriptionSelected = false;
//  };

  return {viewModel: SubscriptionsViewModel, template: supbscriptionsTemplate};

});
