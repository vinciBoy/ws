package com.soaint.proyectoService.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.soaint.proyectoService.contactOSC.ContactOSC;
import com.soaint.proyectoService.contactOSC.Lead;
import com.soaint.proyectoService.contacto.Contact;
import com.soaint.proyectoService.metodosConexion.Metodos;
import com.soaint.proyectoService.security.PropertiesReader;

@Service
public class ServiceSalesCloud {

	Contact contacto = new Contact();
	ContactOSC contactOSC = new ContactOSC();
	Lead lead = new Lead();
	String mensaje;
	
	String urlSalesCloud = PropertiesReader.getUrlSalesCloud();
	String urlSalesCloudApi = PropertiesReader.getUrlSalesCloudApi();
	String urlSalesCloudMail = PropertiesReader.getUrlSalesCloudMail();
	String urlSalesCloudLeed = PropertiesReader.getUrlSalesCloudLeed();
	String urlSalesCloudLeedQuery = PropertiesReader.getUrlSalesCloudLeedQuery();
	String urlSalesCloudLeddQueryMail = PropertiesReader.getUrlSalesCloudLeedQueryEmail();
	String clave = PropertiesReader.getClaveSalesCloud();
	String usuario = PropertiesReader.getUsuarioSalesCloud();

//--------------------------------------------Buscar contacto por email en Sales Cloud--------------------------------------------
	public Boolean buscarEmailSalesCloud(String email) throws Exception {

		try {
			Metodos.conexionEmail(email, urlSalesCloud + urlSalesCloudApi + urlSalesCloudMail);
			Metodos.conexionMetodo("GET");
			Metodos.Autenticador(clave, usuario);

			// sacar el parametro que esta dentro de un array
			JSONObject jsonObject = new JSONObject(Metodos.recorrerJson());
			JSONArray items = (JSONArray) jsonObject.get("items");
			contacto.setId(items.getJSONObject(0).getInt("PartyNumber"));
			contacto.setMail(email);

			Metodos.con.disconnect();

			return true;

		} catch (Exception e) {

			return false;
		}
	}

//---------------------------------------------Buscar lead por id en Sales Cloud---------------------------------------------------
	public void buscarOSCLeadPorId(long id) throws Exception {

		Metodos.conexionId(id, urlSalesCloud + urlSalesCloudLeed + urlSalesCloudLeedQuery);
		Metodos.conexionMetodo("GET");
		Metodos.Autenticador(clave, usuario);

		Metodos.con.disconnect();
		JSONObject obj = new JSONObject(Metodos.recorrerJson());//recojo el json
		JSONArray items = obj.getJSONArray("items");
		for (int i = 0; i < items.length(); i++) {//for para borrar todos los leads que existan
			eliminarLead(Long.parseLong(items.getJSONObject(i).get("LeadId").toString()));
		}
	}
	
//--------------------------------------------Buscar lead por email en Sales Cloud--------------------------------------------------
		public Boolean buscarLeadSalesCloudPorMail(String email) throws Exception {

				Metodos.conexionEmail(email, urlSalesCloud + urlSalesCloudLeed + urlSalesCloudLeddQueryMail);
				Metodos.conexionMetodo("GET");
				Metodos.Autenticador(clave, usuario);

				JSONObject obj = new JSONObject(Metodos.recorrerJson());// recojo el json
				JSONArray items = obj.getJSONArray("items");
				Metodos.con.disconnect();
				
				if(items.length() != 0) { System.out.println("existe lead"); return true; }
				else { System.out.println("no existe lead"); return false;}
			}

//--------------------------------------------Eliminar lead por id en Sales Cloud---------------------------------------------------
	public void eliminarLead(Long id) throws Exception {

		Metodos.conexionId(id, urlSalesCloud + urlSalesCloudLeed + "/");
		Metodos.conexionMetodo("DELETE");
		Metodos.Autenticador(clave, usuario);

		BufferedReader in = new BufferedReader(new InputStreamReader(Metodos.con.getInputStream()));
		in.close();

		Metodos.con.disconnect();
	}

//-----------------------------------------------Serializar el json que recojo------------------------------------------------------
	public String serializarObjecto(String jsonSend) {

		try {
			JSONObject jsonObject = new JSONObject(jsonSend);
			contactOSC.setFirstName(Metodos.parseLetras(jsonObject.getString("first")));
			contactOSC.setLastName(Metodos.parseLetras(jsonObject.getString("last")));
			contactOSC.setEmailAddress(Metodos.parseLetras(jsonObject.getString("address")));
			try {
				String contactJSON = new ObjectMapper().writeValueAsString(contactOSC);
				CrearSalesCloud(contactJSON);
			} catch (Exception e) {
				e.printStackTrace();
			}

			return mensaje;
		} catch (Exception e) {

			return mensaje;
		}
	}

//-----------------------------------------------Crear contacto en Sales Cloud------------------------------------------------------
	public void CrearSalesCloud(String json) throws Exception {

		Metodos.crearContacto(urlSalesCloud + urlSalesCloudApi, clave, usuario, json);
	}

//--------------------------------------------------Crear lead en Sales Cloud-------------------------------------------------------
	public void crearLead() throws Exception {

		lead = new Lead(contacto.getId(), contactOSC.getFirstName());
		String jsonLead = new ObjectMapper().writeValueAsString(lead);

		Metodos.crearContacto(urlSalesCloud + urlSalesCloudLeed, clave, usuario, jsonLead);
	}
	
//--------------------------------------------Eliminar contacto por id en Sales Cloud------------------------------------------------------
		public void eliminarSalesCloudPorId(long id) throws Exception {

			Metodos.eliminar(id, urlSalesCloud + urlSalesCloudApi + "/", usuario, clave);
		}
}