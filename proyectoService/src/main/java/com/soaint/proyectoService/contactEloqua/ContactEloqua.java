package com.soaint.proyectoService.contactEloqua;

public class ContactEloqua {

	private String firstName;
	private String lastName;
	private String emailAddress;
	
	public ContactEloqua(String firstName, String lastName, String emailAddress) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.emailAddress = emailAddress;
	}
	
	public ContactEloqua() {}

	public String getFirstName() {return firstName;}

	public void setFirstName(String firstName) {this.firstName = firstName;}

	public String getLastName() {return lastName;}

	public void setLastName(String lastName) {this.lastName = lastName;}

	public String getEmailAddress() {return emailAddress;}

	public void setEmailAddress(String emailAddress) {this.emailAddress = emailAddress;}
}