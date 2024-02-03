package com.kodakro.jiki.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kodakro.jiki.exception.ResourceNotFoundException;
import com.kodakro.jiki.model.Backlog;
import com.kodakro.jiki.model.Project;
import com.kodakro.jiki.model.Sprint;
import com.kodakro.jiki.model.Story;
import com.kodakro.jiki.repository.BacklogRepository;
import com.kodakro.jiki.repository.ProjectRepository;
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
	@Autowired
	ProjectRepository projectRepository;

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

	public List<Story> getStoriesOnBacklogsByProjectId(Long id){
		List<Story> stories= storyRepository.findStoriesOnBacklogsByProjectId(id);		
		return stories;
	}

	public List<Story> findByProject(Long projectId){
		List<Story> stories= storyRepository.findByProject(projectId);		
		return stories;
	}

	public List<Story> findByReporter(Long reporterId) {
		List<Story> stories= storyRepository.findByReporter(reporterId);		
		return stories;
	}
	
	public List<Story> getStoryByProjectIdAndSprintId(Long projectId, Long sprintId){
		List<Story> stories= storyRepository.findByProjectIdAndSprintId(projectId, sprintId);		
		return stories;
	}
	
	public List<Story> getStoriesBySprintId(Long id){
		List<Story> stories= storyRepository.findBySprintId(id);		
		return stories;
	}
	
	public void deleteStoryById(Long id){
		storyRepository.deleteById(id);
	}

	public void patchStory(Long id, @Valid Map<String, Object> fieldValueMap) {
		//Story dbStory= storyRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Story", "id", id));
//		if (dbStory!=null) {
			if(!fieldValueMap.isEmpty()) {
//				fieldValueMap.forEach((key, value) -> {
//					Field field = ReflectionUtils.findField(Story.class, key);
//					field.setAccessible(true);
//					ReflectionUtils.setField(field, dbStory, value);
//				});
				storyRepository.updateField(id, fieldValueMap);
			}
//		}
	}

	public void updateStory(Long id, Story story){
		Story dbStory =  storyRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("updateStory", "Story", "id", id));
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
		Story dbStory =  storyRepository.findById(story.getId()).orElseThrow(() -> new ResourceNotFoundException("updateStatus", "Story", "id", story.getId()));
		if (dbStory!=null) {
			dbStory.setStatus(story.getStatus());
			storyRepository.updateStatus(dbStory);
		}
	}
	
	public void updateSprintAndBacklog(Story story){
		Story dbStory =  storyRepository.exists(story.getId()).orElseThrow(() -> new ResourceNotFoundException("updateSprintAndBacklog", "Story", "id", story.getId()));
		if (dbStory!=null) {
			if( story.getSprint()!= null) {
				Optional<Sprint> s = sprintRepository.findById(story.getSprint().getId());	
				if (s.isPresent()) {
					dbStory.setSprint(s.get());					
				}
			}else {
				dbStory.setSprint(null);
			}
			if( story.getProject() != null) {
				Optional<Project> p = projectRepository.findById(story.getProject().getId());
				if(p.isPresent()) {
					dbStory.setProject(p.get());
				}
			}else {
				dbStory.setBacklog(null);
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
	
	public Story create(Story story) {
		Optional<Story> dbStory = storyRepository.exists(story.getId());
		if (dbStory.isPresent()) {
			return null;
		}
		return storyRepository.create(story);
	}

	public void moveToBacklog(Long backlogId, List<Story> stories) {
		storyRepository.moveToBacklog(backlogId, stories);
	}
	
	public void moveToSprint(Long backlogId, List<Story> stories) {
		storyRepository.moveToSprint(backlogId, stories);
	}

}
