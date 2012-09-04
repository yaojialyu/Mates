package me.model;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

@Table("Geoinfo")
public class GeoInfo {
	@Column
	@Id
	private int id;
	
	@Column
	private double lon;
	
	@Column
	private double lat;
	
	@Column
	private int rid;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public double getLon() {
		return lon;
	}
	public void setLon(double lon) {
		this.lon = lon;
	}
	public double getLat() {
		return lat;
	}
	public void setLat(double lat) {
		this.lat = lat;
	}
	public int getRid() {
		return rid;
	}
	public void setRid(int rid) {
		this.rid = rid;
	}
	@Override
	public String toString() {
		return "GeoInfo [id=" + id + ", lon=" + lon + ", lat=" + lat + ", rid="
				+ rid + "]";
	}
	
	
}
