package com.drug.notifier.model;

import java.io.Serializable;

public class JwtResponse implements Serializable {

	private static final long serialVersionUID = -8091879091924046844L;
	private final String jwttoken;
	private final String id;
	public JwtResponse(String jwttoken, String id) {
		this.jwttoken = jwttoken;
		this.id = id;
	}

	public String getToken() {
		return this.jwttoken;
	}
	public String getId(){ return this.id;}
}