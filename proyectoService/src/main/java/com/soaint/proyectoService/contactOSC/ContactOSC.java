package com.soaint.proyectoService.contactOSC;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "PartyId",
    "PartyNumer",
    "FirstName",
    "LastName",
    "EmailAddress"
})

public class ContactOSC {
	
	@JsonProperty("PartyId")
	@JsonIgnore
	private int partyId;

	@JsonProperty("PartyNumber")
	@JsonIgnore
	private int partyNumer;
	
	@JsonProperty("FirstName")
	private String firstName;
	
	@JsonProperty("LastName")
	private String lastName;
	
	@JsonProperty("EmailAddress")
	private String emailAddress;
	
	public ContactOSC(int partyId, int partyNumer, String firstName, String lastName, String emailAddress) {
		super();
		this.partyId = partyId;
		this.partyNumer = partyNumer;
		this.firstName = firstName;
		this.lastName = lastName;
		this.emailAddress = emailAddress;
	}

	public ContactOSC() {}
	
	
	@JsonProperty("PartyId")
	@JsonIgnore
	public int getPartyId() {return partyId;}

	@JsonProperty("PartyId")
	public void setPartyId(int partyId) {this.partyId = partyId;}

	@JsonProperty("PartyNumber")
	@JsonIgnore
	public int getPartyNumer() {return partyNumer;}

	@JsonProperty("PartyNumber")
	public void setPartyNumer(int partyNumer) {this.partyNumer = partyNumer;}

	@JsonProperty("FirstName")
	public String getFirstName() {return firstName;}

	@JsonProperty("FirstName")
	public void setFirstName(String firstName) {this.firstName = firstName;}

	@JsonProperty("LastName")
	public String getLastName() {return lastName;}

	@JsonProperty("LastName")
	public void setLastName(String lastName) {this.lastName = lastName;}

	@JsonProperty("EmailAddress")
	public String getEmailAddress() {return emailAddress;}

	@JsonProperty("EmailAddress")
	public void setEmailAddress(String emailAddress) {	this.emailAddress = emailAddress;}
}