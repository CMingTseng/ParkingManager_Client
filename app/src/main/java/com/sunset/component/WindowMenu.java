package com.sunset.component;


import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.PopupWindow;

import com.sunset.ui.ActiveRecordActivity;
import com.sunset.ui.FeeRuleActivity;
import com.sunset.ui.FullRecordActivity;
import com.sunset.ui.LoginActivity;
import com.sunset.ui.R;
import com.sunset.ui.SettingActivity;
public class WindowMenu{

	Activity context;
	PopupWindow popupWindow;
	public WindowMenu(Activity c) {
		context =c;
		View pupview = LayoutInflater.from(context).inflate( R.layout.pupwindow_menu, null);
		 pupview.setFocusableInTouchMode(true);
		 ((Button) pupview.findViewById(R.id.exit_button)).setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View arg0) {
					popupWindow.dismiss();
					Intent intent = new Intent(context,LoginActivity.class);  
					context.startActivity(intent);
					context.finish();
				}});
		 ((Button) pupview.findViewById(R.id.setting_item)).setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View arg0) {
					Intent intent = new Intent(context,SettingActivity.class);  
					context.startActivity(intent);
				}});
		 ((Button) pupview.findViewById(R.id.active_record_button)).setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View arg0) {
					Intent aintent = new Intent(context,ActiveRecordActivity.class);   
					context. startActivity(aintent);
				}});
		 ((Button) pupview.findViewById(R.id.full_menu_button)).setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View arg0) {
					Intent intent = new Intent(context,FullRecordActivity.class);  
					context.startActivity(intent);
				}});
		 
		 ((Button) pupview.findViewById(R.id.rule_menu_button)).setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View arg0) {
					Intent intent = new Intent(context,FeeRuleActivity.class);  
					context.startActivity(intent);
				}});
		 pupview.setOnKeyListener(new OnKeyListener()
		 {

			@Override
			public boolean onKey(View arg0, int keyCode, KeyEvent event) {
				 if (event.getAction() == KeyEvent.ACTION_DOWN
                        && keyCode == KeyEvent.KEYCODE_MENU) {
					 if(!popupWindow.isShowing())
						{

						// popupWindow.showAtLocation(context.findViewById(R.id.window_menu_parent), Gravity.BOTTOM, 0, LayoutParams.WRAP_CONTENT);
						}else
						{
							popupWindow.dismiss();
						}
                  return true;
                }
                return false;
			}
			 
		 });
		
		 popupWindow = new PopupWindow(pupview,LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT,true);  
		 popupWindow.setBackgroundDrawable(new BitmapDrawable());
		 popupWindow.setAnimationStyle(R.style.AnimationPreview);
		 //让导航菜单可以得到焦点
		 popupWindow.setFocusable(true);
		 popupWindow.setTouchable(true);
		 popupWindow.setOutsideTouchable(true);  
	}
	 
	public PopupWindow getPopupWindow()
	{
		return popupWindow;
	}
}
