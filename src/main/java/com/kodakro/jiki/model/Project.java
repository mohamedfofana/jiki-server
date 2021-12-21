package com.kodakro.jiki.model;

import java.io.Serializable;
import java.sql.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Project implements Serializable{
	private Long id;
	
	private String name;
	
	private String description;
	
	private String status;
	
	private Backlog backlog;
	
	private Team team;
	
	private Date creationDate;
	
	private Date endDate;
	
	private Date updateDate;
	
}
