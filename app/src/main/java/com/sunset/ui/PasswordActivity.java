package com.sunset.ui;
import java.io.File;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.sunset.bean.User;
import com.sunset.common.Constant;
import com.sunset.service.API;
import com.sunset.service.HandlerThreads;
import com.sunset.util.BaseContorl;
import com.sunset.util.SqlliteHander;
public class PasswordActivity extends Activity{
	
	EditText oldPassword;
	EditText newPassword;
	Button modifyButton;
	User user ;
	ProgressDialog progressDialog;
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_password);
		
		SqlliteHander.getTnstantiation(this).createTable();
	    user = SqlliteHander.getTnstantiation(this).queryUser();
	    oldPassword = (EditText) this.findViewById(R.id.oldPassword);
	    newPassword = (EditText) this.findViewById(R.id.newPassword);
		((TextView)findViewById(R.id.TITLE_TEXT)).setText("修改密码");
		findViewById(R.id.button_return).setVisibility(View.VISIBLE);
		 ((Button)findViewById(R.id.button_return)).setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View arg0) {
					 
					PasswordActivity.this.finish();
				}});
		modifyButton = (Button) this.findViewById(R.id.modifyButton);
		modifyButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				 String oldPwd = oldPassword.getText().toString().trim();
				 String newPwd  = newPassword.getText().toString().trim();
				 
				 if(oldPwd==null || oldPwd.trim().equals("")||newPwd==null || newPwd.trim().equals(""))
				 {
					 BaseContorl.doShowEToask(PasswordActivity.this, "账号和密码不能为空");
				 }else
				 {
					 new ModiftThread(user.account,oldPwd,newPwd).start();
					 progressDialog = ProgressDialog.show(PasswordActivity.this, "提示", "正在保存,请稍后...", true, false);
					/* User u = new User();
					 u.account="aaa";
					 u.cashierId="123456";
					 SqlliteHander.getTnstantiation(LoginActivity.this).saveUser(u);
					 Portal p = new Portal();
					 p.name="出口A";
					 p.portalId="456789";
					 SqlliteHander.getTnstantiation(LoginActivity.this).savePortal("aaaa", p);
					 Intent intent = new Intent(LoginActivity.this,HomeActivity.class);  
				        startActivity(intent);*/
				 }
			}});
		
		    File dir = new File(Constant.IMAGE_DIR);
	 		if(!dir.exists())
	 		{
	 			dir.mkdir();
	 		}
	 		
	 		 new HandlerThreads.CleanOldFileThread(this).start();
	}
	
	 Handler loginHandler = new Handler() {
			
			public void handleMessage(Message msg) {
				progressDialog.dismiss();
				String code = msg.getData().getString("code");
				if(msg.what == -1 || code ==null || code.equals("2"))
				{
					BaseContorl.doShowEToask(PasswordActivity.this, R.string.tip_network_busy);
					return;
				}
				if(code.equals("1"))
				{
					BaseContorl.doShowEToask(PasswordActivity.this, "旧密码不正确!");
					return;
				}
				if(code.equals("0"))
				{
					BaseContorl.doShowSToask(PasswordActivity.this, "修改成功");
					newPassword.setText(null);
					oldPassword.setText(null);
					//SqlliteHander.getTnstantiation(PasswordActivity.this).saveUser(u);
					return;
				}
				 
			}
		};
		public   class ModiftThread extends Thread{
		 
			String  oldPwd;
			String  newPwd;
			String  account;
			ModiftThread(String a,String o,String n){
				oldPwd= o;
				newPwd = n;
				account = a;
			}
			public void run() {
				Message message=new Message();
				String code = null;
				try {
					code  = API.modifyPassword(account,oldPwd,newPwd);
					 
				} catch (Exception e) {
					e.printStackTrace();
					message.what = -1;
				}
				Bundle bundle=new Bundle();
				bundle.putString("code", code);
				message.setData(bundle);
				loginHandler.sendMessage(message);
			}
		} 
}
