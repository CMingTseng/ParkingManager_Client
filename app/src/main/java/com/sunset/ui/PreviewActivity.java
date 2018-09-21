package com.sunset.ui;



import java.io.File;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.sunset.bean.Record;
import com.sunset.common.Constant;
import com.sunset.util.BaseContorl;

public class PreviewActivity extends Activity{
	ImageView rukouImg;
	ImageView chukouImg;
	Record record;
	Bitmap rukouBitmap;
	Bitmap chukouBitmap;
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_preview);
		findViewById(R.id.button_return).setVisibility(View.VISIBLE);
		((TextView)findViewById(R.id.TITLE_TEXT)).setText("照片预览");
	   ((Button)findViewById(R.id.button_return)).setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				PreviewActivity.this.finish();
			}});
	   
	    rukouImg = (ImageView) findViewById(R.id.rukouCarImg);
	    chukouImg = (ImageView) findViewById(R.id.chukouCarImg);
	    record = (Record) this.getIntent().getExtras().getSerializable("record");
	    
	    
	    File EntryImg = new File(Constant.IMAGE_DIR+"/" +record.getEntryImg());
	    if(EntryImg.exists())
	    {
	    	rukouImg.setImageBitmap(BitmapFactory.decodeFile(Constant.IMAGE_DIR+"/" +record.getEntryImg() ));
	    }else
	    {
	    	 BaseContorl.doShowHToask(this, "正在下载入场照片,请稍后......");
	    	 new ImageThread(imageHandler,Constant.SERVER_URL+"/"+record.getEntryImg(),1).start();
	    }
	    if(record.getExportImg()!=null)
	    {
	    	File ExportImg = new File(Constant.IMAGE_DIR+"/" +record.getExportImg());
	    	if(ExportImg.exists())
		    {
	    		chukouImg.setImageBitmap(BitmapFactory.decodeFile(Constant.IMAGE_DIR+"/" +record.getExportImg() ));
		    }else
		    {
		    	 BaseContorl.doShowHToask(this, "正在下载出场照片,请稍后......");
		    	new ImageThread(imageHandler,Constant.SERVER_URL+"/"+record.getExportImg(),2).start();
		    }
	    }
	   /* if(net)
	    {
	    	
	    	 BaseContorl.doShowHToask(this, "正在下载照片,请稍后......");
	    	 new ImageThread(imageHandler,Constant.SERVER_URL+"/"+record.getEntryImg(),1).start();
	    	if(record.getExportImg()!=null)
		    {
	    		new ImageThread(imageHandler,Constant.SERVER_URL+"/"+record.getExportImg(),2).start();
		    }
	    }else
	    {
	    	File
		    rukouImg.setImageBitmap(BitmapFactory.decodeFile(Constant.IMAGE_DIR+"/" +record.getEntryImg() ));
		    if(record.getExportImg()!=null)
		    {
		    	chukouImg.setImageBitmap(BitmapFactory.decodeFile(Constant.IMAGE_DIR+"/" +record.getExportImg() ));
		    }
	    }*/
	}
	
	 Handler imageHandler = new Handler() {
			public void handleMessage(Message msg) {
				 if(msg.what==1)
					 ((ImageView) PreviewActivity.this.findViewById(R.id.rukouCarImg)).setImageBitmap(rukouBitmap);
	                else
	             ((ImageView) PreviewActivity.this.findViewById(R.id.chukouCarImg)).setImageBitmap(chukouBitmap);
				
			}
		};	
		
	 public   class ImageThread extends Thread{
	  		
	        String  url;
	        Handler handler;
	        int  k;
	     	public ImageThread(Handler h,String u,int  k )
	     	{
	     		this.url = u;
	     		handler = h;
	     		this.k = k;
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
		                if(k==1)
		                	rukouBitmap = Bitmap.createScaledBitmap(bm,bm.getWidth(),bm.getHeight(), true);
		                else
		                	chukouBitmap = Bitmap.createScaledBitmap(bm,bm.getWidth(),bm.getHeight(), true);
		                bm.recycle();
		                
		                is.close();    
					} catch (Exception e) {
						e.printStackTrace();
					}
	                     
					handler.sendEmptyMessage(k);
			}
		 }	
}
