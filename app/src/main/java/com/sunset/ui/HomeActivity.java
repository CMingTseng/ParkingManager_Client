package com.sunset.ui;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.sunset.adapter.RecordListViewAdapter;
import com.sunset.adapter.ViewPaperAdapter;
import com.sunset.bean.Config;
import com.sunset.bean.FeeRule;
import com.sunset.bean.Portal;
import com.sunset.bean.Record;
import com.sunset.bean.User;
import com.sunset.common.Constant;
import com.sunset.component.WindowMenu;
import com.sunset.service.API;
import com.sunset.service.HandlerThreads;
import com.sunset.util.AppTools;
import com.sunset.util.BaseContorl;
import com.sunset.util.GlobalExceptionListener;
import com.sunset.util.SqlliteHander;


public class HomeActivity extends Activity implements OnClickListener{
	ListView  listtView;
	View   rukouView;
	View   chukouView;
	RecordListViewAdapter listAdapter;
	ArrayList<Record> recordList;
	PopupWindow menuWindow;
	private ViewPager pager;
	List<View> pageViews;
	String rukouImg;
	String chukouImg;
	User user;
	Portal portal;
	Bitmap ImageBitmap ;
	Record record;
	FeeRule rule;
	ProgressDialog progressDialog;
	float amt= 0f;
	boolean isVip =false;
	Timer timer = new Timer(true);
	Config pconfig;
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		GlobalExceptionListener.getInstance().init(this);
		init();
	} 
	
	void init()
	{
		menuWindow = new WindowMenu(this).getPopupWindow();
		//progressDialog = new ProgressDialog(HomeActivity.this, 0);
		//progressDialog.setTitle("提示");
		 
		user = SqlliteHander.getTnstantiation(HomeActivity.this).queryUser();
		recordList =  SqlliteHander.getTnstantiation(HomeActivity.this).queryRecordList(user.account);
		portal = SqlliteHander.getTnstantiation(HomeActivity.this).queryPortal();
		rule = SqlliteHander.getTnstantiation(this).queryFeeRule();
		pconfig = SqlliteHander.getTnstantiation(HomeActivity.this).queryConfig("prefix");
		 
		((TextView)findViewById(R.id.account)).setText("当前用户:"+user.name);
		((TextView)findViewById(R.id.portal_label)).setText("出入口:"+portal.name);
		if(!user.account.equals("sunset.net")){
			
		    timer.schedule(task,1000, 10000);
		}
		pageViews = new ArrayList<View>();
		listtView = (ListView) findViewById(R.id.recordListtView);
		listAdapter = new RecordListViewAdapter(this,recordList);
		listtView.setAdapter(listAdapter);
		
		rukouView = findViewById(R.id.rukouPanel);
		chukouView = findViewById(R.id.chukouPanel);
		 
		
		pageViews.add(rukouView);
		pageViews.add(chukouView);
		pageViews.add(findViewById(R.id.recordPanel)); 
		HomeActivity.this.findViewById(R.id.rukou).setOnClickListener(this);
		HomeActivity.this.findViewById(R.id.chukou).setOnClickListener(this);
		findViewById(R.id.record).setOnClickListener(this);
		findViewById(R.id.AcarImg).setOnClickListener(this);
		findViewById(R.id.chukouCarImg).setOnClickListener(this);
		findViewById(R.id.addButton).setOnClickListener(this);
		findViewById(R.id.searchButton).setOnClickListener(this);
		findViewById(R.id.SaveButton).setOnClickListener(this);
		findViewById(R.id.refreshButton).setOnClickListener(this);
		
		pager  = (ViewPager)this.findViewById(R.id.MviewPaper);
		pager.removeAllViews();
		pager.setAdapter( new ViewPaperAdapter(pageViews));
		pager.setOnPageChangeListener(new OnPageChangeListener(){
			public void onPageScrollStateChanged(int arg0) {}
			public void onPageScrolled(int arg0, float arg1, int arg2) {}
			public void onPageSelected(int index) {
				 if(index==0)
				 {
					 ((TextView)HomeActivity.this.findViewById(R.id.rukou)).setBackgroundResource(R.drawable.toptabbar_select_active);
					 ((TextView)HomeActivity.this.findViewById(R.id.rukou)).setTextColor(getResources().getColor(R.color.blue));
					 
					 ((TextView)HomeActivity.this.findViewById(R.id.chukou)).setBackgroundDrawable(null);
					 ((TextView)HomeActivity.this.findViewById(R.id.chukou)).setTextColor(getResources().getColor(R.color.black));
					 
					 ((TextView)HomeActivity.this.findViewById(R.id.record)).setBackgroundDrawable(null);
					 ((TextView)HomeActivity.this.findViewById(R.id.record)).setTextColor(getResources().getColor(R.color.black));
					 
				 }
				 if(index==1)
				 {
					 ((TextView)HomeActivity.this.findViewById(R.id.chukou)).setBackgroundResource(R.drawable.toptabbar_select_active);
					 ((TextView)HomeActivity.this.findViewById(R.id.chukou)).setTextColor(getResources().getColor(R.color.blue));
					 
					 ((TextView)HomeActivity.this.findViewById(R.id.rukou)).setBackgroundDrawable(null);
					 ((TextView)HomeActivity.this.findViewById(R.id.rukou)).setTextColor(getResources().getColor(R.color.black));
					 
					 ((TextView)HomeActivity.this.findViewById(R.id.record)).setBackgroundDrawable(null);
					 ((TextView)HomeActivity.this.findViewById(R.id.record)).setTextColor(getResources().getColor(R.color.black));
				 }
				 if(index==2)
				 {
					 ((TextView)HomeActivity.this.findViewById(R.id.record)).setBackgroundResource(R.drawable.toptabbar_select_active);
					 ((TextView)HomeActivity.this.findViewById(R.id.record)).setTextColor(getResources().getColor(R.color.blue));
					 
					 ((TextView)HomeActivity.this.findViewById(R.id.rukou)).setBackgroundDrawable(null);
					 ((TextView)HomeActivity.this.findViewById(R.id.rukou)).setTextColor(getResources().getColor(R.color.black));
					 
					 ((TextView)HomeActivity.this.findViewById(R.id.chukou)).setBackgroundDrawable(null);
					 ((TextView)HomeActivity.this.findViewById(R.id.chukou)).setTextColor(getResources().getColor(R.color.black));
				 }
				
			}});
		pager.setCurrentItem(0);
		
		listtView.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int index,
					long arg3) {
				Intent intent = new Intent(HomeActivity.this,PreviewActivity.class);   
                intent.putExtra("record", listAdapter.getItem(index)); 
         		startActivity(intent);
			}});//
		
		
		((EditText)rukouView.findViewById(R.id.AcarCode)).setText(pconfig==null?"":pconfig.value);
		((EditText)chukouView.findViewById(R.id.ScarCode)).setText(pconfig==null?"":pconfig.value);
		
	}

 
	 
	@Override
	public void onClick(View v) {
		switch(v.getId())
		{
		   case R.id.rukou:
			   
			    ((TextView)HomeActivity.this.findViewById(R.id.rukou)).setBackgroundResource(R.drawable.toptabbar_select_active);
				 ((TextView)HomeActivity.this.findViewById(R.id.rukou)).setTextColor(getResources().getColor(R.color.blue));
				 ((TextView)HomeActivity.this.findViewById(R.id.chukou)).setBackgroundDrawable(null);
				 ((TextView)HomeActivity.this.findViewById(R.id.chukou)).setTextColor(getResources().getColor(R.color.black));
				 ((TextView)HomeActivity.this.findViewById(R.id.record)).setBackgroundDrawable(null);
				 ((TextView)HomeActivity.this.findViewById(R.id.record)).setTextColor(getResources().getColor(R.color.black));
			   pager.setCurrentItem(0);
			   break;
		   case R.id.chukou:
			   
			   ((TextView)HomeActivity.this.findViewById(R.id.chukou)).setBackgroundResource(R.drawable.toptabbar_select_active);
				 ((TextView)HomeActivity.this.findViewById(R.id.chukou)).setTextColor(getResources().getColor(R.color.blue));
				 ((TextView)HomeActivity.this.findViewById(R.id.rukou)).setBackgroundDrawable(null);
				 ((TextView)HomeActivity.this.findViewById(R.id.rukou)).setTextColor(getResources().getColor(R.color.black));
				 ((TextView)HomeActivity.this.findViewById(R.id.record)).setBackgroundDrawable(null);
				 ((TextView)HomeActivity.this.findViewById(R.id.record)).setTextColor(getResources().getColor(R.color.black));
				 pager.setCurrentItem(1);
			   break;
           case R.id.record:
			   
        	     ((TextView)HomeActivity.this.findViewById(R.id.record)).setBackgroundResource(R.drawable.toptabbar_select_active);
				 ((TextView)HomeActivity.this.findViewById(R.id.record)).setTextColor(getResources().getColor(R.color.blue));
				 ((TextView)HomeActivity.this.findViewById(R.id.rukou)).setBackgroundDrawable(null);
				 ((TextView)HomeActivity.this.findViewById(R.id.rukou)).setTextColor(getResources().getColor(R.color.black));
				 ((TextView)HomeActivity.this.findViewById(R.id.chukou)).setBackgroundDrawable(null);
				 ((TextView)HomeActivity.this.findViewById(R.id.chukou)).setTextColor(getResources().getColor(R.color.black));
				 pager.setCurrentItem(2);
			   break;
           case R.id.AcarImg:
        	   rukouImg = String.valueOf(System.currentTimeMillis())+".jpg";
        	   Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        	   intent.putExtra(MediaStore.Images.Media.ORIENTATION, 0);
        	   intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(Constant.IMAGE_DIR+"/"+rukouImg))); 
        	   startActivityForResult(intent, 1); 
        	   break;
          case R.id.chukouCarImg:
        	   chukouImg = String.valueOf(System.currentTimeMillis())+".jpg";
        	   Intent intent1 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        	   intent1.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(Constant.IMAGE_DIR+"/"+chukouImg))); 
        	   startActivityForResult(intent1, 2); 
        	   break;
           
          case R.id.addButton:
        	   String carCode = ((TextView)findViewById(R.id.AcarCode)).getText().toString().trim();
        	   if(carCode == null || carCode.length() < 7 )
        	   {
        		   BaseContorl.doShowEToask(HomeActivity.this, "请输入完整车牌号,如:粤A12E34");
        		   return;
        	   }
        	   if(rukouImg == null )
        	   {
        		   BaseContorl.doShowEToask(HomeActivity.this, "请先拍照!");
        		   return;
        	   }
			   Record record = new Record();
			   record.setCarId(carCode);
			   record.userId = user.account;
			   record.entryImg = rukouImg;
			   record.entry = portal.name;
			   record.parkId = portal.parkId;
			   record.setRecordId(String.valueOf(System.currentTimeMillis()));
			   
			   ((TextView)rukouView.findViewById(R.id.AcarCode)).setText(pconfig==null?"":pconfig.value);
				 rukouImg = null;
				 ((ImageView) HomeActivity.this.findViewById(R.id.AcarImg)).setImageResource(R.color.white);
				
			   if(user.account.equals("sunset.net"))
			   {
				    BaseContorl.doShowSToask(HomeActivity.this, "保存成功!");
					record.setEntryTime(AppTools.getCurrentlyDate());
					record.setRecordId(String.valueOf(System.currentTimeMillis()));
					SqlliteHander.getTnstantiation(HomeActivity.this).saveRecord(record);
					return;
			   }
			   new HandlerThreads.RecordAddThread(addHandler, record, this).start();
			   //progressDialog.setMessage("正在保存，请稍后......");
			  /// progressDialog.show();
			   BaseContorl.doShowHToask(HomeActivity.this, "正在保存,请稍后......");
			   break;
       	   
          case R.id.searchButton:
       	       String scarCode = ((TextView)findViewById(R.id.ScarCode)).getText().toString().trim();
	       	   if(scarCode == null || scarCode.length() < 7 )
	     	   {
	     		   BaseContorl.doShowEToask(HomeActivity.this, "请输入完整车牌号,如:粤A12E34");
	     		   return;
	     	   }
	       	   if(user.account.equals("sunset.net"))
	       	   {
			       	record = SqlliteHander.getTnstantiation(HomeActivity.this).queryRecord(scarCode);
					if(record == null)
					{
						BaseContorl.doShowHToask(HomeActivity.this, "没有记录");
						
					}else
					{
						((ImageView) HomeActivity.this.findViewById(R.id.rukouCarImg)).setImageBitmap(BitmapFactory.decodeFile(Constant.IMAGE_DIR+"/" +record.getEntryImg() ));
						((TextView) HomeActivity.this.findViewById(R.id.ruchangTime)).setText("进场时间:"+AppTools.dateToDate(record.getEntryTime()));
					}
					return ;
	       	   }
	       	   progressDialog = ProgressDialog.show(this, "提示", "正在查询,请稍后...", true, false);
			   new HandlerThreads.QueryRecordThread(this,queryHandler,scarCode ,portal.parkId).start();
			   new HandlerThreads.CheckVipThread(checkVipHandler,portal.parkId,scarCode ).start();
      	       break;
      	   
          case R.id.SaveButton:
        	   if(this.record == null)
        	   {
        		   BaseContorl.doShowEToask(HomeActivity.this, "无记录!");
	     		   return;
        	   }
        	   if(this.rule == null)
        	   {
        		   BaseContorl.doShowEToask(HomeActivity.this, "车场无收费规则,无法计费!");
	     		   return;
        	   }
        	   this.record.exportImg = chukouImg;
        	   this.record.export = portal.name;
        	   this.record.shouldAmt = String.valueOf(amt);
        	   
        	   
       		   ((TextView)chukouView.findViewById(R.id.ScarCode)).setText(pconfig==null?"":pconfig.value);
				 chukouImg = null;
				((ImageView) HomeActivity.this.findViewById(R.id.rukouCarImg)).setImageResource(R.color.white);
				((ImageView) HomeActivity.this.findViewById(R.id.chukouCarImg)).setImageResource(R.color.white);
				((TextView) HomeActivity.this.findViewById(R.id.ruchangTime)).setText(null);
				((TextView)findViewById(R.id.amt)).setText(null);
				amt = 0f;
				
				
        	   if(user.account.equals("sunset.net"))
			   {
				    BaseContorl.doShowSToask(HomeActivity.this, "保存成功!");
				    this.record.setExportTime(AppTools.getCurrentlyDate());
				    this.record.setEntry(portal.name);
					SqlliteHander.getTnstantiation(HomeActivity.this).updateRecord(this.record);
					HomeActivity.this.record = null;
					return;
					
			   }
        	   //progressDialog.setMessage("正在保存，请稍后......");
			  // progressDialog.show();
        	   BaseContorl.doShowHToask(HomeActivity.this, "正在保存,请稍后......");
        	   new HandlerThreads.RecordUpdateThread(updateHandler, this.record, this).start();
        	   HomeActivity.this.record = null;
     	       break;
          case R.id.refreshButton:
        	  recordList =  SqlliteHander.getTnstantiation(HomeActivity.this).queryRecordList(user.account);
        	  listAdapter.setDataList(recordList);
        	  listAdapter.notifyDataSetChanged();
        	  BaseContorl.doShowSToask(HomeActivity.this, "刷新成功,共"+recordList.size()+"条记录!");
        	  break;
		}
		
	}
	
	 Handler addHandler = new Handler() {
			
			public void handleMessage(Message msg) {
				//progressDialog.dismiss();
				Record record = (Record) msg.getData().getSerializable("record");
				/*
				((TextView)findViewById(R.id.AcarCode)).setText(null);
				 rukouImg = null;
				((ImageView) HomeActivity.this.findViewById(R.id.AcarImg)).setImageBitmap(null);
				*/
				record.setEntryTime(AppTools.getCurrentlyDate());
				record.setEntry(portal.name);
				if(msg.what == -1)
				{
					BaseContorl.doShowHToask(HomeActivity.this, "网络连接失败,数据已存入本地");
					record.setState("0");
					
					 
				}
				if(msg.what ==0)
				{
					record.setState("1");
					BaseContorl.doShowSToask(HomeActivity.this, "数据已经成功上传服务器!");
					
				}
				SqlliteHander.getTnstantiation(HomeActivity.this).saveRecord(record);
			}
		};
		 Handler checkVipHandler = new Handler() {
				
				public void handleMessage(Message msg) {
					 
					if(msg.what ==0)
					{
						isVip = msg.getData().getBoolean("isVip");
					}
					
				}
			};
     Handler updateHandler = new Handler() {
			
			public void handleMessage(Message msg) {
				//progressDialog.dismiss();
				Record record = (Record) msg.getData().getSerializable("record");
				/*
				((TextView)findViewById(R.id.ScarCode)).setText(null);
				 chukouImg = null;
				((ImageView) HomeActivity.this.findViewById(R.id.rukouCarImg)).setImageBitmap(null);
				((ImageView) HomeActivity.this.findViewById(R.id.chukouCarImg)).setImageBitmap(null);
				((TextView) HomeActivity.this.findViewById(R.id.chukouTime)).setText(null);
				((TextView)findViewById(R.id.amt)).setText(null);
				amt = 0f;
				HomeActivity.this.record = null;
				*/
				Record t = SqlliteHander.getTnstantiation(HomeActivity.this).queryRecordById(record.recordId);
				if(msg.what == -1)
				{
					BaseContorl.doShowHToask(HomeActivity.this, "网络连接失败,数据已存入本地");
				}
				if(msg.what ==0)
				{
					BaseContorl.doShowSToask(HomeActivity.this, "数据已经成功上传服务器!");
					record.setState("9");
				}
				if(record.getState() == null || record.getState().equals("1"))
				{
				  record.setState("2");
				}
				if(record.getState().equals("0"))
				{
				  record.setState("4");
				}
				
				
				record.setExportTime(AppTools.getCurrentlyDate());
				if(t!=null)
				{
					SqlliteHander.getTnstantiation(HomeActivity.this).updateRecord(record);
				}else
				{   
					SqlliteHander.getTnstantiation(HomeActivity.this).saveFullRecord(record);
				}
			}
		};
     Handler queryHandler = new Handler() {
			
			public void handleMessage(Message msg) {
				progressDialog.dismiss();
				chukouImg = null;
				((ImageView) HomeActivity.this.findViewById(R.id.rukouCarImg)).setImageBitmap(null);
				((ImageView) HomeActivity.this.findViewById(R.id.chukouCarImg)).setImageBitmap(null);
				((TextView) HomeActivity.this.findViewById(R.id.ruchangTime)).setText(null);
				((TextView)findViewById(R.id.amt)).setText(null);
				amt = 0f;
				HomeActivity.this.record = null;
			    record = (Record) msg.getData().getSerializable("record");
				if(record == null)
				{
					/*record = SqlliteHander.getTnstantiation(HomeActivity.this).queryRecord(carId);
					if(record == null)
					{
						BaseContorl.doShowHToask(HomeActivity.this, "没有记录");
						return ;
					}else
					{
					    Bitmap bitmap = BitmapFactory.decodeFile(Constant.IMAGE_DIR+"/" +record.getEntryImg());  
						((ImageView) HomeActivity.this.findViewById(R.id.rukouCarImg)).setImageBitmap(bitmap);
						((TextView) HomeActivity.this.findViewById(R.id.chukouTime)).setText("入口时间:"+AppTools.dateToDate(record.getEntryTime()));
					}*/
					BaseContorl.doShowHToask(HomeActivity.this, "没有记录");
					return ; 
				}else{
					
					((TextView) HomeActivity.this.findViewById(R.id.ruchangTime)).setText("进场时间:"+AppTools.dateToDate(record.getEntryTime()));
					 if(record.state!=null)
					 {
						    
							File file = new File(Constant.IMAGE_DIR+"/" +record.getEntryImg());
							if(file.exists())
							{
								Bitmap bitmap = BitmapFactory.decodeFile(Constant.IMAGE_DIR+"/" +record.getEntryImg());  
								((ImageView) HomeActivity.this.findViewById(R.id.rukouCarImg)).setImageBitmap(bitmap);
							}
					 }else
					 {
						   new ImageThread(imageHandler,Constant.SERVER_URL+"/"+record.getEntryImg()).start();
						   BaseContorl.doShowHToask(HomeActivity.this, "正在下载进场照片,请稍后......");
					 }
					/*((TextView) HomeActivity.this.findViewById(R.id.chukouTime)).setText("入口时间:"+AppTools.dateToDate(record.getEntryTime()));
					File file = new File(Constant.IMAGE_DIR+"/" +record.getEntryImg());
					if(file.exists())
					{
						Bitmap bitmap = BitmapFactory.decodeFile(Constant.IMAGE_DIR+"/" +record.getEntryImg());  
						((ImageView) HomeActivity.this.findViewById(R.id.rukouCarImg)).setImageBitmap(bitmap);
					}else
					{
					   new ImageThread(imageHandler,Constant.SERVER_URL+"/"+record.getEntryImg()).start();
					   BaseContorl.doShowHToask(HomeActivity.this, "正在下载进场照片,请稍后......");
					}*/
				}
			}
		};
		
		 Handler imageHandler = new Handler() {
				public void handleMessage(Message msg) {
					((ImageView) HomeActivity.this.findViewById(R.id.rukouCarImg)).setImageBitmap(ImageBitmap);
				}
			};	
	@Override
	public boolean onKeyDown(int keyCode,KeyEvent event) {
		 
		  return false;
	}
	
	public boolean onCreateOptionsMenu(Menu menu) {
		 
		if(!menuWindow.isShowing())
		{
			menuWindow.showAtLocation(findViewById(R.id.MviewPaper), Gravity.BOTTOM, 0, LayoutParams.WRAP_CONTENT);
		}else
		{
			menuWindow.dismiss();
		}
	 return false;
}
	 @Override
	    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	        super.onActivityResult(requestCode, resultCode, data);
	        if (resultCode == Activity.RESULT_OK) {
	        	 
	        	if(requestCode==1)
	        	{
	        		BitmapFactory.Options options = new BitmapFactory.Options(); 
	        		options.inSampleSize = 2; 
	        		Bitmap bitmap = BitmapFactory.decodeFile(Constant.IMAGE_DIR+"/"+rukouImg, options);
	        		bitmap = Bitmap.createScaledBitmap(bitmap,bitmap.getWidth() * 300 / bitmap.getHeight(),300,true);
	        		try {
						bitmap.compress(CompressFormat.JPEG, 100,  new FileOutputStream(Constant.IMAGE_DIR+"/"+rukouImg));
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					}
	        		((ImageView) HomeActivity.this.findViewById(R.id.AcarImg)).setImageBitmap(bitmap);
	        	}
	        	if(requestCode==2 && record!=null)
	        	{
	        		BitmapFactory.Options options = new BitmapFactory.Options(); 
	        		options.inSampleSize = 2; 
	        		Bitmap bitmap = BitmapFactory.decodeFile(Constant.IMAGE_DIR+"/"+chukouImg, options);
	        		bitmap = Bitmap.createScaledBitmap(bitmap,bitmap.getWidth() * 300 / bitmap.getHeight(),300,true);
	        		try {
						bitmap.compress(CompressFormat.JPEG, 100,  new FileOutputStream(Constant.IMAGE_DIR+"/"+chukouImg));
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	        		((ImageView) HomeActivity.this.findViewById(R.id.chukouCarImg)).setImageBitmap(bitmap);
	        		if(!user.account.equals("sunset.net"))
	        		{
		        		//计算费用
		        		
		        		if(rule==null)
		        		{
		        			BaseContorl.doShowEToask(HomeActivity.this, "车场无收费规则!");
		        			return ;
		        		}else{
		        			String stime = record.getEntryTime();
			        		String etime = AppTools.getCurrentlyDate();
			        		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
			        		Long c = 0l;
			        		Long d = 0l;
			        		Long surplus = 0l;
			        		try {
								Date s = dateFormat.parse(stime);
								Date e = dateFormat.parse(etime);
							    c = (e.getTime() -s.getTime()) / 1000 / 60 - Long.valueOf(rule.getFreeTime());
							    
							    if(c > 0)
							    {
							        c = (c  - Long.valueOf(rule.getFirstTime()));
					        		amt = Float.valueOf(rule.getFirstPrice()); 
					        		d = c / 60 /24 ; 
									surplus = c % ( 60 *24) ;
									
							        if(d>0 &&amt + AppTools.oneDayMaxFee(rule) > Float.valueOf(rule.maxFee))
							        {
							          amt = amt + AppTools.oneDayMaxFee(rule) - Float.valueOf(rule.maxFee);
							        }
							    	amt += d * AppTools.oneDayMaxFee(rule);
							    	long sAmt = surplus /  Long.valueOf(rule.getSpaceTime());
							    	if(sAmt * Float.valueOf(rule.getSpacePrice()) >  Float.valueOf(rule.maxFee))
							    	{
							    		amt += Float.valueOf(rule.maxFee);
							    	}else
							    	{
				        			   amt += sAmt * Float.valueOf(rule.getSpacePrice());
							    	}
							    	
							    } 
							} catch (ParseException e1) {
								e1.printStackTrace();
							}
			        		
		        			
		        		}
	        		}
	        		if(isVip)
	        		{
	        			amt = 0f;
	        			((TextView)findViewById(R.id.amt)).setText(record.carId+"是车场VIP车辆,免费放行!");
	        			record.setDescription("vip车辆,免费放行");
	        		}else{
	        		    ((TextView)findViewById(R.id.amt)).setText("总费用:"+amt);
	        		}
	        	}
	        	
	        } 
	        
	        super.onActivityResult(requestCode, resultCode, data); 
	 }
	 
	 
	 public   class ImageThread extends Thread{
	  		
	        String  url;
	        Handler handler;
	     	public ImageThread(Handler h,String u)
	     	{
	     		this.url = u;
	     		handler = h;
	     	}
			public void run() {
					
				    URL u;
					try {
						u = new URL(url);
						HttpURLConnection conn;
		                conn = (HttpURLConnection) u.openConnection();
		                conn.setConnectTimeout(6000);
		                conn.setDoInput(true);
		                conn.setUseCaches(false);
		                InputStream is = conn.getInputStream();
		                Bitmap bm = BitmapFactory.decodeStream(is);
		                ImageBitmap = Bitmap.createScaledBitmap(bm,bm.getWidth(),bm.getHeight(), true);
		                bm.recycle();
		                
		                is.close();    
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	                     
					handler.sendEmptyMessage(0);
			}
		 }	
	 
	 
	 TimerTask task = new TimerTask(){  
		public void run() {  
			try {
				recordList = SqlliteHander.getTnstantiation(HomeActivity.this).queryLocalRecordList(user.account);
				for(Record r : recordList)
				{
					String s = null ;
					if(r.state.equals("0"))
					{
						s = API.entryRecord(r, HomeActivity.this);
					}
					if(r.state.equals("2"))
					{
						s = API.updateRecord(r, HomeActivity.this);
					}
					if(r.state.equals("4"))
					{
						s = API.syncRecord(r, HomeActivity.this);
					}
					if(s!=null && s.equals("1"))
					{
						if(r.state.equals("0"))
						{
							s = "1";
						}
						if(r.state.equals("2"))
						{
							s = "9";
						}
						if(r.state.equals("4"))
						{
							s = "9";
						}
						
						SqlliteHander.getTnstantiation(HomeActivity.this).updateRecordStatus(r.recordId, s);
					}
				}
				listUpdateHandler.sendEmptyMessage(0);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}  
	}; 
	 Handler listUpdateHandler = new Handler() {
			
			public void handleMessage(Message msg) {
				recordList = SqlliteHander.getTnstantiation(HomeActivity.this).queryRecordList(user.account);
				listAdapter.setDataList(recordList);
				listAdapter.notifyDataSetChanged();
			}
		};
		
	@Override 
	    public void onConfigurationChanged(Configuration config) { 
	    super.onConfigurationChanged(config); 
	} 
}
