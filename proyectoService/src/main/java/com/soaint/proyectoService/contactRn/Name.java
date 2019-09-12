package com.soaint.proyectoService.contactRn;

public class Name {
	
	private String first;
	private String last;
	
	public Name(String first, String last) {
		super();
		this.first = first;
		this.last = last;
	}
	
	public Name() {}

	public String getFirst() {return first;}

	public void setFirst(String first) {this.first = first;}

	public String getLast() {return last;}

	public void setLast(String last) {this.last = last;}
}