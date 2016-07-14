package com.josegranados.sajavajournals.journal.model;

import com.josegranados.sajavajournals.journal.model.JournalPublication;
import com.josegranados.sajavajournals.user.model.Profile;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-07-14T13:27:19")
@StaticMetamodel(Journal.class)
public class Journal_ { 

    public static volatile SingularAttribute<Journal, String> tags;
    public static volatile SingularAttribute<Journal, String> name;
    public static volatile SingularAttribute<Journal, String> about;
    public static volatile SingularAttribute<Journal, byte[]> image;
    public static volatile SingularAttribute<Journal, Boolean> active;
    public static volatile CollectionAttribute<Journal, JournalPublication> journalPublicationsCollection;
    public static volatile SingularAttribute<Journal, Profile> ownerProfile;
    public static volatile SingularAttribute<Journal, Integer> idJournal;

}