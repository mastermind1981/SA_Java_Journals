package com.josegranados.sajavajournals.authentication.service;

import com.josegranados.sajavajournals.authentication.query.AuthenticationQueryBean;
import com.josegranados.sajavajournals.authentication.model.AuthenticationModel;
import com.josegranados.sajavajournals.user.model.User;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;
import org.apache.xml.security.utils.Base64;

/**
 * SA_Java_Journals
 * @author jose - 04.07.2016 
 * @Title: AuthenticationService
 * @Description: description
 *
 * Changes History
 */
@Stateless
public class AuthenticationService {

    @EJB
	AuthenticationQueryBean authenticationQueryBean;
	@Context
    SecurityContext securityContext;
	/**
	 * Method to get a user by name and password
	 * @param userName 
	 * @param password 
	 * @return the user or null
	 */
	public AuthenticationModel authenticateUser(final String userName, final String password) {
		String token = userName + ":" + password;
		token = Base64.encode(token.getBytes());
		AuthenticationModel authModel = new AuthenticationModel(token, false);
		User user = authenticationQueryBean.getUser(userName, password);
		if (user != null) {
			authModel.setSuccess(true);
			authModel.setRole(user.getUserRole());
			authModel.setUserName(user.getUserName());
		}
		return authModel;
	}
	
	/**
	 * Method to get the authenticated user using the securitycontext and the user principal
	 * @return 
	 */
	public User getAuthenticatedUser() {
		String userName = securityContext.getUserPrincipal().getName();
		return authenticationQueryBean.getUser(userName);
	}

}
