package com.josegranados.sajavajournals.authentication.query;

import com.josegranados.sajavajournals.user.model.User;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * SA_Java_Journals
 *
 * @author jose - 04.07.2016
 * @Title: AuthenticationQueryBean
 * @Description: description
 *
 * Changes History
 */
@Stateless
public class AuthenticationQueryBean {

	@PersistenceContext(unitName = "SA_Java_JournalsPU")
	private EntityManager em;

	/**
	 * Method to get a user by name and password
	 *
	 * @param userName
	 * @param password
	 * @return the user or null
	 */
	public User getUser(final String userName, final String password) {
		Query q = em.createQuery("SELECT u FROM User u Where u.userName = :userName and u.userPassword = :password");
		q.setParameter("userName", userName);
		q.setParameter("password", password);
		List<User> users = q.getResultList();
		if (users.size() > 0) {
			return users.get(0);
		} else {
			return null;
		}
	}

	/**
	 * Method to get the user identified by its userNane
	 * @param userName
	 * @return 
	 */
	public User getUser(final String userName) {
		Query q = em.createQuery("SELECT u FROM User u Where u.userName = :userName");
		q.setParameter("userName", userName);
		List<User> users = q.getResultList();
		if (users.size() > 0) {
			return users.get(0);
		} else {
			return null;
		}
	}

}
