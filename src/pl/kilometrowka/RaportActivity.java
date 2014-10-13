package pl.kilometrowka;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.pdfbox.exceptions.COSVisitorException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;







/*public class RaportActivity extends ActionBarActivity implements OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_raport);
	}


	@Override
	public void onClick(View v) {
		

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}*/

public class RaportActivity extends ActionBarActivity {
    EditText fname, fcontent, fnameread;
    Button write, read;
    TextView filecon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_raport);
        
        try {
			createPdf();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.e("------dsfdsfdsf-------","-----asfasfdasdfasd--------- document exceppppsdsadasdasd");

		} catch (COSVisitorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    }
    
    
    
    private void createPdf() throws IOException, COSVisitorException {
        // TODO Auto-generated method stub
       /* Document document = new Document();
        
        String fpath = "/sdcard/" + "tescik" + ".pdf";
        File file = new File(fpath);
        // If file does not exists, then create it
        if (!file.exists()) {
            file.createNewFile();
        }

        try {
			PdfWriter.getInstance(document, new FileOutputStream(file.getAbsoluteFile()));
			
			document.open();
			
            document.add(new Paragraph("Hello World!"));
            document.close();

			
		} catch (DocumentException e) {
			Log.e("-------------","-------------- document exceppppsdsadasdasd");
			e.printStackTrace();
		}*/
        
    	PDDocument document = new PDDocument();

    	// Create a new blank page and add it to the document
    	PDPage blankPage = new PDPage();
    	document.addPage( blankPage );

    	
    	   String fpath = "/sdcard/" + "tescikk" + ".pdf";
           File file = new File(fpath);
           // If file does not exists, then create it
           if (!file.exists()) {
               file.createNewFile();
           }
    	document.save(new FileOutputStream(file.getAbsoluteFile()));
    	// Save the newly created document
   // 	document.save("BlankPage.pdf");

    	// finally make sure that the document is properly
    	// closed.
    	document.close();

    	

    }      
    
}