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
@Table(name = "Login_Master")
public class Login {
	
	    @Id
	    @GeneratedValue(strategy = GenerationType.AUTO)
		private int id;
	    private Date loginDate;
	    private String loginIp;
	    private Date lastLoginDate;
	    private String lastLoginIp;
	    private String browser;
	    private boolean loginActive;
	    
	    @ManyToOne(targetEntity = Employee.class, fetch = FetchType.EAGER)
	    @JoinColumn(nullable = false, name = "emp_id")
	    private Employee employee;
	    
		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public Date getLoginDate() {
			return loginDate;
		}

		public void setLoginDate(Date loginDate) {
			this.loginDate = loginDate;
		}

		public Date getLastLoginDate() {
			return lastLoginDate;
		}

		public void setLastLoginDate(Date lastLoginDate) {
			this.lastLoginDate = lastLoginDate;
		}

		public String getBrowser() {
			return browser;
		}

		public void setBrowser(String browser) {
			this.browser = browser;
		}
		
		public String getLoginIp() {
			return loginIp;
		}

		public void setLoginIp(String loginIp) {
			this.loginIp = loginIp;
		}

		public String getLastLoginIp() {
			return lastLoginIp;
		}

		public void setLastLoginIp(String lastLoginIp) {
			this.lastLoginIp = lastLoginIp;
		}

		public Employee getEmployee() {
			return employee;
		}

		public void setEmployee(Employee employee) {
			this.employee = employee;
		}

		public boolean isLoginActive() {
			return loginActive;
		}

		public void setLoginActive(boolean loginActive) {
			this.loginActive = loginActive;
		}
			         
}
