package com.kodakro.jiki.model;

import java.io.Serializable;
import java.sql.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Team  implements Serializable{
	private Long id;
	
	private String name;
	
	private String status;
	
	private Date creationDate;
	
	private Date updateDate;
	
}
