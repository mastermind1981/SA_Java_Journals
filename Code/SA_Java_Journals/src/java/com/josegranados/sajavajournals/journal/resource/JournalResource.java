/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.josegranados.sajavajournals.journal.resource;

import com.josegranados.sajavajournals.journal.model.Journal;
import com.josegranados.sajavajournals.journal.query.JournalQueryBean;
import com.josegranados.sajavajournals.journal.service.JournalService;
import com.josegranados.sajavajournals.user.model.User;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
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
import javax.ws.rs.core.SecurityContext;

/**
 * REST Web Service
 *
 * @author jose
 */
@Path("journal")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Stateless
//@RolesAllowed("PUBLISHER")
public class JournalResource {

	@Context
	private UriInfo context;
	
	@EJB
	JournalQueryBean journalQueryBean;
	@EJB
	JournalService journalServiceBean;
	
	@Context
    SecurityContext securityContext;
	
	@PUT
    @Path("{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Journal updateJournal(@PathParam("id") Integer id, Journal journal) {
		return journalServiceBean.updateJournal(journal);
	}
	
	@POST
    @Path("add")
	public Journal addJournal(Journal newJournal) {
		return journalServiceBean.createJournal(newJournal);
	}

	@GET
    @Path("search")
	public List<Journal> searchJournals(@QueryParam("name") String name, @QueryParam("tags") String tags, @QueryParam("ownerProfile") Integer ownerProfile) {
		System.out.println("securityContext.getUserPrincipal() " + securityContext.getUserPrincipal());
		return journalQueryBean.searchJournals(name, tags, ownerProfile);
	}
	
	@DELETE
    @Path("{id}")
	public void remove(@PathParam("id") Integer id) {
		journalServiceBean.deleteJournal(id);
	}
	
	@GET
    @Path("{id}")
	public Journal find(@PathParam("id") Integer id) {
		return journalQueryBean.getJournalById(id);
	}
}
