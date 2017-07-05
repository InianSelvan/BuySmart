package com.buysmart.daoImpl;

import java.util.ArrayList;

public class UserPOJO {
	private String username;
	private ArrayList<UserDetailsPOJO> details;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public ArrayList<UserDetailsPOJO> getDetails() {
		return details;
	}

	public void setDetails(ArrayList<UserDetailsPOJO> details) {
		this.details = details;
	}

}
