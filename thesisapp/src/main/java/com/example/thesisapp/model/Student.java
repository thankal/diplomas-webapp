package com.example.thesisapp.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="students")
public class Student{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="student_id")
	private Long id;
	
	@Column(name="first_name")
	private String firstName;

	@Column(name="last_name")
	private String lastName;

	@Column(name="year_of_studies")
	private int yearOfStudies;

	@Column(name="average_grade")
	private double averageGrade;
	
	@Column(name="remaining_courses")
	private int remainingCourses;
	
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "user_fk", referencedColumnName = "user_id")
	private User user;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public int getYearOfStudies() {
		return yearOfStudies;
	}

	public void setYearOfStudies(int yearOfStudies) {
		this.yearOfStudies = yearOfStudies;
	}

	public double getAverageGrade() {
		return averageGrade;
	}

	public void setAverageGrade(double averageGrade) {
		this.averageGrade = averageGrade;
	}

	public int getRemainingCourses() {
		return remainingCourses;
	}

	public void setRemainingCourses(int remainingCourses) {
		this.remainingCourses = remainingCourses;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "Student [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", yearsOfStudy="
				+ yearOfStudies + ", averageGrade=" + averageGrade + ", remainingCourses=" + remainingCourses + "]";
	}	
	
}

