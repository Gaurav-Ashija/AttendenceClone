package com.sts.attendenceapp.entities;

public class Mail {
	
	 	private String from;
	    private String to;
	    private String subject;
	    private String UUID;

	    public Mail() {
	    }

	    public Mail(String from, String to, String subject, String UUID) {
	        this.from = from;
	        this.to = to;
	        this.subject = subject;
	        this.UUID = UUID;
	    }

	    public String getFrom() {
	        return from;
	    }

	    public void setFrom(String from) {
	        this.from = from;
	    }

	    public String getTo() {
	        return to;
	    }

	    public void setTo(String to) {
	        this.to = to;
	    }

	    public String getSubject() {
	        return subject;
	    }

	    public void setSubject(String subject) {
	        this.subject = subject;
	    }

	    public String getUUID() {
	        return UUID;
	    }

	    public void setUUID(String UUID) {
	        this.UUID = UUID;
	    }

	    @Override
	    public String toString() {
	        return "Mail{" +
	                "from='" + from + '\'' +
	                ", to='" + to + '\'' +
	                ", subject='" + subject + '\'' +
	                ", UUID='" + UUID + '\'' +
	                '}';
	    }

}
