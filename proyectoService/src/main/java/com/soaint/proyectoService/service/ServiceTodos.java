package com.soaint.proyectoService.service;

import org.json.JSONObject;

import com.soaint.proyectoService.contacto.Contact;
import com.soaint.proyectoService.metodosConexion.Metodos;

public class ServiceTodos {

	String mensajeRightNow, mensajeEloqua, mensajeSalesCloud, mensajeSalesCloudLead, mensajeCaracteres;
	Contact contactoTodos = new Contact();
	
	ServiceRightNow rn = new ServiceRightNow();
	ServiceEloqua eloqua = new ServiceEloqua();
	ServiceSalesCloud osc = new ServiceSalesCloud();
	
//----------------------------------------------Eliminar contacto Right Now----------------------------------------------
	public void eliminarRn(String email) throws Exception {
		
		if (rn.buscarEmailRightNow(email)) {
			rn.eliminarRightNowPorId(rn.contacto.getId());
			mensajeRightNow = rn.contacto.toStringEliminar() + " de Right Now"
			+ "\nEl contacto fué eliminado satisfatoriamente de Right Now  😲 \n";
		} else {
			mensajeRightNow = "El contacto no existe en Right Now 😓";
		}
	}
	
//-------------------------------------------------Eliminar contacto Eloqua-----------------------------------------------
	public void eliminarElo(String email) throws Exception {

		if (eloqua.buscarEmailEloqua(email)) {
			eloqua.eliminarEloquaPorId(eloqua.contacto.getId());
			mensajeEloqua = eloqua.contacto.toStringEliminar()  + " de Eloqua"
			+ "\nEl contacto fué borrado satisfatoriamente de Eloqua  😲 \n";
		} else {
			mensajeEloqua = "El contacto no existe en Eloqua 😓";
		}
	}
	
//--------------------------------------------Eliminar contacto Lead Sales Cloud------------------------------------------
	public void eliminarOSCLead(String email) throws Exception {
		
		if (osc.buscarEmailSalesCloud(email)) {
			osc.buscarOSCLeadPorId(osc.contacto.getId()); 			
			mensajeSalesCloudLead = "Los leads del contacto fueron borrados satisfatoriamente Oracle Sales Cloud 😲 \n";
		} else {
			mensajeSalesCloudLead = "No existen leads en este contacto de Sales Cloud 😓";
		}
	}
	
//----------------------------------------------Eliminar contacto Sales Cloud----------------------------------------------
	public void eliminarOSC(String email) throws Exception {
		
		if (osc.buscarEmailSalesCloud(email)) {
			osc.eliminarSalesCloudPorId(osc.contacto.getId());
			mensajeSalesCloud = osc.contacto.toStringEliminar() + " de Sales Cloud"
			+ "\nEl contacto fué borrado satisfatoriamente Oracle Sales Cloud 😲 ";
		} else {
			mensajeSalesCloud = "El contacto no existe en Sales Cloud 😓";
		}
	}

//-----------------------------------Eliminar Todos los contacto y el lead de Sales Cloud------------------------------------
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
	
//--------------------------------------------------Crear contacto Right Now--------------------------------------------------
	public void creacionRn(String json) throws Exception {
		
		if (!rn.buscarEmailRightNow(contactoTodos.getMail())){
			rn.serializarObjecto(json);
			mensajeRightNow = Metodos.comprobacionCaracteres("Right Now");

		} else {
			mensajeRightNow = "¡¡¡¡Error en la creación del contacto en Right Now puede que el email ya exista!!!! 😓";
		}
	}
	
//-----------------------------------------------------Crear contacto Eloqua--------------------------------------------------
	public void creacionElo(String json) throws Exception {
		
		if (!eloqua.buscarEmailEloqua(contactoTodos.getMail())) {
			eloqua.serializarObjecto(json);
			mensajeEloqua = Metodos.comprobacionCaracteres("Eloqua");
			
		} else {
			mensajeEloqua = "¡¡¡¡Error en la creación del contacto en Eloqua puede que el email ya exista!!!! 😓";
		}
	}
	
//---------------------------------------------------Crear contacto Sales Cloud-----------------------------------------------
	public void creacionOSC(String json) throws Exception {
		
		if (!osc.buscarEmailSalesCloud(contactoTodos.getMail())) {
			osc.serializarObjecto(json);
			mensajeSalesCloud = Metodos.comprobacionCaracteres("Sales Cloud");
		} else {
			mensajeSalesCloud = "¡¡¡¡Error en la creación del contacto en Sales Cloud puede que el email ya exista!!!! 😓";
		}
	}
	
//------------------------------------Crear todos los contacto y el lead de Sales Cloud----------------------------------------
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
					if(!osc.buscarLeadSalesCloudPorMail(contactoTodos.getMail())) {
						osc.crearLead();
						mensajeSalesCloudLead = "Lead creado satisfatoriamente en Sales Cloud 😎🍻";
					}
					else { mensajeSalesCloudLead = "¡¡¡¡Error en la creación del lead en Sales Cloud Ya existe un lead para este contacto!!!! 😓";}
				}else 
				{
					mensajeSalesCloudLead = "Fallo en la creación del lead para Sales CLoud";
				}

				return "El contacto: \n\n" + contactoTodos.toStringCrear() + "\n" + mensajeRightNow 
						+ "\n" + mensajeEloqua + "\n" + mensajeSalesCloud + "\n" + mensajeSalesCloudLead;

			} catch (Exception e) {

				return "Error en la creación de los usuarios";
			}
		}
}