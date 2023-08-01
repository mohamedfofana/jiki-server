
package com.kodakro.jiki.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Category {
	private Long id;

	private Project project;
	
	private String title;
}
