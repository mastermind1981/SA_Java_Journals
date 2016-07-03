define(["knockout", "models/JournalModel", "app/app", "text!./journals-view.html", "knockout-mapping"], function (ko, Journal, APP, journalsTemplate, komapping) {

  var JournalSearchCriterias = function () {
    var self = this;
    self.name = ko.observable();
    self.tags = ko.observable();
    self.active = ko.observable();
  };

  function JournalViewModel(route) {
    var self = this;
    self.journalMappings = {
      'observe': [""]
    };
    self.journals = ko.observableArray([]);
    self.currentJournal = ko.observable({});
    self.journalSelected = false;
    self.searchCriterias = ko.observable(new JournalSearchCriterias());
    self.modalTitle = ko.observable();
  }

  /**
   * Method to search journals
   */
  JournalViewModel.prototype.doSearch = function () {
    var self = this;
    $.getJSON(APP.SERVER + APP.REST_PATH + 'journal/searchjournals',
      ko.toJS(this.searchCriterias),
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
      $.ajax({
        method: "POST",
        url: APP.SERVER + APP.REST_PATH + 'journal/add',
        data: ko.toJSON(self.currentJournal()),
        contentType: 'application/json',
        dataType: 'json'
      }).done(function () {
        self.currentJournal({});
        self.journalSelected = false;
        $('#edit').modal('hide');
      });
    } else {
      var journalId = self.currentJournal().idJournal;
      $.ajax({
        method: "PUT",
        url: APP.SERVER + APP.REST_PATH + 'journal/' + journalId,
        data: ko.toJSON(self.currentJournal()),
        contentType: 'application/json',
        dataType: 'json'
      }).done(function (updatedJournal) {
        var currentUpdated;
        komapping.fromJS(updatedJournal, self.journalMappings, currentUpdated);
        self.journals.replace(self.currentJournal, currentUpdated);
        self.currentJournal({});
        self.journalSelected = false;
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
      $.ajax({
        method: "DELETE",
        url: APP.SERVER + APP.REST_PATH + 'journal/' + journalId
      }).done(function () {
        self.journals.remove(self.currentJournal());
        self.currentJournal({});
        self.journalSelected = false;
        $('#delete').modal('hide');
      });
    }
  };

  /**
   * Method to open the new journal modal cleaning previous data
   */
  JournalViewModel.prototype.openNewJournalModal = function () {
    var self = this;
    self.currentJournal({});
    self.journalSelected = false;
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

  return {viewModel: JournalViewModel, template: journalsTemplate};

});
