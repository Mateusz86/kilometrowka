package pl.kilometrowka.fragments;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import pl.kilometrowka.R;
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
import android.widget.Toast;

import com.pdfjet.A4;
import com.pdfjet.Box;
import com.pdfjet.Cell;
import com.pdfjet.Color;
import com.pdfjet.CoreFont;
import com.pdfjet.Font;
import com.pdfjet.Image;
import com.pdfjet.ImageType;
import com.pdfjet.PDF;
import com.pdfjet.Page;
import com.pdfjet.Table;
import com.pdfjet.TextLine;

public class RaportFragment extends Fragment implements OnClickListener {

	public final String TAG = RaportFragment.class.getSimpleName();

	private Button generujPdf;
	private Button pokazPdf;
	private Button generujPdfPamiec;
	private Button pokazPdfPamiec;
	
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
		generujPdfPamiec= (Button) view.findViewById(R.id.generuj_pdf_w_pamieci);
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

		/*
		 * String fpath = "/sdcard/" + "tescikk" + ".pdf"; File file = new
		 * File(fpath); // If file does not exists, then create it if
		 * (!file.exists()) { file.createNewFile(); }
		 */

		FileOutputStream fos = null;
		try {
			// fos = new FileOutputStream("/sdcard/sample.pdf");
			fos = new FileOutputStream("/sdcard/sampleee.pdf");
			PDF pdf = new PDF(fos);
			pdf.setTitle("TYTULLL");
			
			InputStream f = getActivity().getAssets().open("img.jpg");
			Image image = new Image(pdf, f, ImageType.JPG);
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

			text = new TextLine(f1, "Przykladowy tekst");
			text.setPosition(70.0, 50.0);
			text.drawOn(page);

			f1.setSize(14.0);
			String unicode = "\u20AC\u0020\u201A\u0192\u201E\u2026\u2020\u2021\u02C6\u2030\u0160";
			text = new TextLine(f1, unicode);
			text.setPosition(100.0, 700.0);
			text.setColor(Color.blue);
			text.drawOn(page);

			Font f2 = new Font(pdf, CoreFont.HELVETICA);
			f1.setSize(7.0f);
				
			TextLine title = new TextLine(f1, "Laporan Penjualan Pulsa");
			title.setFont(f1);
			title.setColor(Color.black);
	 
			title.setPosition(page.getWidth()/2-title.getWidth()/2, 40f);
			title.drawOn(page);
				
			Table table = new Table();
			List<List<Cell>> tableData = new ArrayList<List<Cell>>();
			List<Cell> kolomJudul = new ArrayList<Cell>();
			Cell judul1 = new Cell(f1,"Tanggal");
			Cell judul2 = new Cell(f1,"Telpon");
			Cell judul3 = new Cell(f1,"Nominal");
			Cell judul4 = new Cell(f1,"Status");
			Cell judul5 = new Cell(f1,"Harga");
			kolomJudul.add(judul1);
			kolomJudul.add(judul2);
			kolomJudul.add(judul3);
			kolomJudul.add(judul4);
			kolomJudul.add(judul5);
			tableData.add(kolomJudul);
				
			List<Cell> record = new ArrayList<Cell>();

			Cell tanggal = new Cell(f1,"r1");
			Cell telpon = new Cell(f1,"r2");
			Cell nominal = new Cell(f1,"r3");
			Cell status = new Cell(f1,"r4");
			Cell harga = new Cell(f1,"r5");
			
			
			record.add(tanggal);
			record.add(telpon);
			record.add(nominal);
			record.add(status);
			record.add(harga);
			tableData.add(record);
			
				
			table.setData(tableData, Table.DATA_HAS_1_HEADER_ROWS);
			table.autoAdjustColumnWidths();
			table.setColumnWidth(0, 80.0f);
			table.wrapAroundCellText();
			table.setPosition(page.getWidth()/2-table.getWidth()/2, 40f);
			
			
			table.drawOn(page);
			
			
			pdf.flush();
			fos.close();
			Toast.makeText(getActivity(), "Wygenerowano pdf", Toast.LENGTH_LONG).show();
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	
	private void showPdfFromMemory() {
		
		Uri uri = Uri.parse("content://pl.kilometrowka/" + "mydata.pdf");


		Intent intent = new Intent(Intent.ACTION_VIEW);
	//	intent.setDataAndType(Uri.fromFile(file), "application/pdf");
		intent.setDataAndType(uri, "application/pdf");

		intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
		startActivity(intent);		
	}

	private void createPdfInMemory() {
		
		
		
		 try {
	         FileOutputStream fos = getActivity().openFileOutput(file,getActivity().MODE_WORLD_READABLE);
	         
	         
	         PDF pdf = new PDF(fos);
				pdf.setTitle("TYTULLL");
				
				InputStream f = getActivity().getAssets().open("img.jpg");
				Image image = new Image(pdf, f, ImageType.JPG);
				Page page = new Page(pdf, A4.PORTRAIT);
				image.setPosition(0, 0);
				image.drawOn(page);

				
				 Box flag = new Box(); flag.setPosition(100.0f, 100.0f);
				 flag.setSize(190.0f, 100.0f); flag.setColor(Color.red);
				 flag.drawOn(page);
				 

				Font f1 = new Font(pdf, CoreFont.HELVETICA_BOLD);

				TextLine text = new TextLine(f1);

				text = new TextLine(f1, "Przykladowy tekst w internal");
				text.setPosition(70.0, 50.0);
				text.drawOn(page);

				pdf.flush();
				Toast.makeText(getActivity(), "Wygenerowano pdf", Toast.LENGTH_LONG).show();
				fos.close();
	         Toast.makeText(getActivity(),"file saved",
	         Toast.LENGTH_SHORT).show();
	      } catch (Exception e) {
	         // TODO Auto-generated catch block
	         e.printStackTrace();
	      }
		
	}
	

}
