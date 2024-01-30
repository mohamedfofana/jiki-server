package com.kodakro.jiki.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.kodakro.jiki.model.Backlog;
import com.kodakro.jiki.repository.BacklogRepository;

@ExtendWith(MockitoExtension.class)
public class BacklogServiceTest {

	@Mock
	BacklogRepository backlogRepository;
	
	@InjectMocks
	BacklogService backlogService;
	
	@Test
	void testCreate_OK() {
		Backlog expected = Backlog.builder().id(1l).description("test").build(); 
		
		when(backlogRepository.create(Mockito.any(Backlog.class))).thenReturn(expected);
		
		final Backlog result = backlogService.create(expected);
		
		assertThat(result).isNotNull();
		assertEquals(expected.getDescription(), result.getDescription());
	}

	@Test
	void testFindById_OK() {
		final long id = 0;
		Backlog expected = Backlog.builder().id(1l).description("test").build(); 
		
		when(backlogRepository.findById(id)).thenReturn(Optional.of(expected));
		
		final Backlog result = backlogService.findById(id);
		
		assertThat(result).isNotNull();
		assertEquals(expected.getId(), result.getId());
	}
	
	@Test
	void testFindAll_OK() {
		Backlog expected = Backlog.builder().id(1l).description("test").build(); 
		Backlog expected2 = Backlog.builder().id(2l).description("test").build(); 
				
		when(backlogRepository.findAll()).thenReturn(List.of(expected, expected2));
		
		final List<Backlog> result = backlogService.findAll();
		
		assertThat(result).isNotNull();
	}
	
	
	@Test
	void testDeleteById_OK() {
		final long id = 1l;
		
		when(backlogRepository.deleteById(id)).thenReturn(true);
		
		final boolean result = backlogService.deleteById(id);
		
		assertThat(result).isNotNull();
		assertTrue(result);
	}

	@Test
	void testDeleteById_KO() {
		final long id = 1l;
		
		when(backlogRepository.deleteById(id)).thenReturn(false);
		
		final boolean result = backlogService.deleteById(id);
		
		assertThat(result).isNotNull();
		assertFalse(result);
	}
}
