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
import pl.kilometrowka.dao.Trasa;
import pl.kilometrowka.dao.TrasaDao;
import pl.kilometrowka.dao.TrasaDao.Properties;
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
import android.widget.Toast;

import com.pdfjet.*;

public class RaportFragment extends Fragment implements OnClickListener {

	public final String TAG = RaportFragment.class.getSimpleName();

	private Button generujPdf;
	private Button pokazPdf;
	private Button generujPdfPamiec;
	private Button pokazPdfPamiec;
	private DatePicker date1, date2;

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
		View view = inflater
				.inflate(R.layout.fragment_raport, container, false);
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

	private void setUpViews(View view) {
		generujPdf = (Button) view.findViewById(R.id.generuj_pdf);
		pokazPdf = (Button) view.findViewById(R.id.pokaz_pdf);
		generujPdfPamiec = (Button) view
				.findViewById(R.id.generuj_pdf_w_pamieci);
		pokazPdfPamiec = (Button) view.findViewById(R.id.pokaz_pdf_z_pamieci);
		date1 = (DatePicker) view.findViewById(R.id.datePicker1);
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH);
		int day = cal.get(Calendar.DAY_OF_MONTH);
		date1.updateDate(year, month, day - 7);
		date2 = (DatePicker) view.findViewById(R.id.datePicker2);

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
			Page page = new Page(pdf, A4.PORTRAIT);
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
			int month1 = date1.getMonth() + 1;
			Date d1 = AppSettings.DATE_YMD_FORMAT.parse(date1.getDayOfMonth()
					+ "-" + month1 + "-" + date1.getYear());
			int month2 = date2.getMonth() + 1;
			Date d2 = AppSettings.DATE_YMD_FORMAT.parse(date2.getDayOfMonth()
					+ "-" + month2 + "-" + date2.getYear());

			TextLine title = new TextLine(f1, "Raport za "
					+ AppSettings.DATE_YMD_FORMAT.format(d1) + " - "
					+ AppSettings.DATE_FORMAT.format(d2) + "\n\n");
			title.setFont(f1);
			title.setColor(Color.black);

			title.setPosition(page.getWidth() / 2 - title.getWidth() / 2, 40f);
			title.drawOn(page);

			Table table = new Table();
			List<List<Cell>> tableData = new ArrayList<List<Cell>>();
			List<Cell> kolomJudul = new ArrayList<Cell>();
			Cell judul1 = new Cell(f1, "Data");
			Cell judul2 = new Cell(f1, "Trasa");
			Cell judul3 = new Cell(f1, "Km");
			Cell judul4 = new Cell(f1, "Cena");

			kolomJudul.add(judul1);
			kolomJudul.add(judul2);
			kolomJudul.add(judul3);
			kolomJudul.add(judul4);

			tableData.add(kolomJudul);

			DaoSession daoSession = DataBase.getInstance().getDaoSession();
			TrasaDao trasaDao = daoSession.getTrasaDao();
			List<Trasa> trasy = trasaDao.queryBuilder()
					.where(Properties.Data.ge(d1), Properties.Data.le(d2))
					.build().list();
			// List<Trasa> trasy = trasaDao.queryBuilder().build().list();

			if (trasy != null && trasy.size() > 0) {

				Map<Date, List<Trasa>> map = new TreeMap<Date, List<Trasa>>();
				for (Trasa t : trasy) {
					if (map.get(t.getData()) == null) {
						map.put(t.getData(), new ArrayList<Trasa>());
					}
					map.get(t.getData()).add(t);

				}

				List<Cell> record;
				
				StringBuilder sbDay;
				Integer sumaKmDnia=0, sumaKmRaportu=0;
				Double sumaDnia =0.0, sumaRaportu=0.0;
				for (Map.Entry<Date, List<Trasa>> entry : map.entrySet()) {
					sbDay = new StringBuilder();
					sumaKmDnia=0;
					sumaDnia=0.0;
					for (Trasa t1 : entry.getValue()) {
						sbDay.append(TrasaUtils.getMiastaByJson(t1.getMiasta()));
						sbDay.append(" = ");
						sbDay.append(t1.getKm());
						sbDay.append("km  ");
						if (t1.getKierowca()) {
							if (t1.getAutoSluzbowe()) {
								sbDay.append(AppSettings.SAMOCHOD_SLUZBOWY);
							} else {
								sbDay.append(AppSettings.SAMOCHOD_PRYWATNY);
							}
							if (t1.getCzyZPasazerem()) {
								sbDay.append(AppSettings.Z_PASAZEREM);
							}

						} else {
							sbDay.append(AppSettings.PASAZER);
						}
						sbDay.append("\n\t\r");
						sumaKmDnia+=t1.getKm();
						sumaDnia += StawkiUtils.getWartoscTrasy(t1);
					}// END  dzien
					
					record = new ArrayList<Cell>();
					
					record.add(new Cell(f1, AppSettings.DATE_YMD_FORMAT.format(entry.getKey())));
					record.add(new Cell(f1, sbDay.toString()));
					record.add(new Cell(f1, String.valueOf(sumaKmDnia)));
					record.add(new Cell(f1, AppSettings.PRICE_FORMAT1.format(sumaDnia) + "€"));

					tableData.add(record);
					
					sumaKmRaportu += sumaKmDnia;
					sumaRaportu += sumaDnia;
				}
				record = new ArrayList<Cell>();
				record.add(new Cell(f1,""));
				record.add(new Cell(f1,""));
				record.add(new Cell(f1, String.valueOf(sumaKmRaportu)));
				record.add(new Cell(f1, AppSettings.PRICE_FORMAT1.format(sumaRaportu) + "€"));

				tableData.add(record);
				


			}

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
