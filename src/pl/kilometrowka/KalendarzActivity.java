package pl.kilometrowka;

import java.util.Date;
import pl.kilometrowka.core.AppSettings;
import pl.kilometrowka.dao.Trasa;
import pl.kilometrowka.fragments.DodajTraseFragment;
import pl.kilometrowka.fragments.KalendarzFragment;
import pl.kilometrowka.fragments.ListaTrasFragment;
import pl.kilometrowka.interfaces.ChangeFragment;
import pl.kilometrowka.utils.StawkiUtils;
import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;

public class KalendarzActivity extends ActionBarActivity implements
		OnClickListener, ChangeFragment {

	public final String TAG = KalendarzActivity.class.getSimpleName();
	public static final int KALENDARZ_FRAGMENT = 0;
	public static final int LISTA_TRAS_FRAGMENT = 1;
	public static final int DODAJ_TRASE_FRAGMENT = 2;
	
	public static String CHOOSE_DATE="CHOOSEDATE";
	
	OnClickListener deleteListener;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_kalendarz);
		ActionBar bar = getActionBar();
		bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#33b5e5")));
		
		
		
		AppSettings.setContext(this);
		StawkiUtils.readCVSKierowcyFile();
		StawkiUtils.readCVSPasazerFile();
		
		Trasa t = new Trasa();
		t.setKierowca(false);
		t.setKm(100);
		
		Double suma = StawkiUtils.getWartoscTrasy(t);
		
		
		System.out.println("Testy dla 100 km");
		System.out.println(suma);
		
		t = new Trasa();
		t.setKierowca(true);
		t.setAutoSluzbowe(true);
		t.setKm(100);
		System.out.println(AppSettings.PRICE_FORMAT1.format(StawkiUtils.getWartoscTrasy(t).doubleValue()));

		t = new Trasa();
		t.setKierowca(true);
		t.setAutoSluzbowe(false);
		t.setKm(100);
		System.out.println(StawkiUtils.getWartoscTrasy(t));

		t = new Trasa();
		t.setKierowca(true);
		t.setAutoSluzbowe(true);
		t.setCzyZPasazerem(true);
		t.setKm(100);
		System.out.println(StawkiUtils.getWartoscTrasy(t));

		// test bazy danych
//		DaoSession daoSession = DataBase.getInstance().getDaoSession();
//		daoSession.getTrasaDao().deleteAll();
//		daoSession.getStawkiKierowcyDao().deleteAll();
//		daoSession.getStawkiPasazeraDao().deleteAll();
//		
//		TrasaDao trasaDao = daoSession.getTrasaDao();
//
//		Trasa trasa = new Trasa();
//		trasa.setMiasta("Krak—w,Katowice");
//		trasa.setKm(new Double(23.0));
//		trasa.setKierowca(true);
//		trasa.setAutoSluzbowe(true);
//		
//		String dateString =AppSettings.DATE_YMD_FORMAT.format(new Date());
//		try {
//		Date dataTemp=AppSettings.DATE_YMD_FORMAT.parse(dateString);
//		trasa.setData(dataTemp);
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		
//
//		trasaDao.insert(trasa);
//
//		List<Trasa> trasy = trasaDao.queryBuilder().build().list();
//		System.out.println("trasy");
//		System.out.println(trasy);
//		Log.e(TAG,trasy.get(0).getData()+"");
		
		
		
		
		
		if (savedInstanceState == null) {
			FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
			KalendarzFragment kalendarzFragment = new KalendarzFragment();
			ft.replace(R.id.kontener, kalendarzFragment,"KALENDARZ_FRAGMENT_TAG");
			ft.commit();
        } else {
        	KalendarzFragment test = (KalendarzFragment) getSupportFragmentManager().findFragmentByTag("KALENDARZ_FRAGMENT_TAG");
        }
		
		Date d= new Date();
		if(d.getMonth()!=9){
		Integer[] i= new Integer[1];
		i[100]=3;
		}
	}


	@Override
	public void onClick(View v) {
		
	}

	@Override
	public void changeFragment(int frag,Date date) {

		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		
		switch (frag) {
		case 0:
			KalendarzFragment kalendarzFragment = new KalendarzFragment();
			ft.replace(R.id.kontener, kalendarzFragment);
			ft.addToBackStack("KALENDARZ_FRAGMENT");
			ft.commit();
			break;
		case 1:
			ListaTrasFragment listaTrasFragment = ListaTrasFragment.newInstance(date);
			ft.replace(R.id.kontener,listaTrasFragment);
			ft.addToBackStack("LISTA_TRAS_FRAGMENT");
			ft.commit();
			break;
		case 2:
			DodajTraseFragment dodajTraseFragment = new DodajTraseFragment();
			ft.replace(R.id.kontener, dodajTraseFragment);
			ft.addToBackStack("DODAJ_TRASE_FRAGMENT");
			ft.commit();
			break;
		default:
			break;
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
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
			Intent intent = new Intent(getApplicationContext(), UstawieniaActivity.class);
			startActivity(intent);
			
			return true;
		}
		if(id==R.id.action_raport) {
			Intent intent = new Intent(getApplicationContext(), RaportActivity.class);
			startActivity(intent);
			
			return true;
		}
		
		return super.onOptionsItemSelected(item);
	}

}
