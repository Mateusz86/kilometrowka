package pl.kilometrowka.dao;

import pl.kilometrowka.core.AppSettings;
import pl.kilometrowka.dao.DaoMaster.DevOpenHelper;
import android.database.sqlite.SQLiteDatabase;

public final class DataBase {

    // nale¿y zwróciæ uwagê na u¿ycie s³owa kluczowego volatile
    private static volatile DataBase instance = null;
    private SQLiteDatabase db;
    private DaoMaster daoMaster;
    private DaoSession daoSession;
    private DevOpenHelper helper;
    
 
    public static DataBase getInstance() {
        if (instance == null) {
            synchronized (DataBase.class) {
                if (instance == null) {
                    instance = new DataBase();
                }
            }
        }
        return instance;
    }
 

    private DataBase() {
    	helper = new DaoMaster.DevOpenHelper(AppSettings.getContext(), AppSettings.DB_NAME, null);
    	db = helper.getWritableDatabase();
    	daoMaster= new DaoMaster(db);
    	daoSession= daoMaster.newSession();
    }


	public DaoSession getDaoSession() {
		return daoSession;
	}

    
}
