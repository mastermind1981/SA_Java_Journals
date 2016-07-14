package com.josegranados.sajavajournals.subscription.service;

import com.josegranados.sajavajournals.authentication.service.AuthenticationService;
import com.josegranados.sajavajournals.journal.model.Journal;
import com.josegranados.sajavajournals.subscription.model.JournalSubscription;
import com.josegranados.sajavajournals.user.model.User;
import java.util.Date;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * HRMS_
 *
 * @author jose - 13.07.2016
 * @Title: SubscriptionServiceBean
 * @Description: description
 *
 * Changes History
 */
@Stateless
public class SubscriptionServiceBean {

	@PersistenceContext(unitName = "SA_Java_JournalsPU")
	private EntityManager em;

	@EJB
	AuthenticationService authenticationService;

	/**
	 * Method to subscribe to a journal
	 *
	 * @param idJournal
	 * @return the subscription created
	 */
	public JournalSubscription subscribeToJournal(Integer idJournal) {
		User creator = authenticationService.getAuthenticatedUser();
		Journal journal = em.find(Journal.class, idJournal);
		JournalSubscription journalSubscription = new JournalSubscription();
		journalSubscription.setJournal(journal);
		journalSubscription.setUser(creator);
		journalSubscription.setSubscriptionDate(new Date());
		em.persist(journalSubscription);
		return journalSubscription;
	}

	/**
	 * Method to remove the subscription to a journal
	 *
	 * @param idSubscription
	 */
	public void removeSubscription(Integer idSubscription) {
		em.remove(em.find(JournalSubscription.class, idSubscription));
	}

}
