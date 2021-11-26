package com.kodakro.jiki.model;

import java.io.Serializable;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HttpAuthResponse implements Serializable{
	private final boolean status;
	private final String token;
	private final User user;
	public HttpAuthResponse(boolean status, String token, User user) {
		this.status = status;
		this.token = token;
		this.user = user;
	}
	
	@Override
	public String toString() {
		ObjectMapper mapper = new ObjectMapper();
		String result="";
		try {
			result = mapper.writeValueAsString(this);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return result;
	}
}
