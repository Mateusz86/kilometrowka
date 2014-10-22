package pl.kilometrowka;

import pl.kilometrowka.fragments.KalendarzFragment;
import pl.kilometrowka.fragments.RaportFragment;
import android.app.ActionBar;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;

public class RaportActivity extends ActionBarActivity implements
		OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_raport);
		ActionBar bar = getActionBar();
		bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#33b5e5")));
		
		
		if (savedInstanceState == null) {
			FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
			RaportFragment raportFragment = new RaportFragment();
			int week = this.getIntent().getIntExtra("week", 0);
			int year = this.getIntent().getIntExtra("year", 0);
			
			Bundle bundle = new Bundle();
			bundle.putInt("week", week);
			bundle.putInt("year", year);
			raportFragment.setArguments(bundle);
			ft.replace(R.id.kontener, raportFragment,"RAPORT_FRAGMENT_TAG");
			ft.commit();
        } else {
        	KalendarzFragment test = (KalendarzFragment) getSupportFragmentManager().findFragmentByTag("RAPORT_FRAGMENT_TAG");
        }
	}

	@Override
	public void onClick(View v) {

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
