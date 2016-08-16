package com.josegranados.sajavajournal.authentication;

import com.josegranados.sajavajournals.authentication.query.AuthenticationQueryBean;
import javax.persistence.EntityManager;
import org.powermock.api.mockito.PowerMockito;
import static org.powermock.api.mockito.PowerMockito.mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * SA_Java_Journals
 *
 * @author jose - 04.07.2016
 * @Title: AuthenticationQueryBeanTest
 * @Description: description
 *
 * Changes History
 */
@PrepareForTest({AuthenticationQueryBean.class})
public class AuthenticationQueryBeanTest {

	private AuthenticationQueryBean authenticationQueryBean;
	
	@BeforeMethod
	public void setUpMethod() throws Exception {
		authenticationQueryBean = PowerMockito.spy(new AuthenticationQueryBean());
		//authenticationQueryBean.em = mock(EntityManager.class);
	}
	
	@Test
	public void testGetUserByNameAndPass() {
		Assert.assertTrue(authenticationQueryBean != null, "The bean should be created");
	}
}
