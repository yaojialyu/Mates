package me.controller;


import java.util.ArrayList;
import java.util.List;
import me.dao.Mysql;
import me.model.Activity;
import me.model.DataVersion;
import me.model.Room;
import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.dao.impl.NutDao;
import org.nutz.mvc.annotation.*;

/**
 * 此对象处理Room相关的Http Request
 * @author kevin
 *
 */
public class RoomModule {
/*	
	//注: 搜索功能在客户端本地做.
	*//**
	 * 根据keyword 查询房间信息.
	 * @param keyword
	 * @return List<Room>  房间对象列表
	 *//*
	@At("/room/search/?")	//?接收keyword   如: /room/search/a101    则a101为keyword
	@Ok("json:{ignoreNull:false}")
	public List<Room> searchRoom(String keyword) {	
		
		return null;										
	}
*/
	
	/**
	 * 客户端发送它的房间version给服务端.
	 * 服务端检测: 若该version为最新房间数据版本的话,返回null.
	 * 若不是最新版本的话,返回所有Room的信息,为json格式
	 * 
	 * 客户端应相对应的判断返回结果是否为null. 
	 * 若返回结构不为null则更新客户端房间信息.
	 * 
	 * @param version
	 * @return
	 */
	@Encoding(input = "UTF-8", output = "UTF-8")
	@At("/room/checkUpdate")   
	@Ok("json:{ignoreNull:false}")
	public List<Room> checkUpdate(@Param("version") String version) {
		Dao dao = new NutDao(Mysql.sds());
		//DataVersion dataVersion = dao.fetch(DataVersion.class, dao.getMaxId(DataVersion.class));			//先获取最大id值, 再根据这个id值获取对象
		DataVersion dataVersion = dao.query(DataVersion.class, Cnd.orderBy().desc("id"), dao.createPager(1, 1)).get(0);	//根据date大小倒序查找, 只显示第一页第一个记录, 也就是最新的那个
		
		
		if (dataVersion!=null&&version.equals(dataVersion.getVersion())) {
			return null;
		}
		else {
			return dao.query(Room.class, null,null);			
		}
		
	}
	
	
	/**
	 * 根据单个房间id, 获取房间"正在进行"的主题
	 * 页面直接返回subject json格式
	 * 若查无该房间的话, 返回字符串"null"
	 * 
	 * @param roomId
	 * @return json
	 */
	@Encoding(input = "UTF-8", output = "UTF-8")
	@At("/room/topic/?")
	@Ok("json")		
	public Subject checkRoomTopic(int roomId) {
		Dao dao = new NutDao(Mysql.sds());
		Activity activity = dao.fetch(Activity.class,Cnd.where("rid","=",roomId));
		if(activity==null) {
			return null;
		}
		Subject subject = new Subject(activity.getSubject());
		
		return subject;
	}

	
	/**
	 * 修改房间主题
	 * @param roomId
	 * @return boolean
	 */
	@Encoding(input = "UTF-8", output = "UTF-8")
	@At("/room/topic/modify/?")
	@Ok("raw:html")		//直接返回string值,非json
	public boolean modifyRoomTopic(int roomId,@Param("newSubject") String newSubject) {
		if(newSubject==null){
			return false;
		}
		Dao dao = new NutDao(Mysql.sds());
		Activity activity = dao.fetch(Activity.class,Cnd.where("rid","=",roomId));
		activity.setSubject(newSubject);
		if(dao.update(activity)==1) {		//1代表成功
			return true;
		}
		return false;
	}
	
	
	/**
	 * 批量获取房间主题
	 * @param topics
	 * @return
	 */
	@Encoding(input = "UTF-8", output = "UTF-8")
	@At("/room/getRoomTopics")
	@Ok("json")	
	public List<Subject> getRoomTopics(@Param("roomIds") String roomIds) {
		if (roomIds==null) {
			return null;
		}
		String[] rooms = roomIds.split(",");
		Dao dao = new NutDao(Mysql.sds());
		Activity activity;
		List<Subject> list = new ArrayList<Subject>();
		Subject subject;
		for (int i = 0; i < rooms.length; i++) {
			activity = dao.fetch(Activity.class, Cnd.where("rid","=",rooms[i]));
			if(activity!=null){		//找不到房间主题则不加入list
				subject = new Subject(activity.getSubject());
				list.add(subject);
			}
		}
		if (list.size()==0) {		//找不到任何房间主题记录
			return null;
		}
		return list;
	}

	
	public class Subject{
		private String subject;
		
		public Subject(String subject) {
			this.subject = subject;
		}
		
		public void setSubject(String subject) {
			this.subject = subject;
		}
		public String getSubject() {
			return this.subject;
		}
	}
	

}

