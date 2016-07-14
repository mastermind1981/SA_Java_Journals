package com.josegranados.sajavajournals.subscription.model;

import com.josegranados.sajavajournals.journal.model.Journal;
import com.josegranados.sajavajournals.user.model.User;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-07-14T13:27:19")
@StaticMetamodel(JournalSubscription.class)
public class JournalSubscription_ { 

    public static volatile SingularAttribute<JournalSubscription, Journal> journal;
    public static volatile SingularAttribute<JournalSubscription, Integer> idJournalSubscription;
    public static volatile SingularAttribute<JournalSubscription, Date> subscriptionDate;
    public static volatile SingularAttribute<JournalSubscription, User> user;

}