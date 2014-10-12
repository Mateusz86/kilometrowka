package pl.kilometrowka.fragments;

import java.util.Date;
import java.util.List;

import pl.kilometrowka.KalendarzActivity;
import pl.kilometrowka.R;
import pl.kilometrowka.adapter.Adapter;
import pl.kilometrowka.dao.Trasa;
import pl.kilometrowka.utils.TrasaUtils;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class ListaTrasFragment extends Fragment {
	
 public final String TAG =ListaTrasFragment.class.getSimpleName();	
 private Date date; 
 private ListView listView;
 private List<Trasa> listaTras;
 private Adapter<Trasa> adapter;
	
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
		this.date = (Date) getArguments().getSerializable(KalendarzActivity.CHOOSE_DATE);
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
		listaTras=TrasaUtils.getDrasyFromDate(date);
		for(Trasa t:listaTras) {
			Log.e(TAG,t.getMiasta());
		}
		listView = (ListView) view.findViewById(R.id.listView);
		adapter = new Adapter<Trasa>(listaTras,getActivity());
		listView.setAdapter(adapter);
	}
}
