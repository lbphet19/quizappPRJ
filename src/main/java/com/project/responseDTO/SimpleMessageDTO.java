package com.project.responseDTO;

public class SimpleMessageDTO {
	private String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public SimpleMessageDTO(String message) {
		super();
		this.message = message;
	}
	
}
