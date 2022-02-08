package com.kodakro.jiki.controller;

import java.util.List;
import java.util.Optional;

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
import com.kodakro.jiki.model.Sprint;
import com.kodakro.jiki.service.SprintService;

@RestController
@RequestMapping(path = "/api/sprint", produces = MediaType.APPLICATION_JSON_VALUE)
public class SprintController {
	@Autowired
	SprintService sprintService;

	@GetMapping("/all")
	public List<Sprint> findAll(){
		return sprintService.findAll();
	}

	@PostMapping("/create")
	public ResponseEntity<?> create(@Valid @RequestBody Sprint sprint){
		final Sprint dbSprint = sprintService.create(sprint);
		if (dbSprint == null) {
			return new ResponseEntity<CustomResponseType<Sprint>>(new CustomResponseType<Sprint>("KO", null, "Sprint  "+ sprint.getTitle() + " already exists !"), HttpStatus.CONFLICT);
		}
		return new ResponseEntity<CustomResponseType<Sprint>>(new CustomResponseType<Sprint>("OK", dbSprint, "Sprint created"), HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public Sprint findById(@PathVariable("id") Long id){
		return sprintService.findById(id);
	}

	@GetMapping("/current/project/{id}")
	public Sprint getCurrentSprintByProjectId(@PathVariable("id") Long id){
		Optional<Sprint> sprint= sprintService.findCurrentByProjectId(id);
		if (sprint.isPresent())
			return sprint.get();
		else
			return null;

	}
	@GetMapping("/project/{id}")
	public List<Sprint> findByProjectId(@PathVariable("id") Long id){
		return sprintService.findByProjectId(id);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?>  deleteById(@PathVariable("id") Long id){
		final boolean status = sprintService.deleteById(id);
		if(status) {
			return new ResponseEntity<CustomResponseType<Sprint>>(new CustomResponseType<Sprint>("OK", null, "Sprint deleted"), HttpStatus.OK);
		}
		return  new ResponseEntity<CustomResponseType<Sprint>>(new CustomResponseType<Sprint>("KO", null, "Unable to delete sprint with id = "+ id + "."), HttpStatus.CONFLICT);
	}

	@PutMapping("/update")
	public ResponseEntity<?> update(@Valid @RequestBody Sprint sprint) {
		final Sprint dbSprint = sprintService.update(sprint);
		if (dbSprint == null) {
			return new ResponseEntity<CustomResponseType<Sprint>>(new CustomResponseType<Sprint>("KO", null, "Unable to uppdate sprint "+ sprint.getTitle() + "."), HttpStatus.CONFLICT);
		}
		return new ResponseEntity<CustomResponseType<Sprint>>(new CustomResponseType<Sprint>("OK", dbSprint, "Sprint updated"), HttpStatus.OK);

	}
}
