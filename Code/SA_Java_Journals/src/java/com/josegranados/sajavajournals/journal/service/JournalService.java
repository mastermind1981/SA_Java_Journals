/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.josegranados.sajavajournals.journal.service;

import com.josegranados.sajavajournals.journal.model.Journal;
import com.josegranados.sajavajournals.user.model.User;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;

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
	
	@Context
    SecurityContext securityContext;
	
	public void createJournal(Journal newJournal) {
		User creator = (User) securityContext.getUserPrincipal();
		System.out.println("----------- user + " + creator);
		creator = em.find(User.class, creator.getIdUser());
		newJournal.setOwnerProfile(creator.getProfile());
		em.persist(newJournal);
		
	}
	
	public void updateJournal(Journal journal) {
		em.merge(journal);
	}
    

}
