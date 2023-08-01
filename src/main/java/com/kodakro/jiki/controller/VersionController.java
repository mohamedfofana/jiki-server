package com.kodakro.jiki.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kodakro.jiki.exception.CustomResponseType;
import com.kodakro.jiki.model.Version;
import com.kodakro.jiki.service.VersionService;

@RestController
@RequestMapping(path = "/api/version", produces = MediaType.APPLICATION_JSON_VALUE)
public class VersionController {
	@Autowired
	VersionService versionService;
	
	@GetMapping("/project/{projectId}")
	List<Version> findByProject(@PathVariable("projectId") Long projectId){
		return versionService.findByProject(projectId);
	}
	
	@PostMapping("/create")
	public ResponseEntity<?> create(@Valid @RequestBody Version version) {
		final Version dbVersion = versionService.create(version);
		if (dbVersion == null) {
			return new ResponseEntity<CustomResponseType<Version>>(new CustomResponseType<Version>("KO", null, "Version  "+ version.getVersion() + " already exists !"), HttpStatus.CONFLICT);
		}
		return new ResponseEntity<CustomResponseType<Version>>(new CustomResponseType<Version>("OK", dbVersion, "Team created"), HttpStatus.OK);
	}
}
