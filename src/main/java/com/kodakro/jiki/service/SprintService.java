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

	public List<Sprint> findAll(){
		return sprintRepository.findAll();
	}

	public List<Sprint> findByProjectId(Long id){
		return sprintRepository.findByProjectId(id);
	}
	
	public Optional<Sprint> findCurrentByProjectId(Long id){
		return sprintRepository.findCurrentByProjectId(id);
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

	public Sprint update(Sprint sprint) {
		Sprint dbSprint= sprintRepository.exists(sprint.getId()).orElseThrow(() -> new ResourceNotFoundException("Sprint", "id", sprint.getId()));
		if (dbSprint!=null) {
			if (sprint.getTitle() != null)
				dbSprint.setTitle(sprint.getTitle());
			if (sprint.getDescription() != null)
				dbSprint.setDescription(sprint.getDescription());
			if (sprint.getStatus() != null)
				dbSprint.setStatus(sprint.getStatus());
			if (sprint.getBusinessValue() != null)
				dbSprint.setBusinessValue(sprint.getBusinessValue());
			if (sprint.getUpdateDate() != null)
				dbSprint.setUpdateDate(sprint.getUpdateDate());
			sprintRepository.update(dbSprint);
			return dbSprint;
		}
		return null;
	}

}
