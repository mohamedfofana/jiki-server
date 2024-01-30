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
public class Project implements Serializable{
	private Long id;
	
	private String shortname;

	private String name;
	
	private String description;
	
	private String status;
	
	private Backlog backlog;
	
	private Team team;
	
	private Timestamp endDate;
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Timestamp creationDate;
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Timestamp updateDate;
	
}
