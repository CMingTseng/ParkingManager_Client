package com.sunset.ui;



import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class AboutActivity extends Activity{
	
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about);
		
		findViewById(R.id.button_return).setVisibility(View.VISIBLE);
		((TextView)findViewById(R.id.TITLE_TEXT)).setText(R.string.about_tips);
	   ((Button)findViewById(R.id.button_return)).setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				 
				AboutActivity.this.finish();
			}});
	}
	
 
}
