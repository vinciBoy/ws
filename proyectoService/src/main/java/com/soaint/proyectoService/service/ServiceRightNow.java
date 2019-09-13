package com.soaint.proyectoService.service;

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
	String urlRnApi = PropertiesReader.getUrlRightNowApi();
	String urlRnMail = PropertiesReader.getUrlRightNowEmail();
	String clave = PropertiesReader.getClaveRightNow();
	String usuario = PropertiesReader.getUsuarioRightNow();

//-------------------------------------------Buscar contacto por email en Right Now--------------------------------------------------
	public Boolean buscarEmailRightNow(String email) throws Exception {

		try {
			
			Metodos.conexionEmail(email, urlRn + urlRnApi + urlRnMail);
			Metodos.conexionMetodo("GET");
			Metodos.Autenticador(clave, usuario);

			// sacar el parametro que esta dentro de un array
			JSONObject jsonObject = new JSONObject(Metodos.recorrerJson());//meto el json que me devuelve el método
			JSONArray items = (JSONArray) jsonObject.get("items");
			contacto.setId(items.getJSONObject(0).getInt("id"));
			//contacto.setMail(email);

			Metodos.con.disconnect();

			return true;

		} catch (Exception e) {

			return false;
		}
	}

//-----------------------------------------------Serializar el json que recojo------------------------------------------------------
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

//------------------------------------------------Crear contacto para Right Now-----------------------------------------------------
	public void crearRightNow(String json) throws Exception {
		
		 Metodos.crearContacto(urlRn + urlRnApi, clave, usuario, json);
	}

//--------------------------------------------Eliminar contacto por id en Right Now--------------------------------------------------
	public void eliminarRightNowPorId(long id) throws Exception {

		Metodos.eliminar(id, urlRn + urlRnApi + "/", usuario, clave);
	}
}