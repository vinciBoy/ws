package com.soaint.proyectoService.service;

import java.io.IOException;

import org.json.JSONObject;

import com.soaint.proyectoService.contacto.Contact;
import com.soaint.proyectoService.metodosConexion.Metodos;

public class ServiceTodos {

	String mensajeRightNow, mensajeEloqua, mensajeSalesCloud, mensajeSalesCloudLead, mensajeCaracteres;
	Contact contactoTodos = new Contact();
	
	ServiceRightNow rn = new ServiceRightNow();
	ServiceEloqua eloqua = new ServiceEloqua();
	ServiceSalesCloud osc = new ServiceSalesCloud();
	
//------------------------------------Eliminar Right Now---------------------------------------
	public void eliminarRn(String email) throws Exception {
		
		if (rn.buscarEmailRightNow(email)) {
			rn.eliminar(rn.contacto.getId());
			mensajeRightNow = "---Datos del contacto en Right Now---\n\n" + rn.contacto.toStringEliminar()
			+ "\nEl contacto fué eliminado satisfatoriamente de Right Now\n";
		} else {
			mensajeRightNow = "El contacto no existe en Right Now";
		}
	}
	
	public void eliminarElo(String email) throws Exception {

		if (eloqua.buscarEmailEloqua(email)) {
			eloqua.eliminar(eloqua.contacto.getId());
			mensajeEloqua = "---Datos del contacto de Eloqua---\n\n" + eloqua.contacto.toStringEliminar()
			+ "\nEl contacto fué borrado satisfatoriamente de Eloqua\n";
		} else {
			mensajeEloqua = "El contacto no existe en Eloqua";
		}
	}
	
	public void eliminarOSCLead(String email) throws Exception {
		
		if (osc.buscarEmailSalesCloud(email)) {
			System.out.println(email);
			System.out.println(osc.contacto.getId());
			osc.buscarOSCLeadPorId(osc.contacto.getId()); 			
			mensajeSalesCloudLead = "\nLos leads del contacto fueron borrados satisfatoriamente Oracle Sales Cloud";
		} else {
			mensajeSalesCloudLead = "No existen leads en este contacto de Sales Cloud";
		}
	}
	
	public void eliminarOSC(String email) throws Exception {
		
		if (osc.buscarEmailSalesCloud(email)) {
			osc.eliminar(osc.contacto.getId());
			mensajeSalesCloud = "---Datos del contacto---\n\n" + osc.contacto.toStringEliminar()
			+ "\nEl contacto fué borrado satisfatoriamente Oracle Sales Cloud";
		} else {
			mensajeSalesCloud = "El contacto no existe en Sales Cloud";
		}
	}

	// Método para eliminar todos
	public String eliminarTodos(String email) {

		try {
			
			eliminarRn(email);
			eliminarElo(email);
			eliminarOSCLead(email);
			eliminarOSC(email);
			
			return mensajeRightNow + "\n" + mensajeEloqua + "\n" + mensajeSalesCloudLead + "\n" + mensajeSalesCloud;

		} catch (Exception e) {

			return "Error en la eliminación de los usuarios";
		}
	}
	
	public String comprobacionCaracteres(String api) throws IOException {
		
		if(Metodos.con.getResponseCode() == 200) {
			mensajeCaracteres = "Fué creado satisfatoriamente en " + api + "  😎🍻.\n";
		}
		else {
			mensajeCaracteres = "Error en el email de " + api + ", pude que contenga caracteres extraños";
		}
		
		return mensajeCaracteres;
	}
	
	public void creacionRn(String json) throws Exception {
		
		if (!rn.buscarEmailRightNow(contactoTodos.getMail())){
			rn.serializarObjecto(json);
			mensajeRightNow = comprobacionCaracteres("Right Now");

		} else {
			mensajeRightNow = "¡¡¡¡Error en la creación del contacto en Right Now puede que el email ya exista!!!!";
		}
	}
	
	public void creacionElo(String json) throws Exception {
		
		if (!eloqua.buscarEmailEloqua(contactoTodos.getMail())) {
			eloqua.serializarObjecto(json);
			mensajeEloqua = comprobacionCaracteres("Eloqua");
			
		} else {
			mensajeEloqua = "¡¡¡¡Error en la creación del contacto en Eloqua puede que el email ya exista!!!!";
		}
	}
	
	public void creacionOSC(String json) throws Exception {
		
		if (!osc.buscarEmailSalesCloud(contactoTodos.getMail())) {
			osc.serializarObjecto(json);
			mensajeSalesCloud = comprobacionCaracteres("Sales Cloud");
		} else {
			mensajeSalesCloud = "¡¡¡¡Error en la creación del contacto en Sales Cloud puede que el email ya exista!!!!";
		}
	}
	
	//Método para crear todos
	public String crearTodos(String json) {

		JSONObject jsonObject = new JSONObject(json);
		contactoTodos.setMail(jsonObject.getString("address"));
		contactoTodos.setNombre(jsonObject.getString("first"));
		contactoTodos.setApellido(jsonObject.getString("last"));

		try {

			creacionRn(json);
			creacionElo(json);
			creacionOSC(json);
			if (osc.buscarEmailSalesCloud(contactoTodos.getMail())) {
				osc.crearLead();
			}

			return "El contacto: \n\n " + contactoTodos.toStringCrear() + "\n" + mensajeRightNow 
					+ "\n" + mensajeEloqua + "\n" + mensajeSalesCloud;

		} catch (Exception e) {

			return "Error en la creación de los usuarios";
		}
	}
}