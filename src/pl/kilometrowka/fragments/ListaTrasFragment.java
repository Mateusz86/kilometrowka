package pl.kilometrowka.fragments;

import java.util.Date;
import java.util.List;

import pl.kilometrowka.KalendarzActivity;
import pl.kilometrowka.R;
import pl.kilometrowka.adapter.Adapter;
import pl.kilometrowka.dao.DaoSession;
import pl.kilometrowka.dao.DataBase;
import pl.kilometrowka.dao.Trasa;
import pl.kilometrowka.utils.TrasaUtils;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ListView;

public class ListaTrasFragment extends Fragment {
	
 public final String TAG =ListaTrasFragment.class.getSimpleName();	
 public static Date date; 
 private ListView listView;
 private List<Trasa> listaTras;
 private Adapter<Trasa> adapter;
 private OnClickListener deleteListener= new OnClickListener() {
	
	@Override
	public void onClick(View v) {
		int pos = (Integer) v.getTag();
		DaoSession daoSession = DataBase.getInstance().getDaoSession();
		List<Trasa> trasy = daoSession.getTrasaDao().queryBuilder().build().list();
		if(trasy!=null && trasy.size()>0) {
			Trasa trasaToDelete = trasy.get(pos);
			daoSession.getTrasaDao().delete(trasaToDelete);
			adapter.setList(daoSession.getTrasaDao().queryBuilder().build().list());
			adapter.notifyDataSetChanged();
		}
		
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
		listaTras=TrasaUtils.getDrasyFromDate(date);
		for(Trasa t:listaTras) {
			Log.e(TAG,t.getMiasta());
		}
		listView = (ListView) view.findViewById(R.id.listView);
		adapter = new Adapter<Trasa>(listaTras,getActivity(),deleteListener);
		listView.setAdapter(adapter);
	}
}
