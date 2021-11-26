package com.kodakro.jiki.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomResponseType {
	private String message;
	
	public CustomResponseType(String message) {
		this.message = message;
	}
}
