package pl.kilometrowka.fragments;

import pl.kilometrowka.R;
import pl.kilometrowka.dao.DaoSession;
import pl.kilometrowka.dao.DataBase;
import pl.kilometrowka.dao.Trasa;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class DodajTraseFragment extends Fragment implements OnClickListener {

	private EditText skad;
	private EditText dokad;
	private EditText km;
	private CheckBox samochodPrywatny;
	private CheckBox samochodPubliczny;
	private CheckBox kierowca;
	private CheckBox pasazer;
	private Button zapisz;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_dodaj_trase, container,
				false);

		setUpViews(view);
		setUpListeners();

		return view;

	}

	private void setUpViews(View view) {
		this.skad = (EditText) view.findViewById(R.id.skadEdt);
		this.dokad = (EditText) view.findViewById(R.id.dokadEdt);
		this.km = (EditText) view.findViewById(R.id.kmEdt);
		this.samochodPrywatny = (CheckBox) view.findViewById(R.id.prywatny);
		this.samochodPubliczny = (CheckBox) view.findViewById(R.id.publiczny);
		this.kierowca = (CheckBox) view.findViewById(R.id.kierowca);
		this.pasazer = (CheckBox) view.findViewById(R.id.pasazer);
		this.zapisz = (Button) view.findViewById(R.id.zapisz);
		
		samochodPrywatny.setChecked(true);
		kierowca.setChecked(true);

	}

	private void setUpListeners() {
		samochodPrywatny.setOnClickListener(this);
		samochodPubliczny.setOnClickListener(this);
		kierowca.setOnClickListener(this);
		pasazer.setOnClickListener(this);
		zapisz.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.prywatny:
			samochodPrywatny.setChecked(true);
			samochodPubliczny.setChecked(false);
			break;

		case R.id.publiczny:
			samochodPrywatny.setChecked(false);
			samochodPubliczny.setChecked(true);
			break;

		case R.id.kierowca:
			kierowca.setChecked(true);
			pasazer.setChecked(false);
			break;
		case R.id.pasazer:
			kierowca.setChecked(false);
			pasazer.setChecked(true);

			break;

		case R.id.zapisz:
				zapiszTrase();
			break;

		default:
			break;
		}

	}

	private void zapiszTrase() {
		
		
		if(skad.getEditableText().toString().trim().length()>0&&skad.getEditableText().toString()!=null && skad.getEditableText().toString()!="" && skad.getEditableText().toString().length()>0)
		{
			
		}
		else {
			showToast("Prosz« uzupe¸ni pole Skˆd");
		}
		
		if(dokad.getEditableText().toString().trim().length()>0&&dokad.getEditableText().toString()!=null && dokad.getEditableText().toString()!="" && dokad.getEditableText().toString().length()>0)
		{
			
		}
		else {
			showToast("Prosz« uzupe¸ni pole Dokˆd");
		}
		
		if(km.getEditableText().toString().trim().length()>0&&km.getEditableText().toString()!=null && km.getEditableText().toString()!="" && km.getEditableText().toString().length()>0)
		{
			
		}
		else {
			showToast("Prosz« uzupe¸ni pole Km");
		}
		
	if(skad.getEditableText().toString().trim().length()>0 &&dokad.getEditableText().toString().trim().length()>0 && km.getEditableText().toString().trim().length()>0)	
		{
		Trasa trasa = new Trasa();
		StringBuilder miasto = new StringBuilder(skad.getEditableText().toString());
		miasto.append(" - " +dokad.getEditableText().toString());
		trasa.setMiasta(miasto.toString());
		trasa.setKm(Double.valueOf(km.getEditableText().toString()));
		
		if(samochodPrywatny.isChecked()) {
			trasa.setAutoSluzbowe(false);
		}
		else {
			trasa.setAutoSluzbowe(true);
		}
		
		if(kierowca.isChecked()) {
			trasa.setKierowca(true);
		}
		else {
			trasa.setKierowca(false);
		}
		 trasa.setData(ListaTrasFragment.date);
		 hideKeyboard();
		 
		showToast("Pomyælnie zapisano trase w bazie danych");
		DaoSession daoSession = DataBase.getInstance().getDaoSession();
		daoSession.getTrasaDao().insert(trasa);
		getFragmentManager().popBackStack();
		}	
	}

	private void hideKeyboard() {
		InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(
			      Context.INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(km.getWindowToken(), 0);
	}

	private void showToast(String desc) {
		Toast.makeText(getActivity(), desc, Toast.LENGTH_LONG).show();
	}

	
	
}
