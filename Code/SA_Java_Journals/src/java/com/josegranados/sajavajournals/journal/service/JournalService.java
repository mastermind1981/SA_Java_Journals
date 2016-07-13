package com.josegranados.sajavajournals.journal.service;

import com.josegranados.sajavajournals.authentication.service.AuthenticationService;
import com.josegranados.sajavajournals.journal.model.Journal;
import com.josegranados.sajavajournals.journal.model.JournalPublication;
import com.josegranados.sajavajournals.user.model.User;
import java.util.Date;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * SA_Java_Journals
 * @author jose - 02.07.2016 
 * @Title: JournalService
 * @Description: description
 *
 * Changes History
 */
@Stateless
public class JournalService {

	private static final Logger LOG = Logger.getLogger(JournalService.class.getName());
	
	@PersistenceContext(unitName = "SA_Java_JournalsPU")
	private EntityManager em;
	
	@EJB
	AuthenticationService authenticationService;
	
	/**
	 * Method to create a new journal
	 * @param newJournal
	 * @return the created journal
	 */
	public Journal createJournal(Journal newJournal) {
		User creator = authenticationService.getAuthenticatedUser();
		newJournal.setOwnerProfile(creator.getProfile());
		newJournal.setActive(true);
		em.persist(newJournal);
		return newJournal;
	}
	
	/**
	 * Metod to update the information of a journal
	 * @param journalValues
	 * @return the updated journal
	 */
	public Journal updateJournal(Journal journalValues) {
		Journal journal = em.find(Journal.class, journalValues.getIdJournal());
		journal.setAbout(journalValues.getAbout());
		journal.setActive(journalValues.isActive());
		journal.setName(journalValues.getName());
		journal.setTags(journalValues.getTags());
		return journal;
	}
    
	/**
	 * Method to delete the journal and its publications
	 * @param id 
	 */
	public void deleteJournal(Integer id) {
		//journalPublications removed in cascade also
		em.remove(em.find(Journal.class, id));
	}
	
	/**
	 * Method do create a new journal publication
	 * @param newJournalPublication
	 * @return the created journal publication
	 */
	public JournalPublication createJournalPublication(JournalPublication newJournalPublication) {
		User creator = authenticationService.getAuthenticatedUser();
		Journal parentJournal = em.find(Journal.class, newJournalPublication.getIdJournalPublication());
		newJournalPublication.setJournal(parentJournal);
		newJournalPublication.setPublisherProfile(creator.getProfile());
		newJournalPublication.setPublicationDate(new Date());
		parentJournal.getJournalPublicationsCollection().add(newJournalPublication);
		em.flush();
		return newJournalPublication;
	}
	
	/**
	 * Method to update de information of a journal publication
	 * @param journalPublicationValues
	 * @return the updated journal publication
	 */
	public JournalPublication updateJournalPublication(JournalPublication journalPublicationValues) {
		JournalPublication journalPublication = em.find(JournalPublication.class, journalPublicationValues.getIdJournalPublication());
		journalPublication.setDescription(journalPublicationValues.getDescription());
		return journalPublication;
	}
    
	/**
	 * Method to delete the journal publication
	 * @param id 
	 */
	public void deleteJournalPublication(Integer id) {
		JournalPublication journalPublication = em.find(JournalPublication.class, id);
		Journal journalParent = journalPublication.getJournal();
		journalParent.getJournalPublicationsCollection().remove(journalPublication);
		em.merge(journalParent);
	}

}
