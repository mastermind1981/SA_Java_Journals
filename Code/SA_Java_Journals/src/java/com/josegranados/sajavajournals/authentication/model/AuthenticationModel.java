/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.josegranados.sajavajournals.authentication.model;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * SA_Java_Journals
 * @author jose - 03.07.2016 
 * @Title: AuthenticationModel
 * @Description: description
 *
 * Changes History
 */
@XmlRootElement
public class AuthenticationModel  implements Serializable {
	
	private String token;
	private String userName;
	private boolean success;
	private String role;

	public AuthenticationModel() {
	}

	public AuthenticationModel(String token, boolean success) {
		this.token = token;
		this.success = success;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	
	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	
	
}