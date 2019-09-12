package com.soaint.proyectoService.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.soaint.proyectoService.service.ServiceRightNow;

@RestController
@RequestMapping("/api/rightNow")
public class Controller_rightNow {

	@SuppressWarnings("rawtypes")
	ServiceRightNow rightNow = new ServiceRightNow(); 
    
    @RequestMapping(value="/eliminar/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<String> BorrarPorId(@PathVariable("id") long id) throws Exception {

    	rightNow.eliminar(id);
        return new ResponseEntity<String>("Eliminado", HttpStatus.OK);
    	//return new ResponseEntity<String>(serviceCloudSales.eliminar(id), HttpStatus.OK);
    }
    
    @RequestMapping(value="/buscarEmailRn/{email}", method = RequestMethod.GET)
    public Boolean buscarPorMail(@PathVariable("email") String email) throws Exception{
    	
		return rightNow.buscarEmailRightNow(email);
    }
    
	@RequestMapping(value="/crear", method = RequestMethod.POST)
    public ResponseEntity<String> crear(@RequestBody String json) throws Exception {
		
    	return new ResponseEntity<String>(rightNow.serializarObjecto(json), HttpStatus.OK);
    }
}