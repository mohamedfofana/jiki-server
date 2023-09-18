package com.kodakro.jiki.repository.intrf;

import java.util.List;
import java.util.Map;

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
	 * find all stories by sprint
	 */
	List<Story> findByProject(Long id);

	List<Story> findByReporter(Long id);
	
	/*
	 * Update story status
	 */
	void updateStatus(Story t);
	
	public void moveToBacklog(Long id, List<Story> stories);
	
	public void moveToSprint(Long id, List<Story> stories);
	
	/*
	 * Update story status
	 */
	void updateField(Long id, Map<String, Object> fieldValueMap) ;
	
	/*
	 * Update the backlog and sprint of a story
	 */
	void updateSprintAndBacklog(Story t) ;
	
	
	
}
