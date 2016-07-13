define(["knockout", "app/app.config", "app/ajaxInterceptor", "text!./journals-view.html", "knockout-mapping"], function (ko, APP, ajaxInterceptor, journalsTemplate, komapping) {

  var JournalSearchCriterias = function () {
    var self = this;
    self.name = ko.observable();
    self.tags = ko.observable();
    self.active = ko.observable();
  };

  function JournalViewModel(params) {
    var self = this;
    self.journalMappings = {
      'observe': [""]
    };
    self.journals = ko.observableArray([]);
    self.currentJournal = ko.observable({});
    self.journalSelected = false;
    self.searchCriterias = ko.observable(new JournalSearchCriterias());
    self.modalTitle = ko.observable();
    self.authModel = params.authModel;
  }

  /**
   * Method to search journals
   */
  JournalViewModel.prototype.doSearch = function () {
    var self = this;
    ajaxInterceptor.sendAjax('GET', ko.toJS(this.searchCriterias),
      'application/json', 'json', 'journal/search',
      function (data) {
        komapping.fromJS(data, self.journalMappings, self.journals);
      });
  };

  /**
   * Method to select the journal to edit
   * @param {Journal} journal
   */
  JournalViewModel.prototype.selectJournalToEdit = function (journal) {
    var self = this;
    self.currentJournal(ko.toJS(journal));//copying to avoid changes directly in the object
    self.journalSelected = true;
  };

  /**
   * Method to select the journal to delete
   * @param {Journal} journal
   */
  JournalViewModel.prototype.selectJournalToDelete = function (journal) {
    var self = this;
    self.currentJournal(journal);
    self.journalSelected = true;
  };

  /**
   * Method to save the changes on an existing journal or save a new journal
   */
  JournalViewModel.prototype.saveJournal = function () {
    var self = this;
    if (!self.journalSelected) {
      ajaxInterceptor.sendAjax('POST', ko.toJSON(self.currentJournal()),
        'application/json', 'json', 'journal/add',
        function (data) {
          self.resetSelection();
          $('#edit').modal('hide');
          self.doSearch();
        });
    } else {
      var journalId = self.currentJournal().idJournal;
      ajaxInterceptor.sendAjax('PUT', ko.toJSON(self.currentJournal()),
        'application/json', 'json', 'journal/' + journalId,
        function (updatedJournal) {
          var currentUpdated;
          komapping.fromJS(updatedJournal, self.journalMappings, currentUpdated);
          self.journals.replace(self.currentJournal, currentUpdated);
          self.resetSelection();
          $('#edit').modal('hide');
          self.doSearch();
        });
    }
  };

  /**
   * Method to delete the selected journal
   */
  JournalViewModel.prototype.deleteJournal = function () {
    var self = this;
    var journalId = self.currentJournal().idJournal;
    if (self.journalSelected) {
      ajaxInterceptor.sendAjax('DELETE', null,
        'application/json', 'json', 'journal/' + journalId,
        function () {
          self.journals.remove(self.currentJournal());
          self.resetSelection();
          $('#delete').modal('hide');
        });
    }
  };

  /**
   * Method to open the new journal modal cleaning previous data
   */
  JournalViewModel.prototype.openNewJournalModal = function () {
    var self = this;
    self.resetSelection();
    self.modalTitle("New journal");
    $('#edit').modal('show');
  };

  /**
   * Method to open the edito journal modal cleaning previous data
   * @param {type} journal
   * @returns {undefined}
   */
  JournalViewModel.prototype.openEditJournalModal = function (journal) {
    var self = this;
    self.modalTitle("Edit the journal");
    self.selectJournalToEdit(journal);
    $('#edit').modal('show');
  };

  /**
   * Method to reset the selection
   */
  JournalViewModel.prototype.resetSelection = function () {
    var self = this;
    self.currentJournal({});
    self.journalSelected = false;
  };

  return {viewModel: JournalViewModel, template: journalsTemplate};

});
