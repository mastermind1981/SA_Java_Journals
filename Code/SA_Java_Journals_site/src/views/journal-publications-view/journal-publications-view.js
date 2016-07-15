define(["knockout", "app/app.config", "app/ajaxInterceptor", "text!./journal-publications-view.html", "knockout-mapping", "knockout-uploader"], function (ko, APP, ajaxInterceptor, journalPublicationsTemplate, komapping) {

  var publicationSearchCriterias = function () {
    var self = this;
    self.idJournal;
    self.dateIni = ko.observable();
    self.dateEnd = ko.observable();
  };

  function JournalPublicationsViewModel(params) {
    var self = this;
    self.publicationMappings = {
      'observe': [""]
    };
    self.downloadPublicationPath = APP.SERVER + APP.REST_PATH + "journalpublication/pdf/";
    self.authModel = params.authModel;
    self.idJournal = params.router.currentRoute().idJournal;
    self.journalPublications = ko.observableArray([]);
    self.currentJournal = ko.observable({});
    self.currentJournalPublication = ko.observable({});
    self.journalPublicationSelected = false;
    self.searchCriterias = ko.observable(new publicationSearchCriterias());

    self.modalTitle = ko.observable();

    self.searchCriterias().idJournal = self.idJournal;
    self.fileData = ko.observable({
      dataURL: ko.observable(),
      // base64String: ko.observable(),
    });
    self.getJournal();
  }
  
  JournalPublicationsViewModel.prototype.onClear = function(fileData){
    if(confirm('Are you sure?')){
      fileData.clear && fileData.clear();
    }                            
  };

  /**
   * Method to get the parent journal
   */
  JournalPublicationsViewModel.prototype.getJournal = function () {
    var self = this;
    ajaxInterceptor.sendAjax('GET', null,
      'application/json', 'json', 'journal/' + self.idJournal,
      function (data) {
        self.currentJournal(komapping.fromJS(data, {}));
      });
  };

  /**
   * Method to search journal publicationss
   */
  JournalPublicationsViewModel.prototype.doSearch = function () {
    var self = this;
    ajaxInterceptor.sendAjax('GET', ko.toJS(this.searchCriterias),
      'application/json', 'json', 'journalpublication/search',
      function (data) {
        komapping.fromJS(data, self.publicationMappings, self.journalPublications);
      });
  };

  /**
   * Method to select the journal publication to edit
   * @param {JournalPublication} journalPublication
   */
  JournalPublicationsViewModel.prototype.selectJournalPublicationToEdit = function (journalPublication) {
    var self = this;
    self.currentJournalPublication(ko.toJS(journalPublication));//copying to avoid changes directly in the object
    self.journalPublicationSelected = true;
  };

  /**
   * Method to select the journal publication to delete
   * @param {JournalPublication} journalPublication
   */
  JournalPublicationsViewModel.prototype.selectJournalPublicationToDelete = function (journalPublication) {
    var self = this;
    self.currentJournalPublication(journalPublication);
    self.journalPublicationSelected = true;
  };

  /**
   * Method to save the changes on an existing journal publication or save a new journal publication
   */
  JournalPublicationsViewModel.prototype.saveJournalPublication = function () {
    var self = this;
    if (!self.journalPublicationSelected) {
      var formData = new FormData(document.getElementById('formUpdate'));
      formData.append('journalPublication', ko.toJSON(self.currentJournalPublication()));
      ajaxInterceptor.sendAjax('POST', formData,
        false, 'json', 'journalpublication/journal/' + self.idJournal + "/add",
        function () {
          self.resetSelection();
          $('#edit').modal('hide');
          self.doSearch();
        }, null, true);
    } else {
      var idJournalPublication = self.currentJournalPublication().idJournalPublication;

      ajaxInterceptor.sendAjax('PUT', ko.toJSON(self.currentJournalPublication()),
        'application/json', 'json', 'journalpublication/' + idJournalPublication,
        function (updatedJournalPublication) {
          var currentUpdated;
          komapping.fromJS(updatedJournalPublication, self.publicationMappings, currentUpdated);
          self.journalPublications.replace(self.currentJournalPublication, currentUpdated);
          self.resetSelection();
          $('#edit').modal('hide');
          self.doSearch();
        });
    }
  };

  /**
   * Method to delete the selected journal publication
   */
  JournalPublicationsViewModel.prototype.deleteJournalPublication = function () {
    var self = this;
    var idJournalPublication = self.currentJournalPublication().idJournalPublication;
    if (self.journalPublicationSelected) {

      ajaxInterceptor.sendAjax('DELETE', null,
        'application/json', 'json', 'journalpublication/' + idJournalPublication,
        function () {
          self.journalPublications.remove(self.currentJournalPublication());
          self.resetSelection();
          $('#delete').modal('hide');
        });
    }
  };

  /**
   * Method to open the new journal publication modal cleaning previous data
   */
  JournalPublicationsViewModel.prototype.openNewJournalPublicationModal = function () {
    var self = this;
    self.resetSelection();
    self.modalTitle('New journal publication');
    $('#edit').modal('show');
  };

  /**
   * Method to open the edito journal publication modal cleaning previous data
   * @param {JournalPublication} journalPublication
   * @returns {undefined}
   */
  JournalPublicationsViewModel.prototype.openEditJournalPublicationModal = function (journalPublication) {
    var self = this;
    self.modalTitle('Edit the journal publication');
    self.selectJournalPublicationToEdit(journalPublication);
    $('#edit').modal('show');
  };

  /**
   * Method to reset the selection
   */
  JournalPublicationsViewModel.prototype.resetSelection = function () {
    var self = this;
    self.currentJournalPublication({});
    self.journalPublicationSelected = false;
    self.fileData().clear && self.fileData().clear();
  };

  return {viewModel: JournalPublicationsViewModel, template: journalPublicationsTemplate};

});
