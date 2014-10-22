package pl.kilometrowka;

import java.util.Date;

import com.splunk.mint.Mint;

import pl.kilometrowka.core.AppSettings;
import pl.kilometrowka.dao.Trasa;
import pl.kilometrowka.fragments.DodajTraseFragment;
import pl.kilometrowka.fragments.DzienFragment;
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
	public static final int DZIEN_FRAGMENT = 3;
	
	public static String CHOOSE_DATE="CHOOSEDATE";
	
	OnClickListener deleteListener;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_kalendarz);
		ActionBar bar = getActionBar();
		bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#33b5e5")));
		
		Mint.initAndStartSession(this, "2cff8d08");
		
		AppSettings.setContext(this);
		StawkiUtils.readCVSKierowcyFile();
		StawkiUtils.readCVSPasazerFile();
		
		
		if (savedInstanceState == null) {
			FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
			KalendarzFragment kalendarzFragment = new KalendarzFragment();
			ft.replace(R.id.kontener, kalendarzFragment,"KALENDARZ_FRAGMENT_TAG");
			ft.commit();
        } else {
        	KalendarzFragment test = (KalendarzFragment) getSupportFragmentManager().findFragmentByTag("KALENDARZ_FRAGMENT_TAG");
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
	/*	case 1:
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
			break;*/
			
		case 3:
			DzienFragment dzienFragment = DzienFragment.newInstance(date);
			ft.replace(R.id.kontener,dzienFragment);
			ft.addToBackStack("DZIEN_FRAGMENT");
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
