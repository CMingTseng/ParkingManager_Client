package com.sunset.ui;



import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.sunset.bean.FeeRule;
import com.sunset.util.SqlliteHander;

public class FeeRuleActivity extends Activity{
	
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_feerule);
		
		findViewById(R.id.button_return).setVisibility(View.VISIBLE);
		((TextView)findViewById(R.id.TITLE_TEXT)).setText("收费规则");
	   ((Button)findViewById(R.id.button_return)).setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				 
				FeeRuleActivity.this.finish();
			}});
	   FeeRule rule = SqlliteHander.getTnstantiation(this).queryFeeRule();
	   if(rule!=null)
	   {
		   ((TextView)findViewById(R.id.freeTime)).setText(rule.freeTime+"分钟");
		   ((TextView)findViewById(R.id.firstTime)).setText(rule.firstTime+"分钟");
		   ((TextView)findViewById(R.id.firstPrice)).setText(rule.firstPrice+"元");
		   ((TextView)findViewById(R.id.spaceTime)).setText(rule.spaceTime+"分钟");
		   ((TextView)findViewById(R.id.spacePrice)).setText(rule.spacePrice+"元");
		   ((TextView)findViewById(R.id.maxFee)).setText(rule.maxFee+"元");
	   }
	   
	   
	}
	
 
}
