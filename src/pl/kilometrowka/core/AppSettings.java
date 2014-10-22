package pl.kilometrowka.core;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

import android.content.Context;

public final class AppSettings {
	public static final String DB_NAME = "KMDBv2";
	public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat ("HH:mm:ss dd-MM-yyyy");
	public static final SimpleDateFormat TIME_DATE_FORMAT = new SimpleDateFormat ("HH:mm dd-MM-yyyy");
	public static final SimpleDateFormat DATE_YMD_FORMAT = new SimpleDateFormat ("dd-MM-yyyy");
	public static final String PRICE_FORMAT = "%.2";
	public static final DecimalFormat PRICE_FORMAT1 = new DecimalFormat("0.00");
	
	private static Context context;
	public static String MIASTA_DELIMER = ",";
	
	// Trasy
	public static final String SAMOCHOD_PRYWATNY = "Samochód prywatny";
	public static final String SAMOCHOD_SLUZBOWY = "Samochód s³u¿bowy";
	public static final String PASAZER = "pasa¿er";
	public static final String Z_PASAZEREM = " + 1 pasa¿er";
	
	// fonts
	public static final String FONT_LATO_NORMAL = "fonts/RobotoRegular.ttf";

	public static final String CVS_STAWKI_KIEROWCY_FILE ="stk.csv";
	public static final String CVS_STAWKI_PASAZERA_FILE ="stp.csv";
	public static final Double STAWKA_KIEROWCY = Double.valueOf("0.1316");

	public static Context getContext() {
		return context;
	}
	public static void setContext(Context context) {
		AppSettings.context = context;
	}
}
