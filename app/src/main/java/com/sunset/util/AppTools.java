package com.sunset.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Locale;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.xml.sax.InputSource;

import android.content.Context;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;

import com.sunset.bean.FeeRule;
import com.sunset.ui.R;

/** 
 * 
 * @author track
 * @version 1.0
 */
public class AppTools {

 
	public static String getCurrentlyDate() {
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyyMMddHHmmss");
		return dateFormat.format(new Date());
	}

	public static String dateToDate(String t) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String r = null;
		try {
			Date date = dateFormat.parse(t);
			r = dateFormat1.format(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return r;
	}
	public static String timeToTime(String t) {
		t= t.substring(8);
		SimpleDateFormat dateFormat = new SimpleDateFormat("HHmmss");
		SimpleDateFormat dateFormat1 = new SimpleDateFormat("HH:mm:ss");
		String r = null;
		try {
			Date date = dateFormat.parse(t);
			r = dateFormat1.format(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return r;
	}
	public static InputStream getNetInputStream(String url)
			throws IOException {

		HttpGet httpRequest = new HttpGet(url);
		HttpClient httpclient = new DefaultHttpClient();
		HttpResponse response = (HttpResponse) httpclient.execute(httpRequest);
		HttpEntity entity = response.getEntity();
		BufferedHttpEntity bufferedHttpEntity = new BufferedHttpEntity(entity);
		InputStream is = bufferedHttpEntity.getContent();
		return is;
	}
	
	public static InputSource getNetInputSource(String site, String format)
	   throws IOException {

		site += "&random=" + Math.random();
		URL url = new URL(site);
		URLConnection conn = url.openConnection();
		InputStream is = conn.getInputStream();
		InputStreamReader isr = new InputStreamReader(is, format);
		InputSource source = new InputSource(isr);
		return source;
    }


	 
	
	 

	public static String getLocaleLanguage() {
		Locale l = Locale.getDefault();
		return String.format("%s", l.getLanguage(), l.getCountry())
				.toUpperCase();
	}
	
	
	public static String getResponseRelsut(String httpUrl)
	throws IOException {
			HttpPost request = new HttpPost(httpUrl);
			request.getParams().setParameter("charset", HTTP.UTF_8);
			HttpClient hc = new DefaultHttpClient();
			HttpResponse response = hc.execute(request);
			String result = EntityUtils.toString(response.getEntity());
			return result;
      }
	
	public static String howTimeAgo(Context context,long t)
	{
		String msg= ""; 
		long nowTime = System.currentTimeMillis();
		long time = (nowTime - t)/(60 * 1000) ;
		if(time > 0 &&time < 60)
		{
			msg = time + context.getString(R.string.minuteago);
		}else if(time == 0)
		{
			msg = context.getString(R.string.at_now);
		}
		time = (nowTime- t)/(60 * 1000 * 60) ;
		if(time > 0 &&time < 24)
		{
			msg = time + context.getString(R.string.hourago);
		}
		time = (nowTime - t)/(60 * 1000 * 60 * 24) ;
		if(time > 0 )
		{
			msg =  time + context.getString(R.string.dayago);
		}
		return msg;
	}
	// 此方法直接照搬自网络上的一个下拉刷新的demo，此处是“估计”headView的width以及height
	public static void measureView(View child) {
		ViewGroup.LayoutParams p = child.getLayoutParams();
		if (p == null) {
			p = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,
					ViewGroup.LayoutParams.WRAP_CONTENT);
		}
		int childWidthSpec = ViewGroup.getChildMeasureSpec(0, 0 + 0, p.width);
		int lpHeight = p.height;
		int childHeightSpec;
		if (lpHeight > 0) {
			childHeightSpec = MeasureSpec.makeMeasureSpec(lpHeight,
					MeasureSpec.EXACTLY);
		} else {
			childHeightSpec = MeasureSpec.makeMeasureSpec(0,
					MeasureSpec.UNSPECIFIED);
		}
		child.measure(childWidthSpec, childHeightSpec);
	}
	
	public static String getLocalIpAddress() {
	    try {
	        for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); 
			en.hasMoreElements();) {
	            NetworkInterface intf = en.nextElement();
	            for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); 
			enumIpAddr.hasMoreElements();) {
	                InetAddress inetAddress = enumIpAddr.nextElement();
	                if (!inetAddress.isLoopbackAddress()) {
	                    return inetAddress.getHostAddress().toString();
	                }
	            }
	        }
	    } catch (SocketException ex) {
	    	ex.printStackTrace();
	    }
	    return null;
	}
	
	
	public  static float   oneDayMaxFee(FeeRule rule)
	{
		long t = 24 * 60 - Long.valueOf(rule.freeTime);
		//t = (t  - Long.valueOf(rule.getFirstTime()));
		///float amt = Float.valueOf(rule.getFirstPrice());
		float amt= 0f;
		long s = t /  Long.valueOf(rule.getSpaceTime());
		if(t %  Long.valueOf(rule.getSpaceTime()) > 0)
		{
		  s ++;
		}
		amt += s * Float.valueOf(rule.getSpacePrice());
		
		return   amt < Float.valueOf(rule.maxFee)?amt:Float.valueOf(rule.maxFee);
	}
	
	public  static boolean   feeLtMaxFee(FeeRule rule)
	{
		long t = 24 * 60 - Long.valueOf(rule.freeTime);
		t = (t  - Long.valueOf(rule.getFirstTime()));
		float amt = Float.valueOf(rule.getFirstPrice());
		long s = t /  Long.valueOf(rule.getSpaceTime());
		if(t %  Long.valueOf(rule.getSpaceTime()) > 0)
		{
		  s ++;
		}
		amt += s * Float.valueOf(rule.getSpacePrice());
		
		return Float.valueOf(rule.maxFee) < amt;
	}
}
