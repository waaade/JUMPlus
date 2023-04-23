package com.cognixia.gradebook.dao;

import java.util.List;
import java.util.Optional;

public interface ClassStudentDao {
	public List<ClassStudent> getAllClassStudent();
	
	public List<StudentGrade> getStudentsByClassId(int classId, boolean orderByGrade);
	
	public Optional<ClassStudent> getClassStudentById(int id);
	
	public Optional<ClassStudent> getByClassAndStudentId(int classId, int studentId);
	
	public boolean createClassStudent(ClassStudent classStudent);
	
	public boolean deleteClassStudent(int id);
	
	public boolean updateClassStudent(ClassStudent classStudent);
}
