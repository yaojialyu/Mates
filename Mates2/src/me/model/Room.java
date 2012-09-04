package me.model;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

@Table("Room")
public class Room {
	@Column
	@Id
	private int id;
	
	@Column
	private Area area;
	
	@Column
	private String roomName;
	
	@Column
	private String openfireRoomId;
	
	@Column
	private double lon;
	
	@Column
	private double lat;
	
	@Column
	private double lonError;
	
	@Column
	private double latError;

	
	
	public static enum Area {
		A,B,C,D,E,F,SHCV_1,SHCV_2,SHCV_3,SHCV_4,SHCV_5,SHCV_6,SHCV_8
	}

	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getRoomName() {
		return roomName;
	}
	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}
	
	public String getOpenfireRoomId() {
		return openfireRoomId;
	}
	
	public void setOpenfireRoomId(String openfireRoomId) {
		this.openfireRoomId = openfireRoomId;
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
	public double getLonError() {
		return lonError;
	}
	public void setLonError(double lonError) {
		this.lonError = lonError;
	}
	public double getLatError() {
		return latError;
	}
	public void setLatError(double latError) {
		this.latError = latError;
	}
	public Area getArea() {
		return area;
	}
	public void setArea(Area area) {	
		this.area = area;

	}
	
	
	@Override
	public String toString() {
		return "Room [id=" + id + ", area=" + area + ", roomName="
				+ roomName + ", openfireRoomId=" + openfireRoomId + ", lon="
				+ lon + ", lat=" + lat + ", lonError=" + lonError
				+ ", latError=" + latError + "]";
	}
	
	
}
