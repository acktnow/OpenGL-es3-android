package com.opengles3.demo.tools;

import android.util.Log;

public class Debug {
	private final String TAG;
	
	
	
	public Debug(Object mainClass) {
		TAG = mainClass.getClass().getName();
	}
	
	public static Boolean debug=true;
	
	

	public static void debug(String text,Class mainClass){
		if(debug){
			Log.d(mainClass.getClass().getName(), text);
		}
	}
	public static void warning(String text,Class mainClass){
		if(debug){
			Log.w(mainClass.getClass().getName(), text);
		}
	}
	public static void verbose(String text,Class mainClass){
		if(debug){
			Log.v(mainClass.getClass().getName(), text);
		}
	}
	public static void error(String text,Class mainClass){
		if(debug){
			Log.e(mainClass.getClass().getName(), text);
		}
	}
}
