package hk.edu.uic.mates.view;

import java.io.Serializable;

import hk.edu.uic.mates.controller.Config;
import hk.edu.uic.mates.controller.LocationUtil;
import hk.edu.uic.mates.controller.WeiboUtil;
import hk.edu.uic.mates.model.vo.User;
import hk.edu.uic.mates.model.vo.User.Gender;
import hk.edu.uic.mates.model.vo.User.Major;
import hk.edu.uic.mates.R;
import android.R.bool;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class LoginActivity extends Activity implements OnClickListener {
	/** Called when the activity is first created. */

	/** UI Components */
	private Button btn_login;

	/** Controllers */
	private LocationUtil locUtil; // 位置信息控制器
	private WeiboUtil weiboUtil;

	/** models */
	private User user;

	/** SharedPreferences */
	private SharedPreferences settings;
	private SharedPreferences.Editor settingsEditor;

	/** Others */
	private Handler loginHandler;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_login);
		CheckNetwork();
		loginHandler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
					//Log.e(Config.TAG, "again!!!!");
					Intent i = new Intent(LoginActivity.this, HomeActivity.class);
					i.putExtra("user", (Serializable)user);
					startActivity(i);
					LoginActivity.this.finish();
			}
		};

		this.initPreferences();
		this.initUIComponents();
		this.initControllers();

	}


	
	private void CheckNetwork() {
		boolean flag = false;
		ConnectivityManager cwjManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		
		if (cwjManager.getActiveNetworkInfo() != null) {
			flag = cwjManager.getActiveNetworkInfo().isAvailable();
		}
		
		
		
		if (!flag) {
			Builder b = new AlertDialog.Builder(this).setTitle(getString(R.string.noNetwork)).setMessage(getString(R.string.noNetworkMessage));
			b.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
			Intent mIntent = new Intent("/");
			ComponentName comp = new ComponentName("com.android.settings", "com.android.settings.WirelessSettings");
			mIntent.setComponent(comp);
			mIntent.setAction("<span class=\"hilite\">android</span>.intent.action.VIEW");
			startActivity(mIntent);
		}
		}).setNeutralButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
		public void onClick(DialogInterface dialog, int whichButton) {
		dialog.cancel();
		}
		}).create();
			
		b.show();}
	}
	
	
	private void loading(final int i) {
		new Thread() {
			@Override
			public void run() {
				try {
					Thread.sleep(i);
					loginHandler.sendEmptyMessage(0);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

		}.start();
	}

	/**
	 * 初始化UI控件
	 */
	public void initUIComponents() {
		btn_login = (Button) findViewById(R.id.btn_login);
		btn_login.setOnClickListener(this);
	}

	/**
	 * 初始化Controllers
	 */
	public void initControllers() {
		locUtil = new LocationUtil(getApplicationContext());
		// TODO 测试

		weiboUtil = new WeiboUtil(LoginActivity.this, HomeActivity.class);
		if (weiboUtil.isAuthed() && settings.getLong("matesId", 0) != 0) {
			//Log.d(Config.TAG, "验证过的");
			btn_login.setVisibility(View.INVISIBLE);

			// 获取用户资料
			user = new User(settings);
			
			Log.d(Config.TAG, user.toString());
			this.loading(3000);
		}
	}

	/**
	 * 初始化Sharedfreferenced
	 */
	public void initPreferences() {
		settings = getSharedPreferences("settings", 0);
		settingsEditor = settings.edit();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_login:
			weiboUtil.auth(LoginActivity.this);
			break;
		}
	}

	public void popToast(String text) {
		Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT)
				.show();
	}

}