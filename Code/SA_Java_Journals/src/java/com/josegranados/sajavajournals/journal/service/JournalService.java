package com.josegranados.sajavajournals.journal.service;

import com.josegranados.sajavajournals.authentication.service.AuthenticationService;
import com.josegranados.sajavajournals.journal.model.Journal;
import com.josegranados.sajavajournals.journal.model.JournalPublication;
import com.josegranados.sajavajournals.user.model.User;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * SA_Java_Journals
 *
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
	 *
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
	 *
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
	 *
	 * @param id
	 */
	public void deleteJournal(Integer id) {
		//journalPublications removed in cascade also
		em.remove(em.find(Journal.class, id));
	}

	/**
	 * Method do create a new journal publication
	 *
	 * @param newJournalPublication
	 * @param idJournal
	 * @param fileInputStream
	 * @param fileName
	 * @return the created journal publication
	 * @throws java.io.IOException
	 */
	public JournalPublication createJournalPublication(final JournalPublication newJournalPublication, final Integer idJournal, final InputStream fileInputStream, final String fileName) throws IOException {
		User creator = authenticationService.getAuthenticatedUser();
		Journal parentJournal = em.find(Journal.class, idJournal);
		newJournalPublication.setJournal(parentJournal);
		newJournalPublication.setPublisherProfile(creator.getProfile());
		newJournalPublication.setPublicationDate(new Date());
		newJournalPublication.setFileName(fileName);
		newJournalPublication.setContent(toByteArray(fileInputStream));
		parentJournal.getJournalPublicationsCollection().add(newJournalPublication);
		em.flush();
		return newJournalPublication;
	}

	/**
	 * Method to update de information of a journal publication
	 *
	 * @param journalPublicationValues
	 * @param fileInputStream
	 * @param fileName
	 * @return the updated journal publication
	 * @throws java.io.IOException
	 */
	public JournalPublication updateJournalPublication(JournalPublication journalPublicationValues, final InputStream fileInputStream, final String fileName) throws IOException {
		JournalPublication journalPublication = em.find(JournalPublication.class, journalPublicationValues.getIdJournalPublication());
		journalPublication.setDescription(journalPublicationValues.getDescription());
		if (fileInputStream != null && fileName != null && !fileName.trim().isEmpty()) {
			journalPublication.setFileName(fileName);
			journalPublication.setContent(toByteArray(fileInputStream));
		}
		return journalPublication;
	}

	/**
	 * Method to delete the journal publication
	 *
	 * @param id
	 */
	public void deleteJournalPublication(Integer id) {
		JournalPublication journalPublication = em.find(JournalPublication.class, id);
		Journal journalParent = journalPublication.getJournal();
		journalParent.getJournalPublicationsCollection().remove(journalPublication);
		em.merge(journalParent);
	}

	/**
	 * Method to convert the file stream in byte array
	 *
	 * @param input
	 * @return
	 * @throws IOException
	 */
	private byte[] toByteArray(InputStream input) throws IOException {
		byte[] buffer = new byte[1024];
		int bytesRead;
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		while ((bytesRead = input.read(buffer)) != -1) {
			output.write(buffer, 0, bytesRead);
		}
		return output.toByteArray();
	}
}
