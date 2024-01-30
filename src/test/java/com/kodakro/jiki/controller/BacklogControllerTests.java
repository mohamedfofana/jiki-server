package com.kodakro.jiki.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.kodakro.jiki.model.Backlog;
import com.kodakro.jiki.service.BacklogService;

@ExtendWith(MockitoExtension.class)
public class BacklogControllerTests {
	@InjectMocks
	BacklogController backlogController;
	
	@Mock
	BacklogService backlogService;
	
	@Test
	public void testFindAll() {
		MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
		Backlog expected1 = Backlog.builder().id(1l).description("test").build(); 
		Backlog expected2 = Backlog.builder().id(2l).description("test").build(); 
        var expected = List.of(expected1, expected2);
        when(backlogService.findAll()).thenReturn(expected);
        
        var result = backlogController.findAll();
        
        
        assertThat(result.size()).isEqualTo(2);
        assertThat(result.get(0).getDescription()).isEqualTo(expected1.getDescription());
        assertThat(result.get(1).getDescription()).isEqualTo(expected2.getDescription());
	}
	
	
	@Test
	public void testFindById() {
		final long id = 1l;
		MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
		Backlog expected = Backlog.builder().id(1l).description("test").build(); 
        when(backlogService.findById(id)).thenReturn(expected);
        
        var result = backlogController.findById(id);
        
        
        assertThat(result).isNotNull();
        assertThat(result.getDescription()).isEqualTo(expected.getDescription());
	}

	@Test
	public void testCreate() {
		MockHttpServletRequest request = new MockHttpServletRequest();
		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
		Backlog expected = Backlog.builder().id(1l).description("test").build(); 
		when(backlogService.create(Mockito.any(Backlog.class))).thenReturn(expected);
		
		var result = backlogController.register(expected);
		
		
		assertThat(result).isNotNull();
		assertThat(result.getStatusCodeValue()).isEqualTo(200);
	}
	
	@Test
	public void testDelete_OK() {
		final long id = 1l;
		MockHttpServletRequest request = new MockHttpServletRequest();
		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
		when(backlogService.deleteById(id)).thenReturn(true);
		
		var result = backlogController.deleteById(id);
		
		
		assertThat(result).isNotNull();
		assertThat(result.getStatusCodeValue()).isEqualTo(200);
	}

	@Test
	public void testDelete_KO() {
		final long id = 1l;
		MockHttpServletRequest request = new MockHttpServletRequest();
		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
		when(backlogService.deleteById(id)).thenReturn(false);
		
		var result = backlogController.deleteById(id);
		
		
		assertThat(result).isNotNull();
		assertThat(result.getStatusCodeValue()).isEqualTo(200);
	}
	
}
