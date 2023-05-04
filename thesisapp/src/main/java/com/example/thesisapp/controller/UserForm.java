package com.example.thesisapp.controller;

import com.example.thesisapp.model.Role;

public class UserForm {
    private String username;
    private String password;
    private Role role;
    private String email;

    private String firstName;
    private String lastName;
    // student specific
    private int yearOfStudies;
    private int coursesRemaining;
    private double avgGrade;
    // professor specific
    private String speciality;


    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public Role getRole() {
        return role;
    }
    public void setRole(Role role) {
        this.role = role;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
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
    public int getCoursesRemaining() {
        return coursesRemaining;
    }
    public void setCoursesRemaining(int coursesRemaining) {
        this.coursesRemaining = coursesRemaining;
    }
    public double getAvgGrade() {
        return avgGrade;
    }
    public void setAvgGrade(double avgGrade) {
        this.avgGrade = avgGrade;
    }
    public String getSpeciality() {
        return speciality;
    }
    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }
    
}
