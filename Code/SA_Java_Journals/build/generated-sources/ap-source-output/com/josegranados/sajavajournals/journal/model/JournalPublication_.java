package com.josegranados.sajavajournals.journal.model;

import com.josegranados.sajavajournals.journal.model.Journal;
import com.josegranados.sajavajournals.user.model.Profile;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-07-14T13:27:19")
@StaticMetamodel(JournalPublication.class)
public class JournalPublication_ { 

    public static volatile SingularAttribute<JournalPublication, byte[]> content;
    public static volatile SingularAttribute<JournalPublication, Journal> journal;
    public static volatile SingularAttribute<JournalPublication, Integer> idJournalPublication;
    public static volatile SingularAttribute<JournalPublication, String> description;
    public static volatile SingularAttribute<JournalPublication, Profile> publisherProfile;
    public static volatile SingularAttribute<JournalPublication, Date> publicationDate;

}