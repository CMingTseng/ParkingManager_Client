package com.sunset.ui;
import java.io.File;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.sunset.bean.Portal;
import com.sunset.bean.User;
import com.sunset.common.Constant;
import com.sunset.service.API;
import com.sunset.service.HandlerThreads;
import com.sunset.util.BaseContorl;
import com.sunset.util.SqlliteHander;
public class LoginActivity extends Activity{
	
	EditText account;
	Button loginButton;
	ProgressDialog progressDialog;
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		SqlliteHander.getTnstantiation(this).createTable();
		
		account = (EditText) this.findViewById(R.id.account);
		((TextView)findViewById(R.id.TITLE_TEXT)).setText("登录");
		loginButton = (Button) this.findViewById(R.id.loginButton);
		loginButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				 String name = account.getText().toString().trim();
				 String password = ((EditText)LoginActivity.this.findViewById(R.id.passowrd)).getText().toString().trim();
				 if(name.equals("admin") && password.equals("admin"))
				 {
					    User u = new User();
					    u.account = "admin";
					    u.password="sunset";
					    u.cashierId="0";
					    u.parkId="0";
		         		Portal p = new Portal();
		         		p.name = "临时岗口";
		         		p.portalId = "0";
		         		p.parkId = "0";
		         		SqlliteHander.getTnstantiation(LoginActivity.this).saveUser(u);
		         		SqlliteHander.getTnstantiation(LoginActivity.this).savePortal("admin", p);
		         		Intent intent = new Intent(LoginActivity.this,HomeActivity.class);   
			            Bundle bundle = new Bundle();
			            bundle.putString("portalId", "0"); 
			            bundle.putString("name", "临时岗口");
			            intent.putExtras(bundle);
			         	startActivity(intent);
			         	LoginActivity.this.finish();
					 return ;
				 }
				 if(name==null || name.trim().equals("")||password==null || password.trim().equals(""))
				 {
					 BaseContorl.doShowEToask(LoginActivity.this, "账号和密码不能为空");
				 }else
				 {
					 new LoginThread(name,password).start();
					 progressDialog = ProgressDialog.show(LoginActivity.this, "登录", "正在登录,请稍后...", true, false);
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
				if(msg.what == -1)
				{
					BaseContorl.doShowEToask(LoginActivity.this, R.string.tip_network_busy);
					return;
				}
				if(msg.what == 1 || msg.what == 2)
				{
					BaseContorl.doShowEToask(LoginActivity.this, "账号或者密码不正确");
					return;
				}
				User u = (User) msg.getData().getSerializable("user");
				
				Intent intent = new Intent(LoginActivity.this,PortalActivity.class);  
		        startActivity(intent);
			    LoginActivity.this.finish();
				SqlliteHander.getTnstantiation(LoginActivity.this).saveUser(u);
			}
		};
		public   class LoginThread extends Thread{
		 
			String  account;
			String  password;
			LoginThread(String a,String p){
				account= a;
				password = p;
			}
			public void run() {
				Message message=new Message();
				User user = null;
				try {
					 user  = API.login(account);
					if( user == null)
					{
						message.what = 1;
					}
					else if( !user.password.equals(password))
					{
						message.what = 2;
					} 
					
				} catch (Exception e) {
					e.printStackTrace();
					message.what = -1;
				}
				Bundle bundle=new Bundle();
				bundle.putSerializable("user", user);
				message.setData(bundle);
				loginHandler.sendMessage(message);
			}
		} 
}
