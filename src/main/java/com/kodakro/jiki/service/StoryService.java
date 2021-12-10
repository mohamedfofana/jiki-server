package com.kodakro.jiki.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kodakro.jiki.exception.ResourceNotFoundException;
import com.kodakro.jiki.model.Backlog;
import com.kodakro.jiki.model.Sprint;
import com.kodakro.jiki.model.Story;
import com.kodakro.jiki.repository.BacklogRepository;
import com.kodakro.jiki.repository.SprintRepository;
import com.kodakro.jiki.repository.StoryRepository;

@Service
public class StoryService {
	@Autowired
	StoryRepository storyRepository;
	@Autowired
	SprintRepository sprintRepository;
	@Autowired
	BacklogRepository backlogRepository;

	public List<Story> getStories(){
		return storyRepository.findAll();
	}

	public Story postStory(Story story){
		return storyRepository.create(story);
	}

	public Story getStoryById(Long id){
		Optional<Story> story= storyRepository.findById(id);
		if (story.isPresent())
			return story.get();
		else
			return null;
	}

	public List<Story> getStoriesByReporterId(Long id){
		List<Story> stories= storyRepository.findByReporterId(id);		
		return stories;
	}
	
	public List<Story> getStoriesByProjectId(Long id){
		List<Story> stories= storyRepository.findByProjectId(id);		
		return stories;
	}

	public List<Story> getStoriesOnBacklogsByProjectId(Long id){
		List<Story> stories= storyRepository.findStoriesOnBacklogsByProjectId(id);		
		return stories;
	}
	
	public List<Story> getStoryByProjectIdAndSprintId(Long projectId, Long sprintId){
		List<Story> stories= storyRepository.findByProjectIdAndSprintId(projectId, sprintId);		
		return stories;
	}
	
	public List<Story> getByProjectIdAndCurrentSprint(Long id){
		List<Story> stories= storyRepository.findByProjectIdAndCurrentSprint(id);		
		return stories;
	}
	
	public List<Story> getStoriesBySprintId(Long id){
		List<Story> stories= storyRepository.findBySprintId(id);		
		return stories;
	}
	
	public void deleteStoryById(Long id){
		storyRepository.deleteById(id);
	}

	public void patchStory(Long id, Story story) {
		Story dbStory= storyRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Story", "id", id));
		if (dbStory!=null) {
			if (story.getAssignedUser() != null)
				dbStory.setAssignedUser(story.getAssignedUser());
			if (story.getAssignedTeam() != null)
				dbStory.setAssignedTeam(story.getAssignedTeam());
			if (story.getBusinessValue() != null)
				dbStory.setBusinessValue(story.getBusinessValue());
			if (story.getDescription() != null)
				dbStory.setDescription(story.getDescription());
			if (story.getStartDate() != null)
				dbStory.setStartDate(story.getStartDate());
			if (story.getEndDate() != null)
				dbStory.setEndDate(story.getEndDate());
			if (story.getEstimatedEndDate() != null)
				dbStory.setEstimatedEndDate(story.getEstimatedEndDate());
			if (story.getPriority() != null)
				dbStory.setPriority(story.getPriority());
			if (story.getTitle() != null)
				dbStory.setTitle(story.getTitle());
			if (story.getType() != null)
				dbStory.setType(story.getType());
			if (story.getBacklog() != null)
				dbStory.setBacklog(story.getBacklog());
			if (story.getStatus() != null)
				dbStory.setStatus(story.getStatus());
			if (story.getStoryPoints() != null)
				dbStory.setStoryPoints(story.getStoryPoints());
			if (story.getReporter() != null)
				dbStory.setReporter(story.getReporter());
			if (story.getSprint() != null)
				dbStory.setSprint(story.getSprint());
			storyRepository.update(dbStory);
		}
	}

	public void updateStory(Long id, Story story){
		Story dbStory =  storyRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Story", "id", id));
		if (dbStory!=null) {
			dbStory.setAssignedUser(story.getAssignedUser());
			dbStory.setAssignedTeam(story.getAssignedTeam());
			dbStory.setBusinessValue(story.getBusinessValue());
			dbStory.setDescription(story.getDescription());
			dbStory.setStartDate(story.getStartDate());
			dbStory.setEndDate(story.getEndDate());
			dbStory.setEstimatedEndDate(story.getEstimatedEndDate());
			dbStory.setPriority(story.getPriority());
			dbStory.setTitle(story.getTitle());
			dbStory.setType(story.getType());
			dbStory.setBacklog(story.getBacklog());
			dbStory.setStatus(story.getStatus());
			dbStory.setStoryPoints(story.getStoryPoints());
			dbStory.setReporter(story.getReporter());
			dbStory.setSprint(story.getSprint());
			storyRepository.update(dbStory);
		}
	}
	public void updateStatus(Story story){
		Story dbStory =  storyRepository.findById(story.getId()).orElseThrow(() -> new ResourceNotFoundException("Story", "id", story.getId()));
		if (dbStory!=null) {
			dbStory.setStatus(story.getStatus());
			storyRepository.updateStatus(dbStory);
		}
	}
	
	public void updateSprintAndBacklog(Story story){
		Story dbStory =  storyRepository.findById(story.getId()).orElseThrow(() -> new ResourceNotFoundException("Story", "id", story.getId()));
		if (dbStory!=null) {
			if( story.getSprint()!= null) {
				Optional<Sprint> s = sprintRepository.findById(story.getSprint().getId());	
				if (s.isPresent()) {
					dbStory.setSprint(s.get());					
				}
			}else {
				dbStory.setSprint(null);
			}
			if( story.getBacklog() != null) {
				Optional<Backlog> b = backlogRepository.findById(story.getBacklog().getId());
				if(b.isPresent()) {
					dbStory.setBacklog(b.get());
				}
			}else {
				dbStory.setBacklog(null);
			}
			storyRepository.updateSprintAndBacklog(dbStory);
		}
	}
}
