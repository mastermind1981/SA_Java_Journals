package com.josegranados.sajavajournals.conf;

import java.util.Set;
import javax.ws.rs.core.Application;

/**
 * SA_Java_Journals
 * @author jose - 02.07.2016 
 * @Title: ApplicationConfig
 * @Description: description
 *
 * Changes History
 */
@javax.ws.rs.ApplicationPath("rest")
public class ApplicationConfig extends Application {

	@Override
	public Set<Class<?>> getClasses() {
		Set<Class<?>> resources = new java.util.HashSet<>();
		addRestResourceClasses(resources);
		return resources;
	}

	/**
	 * Register the resources
	 */
	private void addRestResourceClasses(Set<Class<?>> resources) {
		resources.add(com.josegranados.sajavajournals.authentication.resource.AuthenticationResource.class);
		resources.add(com.josegranados.sajavajournals.conf.CrossOriginResourceSharingFilter.class);
		resources.add(com.josegranados.sajavajournals.journal.resource.JournalPublicationResource.class);
		resources.add(com.josegranados.sajavajournals.journal.resource.JournalResource.class);
		resources.add(com.josegranados.sajavajournals.subscription.resource.SubscriptionResource.class);
	}

}