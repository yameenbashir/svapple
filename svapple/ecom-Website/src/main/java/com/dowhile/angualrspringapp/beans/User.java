package com.dowhile.angualrspringapp.beans;

public class User {

	private String firstName;
	private String lastName;
	private String email;
	private String job;
	private String company;
	private String industry;
	
	
	
	
	
	public User(String firstName, String lastName, String email, String job,
			String company, String industry) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.job = job;
		this.company = company;
		this.industry = industry;
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
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getJob() {
		return job;
	}
	public void setJob(String job) {
		this.job = job;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getIndustry() {
		return industry;
	}
	public void setIndustry(String industry) {
		this.industry = industry;
	}
	
	
	
	
}
