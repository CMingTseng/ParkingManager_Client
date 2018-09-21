package com.sunset.common;

import android.os.Environment;

  


/**
 * 
 * @author track
 * @version 1.0
 */
public interface Constant { 
		public static final String CODE_SUCCESS = "0";
		
		public static final int VALID_FAILED =408;
		public static final int ID_NO_EXIST =0;
		public static final int SUCCESS_FULL = 1;
		public static final int STATE_RECEIVED = 3;
		public static final int STATE_ON_PASSAGE = 0;
		public static final String FORMAT_TYPE = "UTF-8";
		public static final  String APP_NAME="sunsetAPP";
		public static final String SERVER_URL ="http://221.231.137.118:8080/carpark";
		public static final  String IMAGE_DIR=Environment.getExternalStorageDirectory().getPath()+"/sunsetimage";
		
 
}
