package com.soaint.proyectoService.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.soaint.proyectoService.service.ServiceTodos;

@RestController
@RequestMapping("/api/todos")
public class ContollerTodos {

	ServiceTodos todos = new ServiceTodos();

	@RequestMapping(value = "/eliminar/{email}", method = RequestMethod.DELETE)
	public ResponseEntity<String> BorrarPorId(@PathVariable("email") String mail) throws Exception {

		return new ResponseEntity<String>(todos.eliminarTodos(mail), HttpStatus.OK);
	}
    
	@RequestMapping(value="/crear", method = RequestMethod.POST)
    public ResponseEntity<String> crear(@RequestBody String json) throws Exception {
		
    	return new ResponseEntity<String>(todos.crearTodos(json), HttpStatus.OK);
    }
}