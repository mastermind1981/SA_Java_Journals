package com.josegranados.sajavajournals.conf;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;

/**
 * SA_Java_Journals
 * @author jose - 02.07.2016 
 * @Title: CrossOriginResourceSharingFilter
 * @Description: description
 *
 * Changes History
 */
@Provider
public class CrossOriginResourceSharingFilter implements ContainerResponseFilter {

	@Override
	public void filter(ContainerRequestContext requestContext, ContainerResponseContext response) {
		response.getHeaders().add("Access-Control-Allow-Origin", "*");
		response.getHeaders().add("Access-Control-Allow-Methods", "OPTIONS, GET, POST, PUT, DELETE, HEAD");
		response.getHeaders().add("Access-Control-Allow-Headers", "origin, content-type, accept, X-Requested-With, Content-Type, X-Codingpedia");
		response.getHeaders().add("Access-Control-Max-Age", "1209600");
		response.getHeaders().add("Access-Control-Allow-Credentials", "true");
	}

}