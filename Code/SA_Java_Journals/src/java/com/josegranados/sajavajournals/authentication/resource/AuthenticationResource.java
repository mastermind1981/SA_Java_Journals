package com.josegranados.sajavajournals.authentication.resource;

import com.josegranados.sajavajournals.authentication.service.AuthenticationService;
import com.josegranados.sajavajournals.authentication.model.AuthenticationModel;
import com.josegranados.sajavajournals.authentication.model.LoginData;
import javax.annotation.security.PermitAll;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * SA_Java_Journals
 * @author jose - 03.07.2016 
 * @Title: AuthenticationResource
 * @Description: description
 *
 * Changes History
 */
@Path("auth")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Stateless
@PermitAll
public class AuthenticationResource {

	@EJB
	AuthenticationService authenticationService;
	
	@POST
    @Path("login")
	public AuthenticationModel login(LoginData loginData) {
		AuthenticationModel authModel = authenticationService.authenticateUser(loginData.getUserName(), loginData.getUserPassword());
		return authModel;
	}
}