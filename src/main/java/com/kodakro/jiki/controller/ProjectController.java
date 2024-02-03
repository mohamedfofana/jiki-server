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
import com.kodakro.jiki.model.Project;
import com.kodakro.jiki.service.ProjectService;

@RestController
@RequestMapping(path = "/api/project", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProjectController {
	@Autowired
	ProjectService projectService;

	@GetMapping("/all")
	public List<Project> findAll(){
		return projectService.findAll();
	}

	@GetMapping("/team/{id}")
	public Project findByTeam(@PathVariable("id") Long id){
		return projectService.findByTeam(id);
	}
	
	@PostMapping("/create")
	public ResponseEntity<?> create(@Valid @RequestBody Project project) {
		final Project dbProject = projectService.create(project);
		if (dbProject == null) {
			return new ResponseEntity<CustomResponseType<Project>>(new CustomResponseType<Project>("KO", null, "Team  "+ project.getName() + " already exists !"), HttpStatus.CONFLICT);
		}
		return new ResponseEntity<CustomResponseType<Project>>(new CustomResponseType<Project>("OK", dbProject, "Team created"), HttpStatus.OK);
	}
	

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteById(@PathVariable("id") Long id){
		final boolean status = projectService.deleteById(id);
		if(status)
			return new ResponseEntity<CustomResponseType<Project>>(new CustomResponseType<Project>("OK", null, "Project deleted"), HttpStatus.OK);
		
		return  new ResponseEntity<CustomResponseType<Project>>(new CustomResponseType<Project>("KO", null, "Unable to delete project with id = "+ id + "."), HttpStatus.CONFLICT);
	}

	@PutMapping("/update")
	public ResponseEntity<?> update(@Valid @RequestBody Project project){
		final Project dbProject = projectService.update(project);
		if (dbProject == null) {
			return new ResponseEntity<CustomResponseType<Project>>(new CustomResponseType<Project>("KO", null, "Unable to uppdate team "+ project.getName() + "."), HttpStatus.CONFLICT);
		}
		return new ResponseEntity<CustomResponseType<Project>>(new CustomResponseType<Project>("OK", dbProject, "Team updated"), HttpStatus.OK);
	}
}
