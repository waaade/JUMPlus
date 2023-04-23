package com.cognixia.gradebook.dao;

import java.util.List;
import java.util.Optional;

public interface ClassDao {
	public List<SchoolClass> getAllClass();
	
	public Optional<SchoolClass> getClassById(int id);
	
	public List<SchoolClass> getClassByTeacherId(int teacherId);
	
	public boolean createClass( SchoolClass newClass );
	
	public boolean deleteClass( int id );
	
	public boolean updateClass( SchoolClass classUpdates );
}
