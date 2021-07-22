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
	    
	    @ManyToOne(targetEntity = Employee.class, fetch = FetchType.EAGER)
	    @JoinColumn(nullable = false, name = "emp_id")
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
	    
		
}
