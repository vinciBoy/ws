package com.soaint.proyectoService.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.soaint.proyectoService.contactEloqua.ContactEloqua;
import com.soaint.proyectoService.contacto.Contact;
import com.soaint.proyectoService.metodosConexion.Metodos;
import com.soaint.proyectoService.security.PropertiesReader;

@Service
public class ServiceEloqua {

	Contact contacto = new Contact();
	String mensaje;

	String urlEloqua = PropertiesReader.getUrlEloqua();
	String clave = PropertiesReader.getClaveEloqua();
	String urlEloquaCreacion = PropertiesReader.getUrlEloquaCreacion();
	String urlEloquaEmail = PropertiesReader.getUrlEloquaEmail();
	String usuario = PropertiesReader.getUsuarioEloquaCreacion();

	// HTTP GET request
	public Boolean buscarEmailEloqua(String email) throws Exception {

		try {
			Metodos.conexionEmail(email, urlEloquaEmail);
			Metodos.conexionMetodo("GET");
			Metodos.Autenticador(clave, usuario);

			// sacar el parametro que esta dentro de un array
			JSONObject jsonObject = new JSONObject(Metodos.recorrerJson());
			JSONArray items = (JSONArray) jsonObject.get("elements");
			contacto.setId(items.getJSONObject(0).getInt("id"));
			contacto.setMail(email);

			Metodos.con.disconnect();

			return true;

		} catch (Exception e) {

			return false;
		}
	}

	public String serializarObjecto(String jsonSend) {

		try {

			JSONObject jsonObject = new JSONObject(jsonSend);
			ContactEloqua contactEloqua = new ContactEloqua();
			contactEloqua.setFirstName(Metodos.parseLetras(jsonObject.getString("first")));
			contactEloqua.setLastName(Metodos.parseLetras(jsonObject.getString("last")));
			contactEloqua.setEmailAddress(Metodos.parseLetras(jsonObject.getString("address")));
			try {
				String contactJSON = new ObjectMapper().writeValueAsString(contactEloqua);
				crearEloqua(contactJSON);
			} catch (Exception e) {
				e.printStackTrace();
			}

			return mensaje;

		} catch (Exception e) {

			return mensaje;
		}
	}

	// Crear Usuario de Eloqua
	public void crearEloqua(String json) throws Exception {

		Metodos.crearContacto(urlEloquaCreacion, clave, usuario, json);
	}

	// HTTP POST request
	public void eliminar(long id) throws Exception {

		Metodos.conexionId(id, urlEloqua);
		Metodos.conexionMetodo("DELETE");
		Metodos.Autenticador(clave, usuario);

		BufferedReader in = new BufferedReader(new InputStreamReader(Metodos.con.getInputStream()));
		in.close();

		Metodos.con.disconnect();
	}
}