package com.cognixia.gradebook.dao;

public class SchoolClass {
	private int id;
	private int teacherId;
	private String className;
	public SchoolClass(int id, int teacherId, String className) {
		super();
		this.id = id;
		this.teacherId = teacherId;
		this.className = className;
	}
	public SchoolClass(int teacherId, String className) {
		super();
		this.id = 0;
		this.teacherId = teacherId;
		this.className = className;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getTeacherId() {
		return teacherId;
	}
	public void setTeacherId(int teacherId) {
		this.teacherId = teacherId;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	@Override
	public String toString() {
		return "Class [id=" + id + ", teacherId=" + teacherId + ", className=" + className + "]";
	}
	
	
}