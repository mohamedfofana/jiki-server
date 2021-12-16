package com.kodakro.jiki.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kodakro.jiki.model.Story;
import com.kodakro.jiki.service.StoryService;

@RestController
@RequestMapping(path = "/api/story", produces = MediaType.APPLICATION_JSON_VALUE)
public class StoryController {
	@Autowired
	StoryService storyService;

	@GetMapping("/all")
	public List<Story> getStories(){
		return storyService.getStories();
	}

	@PostMapping("/")
	public Story postStory(@Valid @RequestBody Story story){
		return storyService.postStory(story);
	}

	@GetMapping("/{id}")
	public Story getStoryById(@PathVariable("id") Long id){
		return storyService.getStoryById(id);
	}

	@GetMapping("/backlogs/project/{id}")
	public List<Story> getStoriesOnBacklogsByProjectId(@PathVariable("id") Long projectId){
		return storyService.getStoriesOnBacklogsByProjectId(projectId);
	}
	@GetMapping("/project/{projectId}/sprint/{sprintId}")
	public List<Story> getStoryByProjectIdAndSprintId(@PathVariable("projectId") Long projectId, @PathVariable("sprintId") Long sprintId){
		return storyService.getStoryByProjectIdAndSprintId(projectId, sprintId);
	}
	
	@GetMapping("/sprint/{id}")
	public List<Story> getStoryBySprintId(@PathVariable("id") Long sprintId){
		return storyService.getStoriesBySprintId(sprintId);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteStoryById(Long id){
		storyService.deleteStoryById(id);
		return ResponseEntity.ok().build();
	}

	@PatchMapping("/{id}")
	public ResponseEntity<?> patchStory(@PathVariable("id") Long id, @Valid @RequestBody Story story) {
		storyService.patchStory(id, story);
		return ResponseEntity.ok().build();
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> updateStory(@PathVariable(value = "id") Long id, @Valid @RequestBody Story story){
		storyService.updateStory(id, story);
		return ResponseEntity.ok().build();
	}
	
	@PutMapping("/updateStatus")
	public Story updateStatus(@Valid @RequestBody Story story){
		storyService.updateStatus(story);
		return story;
	}
	
	@PutMapping("/update/sprintAndBacklog")
	public Story updateSprintAndBacklog(@Valid @RequestBody Story story){
		storyService.updateSprintAndBacklog(story);
		return story;
	}
}
