package com.cognixia.gradebook.dao;

import java.util.List;
import java.util.Optional;

public interface ClassStudentDao {
	public List<ClassStudent> getAllClassStudent();
	
	public Optional<ClassStudent> getClassStudentById(int id);
	
	public boolean createClassStudent(ClassStudent classStudent);
	
	public boolean deleteClassStudent(int id);
	
	public boolean updateClassStudent(ClassStudent classStudent);
}
