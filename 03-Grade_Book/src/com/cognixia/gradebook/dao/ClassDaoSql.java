package com.cognixia.gradebook.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ClassDaoSql implements ClassDao {
	private Connection conn;
	
	public ClassDaoSql(Connection conn) {
		super();
		this.conn = conn;
	}

	@Override
	public List<SchoolClass> getAllClass() {
	List<SchoolClass> classList = new ArrayList<SchoolClass>();
		
		try( Statement stmt = conn.createStatement(); 
			 ResultSet rs = stmt.executeQuery("select * from class");
				) {
			
			while( rs.next() ) {
				// iterate through all the values in the row
				int id = rs.getInt("class_id");
				int teacherId = rs.getInt("teacher_id");
				String className = rs.getString("class_name");
				
				SchoolClass newClass = new SchoolClass(id, teacherId, className);
				
				classList.add(newClass);
			}
 			
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		return classList;

	}

	@Override
	public Optional<SchoolClass> getClassById(int id) {
		try (
			PreparedStatement pstmt = conn.prepareStatement("select * from class where class_id = ?") ) {
			pstmt.setInt(1, id);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				int classId = rs.getInt("class_id");
				int teacherId = rs.getInt("teacher_id");
				String className = rs.getString("class_name");
				rs.close();
				SchoolClass newClass = new SchoolClass(classId, teacherId, className);
				Optional<SchoolClass> classFound = Optional.of(newClass);
				return classFound;
			}
			else {
				rs.close();
				return Optional.empty();
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			return Optional.empty();
		}
	}
	
	@Override
	public List<SchoolClass> getClassByTeacherId(int teacherId) {
		List<SchoolClass> classList = new ArrayList<SchoolClass>();
		try( PreparedStatement pstmt = conn.prepareStatement("select * from class where teacher_id = ?")) {
			pstmt.setInt(1, teacherId);
			ResultSet rs = pstmt.executeQuery();
			while( rs.next() ) {
				// iterate through all the values in the row
				int id = rs.getInt("class_id");
				String className = rs.getString("class_name");
				
				SchoolClass newClass = new SchoolClass(id, teacherId, className);
				classList.add(newClass);
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		return classList;
	}

	@Override
	public boolean createClass(SchoolClass newClass) {
		try (PreparedStatement pstmt = 
				conn.prepareStatement("insert into class (teacher_id, class_name) values (?,?)")) {
			pstmt.setInt(1, newClass.getTeacherId());
			pstmt.setString(2, newClass.getClassName());
			int result = pstmt.executeUpdate();
			if (result == 1) {
				System.out.println("Class added to database!");
				return true;
			}
			else {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean deleteClass(int id) {
		try (PreparedStatement pstmt = conn.prepareStatement("delete * from class where class_id = ?")) {
			pstmt.setInt(1, id);
			int result = pstmt.executeUpdate();
			if (result == 1) {
				return true;
			} 
			else {
				return false;
			}
		} catch(SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean updateClass(SchoolClass classUpdates) {
		// TODO Auto-generated method stub
		return false;
	}

}
