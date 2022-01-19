package com.kodakro.jiki.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kodakro.jiki.exception.ResourceNotFoundException;
import com.kodakro.jiki.model.Backlog;
import com.kodakro.jiki.repository.BacklogRepository;

@Service
public class BacklogService {

	@Autowired
	BacklogRepository backlogRepository;

	public List<Backlog> findAll(){
		return backlogRepository.findAll();
	}

	public Backlog findById(Long id){
		Optional<Backlog> backlog= backlogRepository.findById(id);
		if (backlog.isPresent())
			return backlog.get();
		else
			return null;
	}

	public boolean deleteById(Long id){
		return backlogRepository.deleteById(id);
	}

	public Backlog create(Backlog backlog) {
		Optional<Backlog> dbBacklog = backlogRepository.exists(backlog.getId());
		if (dbBacklog.isPresent()) {
			return null;
		}
		return backlogRepository.create(backlog);
	}
	public void update(Backlog backlog) {
		Backlog dbBacklog= backlogRepository.exists(backlog.getId()).orElseThrow(() -> new ResourceNotFoundException("Backlog", "id", backlog.getId()));
		if (dbBacklog!=null) {
			if (backlog.getTitle() != null)
				dbBacklog.setTitle(backlog.getTitle());
			if (backlog.getDescription() != null)
				dbBacklog.setDescription(backlog.getDescription());
			if (backlog.getStatus() != null)
				dbBacklog.setStatus(backlog.getStatus());
			if (backlog.getUpdateDate() != null)
				dbBacklog.setUpdateDate(backlog.getUpdateDate());
			backlogRepository.update(dbBacklog);
		}
	}
}
