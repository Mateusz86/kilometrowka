package pl.kilometrowka;



import java.util.Date;
import java.util.List;

import pl.kilometrowka.core.AppSettings;
import pl.kilometrowka.dao.DaoSession;
import pl.kilometrowka.dao.DataBase;
import pl.kilometrowka.dao.Trasa;
import pl.kilometrowka.dao.TrasaDao;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.example.kilometrowka.R;

public class MainActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		AppSettings.setContext(this);
		
		// test bazy danych
		DaoSession daoSession = DataBase.getInstance().getDaoSession() ;
		
		TrasaDao trasaDao = daoSession.getTrasaDao();
		
		Trasa trasa = new Trasa();
		trasa.setMiasta("Krak�w,Katowice");
		trasa.setKm(new Double(23.0));
		trasa.setKierowca(true);
		trasa.setAutoSluzbowe(true);
		trasa.setData(new Date());
		
		trasaDao.insert(trasa);
		
		
		List<Trasa>  trasy =  trasaDao.queryBuilder().build().list();
		System.out.println("trasy");
		System.out.println(trasy);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
