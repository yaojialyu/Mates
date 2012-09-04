package hk.edu.uic.mates.view;

import hk.edu.uic.mates.controller.Config;
import hk.edu.uic.mates.controller.FileUtil;
import hk.edu.uic.mates.controller.HttpTransporter;
import hk.edu.uic.mates.model.vo.OnlineUser;
import hk.edu.uic.mates.model.vo.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.markupartist.android.widget.ActionBar;
import com.markupartist.android.widget.ActionBar.Action;
import com.markupartist.android.widget.ActionBar.IntentAction;

import hk.edu.uic.mates.R;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.SimpleAdapter.ViewBinder;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class OnlineUsersActivity extends Activity implements OnClickListener, OnItemClickListener {
	/** Called when the activity is first created. */
	
	
	/** UI Components */
	private ActionBar actionBar;
	private GridView grid_onlineusers;
	
	/** Adapters */
	private SimpleAdapter grid_adapter;
	
	/** models */
	private List<Map<String, Object>> userItems;
	private List<OnlineUser> userList;
	private List<User> ul;
	private String roomName;
	private int num;
	
	/** Controllers */
	private HttpTransporter httpTransporter;
	private FileUtil fileUtil;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_olineusers);
		
		/**
		 * 先获取intent
		 */
		Intent myLocalIntent = getIntent();
		userList = (List<OnlineUser>) myLocalIntent.getSerializableExtra("users");
		Log.d("MATES", userList.size() + "=============================");
		roomName = (String) myLocalIntent.getCharSequenceExtra("roomName");
		Log.d("MATES", roomName + "=============================");
		
		this.initUIComponents();
		this.initModels();
		this.initAdapters();
		this.initControllers();
		
		new GetAvatarTask().execute();
		
	}

	/**
	 * 初始化UI控件
	 */
	public void initUIComponents() {
		actionBar = (ActionBar) findViewById(R.id.ab_onlineusers);
		//actionBar.setTitle("在 " + roomName +" 的童鞋，共 " + num+ "人");
		actionBar.setTitle(getResources().getString(R.string.loading));
		actionBar.setHomeAction(new Action() {
			@Override
			public void performAction(View view) {
				OnlineUsersActivity.this.finish();
			}
			@Override
			public int getDrawable() {
				return R.drawable.arrow_action_bar;
			}
		});
		
		actionBar.setProgressBarVisibility(View.VISIBLE);
		grid_onlineusers = (GridView) findViewById(R.id.grid_onlineusers);
		grid_onlineusers.setOnItemClickListener(this);
	}
	
	/**
	 * 初始化Adapters
	 */
	public void initAdapters() {
		grid_adapter = new SimpleAdapter(this, userItems, R.layout.griditem_onlineuser, 
				new String[]{"matesId", "avatar", "weiboname"}, new int[] {R.id.txt_onlineusers_matesId, R.id.ic_onlineusers_avatar, R.id.txt_onlineusers_weiboname});
		
		grid_adapter.setViewBinder(new ViewBinder() {
			public boolean setViewValue(View view, Object data, String textRepresentation) {
				if (view instanceof ImageView && data instanceof Bitmap) {
					ImageView iv = (ImageView) view;
					iv.setImageBitmap((Bitmap) data);
					return true;
				} else {
					return false;
				}
			}
		});
		
		grid_onlineusers.setAdapter(grid_adapter);
	}
	/**
	 * 初始化models
	 */
	public void initModels() {
		userItems = new ArrayList<Map<String, Object>>();
		ul = new ArrayList<User>();
	}
	
	/**
	 * 初始化Controllers
	 */
	public void initControllers() {
		httpTransporter = new HttpTransporter();
		fileUtil = new FileUtil();
	}
	
	@Override
	public void onClick(View v) {
		switch(v.getId()) {
		case R.id.btn_login:
			
			break;
		}
	}
	
	class GetAvatarTask extends AsyncTask<Void, Void, String> {

		@Override
		protected String doInBackground(Void... params) {
			
			for(int i = 0; i < userList.size(); i++) {
				User user = new User();
				user.setMatesId(userList.get(i).getMatesId());
				Log.d("MATES", user.getMatesId() + "=============================");
				user.setWeiboName(userList.get(i).getWeiboName());
				
				user.setAvatarUrl_big(userList.get(i).getUrl());
				
				Log.d("MATES", userList.get(i).getUrl() + "=============================");
				Bitmap bm = httpTransporter.getUserAvatar(userList.get(i).getUrl());
				Log.e("mates", "bm " + bm.getHeight());
				
				user.setAvata_big(bm);
				
				ul.add(user);
			}
			
			return null;
		}
		
		@Override
		protected void onPostExecute(String result) {
			for(int i = 0; i < ul.size(); i++) {
				Map<String, Object> item = new HashMap<String, Object>();
				Log.d("MATES", ul.get(i).getMatesId() + "=============================");
				item.put("matesId", ul.get(i).getMatesId());
			
				item.put("avatar", ul.get(i).getAvatar_big());
				item.put("weiboname", ul.get(i).getWeiboName());
				userItems.add(item);
			}
			actionBar.setProgressBarVisibility(View.GONE);
			actionBar.setTitle(roomName);
			grid_adapter.notifyDataSetChanged();
		}
	}
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		// TODO Auto-generated method stub
		Map<String, Object> map = (HashMap<String, Object>) parent.getItemAtPosition(position);
        Log.d(Config.TAG, (String)map.get("weiboname"));
        /**
         * Test
         */
        Intent i = new Intent(OnlineUsersActivity.this, PersonalInfoActivity.class);
        i.putExtra("matesId", (Long)map.get("matesId"));
        startActivity(i);
        
	}
	
	    
	public void popToast(String text) {
		Toast.makeText(getApplicationContext(), text,
				Toast.LENGTH_SHORT).show();
	}

	

	
}