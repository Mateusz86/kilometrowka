package pl.kilometrowka;

import java.util.List;

import pl.kilometrowka.dao.DaoSession;
import pl.kilometrowka.dao.DataBase;
import pl.kilometrowka.dao.StawkiKierowcy;
import pl.kilometrowka.dao.StawkiKierowcyDao;
import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class UstawieniaActivity extends Activity implements
		OnClickListener {

	private Button stawkiBtn, abonamentBtn;
	DaoSession daoSession;

	ActionBar.Tab tab1, tab2, tab3;
	Fragment fragmentTab1 = new FragmentTab1();
	Fragment fragmentTab2 = new FragmentTab2();
	Fragment fragmentTab3 = new FragmentTab3();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ustawienia);
		ActionBar bar = getActionBar();
		bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#33b5e5")));
		daoSession = DataBase.getInstance().getDaoSession();

		ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		tab1 = actionBar.newTab().setText("1");
		tab2 = actionBar.newTab().setText("2");
		tab3 = actionBar.newTab().setText("3");

		//tab1.setTabListener(new MyTabListener(fragmentTab1));
		//tab2.setTabListener(new MyTabListener(fragmentTab2));
		//tab3.setTabListener(new MyTabListener(fragmentTab3));

		actionBar.addTab(tab1);
		actionBar.addTab(tab2);
		actionBar.addTab(tab3);

		prepareStawkiTab();
	}

	private void prepareStawkiTab() {
		/*
		 * LinearLayout tab1 = (LinearLayout) findViewById(R.id.tab1);
		 * StawkiPasazeraDao dao = daoSession.getStawkiPasazeraDao();
		 * List<StawkiPasazera> stawki = dao.queryBuilder().build().list();
		 * EditText km_od,km_do,cena; for(StawkiPasazera stawka: stawki){ km_od
		 * = generateEditText(String.valueOf(stawka.getKm_od())); km_do =
		 * generateEditText(String.valueOf(stawka.getKm_do())); cena =
		 * generateEditText(String.valueOf(stawka.getCena())); }
		 */
	}

	private EditText generateEditText(String value) {
		EditText lvEditText = new EditText(this);
		lvEditText.setMaxLines(1);
		lvEditText.setLines(1);
		lvEditText.setSingleLine(true);
		lvEditText.setText(value);
		return lvEditText;
	}

	private void showAbonamentDialog() {
		// custom dialog
		final Dialog dialog = new Dialog(this);
		dialog.setContentView(R.layout.abonament_custom);
		dialog.setTitle("Title...");

		DaoSession daoSession = DataBase.getInstance().getDaoSession();
		StawkiKierowcyDao stawkiDao = daoSession.getStawkiKierowcyDao();

		List<StawkiKierowcy> stawki = stawkiDao.queryBuilder().build().list();

		LinearLayout wrapper = (LinearLayout) dialog
				.findViewById(R.id.abonament_wrapper);
		EditText km, cena;
		for (StawkiKierowcy stawka : stawki) {
			km = new EditText(this);
			km.setMaxLines(1);
			km.setLines(1);
			km.setSingleLine(true);
			km.setText(String.valueOf(stawka.getKm()));

			cena = new EditText(this);
			cena.setMaxLines(1);
			cena.setLines(1);
			cena.setSingleLine(true);
			cena.setText(String.valueOf(stawka.getKm()));

			wrapper.addView(km);
			wrapper.addView(cena);
		}

		// // set the custom dialog components - text, image and button
		// TextView text = (TextView) dialog.findViewById(R.id.text);
		// text.setText("Android custom dialog example!");
		// ImageView image = (ImageView) dialog.findViewById(R.id.image);
		// image.setImageResource(R.drawable.ic_launcher);

		Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
		// if button is clicked, close the custom dialog
		dialogButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});

		dialog.show();
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

	private class FragmentTab1 extends Fragment {
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View view = inflater.inflate(R.layout.tab, container, false);
			TextView textview = (TextView) view.findViewById(R.id.tabtextview);
			textview.setText("Jeden");
			return view;
		}
	}

	private class FragmentTab2 extends Fragment {
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View view = inflater.inflate(R.layout.tab, container, false);
			TextView textview = (TextView) view.findViewById(R.id.tabtextview);
			textview.setText("dwa");
			return view;
		}
	}

	private class FragmentTab3 extends Fragment {
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View view = inflater.inflate(R.layout.tab, container, false);
			TextView textview = (TextView) view.findViewById(R.id.tabtextview);
			textview.setText("trzy");
			return view;
		}
	}


}
