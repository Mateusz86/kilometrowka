package pl.kilometrowka.core;

import java.text.SimpleDateFormat;

import android.content.Context;

public final class AppSettings {
	public static final String DB_NAME = "kilometrowka-db";
	public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat ("HH:mm:ss dd-MM-yyyy");
	public static final SimpleDateFormat TIME_DATE_FORMAT = new SimpleDateFormat ("HH:mm dd-MM-yyyy");
	public static final SimpleDateFormat DATE_YMD_FORMAT = new SimpleDateFormat ("dd-MM-yyyy");
	
	private static Context context;
	public static String MIASTA_DELIMER = ",";


	public static Context getContext() {
		return context;
	}
	public static void setContext(Context context) {
		AppSettings.context = context;
	}
}
