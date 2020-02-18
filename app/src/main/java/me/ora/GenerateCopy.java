package me.ora;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class GenerateCopy extends AppCompatActivity {
    Bitmap b;
    String path;
    File folder;
    File file;
    File filePath;
    TextView tvNom,tvPrenom , tvCin , tvCne, tvEtablissement, tvDateNaissance , tvNoteBac , tvDateBac, tvFiliere, tvTel ,tvEmail;
    private String signature_pdf_;
    String fileName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_copy);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Typeface custom_font = Typeface.createFromAsset(getAssets(), "fonts/font6.ttf");
        Button clickButton = (Button) findViewById(R.id.imprimer);

        RelativeLayout StartLayout = findViewById(R.id.Layout);


        ArrayList<View> clds = getAllChildren(StartLayout);
        for (int i = 0; i < clds.size(); i += 1) {

            if (clds.get(i) instanceof TextView) {
                ((TextView) clds.get(i)).setTypeface(custom_font);
            }

            if (clds.get(i) instanceof Button) {
                ((Button) clds.get(i)).setTypeface(custom_font);
            }

            if (clds.get(i) instanceof RadioButton) {
                ((RadioButton) clds.get(i)).setTypeface(custom_font);
            }
        }















        clickButton.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                takeScreenShot();
                createPdf();
                viewPdf(fileName);
            }
        });
        tvNom=(TextView)findViewById(R.id.nomGenereted);
        tvNom.setText(getIntent().getStringExtra("nom"));
        tvPrenom=(TextView)findViewById(R.id.prenomGenereted);
        tvPrenom.setText(getIntent().getStringExtra("prenom"));
        tvCin=(TextView)findViewById(R.id.cinGenereted);
        tvCin.setText(getIntent().getStringExtra("cin"));
        tvCne=(TextView)findViewById(R.id.cinGenereted);
        tvCin.setText(getIntent().getStringExtra("cin"));
        tvCne=(TextView)findViewById(R.id.cneGenereted);
        tvCne.setText(getIntent().getStringExtra("cne"));
        tvEtablissement=(TextView)findViewById(R.id.etablissementGenereted);

        String eta = getIntent().getStringExtra("etablissement");
        tvEtablissement.setText(eta);
        tvDateNaissance=(TextView)findViewById(R.id.datenaissanceGenereted);
        tvDateNaissance.setText(getIntent().getStringExtra("datenaissance"));
        tvCne=(TextView)findViewById(R.id.cneGenereted);
        tvCne.setText(getIntent().getStringExtra("cne"));
        tvNoteBac=(TextView)findViewById(R.id.notebacGenereted);
        tvNoteBac.setText(getIntent().getStringExtra("notebac"));
        tvDateBac=(TextView)findViewById(R.id.datebacGenereted);
        tvDateBac.setText(getIntent().getStringExtra("datebac"));
        tvFiliere=(TextView)findViewById(R.id.filiereGenereted);
        String fil = getIntent().getStringExtra("filiere");
        tvFiliere.setText(fil);
        tvTel=(TextView)findViewById(R.id.telGenereted);
        tvTel.setText(getIntent().getStringExtra("tel"));
        tvEmail=(TextView)findViewById(R.id.emailGenereted);
        tvEmail.setText(getIntent().getStringExtra("email"));

    }




    private void takeScreenShot() {


        folder = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Signature/");

        if (!folder.exists()) {
            boolean success = folder.mkdir();
        }

         path = folder.getAbsolutePath();

        fileName = String.valueOf(System.currentTimeMillis());


        path = path + "/" + signature_pdf_ + fileName + ".pdf";// path where pdf will be stored

        View u = findViewById(R.id.scroll);
       ScrollView z = (ScrollView) findViewById(R.id.scroll); // parent view
       int  totalHeight = z.getChildAt(0).getHeight();// parent view height
      int   totalWidth = z.getChildAt(0).getWidth();// parent view width

        //Save bitmap to  below path
        String extr = Environment.getExternalStorageDirectory() + "/Signature/";
        file = new File(extr);
        if (!file.exists())
            file.mkdir();

        String signatcure_img_ = null;
        String fileName = signatcure_img_ + ".jpg";
        File myPath = new File(extr, fileName);
        String imagesUri = myPath.getPath();
        FileOutputStream fos = null;
        b = getBitmapFromView(u, totalHeight, totalWidth);

        try {
            fos = new FileOutputStream(myPath);
            b.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        createPdf();
    }// create pdf after creating bitmap and saving


    public Bitmap getBitmapFromView(View view, int totalHeight, int totalWidth) {

        Bitmap returnedBitmap = Bitmap.createBitmap(totalWidth,totalHeight , Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(returnedBitmap);
        Drawable bgDrawable = view.getBackground();
        if (bgDrawable != null)
            bgDrawable.draw(canvas);
        else
            canvas.drawColor(Color.WHITE);
        view.draw(canvas);
        return returnedBitmap;
    }
    private void createPdf(){

            PdfDocument document = new PdfDocument();

            PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(b.getWidth(), b.getHeight(), 1).create();
            PdfDocument.Page page = document.startPage(pageInfo);

            Canvas canvas = page.getCanvas();


            Paint paint = new Paint();
            paint.setColor(Color.parseColor("#ffffff"));
            canvas.drawPaint(paint);


            Bitmap bitmap = Bitmap.createScaledBitmap(b, b.getWidth(), b.getHeight(), true);

            paint.setColor(Color.BLUE);
            canvas.drawBitmap(bitmap, 0, 0, null);
            document.finishPage(page);
            filePath = new File(path);
            try {
                document.writeTo(new FileOutputStream(filePath));
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(this, "Something wrong: " + e.toString(), Toast.LENGTH_LONG).show();
            }

            // close the document
            document.close();

            //openPdf(path);
        }
    private void viewPdf(String file) {
Intent intent ;
        File pdfFile = new File(Environment.getExternalStorageDirectory() + "/Signature/null" + file+".pdf");
  /*      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {

            Uri uri = FileProvider.getUriForFile(this, getPackageName() + ".provider", pdfFile);
            intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(uri);
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            startActivity(intent);
        } else {
            intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.parse(pdfFile.getAbsolutePath()), "application/pdf");
            intent = Intent.createChooser(intent, "Open File");
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);

        }*/

       /* Toast.makeText(getApplicationContext(), file.toString() , Toast.LENGTH_LONG).show();
        if( pdfFile.exists()) {
            Intent target = new Intent(Intent.ACTION_VIEW);
            target.setDataAndType(Uri.fromFile(pdfFile), "application/pdf");
            target.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);

            intent = Intent.createChooser(target, "Open File");
            try {
                startActivity(intent);
            } catch (ActivityNotFoundException e) {
                // Instruct the user to install a PDF reader here, or something
            }
        }
        else
            Toast.makeText(getApplicationContext(), "File path is incorrect." , Toast.LENGTH_LONG).show();*/


        // Get URI and MIME type of file
       /* Uri uri = FileProvider.getUriForFile(this, getPackageName() + ".fileprovider", pdfFile);
        String mime = getContentResolver().getType(uri);

        // Open file with user selected app
        intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setDataAndType(uri, mime);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        //intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivity(intent);*/
        // create new Intent

            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("file://"+pdfFile.getAbsolutePath()));
            startActivity(browserIntent);



    }


    private ArrayList<View> getAllChildren(View v) {

        if (!(v instanceof ViewGroup)) {
            ArrayList<View> viewArrayList = new ArrayList<View>();
            viewArrayList.add(v);
            return viewArrayList;
        }

        ArrayList<View> result = new ArrayList<View>();

        ViewGroup viewGroup = (ViewGroup) v;
        for (int i = 0; i < viewGroup.getChildCount(); i++) {

            View child = viewGroup.getChildAt(i);

            ArrayList<View> viewArrayList = new ArrayList<View>();
            viewArrayList.add(v);
            viewArrayList.addAll(getAllChildren(child));

            result.addAll(viewArrayList);
        }
        return result;
    }

}
