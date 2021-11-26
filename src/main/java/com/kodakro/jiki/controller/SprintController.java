package com.kodakro.jiki.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kodakro.jiki.exception.ResourceNotFoundException;
import com.kodakro.jiki.model.Sprint;
import com.kodakro.jiki.repository.SprintRepository;

@RestController
@RequestMapping(path = "/api/sprint", produces = MediaType.APPLICATION_JSON_VALUE)
public class SprintController {
	@Autowired
	SprintRepository sprintRepository;

	@GetMapping("/all")
	public List<Sprint> getSprints(){
		return sprintRepository.findAll();
	}

	@PostMapping("/")
	public Sprint postSprint(@Valid @RequestBody Sprint sprint){
		return sprintRepository.create(sprint);
	}

	@GetMapping("/{id}")
	public Sprint getSprintById(@PathVariable("id") Long id){
		Optional<Sprint> sprint= sprintRepository.findById(id);
		if (sprint.isPresent())
			return sprint.get();
		else
			return null;
	}

	@GetMapping("/current/project/{id}")
	public Sprint getCurrentSprintByProjectId(@PathVariable("id") Long id){
		Optional<Sprint> sprint= sprintRepository.findCurrentByProjectId(id);
		if (sprint.isPresent())
			return sprint.get();
		else
			return null;
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?>  deleteSprint(@PathVariable("id") Long id){
		sprintRepository.deleteById(id);
		return ResponseEntity.ok().build();
	}

	@PatchMapping("/{id}")
	public ResponseEntity<?> patchSprint(@PathVariable("id") Long id, @Valid @RequestBody Sprint sprint) {
		Sprint dbSprint= sprintRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Sprint", "id", id));
		if (dbSprint!=null) {
			if (sprint.getDescription() != null)
				dbSprint.setDescription(sprint.getDescription());
			if (sprint.getReporter() != null)
				dbSprint.setReporter(sprint.getReporter());
			if (sprint.getTitle() != null)
				dbSprint.setTitle(sprint.getTitle());
			if (sprint.getStatus() != null)
				dbSprint.setStatus(sprint.getStatus());
			if (sprint.getWorkflow() != null)
				dbSprint.setWorkflow(sprint.getWorkflow());
			sprintRepository.update(dbSprint);
		}
		return ResponseEntity.ok().build();
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> updateSrint(@PathVariable(value = "id") Long id, @Valid @RequestBody Sprint sprint){
		Sprint dbSprint =  sprintRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Sprint", "id", id));
		if (dbSprint!=null) {
			dbSprint.setDescription(sprint.getDescription());
			dbSprint.setReporter(sprint.getReporter());
			dbSprint.setTitle(sprint.getTitle());
			dbSprint.setStatus(sprint.getStatus());
			dbSprint.setWorkflow(sprint.getWorkflow());
			sprintRepository.update(dbSprint);
		}
		return ResponseEntity.ok().build();
	}
}
