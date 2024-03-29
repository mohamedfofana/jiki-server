package com.kodakro.jiki.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kodakro.jiki.exception.ResourceNotFoundException;
import com.kodakro.jiki.model.Backlog;
import com.kodakro.jiki.model.Project;
import com.kodakro.jiki.repository.BacklogRepository;
import com.kodakro.jiki.repository.ProjectRepository;

@Service
public class ProjectService {
	
	@Autowired
	ProjectRepository projectRepository;
	@Autowired
	BacklogRepository backlogRepository;

	public Project create(Project project){
		final Backlog backlog = new Backlog();
		backlog.setTitle("Backlog " + project.getName());
		backlog.setDescription("Backlog " + project.getDescription());	
		backlog.setCreationDate(project.getCreationDate());
		final Backlog dbBacklog = backlogRepository.create(backlog);
		if (dbBacklog == null)
			return null;
		project.setBacklog(dbBacklog);
		return projectRepository.create(project);
	}

	public List<Project> findAll(){
		return projectRepository.findAll();
	}
	
	public Project findByTeam(Long id){
		return projectRepository.findByTeam(id);
	}

	public Project findById(Long id){
		Optional<Project> project= projectRepository.findById(id);
		if (project.isPresent())
			return project.get();
		else
			return null;
	}

	public boolean deleteById(Long id){
		final Project project = findById(id);
		if (project == null) {
			return true;
		}
		if (backlogRepository.deleteById(project.getBacklog().getId())){
			return projectRepository.deleteById(id);
		}
		return false;
	}

	public Project update(Project project){
		Project dbProject =  projectRepository.exists(project.getId()).orElseThrow(() -> new ResourceNotFoundException("update", "Project", "id", project.getId()));
		if (dbProject!=null) {
			if (project.getShortname() != null)
				dbProject.setShortname(project.getShortname());
			if (project.getName() != null)
				dbProject.setName(project.getName());
			if (project.getDescription() != null)
				dbProject.setDescription(project.getDescription());
			if (project.getBacklog() != null)
				dbProject.setBacklog(project.getBacklog());
			if (project.getTeam() != null)
				dbProject.setTeam(project.getTeam());
			if (project.getStatus() != null)
				dbProject.setStatus(project.getStatus());
			if (project.getUpdateDate() != null)
				dbProject.setUpdateDate(project.getUpdateDate());
		}
		projectRepository.update(dbProject);
		return dbProject;
	}
}

