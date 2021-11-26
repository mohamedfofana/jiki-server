package com.kodakro.jiki.controller;

import java.util.List;

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

import com.kodakro.jiki.model.Backlog;
import com.kodakro.jiki.service.BacklogService;

@RestController
@RequestMapping(path = "/api/backlog", produces = MediaType.APPLICATION_JSON_VALUE)
public class BacklogController {
	@Autowired
	BacklogService backlogService;

	@GetMapping("/all")
	public List<Backlog> getBacklogs(){
		return backlogService.getBacklogs();
	}

	@PostMapping("/")
	public Backlog postBacklog(@Valid @RequestBody Backlog backlog){
		return backlogService.postBacklog(backlog);
	}

	@GetMapping("/{id}")
	public Backlog getBacklogById(@PathVariable("id") Long id){
		return backlogService.getBacklogById(id);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteBacklog(@PathVariable("id") Long id){
		backlogService.deleteBacklogById(id);
		return ResponseEntity.ok().build();
	}

	@PatchMapping("/{id}")
	public ResponseEntity<?> patchDailymeeting(@PathVariable("id") Long id, @Valid @RequestBody Backlog backlog) {
		backlogService.patchDailymeeting(id, backlog);
		return ResponseEntity.ok().build();
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> updateBacklog(@PathVariable(value = "id") Long id, @Valid @RequestBody Backlog backlog){
		backlogService.updateBacklog(id, backlog);
		return ResponseEntity.ok().build();
	}
}
