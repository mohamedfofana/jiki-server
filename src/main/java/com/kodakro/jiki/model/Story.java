package com.kodakro.jiki.model;

import java.io.Serializable;
import java.sql.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Story  implements Serializable{
	private Long id;
	
	private User reporter;
	
	private Sprint sprint;

	private Backlog backlog;
	
	private Project project;
	
	private Team assignedTeam;
	
	private User assignedUser;
	
	private String title;
	
	private String description;
	
	private String workflow;

	private String type;
	
	private String priority;
	
	private Integer businessValue;
	
	private String appliVersion;
	
	private Integer storyPoints;
	
	private String status;
	
	private Date creationDate;
	
	private Date updateDate;
	
	private Date startDate;
	
	private Date endDate;
	
	private Date estimatedEndDate;
}
