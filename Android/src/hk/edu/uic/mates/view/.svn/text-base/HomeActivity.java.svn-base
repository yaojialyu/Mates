package hk.edu.uic.mates.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.markupartist.android.widget.ActionBar;
import com.markupartist.android.widget.ActionBar.Action;
import com.viewpagerindicator.TitlePageIndicator;
import com.viewpagerindicator.TitleProvider;

import hk.edu.uic.mates.R;
import hk.edu.uic.mates.controller.AsmackUtil;
import hk.edu.uic.mates.controller.FileUtil;
import hk.edu.uic.mates.controller.HttpTransporter;
import hk.edu.uic.mates.controller.JSONParser;
import hk.edu.uic.mates.controller.WeiboUtil;
import hk.edu.uic.mates.model.db.JoinHistoryDAO;
import hk.edu.uic.mates.model.db.RoomDAO;
import hk.edu.uic.mates.model.db.UserDAO;
import hk.edu.uic.mates.model.vo.Room;
import hk.edu.uic.mates.model.vo.Room.Area;
import hk.edu.uic.mates.model.vo.User;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class HomeActivity extends Activity implements OnItemClickListener {

	/** UI Components */
	private ActionBar actionBar;
	private View layout_nearby;
	private View layout_search;
	private View layout_favourite;
	private ViewPager viewpager;
	private TitlePageIndicator pageindicator;
	private LayoutInflater layoutInflater;
	private EditText etxt_search;
	private TextWatcher txtwatcher;
	private List<View> viewsList;
	private String[] titles;
	private ListView listview_room;
	private Action reConnectAsmackAction;

	/** Controllers */
	private JoinHistoryDAO jhd;
	private RoomDAO rd;
	private UserDAO ud;
	private HttpTransporter httpTransporter;
	private WeiboUtil weiboUtil;
	private JSONParser jsonParser;
	private FileUtil fileUtil;

	/** models */
	private User user;
	private List<Room> roomList;
	private List<Map<String, Object>> roomItems;

	/** Adapter */
	private SimpleAdapter roomList_adapter;
	private MyPageAdapter pagedapter;

	/** SharedPreferences */
	private SharedPreferences settings;
	private SharedPreferences.Editor settingsEditor;
	
	private boolean isConnected = false;
	private boolean isConnectFailed = false;
	private GetFavRooms getFavRoomsTask;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_home);

		initUIComponents();
		initUIListener();
		initModels();
		initAdapters();
		initControllers();

		// 获取当前用户资料
		prepareUser();

		// 连接Openfire
		new ConnectOpenfire().execute();

	}

	/**
	 * 获取用户
	 */
	public void prepareUser() {
		// 获得用户对象
		// 1. Intent， 2. SharedPreferences
		settings = getSharedPreferences("settings", 0);
		settingsEditor = settings.edit();
		if (settings.getString("weiboId", null) != null) {
			// 进入这个code块有两种情况
			//Log.d(Config.TAG, "设置文件不为空");
			Intent localIntent = getIntent();
			user = (User) localIntent.getSerializableExtra("user");
			if (user == null) {
				user = new User(settings);
			}
		} else {
			// 注册新用户
			//Log.d(Config.TAG, "设置文件为空");
			Intent localIntent = getIntent();
			String weiboId = localIntent.getStringExtra("weiboId");
			user = jsonParser.parseUserFromSina(weiboUtil.getUserInfo(weiboId));
			user = httpTransporter.signup(user.getWeiboId(),
					user.getWeiboName(), user.getAvatarUrl_small(),
					user.getAvatarUrl_big());
			// 写进sharedPreferences
			settingsEditor.putLong("matesId", user.getMatesId());
			settingsEditor.putString("weiboId", user.getWeiboId());
			settingsEditor.putString("weiboName", user.getWeiboName());
			settingsEditor.putString("avatarUrl_big", user.getAvatarUrl_big());
			settingsEditor.putString("avatarUrl_small",
					user.getAvatarUrl_small());
			settingsEditor.commit();
			// 下载头像
			new DownloadAvatar().execute();

		}
	}

	/**
	 * 初始化UI控件
	 */
	public void initUIComponents() {

		actionBar = (ActionBar) findViewById(R.id.ab_home);
		actionBar.setHomeAction(new Action() {
			
			@Override
			public void performAction(View view) {
				Intent intent = new Intent(HomeActivity.this,SettingsActivity.class);
				startActivityForResult(intent, 1);
			}
			
			@Override
			public int getDrawable() {
				return R.drawable.setting;
			}
		});
		
		reConnectAsmackAction = new Action() {
			
			@Override
			public void performAction(View view) {
				new ConnectOpenfire().execute();
				actionBar.removeAction(reConnectAsmackAction);
			}
			
			@Override
			public int getDrawable() {
				return R.drawable.refresh_red;
			}
		};
		

		viewpager = (ViewPager) findViewById(R.id.pager);
		pageindicator = (TitlePageIndicator) findViewById(R.id.indicator);
		layoutInflater = getLayoutInflater();
		layout_nearby = layoutInflater.inflate(R.layout.layout_nearby, null);
		layout_search = layoutInflater.inflate(R.layout.layout_search, null);
		layout_favourite = layoutInflater.inflate(R.layout.layout_favourite,
				null);

		etxt_search = (EditText) layout_search.findViewById(R.id.etxt_search);

		// List view
		listview_room = (ListView) layout_search
				.findViewById(R.id.listview_search);
		listview_room.setOnItemClickListener(this);

	}

	/**
	 * 初始化UI Listener
	 */
	public void initUIListener() {

		pageindicator.setOnPageChangeListener(new OnPageChangeListener() {
			@Override
			public void onPageSelected(int page) {
				switch (page) {
				// 转到第一个页面
				case 0:
				//	Toast.makeText(HomeActivity.this, "未完成",Toast.LENGTH_SHORT).show();
					break;
				// 转到第二个页面
				case 1:
					if(getFavRoomsTask != null) {
						getFavRoomsTask.cancel(true);
						actionBar.setProgressBarVisibility(View.GONE);
					}
					listview_room = (ListView) layout_search
							.findViewById(R.id.listview_search);
					listview_room.setAdapter(roomList_adapter);
					if (roomList != null) {
						roomList.clear();
					}
					roomItems.clear();
					roomList_adapter.notifyDataSetChanged();
					listview_room.invalidate();
					break;
				// 转到第三个页面
				case 2:
					if (roomList != null) {
						roomList.clear();
					}
					roomItems.clear();
					getFavRoomsTask = new GetFavRooms();
					getFavRoomsTask.execute();
					break;

				default:
					break;
				}

			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// Toast.makeText(HomeActivity.this,
				// "onPageScrolled: "+arg0+" ; "+ arg1 +" ; "+arg2,
				// Toast.LENGTH_SHORT).show();

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
				// Toast.makeText(HomeActivity.this,"onPageScrollStateChanged: "+
				// arg0, Toast.LENGTH_SHORT).show();

			}
		});

		txtwatcher = new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {

				// Toast.makeText(getApplicationContext(), s.toString(),
				// Toast.LENGTH_SHORT).show();
				if (roomList != null) {
					roomList.clear();
				}
				roomItems.clear();
				if (!s.toString().equals("")) {
					roomList = rd.searchRoom(s.toString());
				}
				//Log.d(Config.TAG, roomList.toString());
				for (int i = 0; i < roomList.size(); i++) {
					Map<String, Object> item = new HashMap<String, Object>();

					item.put("rid", roomList.get(i).getId());
					item.put("jid", roomList.get(i).getOpenfireRoomId());
					int id = 0;
					if (roomList.get(i).getArea().equals(Area.A)) {
						id = R.drawable.buliding_a;
					} else if (roomList.get(i).getArea().equals(Area.B)) {
						id = R.drawable.buliding_b;
					} else if (roomList.get(i).getArea().equals(Area.C)) {
						id = R.drawable.buliding_c;
					} else if (roomList.get(i).getArea().equals(Area.D)) {
						id = R.drawable.buliding_d;
					} else if (roomList.get(i).getArea().equals(Area.E)) {
						id = R.drawable.buliding_e;
					} else if (roomList.get(i).getArea().equals(Area.F)) {
						id = R.drawable.buliding_f;
					}
					item.put("roomarea", id);
					item.put("roomname", roomList.get(i).getRoomName());
					item.put("roomtopic", roomList.get(i).getTopic());
					roomItems.add(item);
				}
				roomList_adapter.notifyDataSetChanged();
				listview_room.invalidate();

				/*
				 * try { Thread.sleep(1000); } catch (InterruptedException e) {
				 * // TODO Auto-generated catch block e.printStackTrace(); }
				 */
			}
		};
		etxt_search.addTextChangedListener(txtwatcher);
	}

	/**
	 * 初始化Controllers
	 */
	public void initControllers() {
		titles = getResources().getStringArray(R.array.titles);
		pagedapter = new MyPageAdapter();
		viewpager.setAdapter(pagedapter);
		pageindicator.setViewPager(viewpager);

		viewsList = new ArrayList<View>();
		viewsList.add(layout_nearby);
		viewsList.add(layout_search);
		viewsList.add(layout_favourite);

		// 初始化当前显示的view
		viewpager.setCurrentItem(1);

		jhd = new JoinHistoryDAO(getApplication());
		httpTransporter = new HttpTransporter();
		weiboUtil = new WeiboUtil(HomeActivity.this);
		fileUtil = new FileUtil();
		jsonParser = new JSONParser();
		rd = new RoomDAO(getApplication());
		ud = new UserDAO(getApplication());
	}

	/**
	 * 初始化models
	 */
	public void initModels() {
		roomItems = new ArrayList<Map<String, Object>>();
	}

	/**
	 * 初始化Adapter
	 */
	public void initAdapters() {
		roomList_adapter = new SimpleAdapter(this, roomItems,
				R.layout.listitem_room, new String[] { "rid", "jid",
						"roomarea", "roomname", "roomtopic" }, new int[] {
						R.id.listitem_roomId, R.id.listitem_roomJid,
						R.id.listitem_roomarea, R.id.listitem_roomname,
						R.id.listitem_roomtopic });
		listview_room.setAdapter(roomList_adapter);
	}

	class MyPageAdapter extends PagerAdapter implements TitleProvider {

		@Override
		public String getTitle(int position) {
			return titles[position % titles.length];
		}

		@Override
		public void destroyItem(View arg0, int arg1, Object arg2) {
			((ViewPager) arg0).removeView(viewsList.get(arg1));
		}

		@Override
		public int getCount() {
			return viewsList.size();
		}

		@Override
		public Object instantiateItem(View arg0, int arg1) {
			((ViewPager) arg0).addView(viewsList.get(arg1), 0);
			return viewsList.get(arg1);
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == (arg1);
		}
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// Join room
		if (isConnected) {
			@SuppressWarnings("unchecked")
			HashMap<String, Object> map = (HashMap<String, Object>) listview_room
					.getItemAtPosition(arg2);

			Room room = new Room();
			room.setId((Integer) map.get("rid"));
			room.setOpenfireRoomId((String) map.get("jid"));
			//Log.d(Config.TAG, map.get("rid") + " " + room.toString());
			//Log.d(Config.TAG, user.toString());
			// room.setArea(Area.valueOf(((String) map.get("roomarea"))));
			// room.setRoomName((String) map.get("roomname"));
			// room.setOpenfireRoomId((String) map.get("jid"));

			// 更新 访问记录
			if (jhd.hasJoinBefore(room.getId(), user.getMatesId())) {
				jhd.updateJoinHistory(room.getId(), user.getMatesId());
			} else {
				jhd.addJoinHistory(room.getId(), user.getMatesId());
			}

			// 跳到chattingroom吧
			Intent i = new Intent(HomeActivity.this, ChattingActivity.class);
			i.putExtra("user", user);
			i.putExtra("rid", room.getId());
			i.putExtra("jid", room.getOpenfireRoomId());
			startActivity(i); 
		} else {
			Toast.makeText(getApplicationContext(), getString(R.string.OpenfireNotConnected), Toast.LENGTH_SHORT).show();
		}
	}

	private class DownloadAvatar extends AsyncTask<String, Void, String> {

		@Override
		protected void onPreExecute() {
			actionBar.setProgressBarVisibility(View.VISIBLE);
			// notice(myId + "");
		}

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			// actionBar.setProgressBarVisibility(View.VISIBLE);

		//	Log.d(Config.TAG, "下载头像。。。");
			Bitmap avatar_big = httpTransporter.getUserAvatar(user
					.getAvatarUrl_big());
			Bitmap avatar_small = httpTransporter.getUserAvatar(user
					.getAvatarUrl_small());
	//		Log.d(Config.TAG, "保存头像。。。");
			String avatarPath_big = fileUtil.writeAvatar(user.getWeiboId(),
					avatar_big, FileUtil.BIG);
			String avatarPath_small = fileUtil.writeAvatar(user.getWeiboId(),
					avatar_small, FileUtil.SMALL);
			settingsEditor.putString("avatarPath_big", avatarPath_big);
			settingsEditor.putString("avatarPath_small", avatarPath_small);
			settingsEditor.commit();

			// actionBar.setProgressBarVisibility(View.GONE);
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			actionBar.setProgressBarVisibility(View.GONE);
		}
	}

	private class GetFavRooms extends AsyncTask<String, Void, String> {

		@Override
		protected void onPreExecute() {
			actionBar.setProgressBarVisibility(View.VISIBLE);
			listview_room = (ListView) layout_favourite
					.findViewById(R.id.listview_fav);
			listview_room.setOnItemClickListener(HomeActivity.this);
			listview_room.setAdapter(roomList_adapter);
			// notice(myId + "");
		}

		@Override
		protected String doInBackground(String... params) {

			
			ArrayList<Integer> rids = jhd.getTopTen(user.getMatesId());
			roomList = rd.getRoomList(rids);
			if (roomList.size() != 0) {
				httpTransporter.getkRoomTopics(roomList);

		//		Log.d(Config.TAG, roomList.toString());
				for (int i = 0; i < roomList.size(); i++) {
					Map<String, Object> item = new HashMap<String, Object>();

					item.put("rid", roomList.get(i).getId());
					item.put("jid", roomList.get(i).getOpenfireRoomId());
					int id = 0;
					if (roomList.get(i).getArea().equals(Area.A)) {
						id = R.drawable.buliding_a;
					} else if (roomList.get(i).getArea().equals(Area.B)) {
						id = R.drawable.buliding_b;
					} else if (roomList.get(i).getArea().equals(Area.C)) {
						id = R.drawable.buliding_c;
					} else if (roomList.get(i).getArea().equals(Area.D)) {
						id = R.drawable.buliding_d;
					} else if (roomList.get(i).getArea().equals(Area.E)) {
						id = R.drawable.buliding_e;
					} else if (roomList.get(i).getArea().equals(Area.F)) {
						id = R.drawable.buliding_f;
					}
					item.put("roomarea", id);
					item.put("roomname", roomList.get(i).getRoomName());
					item.put("roomtopic", roomList.get(i).getTopic());
					roomItems.add(item);
				}
			}

			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			if(roomItems.size()==0){
				Toast.makeText(HomeActivity.this, getString(R.string.noHistory), Toast.LENGTH_SHORT).show();
			}
			roomList_adapter.notifyDataSetChanged(); 
			listview_room.invalidate();
			actionBar.setProgressBarVisibility(View.GONE);
		}

	}

	private class ConnectOpenfire extends AsyncTask<Void, Void, String> {

		@Override
		protected void onPreExecute() {
			actionBar.setProgressBarVisibility(View.VISIBLE);
			// notice(myId + "");
		}

		@Override
		protected String doInBackground(Void... params) {
			// TODO Auto-generated method stub
			// actionBar.setProgressBarVisibility(View.VISIBLE);
			try {
			//	Log.d(Config.TAG, "连接OF");
				if (!AsmackUtil.openConnection(user)) {
					Log.d("MATES", "连接服务器失败");
					return getString(R.string.connectOpenfireFail);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			Log.d("MATES", "连接服务器成功");
			return getString(R.string.connectOpenfireSuccessfully);
		}
 
		@Override
		protected void onPostExecute(String result) {
			Log.d("MATES", result+"ssssssssssssssssssss");
			if(result.equals(getString(R.string.connectOpenfireSuccessfully))){
				actionBar.setProgressBarVisibility(View.GONE);
				isConnected = true;
				if(isConnectFailed==true) {
					actionBar.removeAction(reConnectAsmackAction);
					isConnectFailed = false;
				}
				
			}else {
				//Toast.makeText(HomeActivity.this, "连接服务器失败", Toast.LENGTH_SHORT).show();
				isConnected = false;
				if(isConnectFailed==true) {
					actionBar.removeAction(reConnectAsmackAction);
				}
				isConnectFailed = true;
				actionBar.setProgressBarVisibility(View.GONE);
				actionBar.addAction(reConnectAsmackAction);
			}
			
			Toast.makeText(HomeActivity.this.getApplicationContext(),
					result, Toast.LENGTH_SHORT).show();
		}

	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		AsmackUtil.closeConnection();
	}
	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(resultCode == 1) {
			this.finish();
		}
	}

}
