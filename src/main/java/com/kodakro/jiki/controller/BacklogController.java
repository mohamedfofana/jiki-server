package com.kodakro.jiki.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kodakro.jiki.exception.CustomResponseType;
import com.kodakro.jiki.model.Backlog;
import com.kodakro.jiki.service.BacklogService;

@RestController
@RequestMapping(path = "/api/backlog", produces = MediaType.APPLICATION_JSON_VALUE)
public class BacklogController {
	@Autowired
	BacklogService backlogService;

	@GetMapping("/all")
	public List<Backlog> findAll(){
		return backlogService.findAll();
	}

	@GetMapping("/{id}")
	public Backlog findById(@PathVariable("id") Long id){
		return backlogService.findById(id);
	}

	@PostMapping("/create")
	public ResponseEntity<?> register(@Valid @RequestBody Backlog backlog) {
		Backlog dbBacklog = backlogService.create(backlog);
		if (dbBacklog == null) {
			return new ResponseEntity<CustomResponseType<Backlog>>(new CustomResponseType<Backlog>("KO", null, "Backlog  "+ backlog.getTitle() + " already exists !"), HttpStatus.CONFLICT);
		}
		return new ResponseEntity<CustomResponseType<Backlog>>(new CustomResponseType<Backlog>("OK", dbBacklog, "Backlog created"), HttpStatus.OK);
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteById(@PathVariable("id") Long id){
		backlogService.deleteById(id);
		return ResponseEntity.ok().build();
	}

	@PutMapping("/update")
	public ResponseEntity<?> update(@Valid @RequestBody Backlog backlog){
		backlogService.update(backlog);
		return ResponseEntity.ok().build();
	}
	
	/**
	 * Prefer to use Global ExceptionHandler with ControllerAdvice 
	 * to avoid creating ExceptionHandler inside each controller
	 * @param ex exception to catch
	 * @return
	 */
//	@ExceptionHandler(ResourceNotFoundException.class)
//	public ResponseEntity handleResourceNotFoundException(final ResourceNotFoundException ex) {
//		return new ResponseEntity<CustomResponseType<Backlog>>(
//				new CustomResponseType<Backlog>("KO", null, ex.getResourceName() 
//											   +" "+ ex.getFieldName()+ " " + ex.getFieldValue() + " already exists !"), 
//											   HttpStatus.NOT_FOUND);
//	}
}
