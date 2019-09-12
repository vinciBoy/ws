package com.soaint.proyectoService.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.soaint.proyectoService.contactRn.AddressType;
import com.soaint.proyectoService.contactRn.ContactRn;
import com.soaint.proyectoService.contactRn.Emails;
import com.soaint.proyectoService.contactRn.Name;
import com.soaint.proyectoService.contacto.Contact;
import com.soaint.proyectoService.metodosConexion.Metodos;
import com.soaint.proyectoService.security.PropertiesReader;

@Service
public class ServiceRightNow<HttpClient> {

	Contact contacto = new Contact();
	String mensaje;

	String urlRn = PropertiesReader.getUrlRightNow();
	String clave = PropertiesReader.getClaveRightNow();
	String usuario = PropertiesReader.getUsuarioRightNow();
	String urlRnCrear = PropertiesReader.getUrlRightNowCreacion();

	// HTTP GET request
	public Boolean buscarEmailRightNow(String email) throws Exception {

		try {
			
			Metodos.conexionEmail(email, urlRnCrear);
			Metodos.conexionMetodo("GET");
			Metodos.Autenticador(clave, usuario);

			// sacar el parametro que esta dentro de un array
			JSONObject jsonObject = new JSONObject(Metodos.recorrerJson());//meto lo que me devuelve el método
			JSONArray items = (JSONArray) jsonObject.get("items");
			contacto.setId(items.getJSONObject(0).getInt("id"));
			contacto.setMail(email);

			System.out.println(Metodos.con.getResponseCode());
			Metodos.con.disconnect();

			return true;

		} catch (Exception e) {

			return false;
		}
	}

	public String serializarObjecto(String jsonSend) {

		JSONObject jsonObject = new JSONObject(jsonSend);
		ContactRn contactRn = new ContactRn();
		contactRn.setName(new Name((Metodos.parseLetras(jsonObject.getString("first"))), (Metodos.parseLetras(jsonObject.getString("last")))));
		contactRn.setEmails(new Emails((Metodos.parseLetras(jsonObject.getString("address"))), new AddressType(0)));
		try {
			String contactJSON = new ObjectMapper().writeValueAsString(contactRn);
			crearRightNow(contactJSON);
			return mensaje;

		} catch (Exception e) {
			return mensaje;
		}
	}

	// HTTP POST request
	public void crearRightNow(String json) throws Exception {
		
		 Metodos.crearContacto(urlRn, clave, usuario, json);
	}

	// HTTP POST request
	public void eliminar(long id) throws Exception {

			Metodos.conexionId(id, urlRn);
			Metodos.Autenticador(clave, usuario);
			Metodos.conexionMetodo("DELETE");

			BufferedReader in = new BufferedReader(new
			InputStreamReader(Metodos.con.getInputStream()));
			in.close();

			Metodos.con.disconnect();
	}
}