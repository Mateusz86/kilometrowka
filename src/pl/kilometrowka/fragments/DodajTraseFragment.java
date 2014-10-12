package pl.kilometrowka.fragments;


import java.util.Calendar;
import java.util.Date;

import com.squareup.timessquare.CalendarPickerView;
import com.squareup.timessquare.CalendarPickerView.SelectionMode;

import pl.kilometrowka.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class DodajTraseFragment extends Fragment {
	
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState) {
    	
        View view= inflater.inflate(R.layout.fragment_dodaj_trase, container, false);
        
       
        
        return view;
        
        
    }

	
}
