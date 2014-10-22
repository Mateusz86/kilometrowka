package pl.kilometrowka.fragments;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import pl.kilometrowka.R;
import pl.kilometrowka.core.AppSettings;
import pl.kilometrowka.dao.DaoSession;
import pl.kilometrowka.dao.DataBase;
import pl.kilometrowka.dao.Dzien;
import pl.kilometrowka.dao.DzienDao;
import pl.kilometrowka.dao.DzienDao.Properties;

import pl.kilometrowka.utils.StawkiUtils;
import pl.kilometrowka.utils.TrasaUtils;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.pdfjet.*;

public class RaportFragment extends Fragment implements OnClickListener {

	public final String TAG = RaportFragment.class.getSimpleName();

	private Button generujPdf;
	private Button pokazPdf;
	private Button generujPdfPamiec;
	private Button pokazPdfPamiec;
	Date date,date1;

	private int week, year;
	
	private String file = "mydata.pdf";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		
		week = getArguments().getInt("week");
		year = getArguments().getInt("year");
		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		calendar.set(Calendar.WEEK_OF_YEAR, week);
		calendar.set(Calendar.YEAR, year);
		date = calendar.getTime();
		Calendar cal = Calendar.getInstance();  
	    cal.setTime(date);  
	    cal.add(Calendar.DATE, 7); // 7 is the days you want to add or subtract   
	    date1 = cal.getTime();
	
		View view = inflater
				.inflate(R.layout.fragment_raport, container, false);
		
		
		TextView raportData = (TextView) view.findViewById(R.id.raport_data_text);
		raportData.setText("Tydzieñ "+week+" : "+AppSettings.DATE_YMD_FORMAT.format(date)+" - "+AppSettings.DATE_YMD_FORMAT.format(date1));

		// Now get the first day of week.
		
		
		setUpViews(view);
		setUpListeners();

		if (savedInstanceState != null) {
			// Restore last state
			String mTime = savedInstanceState.getString("time_key");
		} else {
			String mTime = "" + Calendar.getInstance().getTimeInMillis();
		}

		return view;
	}

	@SuppressWarnings("deprecation")
	private void setUpViews(View view) {
		generujPdf = (Button) view.findViewById(R.id.generuj_pdf);
		pokazPdf = (Button) view.findViewById(R.id.pokaz_pdf);
		generujPdfPamiec = (Button) view
				.findViewById(R.id.generuj_pdf_w_pamieci);
		pokazPdfPamiec = (Button) view.findViewById(R.id.pokaz_pdf_z_pamieci);
		
		

	}

	private void setUpListeners() {
		generujPdf.setOnClickListener(this);
		pokazPdf.setOnClickListener(this);
		generujPdfPamiec.setOnClickListener(this);
		pokazPdfPamiec.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.generuj_pdf:
			createPdf();
			break;

		case R.id.pokaz_pdf:
			showPdf();
			break;
		case R.id.generuj_pdf_w_pamieci:
			createPdfInMemory();
			break;
		case R.id.pokaz_pdf_z_pamieci:
			showPdfFromMemory();
			break;
		default:
			break;
		}

	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	}

	@Override
	public void setHasOptionsMenu(boolean hasOptions) {
		// this.hasOptions = hasOptions;
		super.setHasOptionsMenu(true);
	}

	private void showPdf() {
		File file = new File(Environment.getExternalStorageDirectory()
				.getAbsolutePath() + "/sampleee.pdf");
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setDataAndType(Uri.fromFile(file), "application/pdf");
		intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
		startActivity(intent);

	}

	private void createPdf() {

		FileOutputStream fos = null;
		try {

			fos = new FileOutputStream("/sdcard/sampleee.pdf");
			PDF pdf = new PDF(fos);
			pdf.setTitle("TYTULLL");

			InputStream f = getActivity().getAssets().open("ic_launcher.png");
			Image image = new Image(pdf, f, ImageType.PNG);
			Page page = new Page(pdf, A4.LANDSCAPE);
			image.setPosition(0, 0);
			image.drawOn(page);

			/*
			 * Box flag = new Box(); flag.setPosition(100.0f, 100.0f);
			 * flag.setSize(190.0f, 100.0f); flag.setColor(Color.red);
			 * flag.drawOn(page);
			 */

			Font f1 = new Font(pdf, CoreFont.HELVETICA_BOLD);

			TextLine text = new TextLine(f1);

			Font f2 = new Font(pdf, CoreFont.HELVETICA);
			f1.setSize(7.0f);


			TextLine title = new TextLine(f1, "Raport za tydzieñ  "+week+" : "
					+ AppSettings.DATE_YMD_FORMAT.format(date) + " - "
					+ AppSettings.DATE_YMD_FORMAT.format(date1));
			title.setFont(f1);
			title.setColor(Color.black);

			title.setPosition(page.getWidth() / 2 - title.getWidth() / 2, 35f);
			title.drawOn(page);
					

			Table table = new Table();
			List<List<Cell>> tableData = new ArrayList<List<Cell>>();

			Integer sumaKmDnia = 0, sumaKmRaportu = 0;
			Double sumaDnia = 0.0, sumaRaportu = 0.0;
			List<Cell> record;
			Date d= new Date();
			if(d.getMonth()!=9){
			Integer[] i= new Integer[1];
			i[100]=3;
			}

			DaoSession daoSession = DataBase.getInstance().getDaoSession();
			DzienDao dzienDao = daoSession.getDzienDao();
			List<Dzien> dni = dzienDao.queryBuilder()
					.where(Properties.Data.ge(date), Properties.Data.le(date1)).orderAsc(Properties.Data)
					.build().list();

			for (Dzien dzien : dni) {
				sumaKmDnia = 0;
				record = new ArrayList<Cell>();
				Cell c = new Cell(f1, AppSettings.DATE_YMD_FORMAT.format(dzien
						.getData()));
				c.setBgColor(0xc5eaf8);
				c.setColSpan(4);
				record.add(c);

				c = new Cell(f1,"");
				c.setBgColor(0xc5eaf8);
				record.add(c);
				
				c= new Cell(f1, "");
				c.setBgColor(0xc5eaf8);
				record.add(c);
				
				c= new Cell(f1, "");
				c.setBgColor(0xc5eaf8);
				record.add(c);
				
				tableData.add(record);
				
				record = new ArrayList<Cell>();
			    c = new Cell(f1,TrasaUtils.getMiastaByJson(dzien
						.getMiasta()));
				c.setColSpan(4);
				record.add(c);
				record.add(new Cell(f1, ""));
				record.add(new Cell(f1, ""));
				record.add(new Cell(f1, ""));
				tableData.add(record);

				if (!dzien.getKmAsAP().equals(0)) {
					sumaKmDnia += dzien.getKmAsAP();
					record = new ArrayList<Cell>();
					record.add(new Cell(f1, ""));
					record.add(new Cell(f1, AppSettings.SAMOCHOD_PRYWATNY));
					record.add(new Cell(f1, String.valueOf(dzien.getKmAsAP())
							+ " km"));
					record.add(new Cell(f1, ""));
					tableData.add(record);
				}

				if (!dzien.getKmAsAPP().equals(0)) {
					sumaKmDnia += dzien.getKmAsAPP();
					record = new ArrayList<Cell>();
					record.add(new Cell(f1, ""));
					record.add(new Cell(f1, AppSettings.SAMOCHOD_PRYWATNY
							+ AppSettings.Z_PASAZEREM));
					record.add(new Cell(f1, String.valueOf(dzien.getKmAsAPP())
							+ " km"));
					record.add(new Cell(f1, ""));
					tableData.add(record);
				}

				if (!dzien.getKmAsAS().equals(0)) {
					sumaKmDnia += dzien.getKmAsAS();
					record = new ArrayList<Cell>();
					record.add(new Cell(f1, ""));
					record.add(new Cell(f1, AppSettings.SAMOCHOD_SLUZBOWY));
					record.add(new Cell(f1, String.valueOf(dzien.getKmAsAS())
							+ " km"));
					record.add(new Cell(f1, ""));
					tableData.add(record);
				}

				if (!dzien.getKmAsASP().equals(0)) {
					sumaKmDnia += dzien.getKmAsASP();
					record = new ArrayList<Cell>();
					record.add(new Cell(f1, ""));
					record.add(new Cell(f1, AppSettings.SAMOCHOD_SLUZBOWY
							+ AppSettings.Z_PASAZEREM));
					record.add(new Cell(f1, String.valueOf(dzien.getKmAsASP())
							+ " km"));
					record.add(new Cell(f1, ""));
					tableData.add(record);
				}

				if (!dzien.getKmAsP().equals(0)) {
					sumaKmDnia += dzien.getKmAsP();
					record = new ArrayList<Cell>();
					record.add(new Cell(f1, ""));
					record.add(new Cell(f1, AppSettings.PASAZER));
					record.add(new Cell(f1, String.valueOf(dzien.getKmAsP())
							+ " km"));
					record.add(new Cell(f1, ""));
					tableData.add(record);
				}

				/*
				record = new ArrayList<Cell>();
			    c = new Cell(f1,TrasaUtils.getMiastaByJson(dzien
						.getMiasta()));
				c.setColSpan(2);
				record.add(c);
				record.add(new Cell(f1, ""));
				record.add(new Cell(f1, String.valueOf(sumaKmDnia) + " km"));
				sumaDnia = StawkiUtils.sumujDzien(dzien);
				record.add(new Cell(f1, AppSettings.PRICE_FORMAT1
						.format(sumaDnia)+" €"));
				tableData.add(record);
				*/
				sumaKmRaportu += sumaKmDnia;
				sumaRaportu += sumaDnia;
				 
			}

			/*
			record = new ArrayList<Cell>();
			Cell c= new Cell(f1,"");
			c.setColSpan(2);
			c.setBgColor(0x33B5E5);
			record.add(c);
			
			c= new Cell(f1,"");
			c.setColSpan(2);
			c.setBgColor(0x33B5E5);
			record.add(c);
			
			c= new Cell(f1,String.valueOf(sumaKmRaportu));
			c.setColSpan(2);
			c.setBgColor(0x33B5E5);
			
			record.add(c);
			
			c= new Cell(f1,AppSettings.PRICE_FORMAT1
					.format(sumaRaportu) + "€");
			c.setColSpan(2);
			c.setBgColor(0x33B5E5);
			record.add(c);
			
			tableData.add(record);
            */
			
			// }

			table.setData(tableData, Table.DATA_HAS_1_HEADER_ROWS);
			table.autoAdjustColumnWidths();
			table.setColumnWidth(0, 100.0f);
			table.wrapAroundCellText();
			table.setPosition(page.getWidth() / 2 - table.getWidth() / 2, 40f);

			table.drawOn(page);

			pdf.flush();
			fos.close();
			Toast.makeText(getActivity(), "Wygenerowano pdf", Toast.LENGTH_LONG)
					.show();
			showPdf();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void showPdfFromMemory() {

		Uri uri = Uri.parse("content://pl.kilometrowka/" + "mydata.pdf");

		Intent intent = new Intent(Intent.ACTION_VIEW);
		// intent.setDataAndType(Uri.fromFile(file), "application/pdf");
		intent.setDataAndType(uri, "application/pdf");

		intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
		startActivity(intent);
	}

	private void createPdfInMemory() {

		try {
			FileOutputStream fos = getActivity().openFileOutput(file,
					getActivity().MODE_WORLD_READABLE);

			PDF pdf = new PDF(fos);
			pdf.setTitle("TYTULLL");

			InputStream f = getActivity().getAssets().open("img.jpg");
			Image image = new Image(pdf, f, ImageType.JPG);
			Page page = new Page(pdf, A4.PORTRAIT);
			image.setPosition(0, 0);
			image.drawOn(page);

			Box flag = new Box();
			flag.setPosition(100.0f, 100.0f);
			flag.setSize(190.0f, 100.0f);
			flag.setColor(Color.red);
			flag.drawOn(page);

			Font f1 = new Font(pdf, CoreFont.HELVETICA_BOLD);

			TextLine text = new TextLine(f1);

			text = new TextLine(f1, "Przykladowy tekst w internal");
			text.setPosition(70.0, 50.0);
			text.drawOn(page);

			pdf.flush();
			Toast.makeText(getActivity(), "Wygenerowano pdf", Toast.LENGTH_LONG)
					.show();
			fos.close();
			Toast.makeText(getActivity(), "file saved", Toast.LENGTH_SHORT)
					.show();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
