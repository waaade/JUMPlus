package com.cognixia.gradebook.dao;

import java.util.List;
import java.util.Optional;

public interface TeacherDao {
	
	public List<Teacher> getAllTeacher();
	
	public Optional<Teacher> getTeacherById(int id);
	
	public boolean createTeacher( Teacher teacher );
	
	public boolean deleteTeacher( int id );
	
	public boolean updateTeacher( Teacher teacher );
	
}
