package pl.kilometrowka.fragments;

import java.util.Calendar;
import java.util.Date;

import pl.kilometrowka.KalendarzActivity;
import pl.kilometrowka.R;
import pl.kilometrowka.RaportActivity;
import pl.kilometrowka.interfaces.ChangeFragment;
import pl.kilometrowka.utils.StawkiUtils;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.timessquare.CalendarPickerView;
import com.squareup.timessquare.CalendarPickerView.OnDateSelectedListener;
import com.squareup.timessquare.CalendarPickerView.OnWeekNrSelectedListener;
import com.squareup.timessquare.CalendarPickerView.SelectionMode;

public class KalendarzFragment extends Fragment implements OnClickListener{

	public final String TAG = KalendarzFragment.class.getSimpleName();
	private CalendarPickerView calendar;
	private Button showListTrace;
	ChangeFragment changeFragmentListener;
	private Context context;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	//	setHasOptionsMenu(false);
	//	setMenuVisibility(false);
		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.fragment_kalendarz, container,
				false);
		setUpViews(view);
		setUpListeners();
		context = view.getContext();
		
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
		
		CalendarPickerView.setUzupelnioneDaty(StawkiUtils.getUzupelnioneDni());
		
		calendar.setWeekNrSelectedListener(new OnWeekNrSelectedListener() {

			@Override
			public void OnWeekNrSelected(int week,int year) {
				// TODO Auto-generated method stub
				
				//changeFragmentListener.changeFragment(KalendarzActivity.DZIEN_FRAGMENT,date);
				Intent intent = new Intent(context, RaportActivity.class);
				intent.putExtra("week", week);
				intent.putExtra("year", year);
				startActivity(intent);
			}

		});
		
		calendar.setOnDateSelectedListener(new OnDateSelectedListener() {
			
			@Override
			public void onDateUnselected(Date date) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onDateSelected(Date date) {
				// TODO Auto-generated method stub
				if(date!=null)
					changeFragmentListener.changeFragment(KalendarzActivity.DZIEN_FRAGMENT,date);
			}
		});
		
		
		
	}

	private void setUpListeners() {
		showListTrace.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		 
		switch (v.getId()) {
		case R.id.pokazListeTras:
			changeFragmentListener.changeFragment(KalendarzActivity.DZIEN_FRAGMENT,calendar.getSelectedDate());    
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
