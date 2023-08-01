package com.kodakro.jiki.controller;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

import com.kodakro.jiki.exception.CustomResponseType;
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
	public Story findById(@PathVariable("id") Long id){
		return storyService.getStoryById(id);
	}

	@GetMapping("/backlogs/project/{id}")
	public List<Story> findOnBacklogsByProjectId(@PathVariable("id") Long projectId){
		return storyService.getStoriesOnBacklogsByProjectId(projectId);
	}

	@GetMapping("/project/{id}")
	public List<Story> findByProject(@PathVariable("id") Long projectId){
		return storyService.findByProject(projectId);
	}
	
	@GetMapping("/project/{projectId}/sprint/{sprintId}")
	public List<Story> findByProjectIdAndSprintId(@PathVariable("projectId") Long projectId, @PathVariable("sprintId") Long sprintId){
		return storyService.getStoryByProjectIdAndSprintId(projectId, sprintId);
	}
	
	@GetMapping("/sprint/{id}")
	public List<Story> findBySprintId(@PathVariable("id") Long sprintId){
		return storyService.getStoriesBySprintId(sprintId);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteStoryById(Long id){
		storyService.deleteStoryById(id);
		return ResponseEntity.ok().build();
	}

	@PatchMapping("/{id}")
	public ResponseEntity<?> patchStory(@PathVariable("id") Long id, @Valid @RequestBody Map<String, Object> fieldValueMap ) {
		storyService.patchStory(id, fieldValueMap);
		return ResponseEntity.ok().build();
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> updateStory(@PathVariable(value = "id") Long id, @Valid @RequestBody Story story){
		storyService.updateStory(id, story);
		
		return ResponseEntity.ok().build();
	}
	
	@PutMapping("/moveToBacklog/{id}")
	public ResponseEntity<?> moveToBacklog(@PathVariable(value = "id") Long id, @Valid @RequestBody List<Story> stories){
		storyService.moveToBacklog(id, stories);
		
		return new ResponseEntity<CustomResponseType<Boolean>>(new CustomResponseType<Boolean>("OK", null, "Stories updated"), HttpStatus.OK);
	}

	@PutMapping("/moveToSprint/{id}")
	public ResponseEntity<?> moveToSprint(@PathVariable(value = "id") Long id, @Valid @RequestBody List<Story> stories){
		storyService.moveToSprint(id, stories);
		
		return new ResponseEntity<CustomResponseType<Boolean>>(new CustomResponseType<Boolean>("OK", null, "Stories updated"), HttpStatus.OK);
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
	
	
	@PostMapping("/create")
	public ResponseEntity<?> register(@Valid @RequestBody Story story) {
		final Story dbStory = storyService.create(story);
		if (dbStory == null) {
			return new ResponseEntity<CustomResponseType<Story>>(new CustomResponseType<Story>("KO", null, "Story  "+ story.getTitle() + " already exists !"), HttpStatus.CONFLICT);
		}
		
		return new ResponseEntity<CustomResponseType<Story>>(new CustomResponseType<Story>("OK", dbStory, "Story created"), HttpStatus.OK);
	}
}
