package com.azureeventgrid.demo.schemamodel;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomSchema {
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getEventTime() {
		return eventTime;
	}
	public void setEventTime(String eventTime) {
		this.eventTime = eventTime;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getEventType() {
		return eventType;
	}
	public void setEventType(String eventType) {
		this.eventType = eventType;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public String getDataVersion() {
		return dataVersion;
	}
	public void setDataVersion(String dataVersion) {
		this.dataVersion = dataVersion;
	}
	public CustomSchema(String id, String eventTime, String subject, String eventType, String data, String dataVersion) {
		super();
		this.id = id;
		this.eventTime = eventTime;
		this.subject = subject;
		this.eventType = eventType;
		this.data = data;
		this.dataVersion = dataVersion;
	}
	private String id; 
	private String eventTime;
	private String subject;
	private String eventType;
	private String data;
	private String dataVersion;
	
	
   }
	


