/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.josegranados.sajavajournals.authentication.model;

import java.io.Serializable;

/**
 * SA_Java_Journals
 * @author jose - 04.07.2016 
 * @Title: LoginData
 * @Description: description
 *
 * Changes History
 */
public class LoginData implements Serializable {
	private String userName;
	private String userPassword;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
	
	
}