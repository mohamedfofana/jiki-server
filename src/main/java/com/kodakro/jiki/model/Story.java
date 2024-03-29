package com.kodakro.jiki.model;

import java.io.Serializable;
import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Story  implements Serializable{
	private Long id;
	
	private User reporter;
	
	private Sprint sprint;

	private Backlog backlog;
	
	private Project project;
	
	private Team assignedTeam;
	
	private User assignedUser;
	
	private String shortTitle;

	private String title;
	
	private String description;
	
	private String workflow;

	private String type;

	private String scope;
	
	private String priority;
	
	private Integer businessValue;
	
	private String appliVersion;
	
	private Integer storyPoints;
	
	private String status;
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Timestamp creationDate;
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Timestamp updateDate;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Timestamp startDate;
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")	
	private Timestamp endDate;
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")	
	private Timestamp estimatedEndDate;
}
