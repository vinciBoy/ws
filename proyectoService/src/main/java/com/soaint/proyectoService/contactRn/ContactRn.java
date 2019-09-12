package com.soaint.proyectoService.contactRn;

public class ContactRn {

	private Name name;
	private Emails emails;
	
	public ContactRn(Name name, Emails emails) {
		super();
		this.name = name;
		this.emails = emails;
	}

	public ContactRn() {}

	public Name getName() {return name;}

	public void setName(Name name) {this.name = name;}

	public Emails getEmails() {return emails;}

	public void setEmails(Emails emails) {this.emails = emails;}
}