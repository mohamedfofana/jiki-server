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

	public List<Backlog> getBacklogs(){
		return backlogRepository.findAll();
	}

	public Backlog postBacklog(Backlog backlog){
		return backlogRepository.create(backlog);
	}

	public Backlog getBacklogById(Long id){
		Optional<Backlog> backlog= backlogRepository.findById(id);
		if (backlog.isPresent())
			return backlog.get();
		else
			return null;
	}

	public void deleteBacklogById(Long id){
		backlogRepository.deleteById(id);
	}

	public void patchDailymeeting(Long id, Backlog backlog) {
		Backlog dbBacklog= backlogRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Backlog", "id", id));
		if (dbBacklog!=null) {
			if (backlog.getTitle() != null)
				dbBacklog.setTitle(backlog.getTitle());
			if (backlog.getDescription() != null)
				dbBacklog.setDescription(backlog.getDescription());
			if (backlog.getStatus() != null)
				dbBacklog.setStatus(backlog.getStatus());
//			if (backlog.getBackLogReporter() != null)
//				dbBacklog.setBackLogReporter(backlog.getBackLogReporter());
			backlogRepository.update(dbBacklog);
		}
	}

	public void updateBacklog(Long id, Backlog backlog){
		Backlog dbBacklog =  backlogRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Backlog", "id", id));
		if (dbBacklog!=null) {
			dbBacklog.setTitle(backlog.getTitle());
			dbBacklog.setDescription(backlog.getDescription());
			dbBacklog.setStatus(backlog.getStatus());
//			dbBacklog.setBackLogReporter(backlog.getBackLogReporter());
			backlogRepository.update(dbBacklog);
		}
	}
}
