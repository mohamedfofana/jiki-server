package com.kodakro.jiki.repository;

import java.util.List;
import java.util.Optional;

public interface IGenericRepository<T> {

	/*
	 * Fetch all records from the database
	 */
	List<T> findAll();

	/*
	 * find if a record exists 
	 * @param id
	 * 
	 * Return basic record data without joined records
	 */
	Optional<T> exists(Long id);

	/*
	 * 
	 * Return complete record
	 */
	Optional<T> findById(Long id);

	/*
	 * 
	 * Remove the record from the database
	 */
	boolean deleteById(Long id);

	/*
	 * Update all fields in the record
	 */
	boolean update(T t);

	/*
	 * 
	 * Create a new record
	 */
	T create(T t);
}
