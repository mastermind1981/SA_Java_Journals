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
    self.journalsToSubscribe = ko.observableArray([]);
    self.currentJournal = ko.observable({});
    self.journalSelected = false;
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
      'application/json', 'json', 'journal/searchforsubscription',
      function (data) {
        komapping.fromJS(data, self.journalMappings, self.journalsToSubscribe);
      });
  };

  /**
   * Method to select the journal to subscribe
   * @param {Journal} journal
   */
  SubscriptionsViewModel.prototype.selectJournalToSubscribe = function (journal) {
    var self = this;
    self.currentJournal(journal);
    self.journalSelected = true;
  };

  SubscriptionsViewModel.prototype.subscribeToJournal = function () {
    var self = this;
    var journalId = self.currentJournal().idJournal;
    if (self.journalSelected) {
      ajaxInterceptor.sendAjax('POST', null,
        'application/json', 'json', 'subscription/subscribe/' + journalId,
        function () {
          self.journalsToSubscribe.remove(self.currentJournal());
          self.resetJournalToSubscribeSelection();
          self.doSearch();
          $('#subscribeJournal').modal('hide');
        });
    }
  };

  /**
   * Method to reset the selection of a journal
   */
  SubscriptionsViewModel.prototype.resetJournalToSubscribeSelection = function () {
    var self = this;
    self.currentJournal({});
    self.journalSelected = false;
  };

  /**
   * Method to select the subscription to delete
   * @param {JournalSubscription} JournalSubscription
   */
  SubscriptionsViewModel.prototype.selectSubscriptionToDelete = function (JournalSubscription) {
    var self = this;
    self.currentSubscription(JournalSubscription);
    self.subscriptionSelected = true;
  };

  SubscriptionsViewModel.prototype.deleteSubscription = function () {
    var self = this;
    var journalSubscriptionId = self.currentSubscription().idJournalSubscription;
    if (self.subscriptionSelected) {
      ajaxInterceptor.sendAjax('DELETE', null,
        'application/json', 'json', 'subscription/unsubscribe/' + journalSubscriptionId,
        function () {
          self.subscriptions.remove(self.currentSubscription());
          self.resetSubscriptionToDelete();
          $('#unsubscribeJournal').modal('hide');
        });
    }
  };

  /**
   * Method to reset the selection of a subscription
   */
  SubscriptionsViewModel.prototype.resetSubscriptionToDelete = function () {
    var self = this;
    self.currentSubscription({});
    self.subscriptionSelected = false;
  };

  return {viewModel: SubscriptionsViewModel, template: supbscriptionsTemplate};

});
