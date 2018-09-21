package com.sunset.ui;



import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import com.sunset.adapter.PortalGridViewAdapter;
import com.sunset.bean.Portal;
import com.sunset.bean.User;
import com.sunset.service.API;
import com.sunset.service.HandlerThreads;
import com.sunset.util.BaseContorl;
import com.sunset.util.SqlliteHander;

public class PortalActivity extends Activity{
	GridView gridView;
	List<Portal>  list ;
	ProgressDialog progressDialog;
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_portal);
		
		findViewById(R.id.button_return).setVisibility(View.VISIBLE);
		((TextView)findViewById(R.id.TITLE_TEXT)).setText("选择出入口");
	   ((Button)findViewById(R.id.button_return)).setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				 
				Intent intent = new Intent(PortalActivity.this,LoginActivity.class);
				startActivity(intent);
				PortalActivity.this.finish();
			}});
	   
	    gridView = (GridView) findViewById(R.id.portalGridView);
		gridView.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int arg2,
					long arg3) {
				Intent intent = new Intent(PortalActivity.this,HomeActivity.class);   
                Bundle bundle = new Bundle();
                bundle.putString("portalId", ((TextView)view.findViewById(R.id.portalId)).getText().toString()); 
                bundle.putString("name", ((TextView)view.findViewById(R.id.label)).getText().toString());
                intent.putExtras(bundle);
         		startActivity(intent);
         		PortalActivity.this.finish();
         		String account = SqlliteHander.getTnstantiation(PortalActivity.this).queryUser().account;
         		Portal p = new Portal();
         		p.name = ((TextView)view.findViewById(R.id.label)).getText().toString();
         		p.portalId = ((TextView)view.findViewById(R.id.portalId)).getText().toString();
         		p.parkId = ((TextView)view.findViewById(R.id.parkId)).getText().toString();
         		SqlliteHander.getTnstantiation(PortalActivity.this).savePortal(account, p);
			}});
		
		new QueryThread().start();
		
		User user = SqlliteHander.getTnstantiation(PortalActivity.this).queryUser();
		if(!user.account.equals("sunset.net")){
		    new HandlerThreads.QueryFeeRuleThread(this).start();
		}
		
		progressDialog = ProgressDialog.show(PortalActivity.this, "提示", "正在查询出入口,请稍后...", true, false); 
	}
	
	 Handler queryHandler = new Handler() {
			
			public void handleMessage(Message msg) {
				 progressDialog.dismiss();
				if(msg.what == -1)
				{
					BaseContorl.doShowEToask(PortalActivity.this, R.string.tip_network_busy);
					return;
				}
			 
				gridView.setAdapter(new PortalGridViewAdapter(PortalActivity.this,list));
			}
		};
		public   class QueryThread extends Thread{
		 
			 
			public void run() {
				Message message=new Message();
				try {
				 String parkId = SqlliteHander.getTnstantiation(PortalActivity.this).queryUser().parkId;
				 list  = API.portalList(parkId);
					
				} catch (Exception e) {
					e.printStackTrace();
					message.what = -1;
				}
				queryHandler.sendMessage(message);
			}
		} 
}
