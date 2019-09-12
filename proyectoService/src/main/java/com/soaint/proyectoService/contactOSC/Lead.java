package com.soaint.proyectoService.contactOSC;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Lead {

	@JsonProperty("ContactPartyNumber")	
	private int partyNumber;
	@JsonProperty("Name")
	private String name;
	
	public Lead(int partyNumber, String name) {
		super();
		this.partyNumber = partyNumber;
		this.name = name;
	}
	
	public Lead() {
	}

	public int getPartyNumber() {
		return partyNumber;
	}

	public void setPartyNumber(int partyNumber) {
		this.partyNumber = partyNumber;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}