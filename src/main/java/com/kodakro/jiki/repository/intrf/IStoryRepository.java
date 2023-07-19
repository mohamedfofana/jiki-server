package com.kodakro.jiki.repository.intrf;

import java.util.List;

import com.kodakro.jiki.model.Story;

public interface IStoryRepository {

	/*
	 * find all stories on project backlogs (i.e. not in a sprint)
	 */
	List<Story> findStoriesOnBacklogsByProjectId(Long id);
	
	/*
	 * find all stories by project and sprint
	 */
	List<Story> findByProjectIdAndSprintId(Long idP, Long idS);
	
	/*
	 * find all stories by sprint
	 */
	List<Story> findBySprintId(Long id);
	
	/*
	 * Update story status
	 */
	void updateStatus(Story t) ;
	
	/*
	 * Update the backlog and sprint of a story
	 */
	void updateSprintAndBacklog(Story t) ;
	
	
	
}
