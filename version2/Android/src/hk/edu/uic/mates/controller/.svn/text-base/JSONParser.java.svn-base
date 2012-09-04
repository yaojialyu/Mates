package hk.edu.uic.mates.controller;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import hk.edu.uic.mates.model.vo.Room;
import hk.edu.uic.mates.model.vo.Room.Area;
import hk.edu.uic.mates.model.vo.User;
import hk.edu.uic.mates.model.vo.User.Gender;
import hk.edu.uic.mates.model.vo.User.Grade;
import hk.edu.uic.mates.model.vo.User.Major;

public class JSONParser {

	/**
	 * 解析从Mates服务器传来的JSON字符串，还原User对象
	 * 
	 * @param userStr
	 * @return User对象
	 */
	public User parseUserFromMates(String userStr) {
		try {
			JSONObject o = new JSONObject(userStr);
			User user = new User();
			
			user.setMatesId(o.getLong("id"));
			user.setWeiboId(o.getString("weiboId"));
			user.setWeiboName(o.getString("weiboName"));
			user.setAvatarUrl_big(o.getString("avatarUrl_big"));
			user.setAvatarUrl_small(o.getString("avatarUrl_small"));
			if(!o.getString("gender").equals("null")) {
				user.setGender(Gender.valueOf(o.getString("gender")));
			}
			if(!o.getString("grade").equals("null")) {
				user.setGrade(Grade.valueOf(o.getString("grade")));
			}
			if(!o.getString("major").equals("null")) {
				user.setMajor(Major.valueOf(o.getString("major")));
			}
			if(!o.getString("phone").equals("null")) {
				user.setPhone(o.getString("phone"));
			}
			if(!o.getString("qq").equals("null")) {
				user.setQq(o.getString("qq"));
			}
			
			return user;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * 解析从Sina服务器传来的JSON字符串，还原User对象
	 * 
	 * @param userStr
	 * @return User对象
	 */
	public User parseUserFromSina(String userStr) {
		User user = new User();
		
		try {
			JSONObject o = new JSONObject(userStr);
			user.setWeiboId(o.getString("id"));
			user.setWeiboName(o.getString("screen_name"));
			user.setAvatarUrl_small(o.getString("profile_image_url"));
			user.setAvatarUrl_big(o.getString("avatar_large"));
			return user;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * 解析包含avatarUrl和uid的JSON字符串
	 * 
	 * @param urlAndUid
	 * @return user list
	 */
	public List<User> parseAvatarUrlAndUid(String urlAndUid) {
		List<User> userList = new LinkedList<User>();
		
		try {
			JSONArray ary = new JSONArray(urlAndUid);
			for(int i = 0; i < ary.length(); i++) {
				JSONObject o = ary.getJSONObject(i);
				User u = new User();
				u.setMatesId(o.getLong("id"));
				u.setAvatarUrl_big(o.getString("avatarUrl_big"));
				u.setAvatarUrl_small(o.getString("avatarUrl_small"));
				
				userList.add(u);
			}
			
			return userList;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	
	/**
	 * 解析包含roomlist的JSON字符串
	 * 
	 * @param urlAndUid
	 * @return 数组
	 */
	public List<Room> parseRoomList(String roomListStr) {
		List<Room> roomList = new LinkedList<Room>();
		try {
			JSONArray ary = new JSONArray(roomListStr);
			for(int i = 0; i < ary.length(); i++) {
				JSONObject o = ary.getJSONObject(i);
				Room r = new Room();
				
				r.setArea(Area.valueOf(o.getString("area")));
				r.setRoomName(o.getString("roomName"));
				r.setOpenfireRoomId(o.getString("openfireRoomId"));
				r.setLat(o.getDouble("lat"));
				r.setLon(o.getDouble("lon"));
				r.setLatError(o.getDouble("latError"));
				r.setLonError(o.getDouble("lonError"));
				
				roomList.add(r);
			}
			
			return roomList;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public String parseRoomTopic(String topicStr) {
		try {
			JSONObject o = new JSONObject(topicStr);
			return o.getString("subject");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public ArrayList<String> parseRoomTpoics(String topicsStr) {
		ArrayList<String> topicAry = new ArrayList<String>();
		try {
			JSONArray ary = new JSONArray(topicsStr);
			
			for(int i = 0; i < ary.length(); i++) {
				JSONObject o = ary.getJSONObject(i);
				topicAry.add(o.getString("subject"));
			}
			
			return topicAry;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
