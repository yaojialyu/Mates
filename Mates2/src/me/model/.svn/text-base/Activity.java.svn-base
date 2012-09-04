package me.model;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;
@Table("Activity")

public class Activity {
	@Column
	@Id
	private int id;
	
	@Column
	private int rid;
	
	@Column
	private String subject;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getRid() {
		return rid;
	}
	public void setRid(int rid) {
		this.rid = rid;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	@Override
	public String toString() {
		return "Activity [id=" + id + ", rid=" + rid + ", subject="
				+ subject + "]";
	}
	
	
}
