
package com.kodakro.jiki.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Version {
	private Long id;

	private Project project;
	
	private String version;
}
