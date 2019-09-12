package com.soaint.proyectoService.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.soaint.proyectoService.service.ServiceSalesCloud;

@RestController
@RequestMapping("/api/salesCloud")
public class ControllerSalesCloud{

	ServiceSalesCloud serviceCloudSales = new ServiceSalesCloud();
	
    @RequestMapping(value="/eliminar/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<String> BorrarPorId(@PathVariable("id") long id) throws Exception {

    	serviceCloudSales.eliminar(id);
        return new ResponseEntity<String>("Eliminado", HttpStatus.OK);
    	//return new ResponseEntity<String>(serviceCloudSales.eliminar(id), HttpStatus.OK);
    }
    
    @RequestMapping(value="/buscarEmailOSC/{email}", method = RequestMethod.GET)
    public Boolean buscarPorMail(@PathVariable("email") String email) throws Exception{
    	
		return serviceCloudSales.buscarEmailSalesCloud(email);
    }
    
	@RequestMapping(value="/crear", method = RequestMethod.POST)
    public ResponseEntity<String> crear(@RequestBody String json) throws Exception {
		
    	return new ResponseEntity<String>(serviceCloudSales.serializarObjecto(json), HttpStatus.OK);
    }
}