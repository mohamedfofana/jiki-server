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

import com.kodakro.jiki.model.Project;
import com.kodakro.jiki.service.ProjectService;

@RestController
@RequestMapping(path = "/api/project", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProjectController {
	@Autowired
	ProjectService projectService;


	@PostMapping("/create")
	public Project postProject(@Valid @RequestBody Project project){
		return projectService.postProject(project);
	}

	@GetMapping("/all")
	public List<Project> getProjects(){
		return projectService.getProjects();
	}

	@GetMapping("/{id}")
	public Project getProjectById(@PathVariable("id") Long id){
		return projectService.getProjectById(id);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteProjectById(@PathVariable("id") Long id){
		projectService.deleteProjectById(id);
		return ResponseEntity.ok().build();
	}

	@PatchMapping("/{id}")
	public ResponseEntity<?> patchProject(@PathVariable("id") Long id, @Valid @RequestBody Project project) {
		projectService.patchProject(id, project);
		return ResponseEntity.ok().build();
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> updateProject(@PathVariable("id") Long id, @Valid @RequestBody Project project){
		projectService.updateProject(id, project);
		return ResponseEntity.ok().build();
	}
}
