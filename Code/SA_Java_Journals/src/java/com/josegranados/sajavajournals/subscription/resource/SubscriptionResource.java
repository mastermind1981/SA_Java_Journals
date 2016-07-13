/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.josegranados.sajavajournals.subscription.resource;

import com.josegranados.sajavajournals.subscription.model.JournalSubscription;
import com.josegranados.sajavajournals.subscription.query.SubscriptionQueryBean;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

/**
 * HRMS_
 * @author jose - 11.07.2016 
 * @Title: SubscriptionResource
 * @Description: description
 *
 * Changes History
 */
@Path("subscription")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Stateless
@RolesAllowed({"PUBLISHER", "PUBLIC"})
public class SubscriptionResource {

	@EJB
	private SubscriptionQueryBean subscriptionQueryBean;

	
	@GET
    @Path("search")
	public List<JournalSubscription> searchJournals(@QueryParam("name") String journalName, @QueryParam("dateIni") String dateIniStr, @QueryParam("dateEnd") String dateEndStr) {
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
		return subscriptionQueryBean.searchMySubscriptions(journalName, dateIni, dateEnd);
	}
}