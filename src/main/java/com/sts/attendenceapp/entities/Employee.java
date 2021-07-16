 package com.sts.attendenceapp.entities;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;


@Entity
@Table(name = "Employees")
public class Employee {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "emp_id")
	private int id;
	
	@Column(name = "firstName")
	@NotBlank(message = "Please Enter the First Name")
	private String firstName;
	
	@Column(name = "lastName")
	@NotBlank(message = "Please Enter the Last Name")
	private String lastName;
	
	@Pattern(message = "Please Enter Valid Email Address",regexp = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$")
	@Column(unique = true)
	private String email;
	
	/*
	@Size(min = 8, max = 15,message = "Please Enter Minimum 8 Characters and Maximum 15 Characters Password")
	private String password;
	*/
	
	private String password;
	
	private boolean passwordReset;
	
	@NotBlank(message = "Please Enter the Designation")
	private String designation;
	@NotBlank(message = "Please Enter the Branch")
	private String branch;
	
	@Column(name = "mobileNo")
	@Pattern(regexp = "(0|91)?[7-9][0-9]{9}",message = "Please Enter Valid Mobile Number")
	private String mobileNo;
	
	@NotBlank(message = "Please Enter the Qualification")
	private String qualification;
	
	private String gender;

	@ManyToOne
    private Role role;
	
	@ManyToOne
    private Department department;
	
	@Column(name = "headId")
	private long headId;
	@Column(name = "headName")
	private String headName;
	@Column(name = "headEmail")
	private String headEmail;
	@Column(name = "createdDate")
	private Date createdDate;
	@Column(name = "createdBy")
	private long createdBy;
	@Column(name = "modifiedDate")
	private Date modifiedDate;
	@Column(name = "modifiedBy")
	private long modifiedBy;
	private boolean active;
	
	@Column(name = "activeDate")
	private Date activeDate;
	@Column(name = "activeById")
	private long activeById;
	private boolean deleted;
	
	@Column(name = "deletedDate")
	private Date deletedDate;
	@Column(name = "deletedById")
	private long deletedById;
	
	public Employee() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getQualification() {
		return qualification;
	}

	public void setQualification(String qualification) {
		this.qualification = qualification;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public long getHeadId() {
		return headId;
	}

	public void setHeadId(long headId) {
		this.headId = headId;
	}

	public String getHeadName() {
		return headName;
	}

	public void setHeadName(String headName) {
		this.headName = headName;
	}

	public String getHeadEmail() {
		return headEmail;
	}

	public void setHeadEmail(String headEmail) {
		this.headEmail = headEmail;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(long createdBy) {
		this.createdBy = createdBy;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public long getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(long modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Date getActiveDate() {
		return activeDate;
	}

	public void setActiveDate(Date activeDate) {
		this.activeDate = activeDate;
	}

	public long getActiveById() {
		return activeById;
	}

	public void setActiveById(long activeById) {
		this.activeById = activeById;
	}

	public Date getDeletedDate() {
		return deletedDate;
	}

	public void setDeletedDate(Date deletedDate) {
		this.deletedDate = deletedDate;
	}

	public long getDeletedById() {
		return deletedById;
	}

	public void setDeletedById(long deletedById) {
		this.deletedById = deletedById;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public boolean isPasswordReset() {
		return passwordReset;
	}

	public void setPasswordReset(boolean passwordReset) {
		this.passwordReset = passwordReset;
	}
		
}
