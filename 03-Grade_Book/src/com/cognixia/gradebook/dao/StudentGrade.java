package com.cognixia.gradebook.dao;

public class StudentGrade {
	private String name;
	private int studentId;
	private int grade;
	public StudentGrade(String name, int studentId, int grade) {
		super();
		this.name = name;
		this.studentId = studentId;
		this.grade = grade;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getGrade() {
		return grade;
	}
	public void setGrade(int grade) {
		this.grade = grade;
	}
	public int getStudentId() {
		return studentId;
	}
	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}
	@Override
	public String toString() {
		return "StudentGrade [name=" + name + ", studentId=" + studentId + ", grade=" + grade + "]";
	}
	
}
