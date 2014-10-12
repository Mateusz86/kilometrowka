package pl.kilometrowka.fragments;

import java.util.Calendar;
import java.util.Date;

import pl.kilometrowka.KalendarzActivity;
import pl.kilometrowka.R;
import pl.kilometrowka.interfaces.ChangeFragment;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.squareup.timessquare.CalendarPickerView;
import com.squareup.timessquare.CalendarPickerView.SelectionMode;

public class KalendarzFragment extends Fragment implements OnClickListener {

	public final String TAG = KalendarzFragment.class.getSimpleName();
	private CalendarPickerView calendar;
	private Button showListTrace;
	ChangeFragment changeFragmentListener;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(false);
		setMenuVisibility(false);
		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.fragment_kalendarz, container,
				false);
		setUpViews(view);
		setUpListeners();
		
		if (savedInstanceState != null) {
            // Restore last state
          String  mTime = savedInstanceState.getString("time_key");
        } else {
        	String   mTime = "" + Calendar.getInstance().getTimeInMillis();
        }
		
		
		return view;
	}

	private void setUpViews(View view) {
		calendar = (CalendarPickerView) view.findViewById(R.id.calendar_view);
		showListTrace = (Button) view.findViewById(R.id.pokazListeTras);

		final Calendar nextYear = Calendar.getInstance();
		nextYear.add(Calendar.YEAR, 1);

		final Calendar lastYear = Calendar.getInstance();
		lastYear.add(Calendar.YEAR, -1);

		calendar.init(lastYear.getTime(), nextYear.getTime()) //
				.inMode(SelectionMode.SINGLE) //
		 .withSelectedDate(new Date());

	}

	private void setUpListeners() {
		showListTrace.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		 
		switch (v.getId()) {
		case R.id.pokazListeTras:
			changeFragmentListener.changeFragment(KalendarzActivity.LISTA_TRAS_FRAGMENT,calendar.getSelectedDate());    
			break;

		default:
			break;
		}

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
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("time_key", "a");
    }
	
	
	
	@Override
    public void setHasOptionsMenu(boolean hasOptions){
     //   this.hasOptions = hasOptions;
        super.setHasOptionsMenu(true);
    }


	

}
