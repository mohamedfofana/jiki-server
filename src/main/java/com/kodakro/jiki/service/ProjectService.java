package com.kodakro.jiki.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kodakro.jiki.exception.ResourceNotFoundException;
import com.kodakro.jiki.model.Project;
import com.kodakro.jiki.repository.ProjectRepository;

@Service
public class ProjectService {
	
	@Autowired
	ProjectRepository projectRepository;

	public Project postProject(Project project){
		return projectRepository.create(project);
	}

	public List<Project> getProjects(){
		return projectRepository.findAll();
	}

	public Project getProjectById(Long id){
		Optional<Project> project= projectRepository.findById(id);
		if (project.isPresent())
			return project.get();
		else
			return null;
	}

	public void deleteProjectById(Long id){
		projectRepository.deleteById(id);
	}

	public void patchProject(Long id, Project project) {
		Project dbProject= projectRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Project", "id", id));
		if (dbProject!=null) {
			if (project.getName() != null)
				dbProject.setName(project.getName());
			if (project.getStatus() != null)
				dbProject.setStatus(project.getStatus());
			projectRepository.update(dbProject);
		}
	}

	public void updateProject(Long id, Project project){
		Project dbProject =  projectRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Project", "id", id));
		if (dbProject!=null) {
			dbProject.setName(project.getName());
			dbProject.setStatus(project.getStatus());
		}
		projectRepository.update(dbProject);
	}
}
