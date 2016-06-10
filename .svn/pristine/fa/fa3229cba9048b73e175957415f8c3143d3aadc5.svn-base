package com.trgis.ticg.incident.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 * @Description 事件表
 * @author qinm
 * @date 2016年6月8日
 */
@Entity
@Table(name = "event_record")
public class Events implements Serializable{
	private static final long serialVersionUID = 1L;
	
	public Events(long id) {
		this.id = id;
	}
	
	@Id
   // @GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@Column
	private String name;
	
	@Column
	private String place;
	
	@Column
	private String event_type;
	
	@Column
	private String reseauer;
	
	@Column
	private String check_type;
	
	@Column
	private String picture;
	
	@Column
	private String video;
	
	@Column
	private String details;
	
	@Column
	private String occurrence_time;
	
	@Column
	private String geo;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public String getEvent_type() {
		return event_type;
	}

	public void setEvent_type(String event_type) {
		this.event_type = event_type;
	}

	public String getReseauer() {
		return reseauer;
	}

	public void setReseauer(String reseauer) {
		this.reseauer = reseauer;
	}

	public String getCheck_type() {
		return check_type;
	}

	public void setCheck_type(String check_type) {
		this.check_type = check_type;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public String getVideo() {
		return video;
	}

	public void setVideo(String video) {
		this.video = video;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public String getOccurrence_time() {
		return occurrence_time;
	}

	public void setOccurrence_time(String occurrence_time) {
		this.occurrence_time = occurrence_time;
	}

	public String getGeo() {
		return geo;
	}

	public void setGeo(String geo) {
		this.geo = geo;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
