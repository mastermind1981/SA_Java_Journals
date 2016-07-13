package com.josegranados.sajavajournals.subscription.query;

import com.josegranados.sajavajournals.authentication.service.AuthenticationService;
import com.josegranados.sajavajournals.journal.model.Journal_;
import com.josegranados.sajavajournals.subscription.model.JournalSubscription;
import com.josegranados.sajavajournals.subscription.model.JournalSubscription_;
import com.josegranados.sajavajournals.user.model.User;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * HRMS_
 *
 * @author jose - 09.07.2016
 * @Title: SubscriptionQueryBean
 * @Description: description
 *
 * Changes History
 */
@Stateless
public class SubscriptionQueryBean {

	@EJB
	private AuthenticationService authenticationService;

	@PersistenceContext(unitName = "SA_Java_JournalsPU")
	private EntityManager em;

	public List<JournalSubscription> searchMySubscriptions(final String journalName, final Date subscriptionDateIni, final Date subscriptionDateEnd) {
		CriteriaBuilder journalSubscriptionBuilder = em.getCriteriaBuilder();
		CriteriaQuery<JournalSubscription> query = journalSubscriptionBuilder.createQuery(JournalSubscription.class);
		Root<JournalSubscription> journalSubscriptionRoot = query.from(JournalSubscription.class);
		query.select(journalSubscriptionRoot);

		List<Predicate> predicateList = new ArrayList<>();

		Predicate journalPredicate, subscriptionDatePredicate, subscriberPredicate;

		if (journalName != null && !journalName.isEmpty()) {
			journalPredicate = journalSubscriptionBuilder.like(
					journalSubscriptionBuilder.upper(journalSubscriptionRoot.get(JournalSubscription_.journal).get(Journal_.name)), "%" + journalName + "%");
			predicateList.add(journalPredicate);
		}

		if (subscriptionDateIni != null && subscriptionDateEnd != null) {
			subscriptionDatePredicate = journalSubscriptionBuilder.between(
					journalSubscriptionRoot.get(JournalSubscription_.subscriptionDate), subscriptionDateIni, subscriptionDateEnd);
			predicateList.add(subscriptionDatePredicate);
		}

		//TODO check this
		User currentUser = authenticationService.getAuthenticatedUser();
		subscriberPredicate = journalSubscriptionBuilder.equal(journalSubscriptionRoot.get(JournalSubscription_.user), em.find(User.class, currentUser.getIdUser()));
		predicateList.add(subscriberPredicate);

		Predicate[] predicates = new Predicate[predicateList.size()];
		predicateList.toArray(predicates);
		query.where(predicates);

		return em.createQuery(query).getResultList();
	}

}
