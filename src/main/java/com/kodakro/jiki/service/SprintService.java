package com.kodakro.jiki.service;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kodakro.jiki.exception.ResourceNotFoundException;
import com.kodakro.jiki.model.Sprint;
import com.kodakro.jiki.repository.SprintRepository;
import com.kodakro.jiki.repository.StoryRepository;

@Service
public class SprintService {
	@Autowired
	SprintRepository sprintRepository;
	@Autowired
	StoryRepository storyRepository;

	public List<Sprint> findAll(){
		return sprintRepository.findAll();
	}

	public List<Sprint> findByProjectId(Long id){
		return sprintRepository.findByProjectId(id);
	}
	
	public Optional<Sprint> findCurrentByProjectId(Long id){
		return sprintRepository.findCurrentByProjectId(id);
	}
	
	public Optional<Sprint> findRunningByProjectId(Long id){
		return sprintRepository.findRunningByProjectId(id);
	}
	
	public Sprint create(Sprint sprint){
		return sprintRepository.create(sprint);
	}

	public Sprint findById(Long id){
		Optional<Sprint> sprint= sprintRepository.findById(id);
		if (sprint.isPresent())
			return sprint.get();
		else
			return null;
	}

	public boolean deleteById(Long id){
		return sprintRepository.deleteById(id);
	}

	public Sprint start(Sprint sprint) {
		Sprint dbSprint= sprintRepository.exists(sprint.getId()).orElseThrow(() -> new ResourceNotFoundException("start", "Sprint", "id", sprint.getId()));
		if (dbSprint!=null) {
			dbSprint.setStatus(sprint.getStatus());
			dbSprint.setName(sprint.getName());
			dbSprint.setGoal(sprint.getGoal());
			dbSprint.setDuration(sprint.getDuration());
			dbSprint.setBusinessValue(sprint.getBusinessValue());
			dbSprint.setStartDate(sprint.getStartDate());
			dbSprint.setUpdateDate(sprint.getUpdateDate());
			dbSprint.setEndDate(sprint.getEndDate());
			
			sprintRepository.start(dbSprint);
			
			return dbSprint;
		}
		return null;
	}

	public List<Sprint> findByStatusInProject(Long id, String status) {
		return sprintRepository.findByStatusInProject(id, status);
	}

	public Sprint close(@Valid Sprint sprint) {
		Sprint dbSprint= sprintRepository.exists(sprint.getId()).orElseThrow(() -> new ResourceNotFoundException("close", "Sprint", "id", sprint.getId()));
		if (dbSprint!=null) {
			dbSprint.setStatus(sprint.getStatus());
			dbSprint.setUpdateDate(sprint.getUpdateDate());
			dbSprint.setEndDate(sprint.getEndDate());
			
			sprintRepository.close(dbSprint);
			
			return dbSprint;
		}
		return null;
	}

}
