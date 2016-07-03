define(["knockout"], function (ko) {

  function Journal(name, about, tags, ownerProfile, active, id) {
    var self = this;
    self.name = ko.observable(name);
    self.about = ko.observable(about);
    self.tags = ko.observable(tags);
    self.ownerProfile = ko.observable(ownerProfile);
    self.active = ko.observable(active);
    self.idJournal = ko.observable(id);

    /*self.fullInfo = ko.pureComputed(function () {
      return self.email() + " " + self.role();
    });*/
  }
  
  return Journal;
});