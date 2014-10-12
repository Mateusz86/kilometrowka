package pl.kilometrowka.fragments;

import pl.kilometrowka.R;
import pl.kilometrowka.dao.Trasa;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

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
		Trasa trasa = new Trasa();
		
	}

}
