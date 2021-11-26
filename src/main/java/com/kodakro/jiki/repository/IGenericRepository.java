package com.kodakro.jiki.repository;

import java.util.List;
import java.util.Optional;

public interface IGenericRepository<T> {
	List<T> findAll();

	Optional<T> findById(Long id);

	void deleteById(Long id);

	void update(T t);

	T create(T t);
}
