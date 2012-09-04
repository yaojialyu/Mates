package me.test;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import me.config.Config;
import me.controller.Smack;

import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;

public class smackTest {
	

	/**
	 * @param args
	 * @throws XMPPException 
	 */
	public static void main(String[] args) throws XMPPException {
		Smack smack = new Smack();
		XMPPConnection connection = smack.connect();
		//connection.login("2020202021", "2020202021");			//登录用户. 更改房间主题时需要登录. 但服务器允许免登录创建新用户.
		
		//print("Service Name: "+connection.getServiceName());	//查服务器名, 我们的服务器定为 mates
		
		
		//测试创建用户,然后断开连接
		Map<String, String> userAttritubes = new HashMap<String, String>();
		String userWeiboID = "20202020233";
		userAttritubes.put("name", "Kevin");	//创建用户名(聊天昵称)
		smack.createUser(connection, userWeiboID, userAttritubes);	
		smack.disconnect();
        
		
		//根据房间jid,更改房间主题
		//smack.changeRoomSubject(connection, "a101@uic.mates", "new subject");
       
        
/*		 //查询uic.mates上的聊天室 
        Collection<HostedRoom> rooms = MultiUserChat.getHostedRooms(connection, Config.SERVICE);
        for(HostedRoom room : rooms) { 
        	//查看Room信息 
        	System.out.println(room.getName() + " - " +room.getJid()); 			//房间的Jid是唯一的,相当于id了 
        	RoomInfo roomInfo = MultiUserChat.getRoomInfo(connection, room.getJid());
		    if(roomInfo != null) { 
		    	//在线人数
		    	print("在线人数:"+roomInfo.getOccupantsCount());
		       	//房间主题
		    	print("房间主题:"+roomInfo.getSubject());
		       	//房间描述
		    	print("房间描述:"+roomInfo.getDescription()+"\n");
		    }   
		    
         }  */
        
        
        
    /*    //已知room id, 查看聊天室信息:
        MultiUserChat muc = new MultiUserChat(connection, "a101@uic.mates");
        muc.join("admin");  //一定要join,否则muc无法调用
        
        //有哪些人在聊天室
        for (Iterator iterator = muc.getOccupants(); iterator.hasNext();) {
        	String type = (String) iterator.next();
        	print(type);
		}*/
        
		
       
	}
	
	
	public static void print(Object object) {
		System.out.println(object);
	}


}
