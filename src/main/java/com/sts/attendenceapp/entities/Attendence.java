package com.sts.attendenceapp.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "Emp_Attendence")
public class Attendence {

	    @Id
	    @GeneratedValue(strategy = GenerationType.AUTO)
		private int id;
	    private String punchIn;
	    private String punchOut;
	    private String workDuration;
	    private Date punchDate;
	    private int punchTimes;
	    private String punchInLocation;
	    private String punchOutLocation;
	    
	    @ManyToOne(targetEntity = Employee.class, fetch = FetchType.EAGER)
	    @JoinColumn(nullable = false, name = "emp_id")
	    @JsonManagedReference
	    private Employee employee;
	    
		public Attendence() {
			
		}

		public Attendence(Employee employee) {
			this.employee = employee;
		}

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public String getPunchIn() {
			return punchIn;
		}

		public void setPunchIn(String punchIn) {
			this.punchIn = punchIn;
		}

		public String getPunchOut() {
			return punchOut;
		}

		public void setPunchOut(String punchOut) {
			this.punchOut = punchOut;
		}

		public String getWorkDuration() {
			return workDuration;
		}

		public void setWorkDuration(String workDuration) {
			this.workDuration = workDuration;
		}

		public Date getPunchDate() {
			return punchDate;
		}

		public void setPunchDate(Date punchDate) {
			this.punchDate = punchDate;
		}

		public Employee getEmployee() {
			return employee;
		}

		public void setEmployee(Employee employee) {
			this.employee = employee;
		}

		public int getPunchTimes() {
			return punchTimes;
		}

		public void setPunchTimes(int punchTimes) {
			this.punchTimes = punchTimes;
		}

		public String getPunchInLocation() {
			return punchInLocation;
		}

		public void setPunchInLocation(String punchInLocation) {
			this.punchInLocation = punchInLocation;
		}

		public String getPunchOutLocation() {
			return punchOutLocation;
		}

		public void setPunchOutLocation(String punchOutLocation) {
			this.punchOutLocation = punchOutLocation;
		}
	    		
}
