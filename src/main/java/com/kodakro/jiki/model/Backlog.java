package com.kodakro.jiki.model;

import java.io.Serializable;
import java.sql.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Backlog  implements Serializable{
	private Long id;
	
	private String title;
	
	private String description;
	
	private String status;
	
	private Date creationDate;
	
	private Date updateDate;
	
}
