package com.soaint.proyectoService.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.soaint.proyectoService.service.ServiceEloqua;

@RestController
@RequestMapping("/api/eloqua")
public class ControllerEloqua {

	ServiceEloqua service_eloqua = new ServiceEloqua();
	
    @RequestMapping(value="/eliminar/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<String> BorrarPorId(@PathVariable("id") long id) throws Exception {

    	service_eloqua.eliminarEloquaPorId(id);
        return new ResponseEntity<String>("Eliminado", HttpStatus.OK);
       // return new ResponseEntity<String>(service_eloqua.eliminar(id), HttpStatus.OK);
    }
    
    @RequestMapping(value="/buscarEmailEloqua/{email}", method = RequestMethod.GET)
    public Boolean buscarPorMail(@PathVariable("email") String email) throws Exception{
    	
		return service_eloqua.buscarEmailEloqua(email);
    }
    
	@RequestMapping(value="/crear", method = RequestMethod.POST)
    public ResponseEntity<String> crear(@RequestBody String json) throws Exception {
		
    	return new ResponseEntity<String>(service_eloqua.serializarObjecto(json), HttpStatus.OK);
    }
}