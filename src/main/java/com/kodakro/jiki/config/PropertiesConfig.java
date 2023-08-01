package com.kodakro.jiki.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:sql/backlog.properties")
@PropertySource("classpath:sql/team.properties")
@PropertySource("classpath:sql/project.properties")
@PropertySource("classpath:sql/user.properties")
@PropertySource("classpath:sql/sprint.properties")
@PropertySource("classpath:sql/story.properties")
@PropertySource("classpath:sql/version.properties")
@PropertySource("classpath:sql/category.properties")
public class PropertiesConfig {

}
