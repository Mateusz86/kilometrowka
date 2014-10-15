package pl.kilometrowka.adapter;

import java.util.List;

import pl.kilometrowka.R;
import pl.kilometrowka.core.AppSettings;
import pl.kilometrowka.dao.Trasa;
import pl.kilometrowka.utils.TrasaUtils;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;



public class Adapter<T> extends BaseAdapter {

	private List<?> list;
	private Context context;
	private LayoutInflater inflater;
	private OnClickListener deleteListener;
	
	
	public Adapter(List<?> list, Context context,OnClickListener deleteListener) {
		this.list = list;
		this.context = context;
		this.inflater= LayoutInflater.from(context);
		this.deleteListener=deleteListener;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View row, ViewGroup parent) {
		
		MyViewHolder mViewHolder;

        if(row == null) {
            row = inflater.inflate(R.layout.row_trasa, null);
            mViewHolder = new MyViewHolder();
            mViewHolder.trasa= (TextView) row.findViewById(R.id.trasaa);
            mViewHolder.km= (TextView) row.findViewById(R.id.km);
            mViewHolder.samochod= (TextView) row.findViewById(R.id.samochod);
            mViewHolder.data= (TextView) row.findViewById(R.id.data);
            mViewHolder.delete= (Button) row.findViewById(R.id.delete);
            row.setTag(mViewHolder);
        } else {
            mViewHolder = (MyViewHolder) row.getTag();
        }
       
       Object object= list.get(position);
        
        if(object instanceof Trasa) {
        	mViewHolder.trasa.setText(TrasaUtils.getMiastaByJson(((Trasa)object).getMiasta()));
        	mViewHolder.km.setText(((Trasa)object).getKm()+"");
        	if(((Trasa)object).getAutoSluzbowe()) {
        		mViewHolder.samochod.setText("Samochod sluzbowy");
        	}
        	else {
        		mViewHolder.samochod.setText("Samochod prywatny");
        	}
        	
        	mViewHolder.data.setText(AppSettings.DATE_YMD_FORMAT.format(((Trasa)object).getData()));
        }
        
        mViewHolder.delete.setTag(position);
        mViewHolder.delete.setOnClickListener(deleteListener);
	
		return row;
	}

	
	private class MyViewHolder {
        TextView trasa,km,data,samochod,kierowca;
        Button delete;
    }


	public List<?> getList() {
		return list;
	}

	public void setList(List<?> list) {
		this.list = list;
	}
	
	
	
}
