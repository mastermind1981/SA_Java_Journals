package com.josegranados.sajavajournals.journal.resource;

import com.josegranados.sajavajournals.journal.model.JournalPublication;
import com.josegranados.sajavajournals.journal.query.JournalQueryBean;
import com.josegranados.sajavajournals.journal.service.JournalService;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.SecurityContext;
import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

/**
 * SA_Java_Journals
 *
 * @author jose - 03.07.2016
 * @Title: JournalPublicationResource
 * @Description: description
 *
 * Changes History
 */
@Path("journalpublication")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Stateless
@RolesAllowed("PUBLISHER")
public class JournalPublicationResource {

	private static final Logger LOG = Logger.getLogger(JournalPublicationResource.class.getName());

	@EJB
	JournalQueryBean journalQueryBean;
	@EJB
	JournalService journalServiceBean;

	@Context
	SecurityContext securityContext;

	@PUT
	@Path("{id}")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public JournalPublication updateJournalPublication(@PathParam("id") Integer id,
			@FormDataParam("file") InputStream fileInputStream,
			@FormDataParam("file") FormDataContentDisposition cdh,
			@FormDataParam("journalPublication") FormDataBodyPart journalPublicationJSON) {
		journalPublicationJSON.setMediaType(MediaType.APPLICATION_JSON_TYPE);
		JournalPublication journalPublication = journalPublicationJSON.getEntityAs(JournalPublication.class);
		try {
			return journalServiceBean.updateJournalPublication(journalPublication, fileInputStream, cdh.getFileName());
		} catch (IOException ex) {
			LOG.log(Level.SEVERE, null, ex);
			throw new WebApplicationException(Response.Status.BAD_REQUEST);
		}
	}

	@POST
	@Path("journal/{idJournal}/add")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public JournalPublication addJournalPublicationWithFile(
			@PathParam("idJournal") Integer idJournal,
			@FormDataParam("file") InputStream fileInputStream,
			@FormDataParam("file") FormDataContentDisposition cdh,
			@FormDataParam("journalPublication") FormDataBodyPart journalPublicationJSON) {
		try {
			journalPublicationJSON.setMediaType(MediaType.APPLICATION_JSON_TYPE);
			JournalPublication journalPublication = journalPublicationJSON.getEntityAs(JournalPublication.class);
			return journalServiceBean.createJournalPublication(journalPublication, idJournal, fileInputStream, cdh.getFileName());
		} catch (IOException ex) {
			LOG.log(Level.SEVERE, null, ex);
			throw new WebApplicationException(Response.Status.BAD_REQUEST);
		}
	}

	@GET
	@Path("search")
	@RolesAllowed({"PUBLISHER", "PUBLIC"})
	public List<JournalPublication> searchJournalPublications(@QueryParam("idJournal") Integer idJournal, @QueryParam("dateIni") String dateIniStr, @QueryParam("dateEnd") String dateEndStr) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date dateIni;
		Date dateEnd;
		try {
			if (dateIniStr != null) {
				dateIni = sdf.parse(dateIniStr);
			} else {
				dateIni = null;
			}
		} catch (ParseException e) {
			dateIni = null;
		}

		try {
			if (dateEndStr != null) {
				dateEnd = sdf.parse(dateEndStr);
			} else {
				dateEnd = null;
			}
		} catch (ParseException e) {
			dateEnd = null;
		}
		return journalQueryBean.searchJournalPublications(idJournal, dateIni, dateEnd, null);
	}

	@DELETE
	@Path("{id}")
	public void remove(@PathParam("id") Integer id) {
		journalServiceBean.deleteJournalPublication(id);
	}

	@GET
	@Path("{id}")
	@RolesAllowed({"PUBLISHER", "PUBLIC"})
	public JournalPublication find(@PathParam("id") Integer id) {
		return journalQueryBean.getJournalPublicationById(id);
	}

	@GET
	@Path("pdf/{id}")
	@RolesAllowed({"PUBLISHER", "PUBLIC"})
	public Response downloadPublicationFile(@PathParam("id") Integer id) {
		JournalPublication journalPublication = journalQueryBean.getJournalPublicationById(id);
		if (journalPublication != null && journalPublication.getContent() != null && journalPublication.getFileName() != null
				&& !journalPublication.getFileName().trim().isEmpty()) {
			ResponseBuilder response = Response.ok(journalPublication.getContent())
					.header("Content-Disposition", "attachment; filename=\"" + journalPublication.getFileName() + "\"")
					.header("Content-Type", "application/pdf")
					.header("Content-Transfer-Encoding", "binary");
			return response.build();
		} else {
			throw new WebApplicationException(Response.Status.BAD_REQUEST);
		}
	}
}
