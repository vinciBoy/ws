package com.soaint.proyectoService.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Base64;

import org.apache.http.HttpHeaders;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.soaint.proyectoService.contactOSC.ContactOSC;
import com.soaint.proyectoService.contactOSC.Lead;
import com.soaint.proyectoService.contacto.Contact;
import com.soaint.proyectoService.metodosConexion.Metodos;
import com.soaint.proyectoService.security.PropertiesReader;

import ch.qos.logback.core.pattern.parser.Parser;

@Service
public class ServiceSalesCloud {

	Contact contacto = new Contact();
	ContactOSC contactOSC = new ContactOSC();
	Lead lead = new Lead();
	String mensaje;

	String urlSalesCloud = PropertiesReader.getUrlSalesCloud();
	String clave = PropertiesReader.getClaveSalesCloud();
	String usuario = PropertiesReader.getUsuarioSalesCloud();
	String urlMailSalesCloud = PropertiesReader.getUrlMailSalesCloud();
	String urlSalesCloudCrear = PropertiesReader.getUrlSalesCloudCreacion();
	String urlSalesCloudLeed = PropertiesReader.getUrlSalesCloudLeed();
	String urlSalesCloudLeedQuery = PropertiesReader.getUrlSalesCloudLeedQuery();

	// HTTP GET request
	public Boolean buscarEmailSalesCloud(String email) throws Exception {

		try {
			Metodos.conexionEmail(email, urlMailSalesCloud);
			Metodos.conexionMetodo("GET");
			Metodos.Autenticador(clave, usuario);
			System.out.println(Metodos.con.getResponseCode()); 

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
	
	// Solicitud HTTP GET
	public void buscarOSCLeadPorId(long id) throws Exception {

		Metodos.conexionId(id, urlSalesCloudLeedQuery);
		Metodos.conexionMetodo("GET");
		Metodos.Autenticador(clave, usuario);

		BufferedReader in = new BufferedReader(new InputStreamReader(Metodos.con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		
		Metodos.con.disconnect();
		JSONObject obj = new JSONObject(response.toString());
		JSONArray items = obj.getJSONArray("items");
		for(int i=0;i<items.length();i++) {	
			System.out.println(Long.parseLong(items.getJSONObject(i).get("LeadId").toString()));
			eliminarLead(Long.parseLong(items.getJSONObject(i).get("LeadId").toString()));
			System.out.println("Olé");
		}
	}
	
	public void eliminarLead(Long id) throws Exception {

		Metodos.conexionId(id, urlSalesCloudLeed + "/");
		Metodos.conexionMetodo("DELETE");
		Metodos.Autenticador(clave, usuario);

		BufferedReader in = new BufferedReader(new
		InputStreamReader(Metodos.con.getInputStream()));
		in.close();

		Metodos.con.disconnect();
}

	public String serializarObjecto(String jsonSend) {

		try {
			JSONObject jsonObject = new JSONObject(jsonSend);
			contactOSC.setFirstName(Metodos.parseLetras(jsonObject.getString("first"))); 
			contactOSC.setLastName(Metodos.parseLetras(jsonObject.getString("last")));
			contactOSC.setEmailAddress(Metodos.parseLetras(jsonObject.getString("address")));
			try {
				String contactJSON = new ObjectMapper().writeValueAsString(contactOSC);
				CrearSalesCloud(contactJSON);
				System.out.println(contactJSON);
			} catch (Exception e) {
				e.printStackTrace();
			}

			return mensaje;
		} catch (Exception e) {

			return mensaje;
		}
	}

	// Crear contacto en SalesCloud
	public void CrearSalesCloud(String json) throws Exception {

		Metodos.crearContacto(urlSalesCloudCrear, clave, usuario, json);
	}
	
	public void crearLead() throws Exception { 
		
		lead = new Lead(contacto.getId(), contactOSC.getFirstName());
		String jsonLead = new ObjectMapper().writeValueAsString(lead);
		
		Metodos.crearContacto(urlSalesCloudLeed, clave, usuario, jsonLead);
	}

	// Solicitud HTTP POST
	public void eliminar(long id) throws Exception {

			Metodos.conexionId(id, urlSalesCloud);
			Metodos.conexionMetodo("DELETE");
			Metodos.Autenticador(clave, usuario);

			BufferedReader in = new BufferedReader(new
			InputStreamReader(Metodos.con.getInputStream()));
			in.close();

			Metodos.con.disconnect();
	}
}