/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.josegranados.sajavajournals.journal.query;

import com.josegranados.sajavajournals.journal.model.Journal;
import com.josegranados.sajavajournals.journal.model.JournalPublication;
import com.josegranados.sajavajournals.journal.model.JournalPublication_;
import com.josegranados.sajavajournals.journal.model.Journal_;
import com.josegranados.sajavajournals.user.model.Profile;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * SA_Java_Journals
 * @author jose - 02.07.2016 
 * @Title: JournalQueryBean
 * @Description: description
 *
 * Changes History
 */
@Stateless
public class JournalQueryBean {

    @PersistenceContext(unitName = "SA_Java_JournalsPU")
	private EntityManager em;
	
	/**
	 * Method to get a journal using the id
	 * @param id
	 * @return the jurnal or null
	 */
	public Journal getJournalById(final int id) {
		return em.find(Journal.class, id);
	}
	
	/**
	 * Method to search journals using name and tags. 
	 * If a parameter is null then it is not included in the search
	 * @param name
	 * @param tags
	 * @param ownerProfile 
	 * @return List with the journals
	 */
	public List<Journal> searchJournals(final String name, final String tags, final Integer ownerProfile) {
		CriteriaBuilder journalsBuilder = em.getCriteriaBuilder();
		CriteriaQuery<Journal> query = journalsBuilder.createQuery(Journal.class);
		Root<Journal> journalsRoot = query.from(Journal.class);
		query.select(journalsRoot);
		
		List<Predicate> predicateList = new ArrayList<>();

		Predicate namePredicate, tagsPredicate, ownerProfilePredicate;

		if (name != null && !(name.isEmpty())) {
			namePredicate = journalsBuilder.like(
					journalsBuilder.upper(journalsRoot.get(Journal_.name)), "%" + name.toUpperCase() + "%");
			predicateList.add(namePredicate);
		}
		
		if (tags != null && !tags.isEmpty()) {
			tagsPredicate = journalsBuilder.like(
					journalsBuilder.upper(journalsRoot.get(Journal_.tags)), "%" + tags.toUpperCase() + "%");
			predicateList.add(tagsPredicate);
		}
		
		//TODO check this
		if (ownerProfile != null && ownerProfile > 0) {
			tagsPredicate = journalsBuilder.equal(journalsRoot.get(Journal_.ownerProfile), em.find(Profile.class, ownerProfile));
			predicateList.add(tagsPredicate);
		}
		
		Predicate[] predicates = new Predicate[predicateList.size()];
		predicateList.toArray(predicates);
		query.where(predicates);
 
		return em.createQuery(query).getResultList();
	}
	
	/**
	 * Method to get a journal publication using the id
	 * @param id
	 * @return the jurnal or null
	 */
	public JournalPublication getJournalPublicationById(final int id) {
		return em.find(JournalPublication.class, id);
	}
	
	/**
	 * Method to search the journal publications of a journal
	 * @param journal
	 * @param publicationDateIni
	 * @param publicationDateEnd
	 * @param ownerProfile
	 * @return The list with the publications
	 */
	public List<JournalPublication> searchJournalPublications(final Integer journal, final Date publicationDateIni, final Date publicationDateEnd, final Integer ownerProfile) {
		CriteriaBuilder journalPublicationsBuilder = em.getCriteriaBuilder();
		CriteriaQuery<JournalPublication> query = journalPublicationsBuilder.createQuery(JournalPublication.class);
		Root<JournalPublication> journalPublicationsRoot = query.from(JournalPublication.class);
		query.select(journalPublicationsRoot);
		
		List<Predicate> predicateList = new ArrayList<>();

		Predicate journalPredicate, publicationDatePredicate, ownerProfilePredicate;

		if (journal != null && journal > 0) {
			journalPredicate = journalPublicationsBuilder.equal(journalPublicationsRoot.get(JournalPublication_.journal), em.find(Journal.class, journal));
			predicateList.add(journalPredicate);
		}
		
		if (publicationDateIni != null && publicationDateEnd != null) {
			publicationDatePredicate = journalPublicationsBuilder.between(
					journalPublicationsRoot.get(JournalPublication_.publicationDate), publicationDateIni, publicationDateEnd);
			predicateList.add(publicationDatePredicate);
		}

		//TODO check this
		if (ownerProfile != null && ownerProfile > 0) {
			ownerProfilePredicate = journalPublicationsBuilder.equal(journalPublicationsRoot.get(JournalPublication_.publisherProfile), em.find(Profile.class, ownerProfile));
			predicateList.add(ownerProfilePredicate);
		}
		
		Predicate[] predicates = new Predicate[predicateList.size()];
		predicateList.toArray(predicates);
		query.where(predicates);
 
		return em.createQuery(query).getResultList();
	}

}
