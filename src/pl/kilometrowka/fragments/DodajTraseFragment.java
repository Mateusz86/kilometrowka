package pl.kilometrowka.fragments;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;

import pl.kilometrowka.R;
import pl.kilometrowka.dao.DaoSession;
import pl.kilometrowka.dao.DataBase;
import pl.kilometrowka.dao.Trasa;
import android.content.Context;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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
    private Button addCity;

    private RelativeLayout kierowcaWrapper;
    private CheckBox czyZpasazerem;
    
    private LinearLayout root;
    private List<EditText> citis;
	
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
		
		this.citis = new ArrayList<EditText>();
		
		this.skad = (EditText) view.findViewById(R.id.skadEdt);
		citis.add(skad);
		this.dokad = (EditText) view.findViewById(R.id.dokadEdt);
		citis.add(dokad);
		this.km = (EditText) view.findViewById(R.id.kmEdt);
		this.samochodPrywatny = (CheckBox) view.findViewById(R.id.prywatny);
		this.samochodPubliczny = (CheckBox) view.findViewById(R.id.sluzbowy);
		this.kierowca = (CheckBox) view.findViewById(R.id.kierowca);
		this.pasazer = (CheckBox) view.findViewById(R.id.pasazer);
		this.zapisz = (Button) view.findViewById(R.id.zapisz);
		this.root = (LinearLayout) view.findViewById(R.id.citywrapper);
		this.kierowcaWrapper=(RelativeLayout)view.findViewById(R.id.kierowca_wrapper);
		this.czyZpasazerem=(CheckBox) view.findViewById(R.id.czy_z_pasazerem);
		
		samochodPrywatny.setChecked(true);
		kierowca.setChecked(true);
		
		this.addCity = (Button) view.findViewById(R.id.add_new_city);
		
		this.addCity.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				EditText vlEdit = new EditText(getActivity());
				vlEdit.requestFocus();
				vlEdit.setMaxLines(1);
				vlEdit.setLines(1);
				vlEdit.setSingleLine(true);
//				vlEdit.setInputType()
				vlEdit.setInputType(InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
				
				root.addView(vlEdit);
				citis.add(vlEdit);
				
			}
		});

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

		case R.id.sluzbowy:
			samochodPrywatny.setChecked(false);
			samochodPubliczny.setChecked(true);
			break;

		case R.id.kierowca:
			kierowca.setChecked(true);
			kierowcaWrapper.setVisibility(View.VISIBLE);
			pasazer.setChecked(false);
			break;
		case R.id.pasazer:
			kierowca.setChecked(false);
			pasazer.setChecked(true);
			kierowcaWrapper.setVisibility(View.INVISIBLE);

			break;

		case R.id.zapisz:
				zapiszTrase();
			break;

		default:
			break;
		}

	}

	private void zapiszTrase() {
		
		
		/*if(skad.getEditableText().toString().trim().length()>0&&skad.getEditableText().toString()!=null && skad.getEditableText().toString()!="" && skad.getEditableText().toString().length()>0)
		{
			
		}
		else {
			showToast("Prosz« uzupe¸niŤ pole Sk�d");
		}
		
		if(dokad.getEditableText().toString().trim().length()>0&&dokad.getEditableText().toString()!=null && dokad.getEditableText().toString()!="" && dokad.getEditableText().toString().length()>0)
		{
			
		}
		else {
			showToast("Prosz« uzupe¸niŤ pole Dok�d");
		}*/
		
		if(km.getEditableText().toString().trim().length()>0&&km.getEditableText().toString()!=null && km.getEditableText().toString()!="" && km.getEditableText().toString().length()>0)
		{
			
		}
		else {
			showToast("Prosz« uzupe¸niŤ pole Km");
		}
		
	if( km.getEditableText().toString().trim().length()>0)	
		{
		Trasa trasa = new Trasa();

		JSONArray jsMiasta = new JSONArray();
		for(EditText city:citis){
			jsMiasta.put(city.getEditableText().toString());
		}
		
		
		trasa.setMiasta(jsMiasta.toString());
		trasa.setKm(Integer.valueOf(km.getEditableText().toString()));
		
		if(kierowca.isChecked()){
			trasa.setKierowca(true);
			if(samochodPrywatny.isChecked()) {
				trasa.setAutoSluzbowe(false);
			}
			else {
				trasa.setAutoSluzbowe(true);
			}
			if(czyZpasazerem.isChecked()){
				trasa.setCzyZPasazerem(true);
			}else{
				trasa.setCzyZPasazerem(false);
			}
		}else{
			trasa.setKierowca(false);
			trasa.setAutoSluzbowe(false);
			trasa.setCzyZPasazerem(false);
		}
		
		
		
		if(kierowca.isChecked()) {
			trasa.setKierowca(true);
		}
		else {
			trasa.setKierowca(false);
		}
		
		
		 trasa.setData(ListaTrasFragment.date);
		 hideKeyboard();
		 
		showToast("Pomyćlnie zapisano trase w bazie danych");
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
