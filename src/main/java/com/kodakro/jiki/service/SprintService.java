package com.kodakro.jiki.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kodakro.jiki.exception.ResourceNotFoundException;
import com.kodakro.jiki.model.Sprint;
import com.kodakro.jiki.repository.SprintRepository;

@Service
public class SprintService {
	@Autowired
	SprintRepository sprintRepository;

	public List<Sprint> getSprints(){
		return sprintRepository.findAll();
	}

	public Sprint postSprint(Sprint sprint){
		return sprintRepository.create(sprint);
	}

	public Sprint getSprintById(Long id){
		Optional<Sprint> sprint= sprintRepository.findById(id);
		if (sprint.isPresent())
			return sprint.get();
		else
			return null;
	}

	public void deleteSprintById(Long id){
		sprintRepository.deleteById(id);
	}

	public void patchSprint(Long id, Sprint sprint) {
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
	}

	public void updateSrint(Long id, Sprint sprint){
		Sprint dbSprint =  sprintRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Sprint", "id", id));
		if (dbSprint!=null) {
			dbSprint.setDescription(sprint.getDescription());
			dbSprint.setReporter(sprint.getReporter());
			dbSprint.setTitle(sprint.getTitle());
			dbSprint.setStatus(sprint.getStatus());
			dbSprint.setWorkflow(sprint.getWorkflow());
			sprintRepository.update(dbSprint);
		}
	}
}
