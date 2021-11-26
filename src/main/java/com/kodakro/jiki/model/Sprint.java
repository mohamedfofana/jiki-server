package com.kodakro.jiki.model;

import java.io.Serializable;
import java.sql.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Sprint  implements Serializable{
	private Long id;
	
	private User reporter;
	
	private Project project;
	
	private Backlog backlog;
	
	private String title;
	
	private String description;
	
	private String workflow;

	private String status;
	
	private String appliVersion;
	
	private Integer businessValue;
	
	private Integer storyPoints;
	
	private Date startDate;
	
	private Date endDate;
	
	private Date estimatedEndDate;
	
	private Date creationDate;
	
	private Date updateDate;

}
