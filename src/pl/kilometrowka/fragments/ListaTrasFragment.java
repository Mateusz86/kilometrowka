package pl.kilometrowka.fragments;

import java.util.Date;
import java.util.List;

import com.pdfjet.TextAlign;

import pl.kilometrowka.KalendarzActivity;
import pl.kilometrowka.R;
import pl.kilometrowka.adapter.Adapter;
import pl.kilometrowka.core.AppSettings;
import pl.kilometrowka.dao.DaoSession;
import pl.kilometrowka.dao.DataBase;
import pl.kilometrowka.dao.Trasa;
import pl.kilometrowka.interfaces.ChangeFragment;
import pl.kilometrowka.utils.StawkiUtils;
import pl.kilometrowka.utils.TrasaUtils;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class ListaTrasFragment extends Fragment implements OnClickListener{
	
 public final String TAG =ListaTrasFragment.class.getSimpleName();	
 public static Date date; 
 private ListView listView;
 private List<Trasa> listaTras;
 private Adapter<Trasa> adapter;
 private Button dodajTrase;
 ChangeFragment changeFragmentListener;
 
 
 private OnClickListener deleteListener= new OnClickListener() {
	
	@Override
	public void onClick(View v) {
		int pos = (Integer) v.getTag();
		DaoSession daoSession = DataBase.getInstance().getDaoSession();
		//List<Trasa> trasy = daoSession.getTrasaDao().queryBuilder().build().list();
		//if(trasy!=null && trasy.size()>0) {
			Trasa trasaToDelete = listaTras.get(pos);
			daoSession.getTrasaDao().delete(trasaToDelete);
			listaTras.remove(pos);
			adapter.setList(listaTras);
			adapter.notifyDataSetChanged();
		//}
		
	}
};
	
	 public static ListaTrasFragment newInstance(Date date) {
		 ListaTrasFragment listaTrasFragment = new ListaTrasFragment();

	        // Supply index inputas an argument.
	        Bundle args = new Bundle();
	        args.putSerializable(KalendarzActivity.CHOOSE_DATE, date);
	        listaTrasFragment.setArguments(args);
	        return listaTrasFragment;
	    }
	
	
	 @Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		ListaTrasFragment.date = (Date) getArguments().getSerializable(KalendarzActivity.CHOOSE_DATE);
		setHasOptionsMenu(true);
		setMenuVisibility(true);
	 }
	 
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_lista_tras, container, false);
        
        setUpViews(view);
        Log.e(TAG,date.toString());
        return view;
        
    }


	private void setUpViews(View view) {
		listaTras=TrasaUtils.getTrasyFromDate(date);
		dodajTrase = (Button) view.findViewById(R.id.dodajTrase);
		dodajTrase.setOnClickListener(this);
		for(Trasa t:listaTras) {
			Log.e(TAG,t.getMiasta());
		}
		listView = (ListView) view.findViewById(R.id.listView);
		adapter = new Adapter<Trasa>(listaTras,getActivity(),deleteListener);
		listView.setAdapter(adapter);
		System.out.println(listaTras);
		
		TextView data = (TextView) view.findViewById(R.id.list_date); 
		data.setText(AppSettings.DATE_YMD_FORMAT.format(date));
		Double suma=0.0;
		int sumaKm=0;
		for(Trasa t:listaTras){
			sumaKm+=t.getKm();
			suma+=StawkiUtils.getWartoscTrasy(t);
		}
		TextView sumaDnia =(TextView) view.findViewById(R.id.sumaDnia);
		sumaDnia.setText(""+sumaKm+" km - "+AppSettings.PRICE_FORMAT1.format(suma.doubleValue())+"€");
	}

	
	@Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        
        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
        	changeFragmentListener = (ChangeFragment) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement ChangeFragment");
        }
    }
	

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.dodajTrase:
			changeFragmentListener.changeFragment(KalendarzActivity.DODAJ_TRASE_FRAGMENT, date);
			
			break;

		default:
			break;
		}
	}
}
