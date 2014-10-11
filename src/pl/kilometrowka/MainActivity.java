package pl.kilometrowka;

import java.util.Date;
import java.util.List;

import pl.kilometrowka.core.AppSettings;
import pl.kilometrowka.dao.DaoSession;
import pl.kilometrowka.dao.DataBase;
import pl.kilometrowka.dao.Trasa;
import pl.kilometrowka.dao.TrasaDao;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;


public class MainActivity extends ActionBarActivity implements OnClickListener {

	private Button kalendarz;
	private Button raport;
	private Button ustawienia;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		AppSettings.setContext(this);

		setUpView();
		setUpListeners();

		// test bazy danych
		DaoSession daoSession = DataBase.getInstance().getDaoSession();

		TrasaDao trasaDao = daoSession.getTrasaDao();

		Trasa trasa = new Trasa();
		trasa.setMiasta("Kraków,Katowice");
		trasa.setKm(new Double(23.0));
		trasa.setKierowca(true);
		trasa.setAutoSluzbowe(true);
		trasa.setData(new Date());

		trasaDao.insert(trasa);

		List<Trasa> trasy = trasaDao.queryBuilder().build().list();
		System.out.println("trasy");
		System.out.println(trasy);
	}

	private void setUpView() {
		kalendarz = (Button) findViewById(R.id.kalendarz);
		raport = (Button) findViewById(R.id.raport);
		ustawienia = (Button) findViewById(R.id.ustawienia);
	}

	private void setUpListeners() {
		kalendarz.setOnClickListener(this);
		raport.setOnClickListener(this);
		ustawienia.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		Intent intent;
		switch (v.getId()) {
		case R.id.kalendarz:
			intent = new Intent(MainActivity.this, KalendarzActivity.class);
			startActivity(intent);
			break;

		case R.id.raport:
			intent = new Intent(MainActivity.this, RaportActivity.class);
			startActivity(intent);

			break;

		case R.id.ustawienia:
			intent = new Intent(MainActivity.this, UstawieniaActivity.class);
			startActivity(intent);
			break;
		default:
			break;
		}

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
