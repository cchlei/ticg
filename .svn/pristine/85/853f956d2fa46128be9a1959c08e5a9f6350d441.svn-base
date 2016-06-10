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
 * @Description 事件流水表
 * @author qinm
 * @date 2016年6月8日
 */
@Entity
@Table(name = "event_flowing")
public class Flowing implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private long flow_id;
	
	@Column
	private long event_id;
	
	@Column
	private long event_flowing;

	public long getFlow_id() {
		return flow_id;
	}

	public void setFlow_id(long flow_id) {
		this.flow_id = flow_id;
	}

	public long getEvent_id() {
		return event_id;
	}

	public void setEvent_id(long event_id) {
		this.event_id = event_id;
	}

	public long getEvent_flowing() {
		return event_flowing;
	}

	public void setEvent_flowing(long event_flowing) {
		this.event_flowing = event_flowing;
	}
	
	
}
