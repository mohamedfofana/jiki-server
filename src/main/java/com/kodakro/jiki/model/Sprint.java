package com.kodakro.jiki.model;

import java.io.Serializable;
import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Sprint  implements Serializable{
	private Long id;
	
	private User reporter;
	
	private Project project;
	
	private String title;
	
	private String description;
	
	private String status;
	
	private Integer businessValue;
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Timestamp endDate;
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Timestamp creationDate;
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Timestamp updateDate;

}
