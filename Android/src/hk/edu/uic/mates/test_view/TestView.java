package hk.edu.uic.mates.test_view;

import java.util.HashSet;
import java.util.Iterator;

import hk.edu.uic.mates.controller.AsmackUtil;
import hk.edu.uic.mates.model.vo.Room;
import hk.edu.uic.mates.model.vo.User;
import hk.edu.uic.mates.R;

import org.jivesoftware.smack.XMPPConnection;

import android.app.Activity;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class TestView extends Activity {
	
	Button test1 = null;
	Button test2 = null;
	Button test3 = null;
	Button test4 = null;
	Button test5 = null;
	Button test6 = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		final Room room = new Room();
		room.setOpenfireRoomId("b203@uic.mates");
		final User user = new User();
		user.setWeiboId("test2");
		user.setWeiboName("jerry");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test);
		final AsmackUtil asmackUtil = new AsmackUtil();
		test1 = (Button) this.findViewById(R.id.test_button1);
		test1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				AsmackUtil.getConnection(user);
				asmackUtil.joinRoom(user, room.getOpenfireRoomId());
			}
		});
		
		test2 = (Button) this.findViewById(R.id.test_button2);
		test2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				asmackUtil.sendMessage("goodnight");
			}
			
		});
		
		test3 = (Button) this.findViewById(R.id.test_button3);
		test3.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				asmackUtil.exitRoom();
			}
			
		});
		
		test4 = (Button) this.findViewById(R.id.test_button4);
		test4.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				AsmackUtil.closeConnection();
			}
			
		});
		
		
		test5 = (Button) this.findViewById(R.id.test_button5);
		test5.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				System.out.println(asmackUtil.getOccupantsList());
			}
		});
		test6 = (Button) this.findViewById(R.id.test_button6);
		test6.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				asmackUtil.changeSubject("test Act");
			}
			
		});
	}
	
}
