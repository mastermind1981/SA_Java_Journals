/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.josegranados.sajavajournals.journal.resource;

import com.josegranados.sajavajournals.journal.model.Journal;
import com.josegranados.sajavajournals.journal.query.JournalQueryBean;
import com.josegranados.sajavajournals.journal.service.JournalService;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

/**
 * REST Web Service
 *
 * @author jose
 */
@Path("journal")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Stateless
@RolesAllowed("PUBLISHER")
public class JournalResource {

	@Context
	private UriInfo context;
	
	@EJB
	JournalQueryBean journalQueryBean;
	@EJB
	JournalService journalServiceBean;
	
	@PUT
    @Path("{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public void updateJournal(@PathParam("id") Integer id, Journal journal) {
		journalServiceBean.updateJournal(journal);
	}
	
	@POST
    @Path("/new/{id}")
	public void addJournal(@PathParam("id") Integer id, Journal newJournal) {
		journalServiceBean.createJournal(newJournal);
	}

	@GET
    @Path("searchjournals")
	public List<Journal> searchJournals(@QueryParam("journalName") String journalName, @QueryParam("tags") String tags, @QueryParam("ownerProfile") Integer ownerProfile) {
		return journalQueryBean.searchJournals(journalName, tags, ownerProfile);
	}
}
