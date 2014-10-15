package pl.kilometrowka.utils;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;

import pl.kilometrowka.core.AppSettings;
import pl.kilometrowka.dao.DaoSession;
import pl.kilometrowka.dao.DataBase;
import pl.kilometrowka.dao.Trasa;
import pl.kilometrowka.dao.TrasaDao;
import pl.kilometrowka.dao.TrasaDao.Properties;

public class TrasaUtils {
	
	private static DaoSession daoSession = DataBase.getInstance().getDaoSession();
	
	public static List<Trasa> getTrasyFromDate(Date date) {
	
		try {
			String dateString = AppSettings.DATE_YMD_FORMAT.format(date);
			TrasaDao trasaDao = daoSession.getTrasaDao();
			List<Trasa> trasy;
			trasy = trasaDao.queryBuilder().where(Properties.Data.eq(AppSettings.DATE_YMD_FORMAT.parse(dateString))).orderDesc(Properties.Data).list();
			return trasy;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return  new ArrayList<Trasa>();
	}
	
	public static String getMiastaByJson(String json){
		try {
			JSONArray jsonCities = new JSONArray(json);
			StringBuilder lvSBilder = new StringBuilder();
			for(int i =0; i<jsonCities.length();i++){
				if(i!=0){
					lvSBilder.append(AppSettings.MIASTA_DELIMER);
				}
				lvSBilder.append(jsonCities.getString(i));
			}
			return lvSBilder.toString();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			System.out.println("Coæ posz¸o nie tak przy parsowaniu jsona");
			e.printStackTrace();
			return "";
		}
	}
}
