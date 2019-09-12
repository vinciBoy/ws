package com.soaint.proyectoService.contacto;

public class Contact {

	int id;
	String nombre;
	String apellido;
	String mail;

	public String getApellido() { return apellido; }

	public void setApellido(String apellido) { this.apellido = apellido; }

	public String getMail() { return mail; }

	public void setMail(String mail) { this.mail = mail; }

	public int getId() { return id; }

	public void setId(int id) { this.id = id; }

	public String getNombre() { return nombre; }

	public void setNombre(String nombre) { this.nombre = nombre; }

	public String toStringCrear() {
		return "- Nombre: " + nombre + "\n" + "- Apellido: " + apellido + "\n" + "- Email: " + mail + "\n";
	}
	
	public String toStringEliminar() {
		return "- Id: " + id + "\n" + "- Email: " + mail + "\n";
	}
}