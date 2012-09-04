package hk.edu.uic.mates.model.db;

import hk.edu.uic.mates.model.vo.Room;
import hk.edu.uic.mates.model.vo.Room.Area;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;

public class RoomDAO extends AndroidTestCase {

	private Context context;

	public RoomDAO() {

	}

	public RoomDAO(Context context) {
		this.context = context;
	}

	/**
	 * 即时搜索聊天室（当输入框中第一位为字母、第二位开始为数字时调用）
	 * 
	 * @param roomArea
	 *            房间区域
	 * @param roomName
	 *            房间名
	 * @return roomList 房间列表
	 */
	public List<Room> searchRoom(Area roomArea, String roomName) {
		ArrayList<Room> array = new ArrayList<Room>();
		Room room = new Room();
		String sql = "select * from Room where roomArea = '" + roomArea
				+ "' and roomName = '" + roomName + "'";
		try {
			SQLiteDatabase dB = new DatabaseHelper(this.context)
					.getReadableDatabase();
			Cursor cursor = dB.rawQuery(sql, new String[] {});
			while (cursor.moveToNext()) {
				room.setId(cursor.getInt(cursor.getColumnIndex("id")));
				// room.setArea(cursor.getString(cursor.getColumnIndex("roomArea")));
				room.setArea(roomArea);
				room.setRoomName(cursor.getString(cursor
						.getColumnIndex("roomName")));
				room.setLon(cursor.getDouble(cursor.getColumnIndex("lon")));
				room.setLat(cursor.getDouble(cursor.getColumnIndex("lat")));
				room.setLonError(cursor.getDouble(cursor
						.getColumnIndex("lonError")));
				room.setLatError(cursor.getDouble(cursor
						.getColumnIndex("latError")));
				room.setOpenfireRoomId(cursor.getString(cursor
						.getColumnIndex("openfireRoomId")));
				array.add(room);

			}
			cursor.close();
			dB.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return array;
	}

	/**
	 * 即时搜索聊天室（当输入框中仅有一位并为字母时调用）
	 * 
	 * @param roomArea
	 *            房间区域
	 * @return roomList 房间列表
	 */
	public List<Room> searchRoom(Area roomArea) {
		ArrayList<Room> array = new ArrayList<Room>();
		Room room = new Room();
		String sql = "select * from Room where roomArea = '" + roomArea + "'";
		try {
			SQLiteDatabase dB = new DatabaseHelper(this.context)
					.getReadableDatabase();
			Cursor cursor = dB.rawQuery(sql, new String[] {});
			while (cursor.moveToNext()) {
				room.setId(cursor.getInt(cursor.getColumnIndex("id")));
				room.setArea(Area.valueOf(cursor.getString(cursor
						.getColumnIndex("roomArea"))));
				room.setRoomName(cursor.getString(cursor
						.getColumnIndex("roomName")));
				room.setLon(cursor.getDouble(cursor.getColumnIndex("lon")));
				room.setLat(cursor.getDouble(cursor.getColumnIndex("lat")));
				room.setLonError(cursor.getDouble(cursor
						.getColumnIndex("lonError")));
				room.setLatError(cursor.getDouble(cursor
						.getColumnIndex("latError")));
				room.setOpenfireRoomId(cursor.getString(cursor
						.getColumnIndex("openfireRoomId")));
	//			Log.d(Config.TAG, room.toString());
				array.add(room);
			}
			cursor.close();
			dB.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 即时搜索聊天室（当输入框中第一位为字母时调用）
	 * 
	 * @param roomName
	 *            房间名
	 * @return roomList 房间列表
	 */
	public List<Room> searchRoom(String roomName) {
		LinkedList<Room> array = new LinkedList<Room>();
		
		String sql = "select * from Room where openfireRoomId like ?";
		try {
			SQLiteDatabase dB = new DatabaseHelper(this.context).open();
			Cursor cursor = dB.rawQuery(sql, new String[] { "%" + roomName
					+ "%@%" });
			while (cursor.moveToNext()) {
				Room room = new Room();
				room.setId(cursor.getInt(cursor.getColumnIndex("id")));
				// room.setArea(cursor.getString(cursor.getColumnIndex("roomArea")));
				room.setArea(Area.valueOf(cursor.getString(cursor
						.getColumnIndex("roomArea"))));
				room.setRoomName(cursor.getString(cursor
						.getColumnIndex("roomName")));
				room.setLon(cursor.getDouble(cursor.getColumnIndex("lon")));
				room.setLat(cursor.getDouble(cursor.getColumnIndex("lat")));
				room.setLonError(cursor.getDouble(cursor
						.getColumnIndex("lonError")));
				room.setLatError(cursor.getDouble(cursor
						.getColumnIndex("latError")));
				room.setOpenfireRoomId(cursor.getString(cursor
						.getColumnIndex("openfireRoomId")));
				array.add(room);
			}
			cursor.close();
			dB.close();
			return array;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 清空表Room数据 更新房间信息
	 * 
	 * @param roomList
	 * @return
	 */
	public boolean updateRoomList(List<Room> roomList) {
		boolean isUp = false;
		int i;
		String sql = "delete from Room";
		try {
			SQLiteDatabase dB = new DatabaseHelper(this.context).open();
			dB.execSQL(sql);
			for (i = 0; i < roomList.size(); i++) {
				String sql_1 = "insert into Room(roomArea, roomName, openfireRoomId, lon, lat, lonError, latError) values"
						+ "('"
						+ roomList.get(i).getArea()
						+ "', "
						+ "'"
						+ roomList.get(i).getRoomName()
						+ "', '"
						+ roomList.get(i).getOpenfireRoomId()
						+ "',"
						+ "'"
						+ roomList.get(i).getLon()
						+ "', '"
						+ roomList.get(i).getLat()
						+ "' ,"
						+ "'"
						+ roomList.get(i).getLonError()
						+ "', '"
						+ roomList.get(i).getLatError() + "')";
				dB.execSQL(sql_1);
				isUp = true;
			}
			dB.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return isUp;
	}

	/**
	 * 根据roomId array获取相应房间列表
	 * 
	 * @param rids
	 *            常去房间id
	 * @return roomList 常去房间
	 */
	public List<Room> getRoomList(ArrayList<Integer> rids) {
	//	Log.d(Config.TAG, rids.toString());
		LinkedList<Room> array = new LinkedList<Room>();
		SQLiteDatabase dB = new DatabaseHelper(this.context).open();
		int i;
		try {
			for (i = 0; i < rids.size(); i++) {
			//	Log.d(Config.TAG, rids.get(i)+"");
				String sql = "select * from Room where id = '"+rids.get(i)+"'";
				Cursor cursor = dB.rawQuery(sql, new String[] {});
			//	Log.d(Config.TAG,"getroomlist: " + sql);
			//	Log.d(Config.TAG, cursor.getCount()+"");
				while (cursor.moveToNext()) {
					Room room = new Room();
					room.setId(cursor.getInt(cursor.getColumnIndex("id")));
					room.setArea(Area.valueOf(cursor.getString(cursor
							.getColumnIndex("roomArea"))));
					room.setRoomName(cursor.getString(cursor
							.getColumnIndex("roomName")));
					room.setLon(cursor.getDouble(cursor.getColumnIndex("lon")));
					room.setLat(cursor.getDouble(cursor.getColumnIndex("lat")));
					room.setLonError(cursor.getDouble(cursor
							.getColumnIndex("lonError")));
					room.setLatError(cursor.getDouble(cursor
							.getColumnIndex("latError")));
					room.setOpenfireRoomId(cursor.getString(cursor
							.getColumnIndex("openfireRoomId")));
				//	System.out.println(room.toString());
					array.add(room);
				}
				cursor.close();
			}
			
			dB.close();
			return array;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
