package com.cognixia.gradebook.dao;

import java.util.List;
import java.util.Optional;

public interface StudentDao {
	public List<Student> getAllStudent();
	
	public Optional<Student> getStudentById(int id);
	
	public boolean createStudent( Student student );
	
	public boolean deleteStudent( int id );
	
	public boolean updateStudent( Student student );
}
