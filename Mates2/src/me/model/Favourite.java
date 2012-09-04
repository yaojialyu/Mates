package me.model;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;


@Table("Favourite")
public class Favourite {
	@Column
	@Id
	private int id;
	
	@Column
	private int uid;
	
	@Column
	private int rid;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUid() {
		return uid;
	}
	public void setUid(int uid) {
		this.uid = uid;
	}
	public int getRid() {
		return rid;
	}
	public void setRid(int rid) {
		this.rid = rid;
	}
	@Override
	public String toString() {
		return "Favourite [id=" + id + ", uid=" + uid + ", rid=" + rid + "]";
	}
	
	
}
