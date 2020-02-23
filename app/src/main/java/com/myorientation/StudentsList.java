package com.myorientation;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.StrictMode;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.chootdev.csnackbar.Align;
import com.chootdev.csnackbar.Duration;
import com.chootdev.csnackbar.Snackbar;
import com.chootdev.csnackbar.Type;
import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.html2pdf.resolver.font.DefaultFontProvider;
import com.itextpdf.io.font.FontProgram;
import com.itextpdf.io.font.FontProgramFactory;
import com.itextpdf.layout.font.FontProvider;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;


public class StudentsList extends AppCompatActivity {
    Spinner filiereSpinner;
    String itemSElected1;
    Button btnOuvrir;
    Bitmap b;
    String path;
    File folder;
    File file;
    File filePath;
    private String signature_pdf_;
    String fileName;

    String html = "";
    String adminEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.students_list);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();
        btnOuvrir = findViewById(R.id.ouvrir_pdf);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);


        Typeface custom_font = Typeface.createFromAsset(getAssets(), "fonts/font6.ttf");


        RelativeLayout StartLayout = findViewById(R.id.Layout);

        ArrayList<View> clds = getAllChildren(StartLayout);
        for (int i = 0; i < clds.size(); i += 1) {

            if (clds.get(i) instanceof TextView) {
                ((TextView) clds.get(i)).setTypeface(custom_font);
            }

            if (clds.get(i) instanceof Button) {
                ((Button) clds.get(i)).setTypeface(custom_font);
            }
        }


        TextView nomEtablissement;
        String etablissement = null;
        final Intent intent = null;

        nomEtablissement = findViewById(R.id.nomEtablissement);
        try {

            ResultSet resultSet = Database.executeQuery("select \"nometablissement\" from ETABLISSEMENT where ETABLISSEMENT.\"id_etablissement\" = (select FK_etablissement from ADMIN where \"email\" ='" + LoginPage.adminEmail + "')");
            resultSet.next();
            etablissement = resultSet.getString(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        nomEtablissement.setText(etablissement);


        final ArrayList<String> filieres = new ArrayList<>();
//        intent.putExtra("filiere", itemSElected1);
        try {

            ResultSet resultSet = Database.executeQuery("SELECT \"nomfiliere\" FROM \"FILIERE\" where FILIERE.\"id_etablissement\"=(select \"id_etablissement\" from ETABLISSEMENT where \"nometablissement\"='" + etablissement + "')");
            while (resultSet.next()) {
                filieres.add(resultSet.getString(1));


            }


        } catch (Exception e) {
            e.printStackTrace();
        }


        filiereSpinner = (Spinner) findViewById(R.id.filiereAdmin);

        ArrayAdapter filiereAdapter = new ArrayAdapter<>(StudentsList.this, R.layout.support_simple_spinner_dropdown_item, filieres);
        filiereSpinner.setAdapter(filiereAdapter);


        filiereSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                itemSElected1 = filieres.get(position).toString();

                //Toast.makeText(StudentsList.this, filieres.get(position), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        Database.connect();

        final String finalEtablissement = etablissement;
        btnOuvrir.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                html = "";
                File l1 = new File(Environment.getExternalStorageDirectory() + "/Notes/liste.html");
                File l2 = new File(Environment.getExternalStorageDirectory() + "/Notes/liste.pdf");
                if (l1.exists()) {
                    l1.delete();
                }

                if (l2.exists()) {
                    l2.delete();
                }
                // showSnackBar("Préparation de fichier PDF ...", "#E91E63", 100);

                Snackbar.with(StudentsList.this, null)
                        .type(Type.SUCCESS)
                        .message(" votre list est en train de générer ! ")
                        .duration(Duration.LONG)
                        .fillParent(true)
                        .textAlign(Align.LEFT)
                        .show();


// votre list est en train de générer !


                new CountDownTimer(200, 100) {
                    public void onTick(long millisUntilFinished) {

                    }

                    public void onFinish() {


                        Date date = new Date();


                        html = "<html xmlns:o=\"urn:schemas-microsoft-com:office:office\"\n" +
                                "xmlns:x=\"urn:schemas-microsoft-com:office:excel\"\n" +
                                "xmlns=\"http://www.w3.org/TR/REC-html40\">\n" +
                                "\n" +
                                "<head>\n" +
                                "<meta http-equiv=Content-Type content=\"text/html; charset=utf-8\">\n" +
                                "<meta name=ProgId content=Excel.Sheet>\n" +
                                "<meta name=Generator content=\"Microsoft Excel 15\">\n" +
                                "<link rel=File-List href=\"L3.fld/filelist.xml\">\n" +
                                "<style id=\"L3_8885_Styles\">\n" +
                                "<!--table\n" +
                                "\t{mso-displayed-decimal-separator:\"\\.\";\n" +
                                "\tmso-displayed-thousand-separator:\"\\,\";}\n" +
                                "@page\n" +
                                "\t{margin:.75in .7in .75in .7in;\n" +
                                "\tmso-header-margin:.3in;\n" +
                                "\tmso-footer-margin:.3in;}\n" +
                                "tr\n" +
                                "\t{mso-height-source:auto;}\n" +
                                "col\n" +
                                "\t{mso-width-source:auto;}\n" +
                                "br\n" +
                                "\t{mso-data-placement:same-cell;}\n" +
                                ".style0\n" +
                                "\t{mso-number-format:General;\n" +
                                "\ttext-align:general;\n" +
                                "\tvertical-align:bottom;\n" +
                                "\twhite-space:nowrap;\n" +
                                "\tmso-rotate:0;\n" +
                                "\tmso-background-source:auto;\n" +
                                "\tmso-pattern:auto;\n" +
                                "\tcolor:black;\n" +
                                "\tfont-size:12.0pt;\n" +
                                "\tfont-weight:400;\n" +
                                "\tfont-style:normal;\n" +
                                "\ttext-decoration:none;\n" +
                                "\tfont-family:Calibri, sans-serif;\n" +
                                "\tmso-font-charset:0;\n" +
                                "\tborder:none;\n" +
                                "\tmso-protection:locked visible;\n" +
                                "\tmso-style-name:Normal;\n" +
                                "\tmso-style-id:0;}\n" +
                                "td\n" +
                                "\t{mso-style-parent:style0;\n" +
                                "\tpadding-top:1px;\n" +
                                "\tpadding-right:1px;\n" +
                                "\tpadding-left:1px;\n" +
                                "\tmso-ignore:padding;\n" +
                                "\tcolor:black;\n" +
                                "\tfont-size:12.0pt;\n" +
                                "\tfont-weight:400;\n" +
                                "\tfont-style:normal;\n" +
                                "\ttext-decoration:none;\n" +
                                "\tfont-family:Calibri, sans-serif;\n" +
                                "\tmso-font-charset:0;\n" +
                                "\tmso-number-format:General;\n" +
                                "\ttext-align:general;\n" +
                                "\tvertical-align:bottom;\n" +
                                "\tborder:none;\n" +
                                "\tmso-background-source:auto;\n" +
                                "\tmso-pattern:auto;\n" +
                                "\tmso-protection:locked visible;\n" +
                                "\twhite-space:nowrap;\n" +
                                "\tmso-rotate:0;}\n" +
                                ".xl65\n" +
                                "\t{mso-style-parent:style0;\n" +
                                "\tfont-size:14.0pt;\n" +
                                "\tfont-weight:700;\n" +
                                "\tfont-family:saxMono;\n" +
                                "\tmso-generic-font-family:auto;\n" +
                                "\tmso-font-charset:0;\n" +
                                "\ttext-align:center;\n" +
                                "\tvertical-align:middle;\n" +
                                "\tborder:.5pt solid windowtext;\n" +
                                "\twhite-space:normal;}\n" +
                                ".xl66\n" +
                                "\t{mso-style-parent:style0;\n" +
                                "\tfont-size:14.0pt;\n" +
                                "\tfont-weight:700;\n" +
                                "\tfont-family:saxMono;\n" +
                                "\tmso-generic-font-family:auto;\n" +
                                "\tmso-font-charset:0;\n" +
                                "\ttext-align:center;\n" +
                                "\tvertical-align:middle;\n" +
                                "\tborder:.5pt solid windowtext;}\n" +
                                ".xl67\n" +
                                "\t{mso-style-parent:style0;\n" +
                                "\tfont-size:14.0pt;\n" +
                                "\tfont-weight:700;\n" +
                                "\tfont-family:saxMono;\n" +
                                "\tmso-generic-font-family:auto;\n" +
                                "\tmso-font-charset:0;\n" +
                                "\ttext-align:center;\n" +
                                "\tvertical-align:middle;\n" +
                                "\tborder-top:.5pt solid windowtext;\n" +
                                "\tborder-right:none;\n" +
                                "\tborder-bottom:.5pt solid windowtext;\n" +
                                "\tborder-left:.5pt solid windowtext;\n" +
                                "\tbackground:white;\n" +
                                "\tmso-pattern:black none;}\n" +
                                ".xl68\n" +
                                "\t{mso-style-parent:style0;\n" +
                                "\tfont-size:14.0pt;\n" +
                                "\tfont-weight:700;\n" +
                                "\tfont-family:saxMono;\n" +
                                "\tmso-generic-font-family:auto;\n" +
                                "\tmso-font-charset:0;\n" +
                                "\ttext-align:center;\n" +
                                "\tvertical-align:middle;\n" +
                                "\tborder-top:.5pt solid windowtext;\n" +
                                "\tborder-right:none;\n" +
                                "\tborder-bottom:.5pt solid windowtext;\n" +
                                "\tborder-left:none;\n" +
                                "\tbackground:white;\n" +
                                "\tmso-pattern:black none;}\n" +
                                ".xl69\n" +
                                "\t{mso-style-parent:style0;\n" +
                                "\tfont-size:14.0pt;\n" +
                                "\tfont-weight:700;\n" +
                                "\tfont-family:saxMono;\n" +
                                "\tmso-generic-font-family:auto;\n" +
                                "\tmso-font-charset:0;\n" +
                                "\ttext-align:center;\n" +
                                "\tvertical-align:middle;\n" +
                                "\tborder-top:.5pt solid windowtext;\n" +
                                "\tborder-right:.5pt solid windowtext;\n" +
                                "\tborder-bottom:.5pt solid windowtext;\n" +
                                "\tborder-left:none;\n" +
                                "\tbackground:white;\n" +
                                "\tmso-pattern:black none;}\n" +
                                ".xl70\n" +
                                "\t{mso-style-parent:style0;\n" +
                                "\tfont-weight:700;\n" +
                                "\tfont-family:saxMono;\n" +
                                "\tmso-generic-font-family:auto;\n" +
                                "\tmso-font-charset:0;}\n" +
                                ".xl71\n" +
                                "\t{mso-style-parent:style0;\n" +
                                "\tfont-size:14.0pt;\n" +
                                "\tfont-weight:700;\n" +
                                "\tfont-family:saxMono;\n" +
                                "\tmso-generic-font-family:auto;\n" +
                                "\tmso-font-charset:0;\n" +
                                "\ttext-align:center;\n" +
                                "\tvertical-align:middle;\n" +
                                "\tborder:.5pt solid windowtext;\n" +
                                "\tbackground:white;\n" +
                                "\tmso-pattern:black none;}\n" +
                                ".xl72\n" +
                                "\t{mso-style-parent:style0;\n" +
                                "\tfont-weight:700;\n" +
                                "\tfont-family:saxMono;\n" +
                                "\tmso-generic-font-family:auto;\n" +
                                "\tmso-font-charset:0;\n" +
                                "\ttext-align:center;\n" +
                                "\tvertical-align:middle;\n" +
                                "\tborder:.5pt solid windowtext;\n" +
                                "\tbackground:white;\n" +
                                "\tmso-pattern:black none;}\n" +
                                ".xl73\n" +
                                "\t{mso-style-parent:style0;\n" +
                                "\tfont-size:14.0pt;\n" +
                                "\tfont-weight:700;\n" +
                                "\tfont-family:saxMono;\n" +
                                "\tmso-generic-font-family:auto;\n" +
                                "\tmso-font-charset:0;\n" +
                                "\ttext-align:center;\n" +
                                "\tvertical-align:middle;\n" +
                                "\tbackground:white;\n" +
                                "\tmso-pattern:black none;}\n" +
                                ".xl74\n" +
                                "\t{mso-style-parent:style0;\n" +
                                "\tfont-weight:700;\n" +
                                "\tfont-family:saxMono;\n" +
                                "\tmso-generic-font-family:auto;\n" +
                                "\tmso-font-charset:0;\n" +
                                "\ttext-align:center;\n" +
                                "\tvertical-align:middle;\n" +
                                "\tborder:.5pt solid windowtext;\n" +
                                "\tbackground:#92D050;\n" +
                                "\tmso-pattern:black none;}\n" +
                                "-->\n" +
                                "</style>\n" +
                                "</head>\n" +
                                "\n" +
                                "<body link=\"#0563C1\" vlink=\"#954F72\" class=xl70>\n" +
                                "<!--[if !excel]>&nbsp;&nbsp;<![endif]-->\n" +
                                "<!--The following information was generated by Microsoft Excel's Publish as Web\n" +
                                "Page wizard.-->\n" +
                                "<!--If the same item is republished from Excel, all information between the DIV\n" +
                                "tags will be replaced.-->\n" +
                                "<!----------------------------->\n" +
                                "<!--START OF OUTPUT FROM EXCEL PUBLISH AS WEB PAGE WIZARD -->\n" +
                                "<!----------------------------->\n" +
                                "\n" +
                                "<div id=\"L3_8885\" align=center x:publishsource=\"Excel\">\n" +
                                "\n" +
                                "<table border=0 cellpadding=0 cellspacing=0 width=660 style='border-collapse:\n" +
                                " collapse;table-layout:fixed;width:494pt'>\n" +
                                " <col class=xl70 width=103 style='mso-width-source:userset;mso-width-alt:3285;\n" +
                                " width:77pt'>\n" +
                                " <col class=xl70 width=83 style='mso-width-source:userset;mso-width-alt:2645;\n" +
                                " width:62pt'>\n" +
                                " <col class=xl70 width=97 style='mso-width-source:userset;mso-width-alt:3114;\n" +
                                " width:73pt'>\n" +
                                " <col class=xl70 width=95 style='mso-width-source:userset;mso-width-alt:3029;\n" +
                                " width:71pt'>\n" +
                                " <col class=xl70 width=99 style='mso-width-source:userset;mso-width-alt:3157;\n" +
                                " width:74pt'>\n" +
                                " <col class=xl70 width=68 style='mso-width-source:userset;mso-width-alt:2176;\n" +
                                " width:51pt'>\n" +
                                " <col class=xl70 width=115 style='mso-width-source:userset;mso-width-alt:3669;\n" +
                                " width:86pt'>\n" +
                                " <tr height=29 style='mso-height-source:userset;height:22.0pt'>\n" +
                                "  <td colspan=3 rowspan=2 height=57 class=xl65 width=283 style='height:43.0pt;\n" +
                                "  width:212pt'>Université <br>\n" +
                                "    Chouaib Doukkali</td>\n" +
                                "  <td class=xl70 width=95 style='width:71pt'></td>\n" +
                                "  <td colspan=3 rowspan=2 class=xl65 width=282 style='width:211pt'>Ecole\n" +
                                "  Superieure <br>\n" +
                                "    de Technologie</td>\n" +
                                " </tr>\n" +
                                " <tr height=28 style='mso-height-source:userset;height:21.0pt'>\n" +
                                "  <td height=28 class=xl70 style='height:21.0pt'></td>\n" +
                                " </tr>\n" +
                                " <tr height=21 style='height:16.0pt'>\n" +
                                "  <td height=21 class=xl70 style='height:16.0pt'></td>\n" +
                                "  <td class=xl70></td>\n" +
                                "  <td class=xl70></td>\n" +
                                "  <td class=xl70></td>\n" +
                                "  <td class=xl70></td>\n" +
                                "  <td class=xl70></td>\n" +
                                "  <td class=xl70></td>\n" +
                                " </tr>\n" +
                                " <tr height=29 style='mso-height-source:userset;height:22.0pt'>\n" +
                                "  <td height=29 class=xl70 style='height:22.0pt'></td>\n" +
                                "  <td colspan=5 class=xl67 style='border-right:.5pt solid black'>Liste des\n" +
                                "  étudiants de la filière</td>\n" +
                                "  <td class=xl70></td>\n" +
                                " </tr>\n" +
                                " <tr height=11 style='mso-height-source:userset;height:8.0pt'>\n" +
                                "  <td height=11 class=xl70 style='height:8.0pt'></td>\n" +
                                "  <td class=xl73>&nbsp;</td>\n" +
                                "  <td class=xl68 style='border-top:none'>&nbsp;</td>\n" +
                                "  <td class=xl68 style='border-top:none'>&nbsp;</td>\n" +
                                "  <td class=xl68 style='border-top:none'>&nbsp;</td>\n" +
                                "  <td class=xl73>&nbsp;</td>\n" +
                                "  <td class=xl70></td>\n" +
                                " </tr>\n" +
                                " <tr height=32 style='mso-height-source:userset;height:24.0pt'>\n" +
                                "  <td height=32 class=xl70 style='height:24.0pt'></td>\n" +
                                "  <td class=xl70></td>\n" +
                                "  <td colspan=3 class=xl71>" + itemSElected1 + "</td>\n" +
                                "  <td class=xl70></td>\n" +
                                "  <td class=xl70></td>\n" +
                                " </tr>\n" +
                                " <tr height=32 style='mso-height-source:userset;height:24.0pt'>\n" +
                                "  <td height=32 class=xl70 style='height:24.0pt'></td>\n" +
                                "  <td class=xl70></td>\n" +
                                "  <td colspan=3 class=xl71>" + date.toLocaleString() + "</td>\n" +
                                "  <td class=xl70></td>\n" +
                                "  <td class=xl70></td>\n" +
                                " </tr>\n" +
                                " <tr height=21 style='height:16.0pt'>\n" +
                                "  <td height=21 class=xl70 style='height:16.0pt'></td>\n" +
                                "  <td class=xl70></td>\n" +
                                "  <td class=xl70></td>\n" +
                                "  <td class=xl70></td>\n" +
                                "  <td class=xl70></td>\n" +
                                "  <td class=xl70></td>\n" +
                                "  <td class=xl70></td>\n" +
                                " </tr>\n" +
                                " <tr height=21 style='height:16.0pt'>\n" +
                                "  <td height=21 class=xl74 style='height:16.0pt'>CIN</td>\n" +
                                "  <td class=xl74 style='border-left:none'>CNE</td>\n" +
                                "  <td class=xl74 style='border-left:none'>NOM</td>\n" +
                                "  <td class=xl74 style='border-left:none'>PRENOM</td>\n" +
                                "  <td class=xl74 style='border-left:none'>NAISSANCE</td>\n" +
                                "  <td class=xl74 style='border-left:none'>NOTE</td>\n" +
                                "  <td class=xl74 style='border-left:none'>ANNEE DE BAC</td>\n" +
                                " </tr>";


                        try {
//and ROWNUM <= 20 AND \"noteBac\"< 16 ");

                            ResultSet resultSet = Database.executeQuery("select \"cne\" , \"CIN\" , \"nom\", \"prenom\" , \"date_de_naissance\",\"noteBac\",\"annee_bac\" from ETUDIANT where \"id_filiere\" = (select \"id_filiere\" from FILIERE where \"nomfiliere\"='" + itemSElected1 + "') ORDER BY \"noteBac\" DESC");


                            while (resultSet.next()) {

                                String cne = resultSet.getString(1);
                                String cin = resultSet.getString(2);
                                String nom = resultSet.getString(3);
                                String prenom = resultSet.getString(4);
                                String dn = resultSet.getString(5);
                                String notebac = resultSet.getString(6);
                                String anneebac = resultSet.getString(7);

                                html += " <tr height=21 style='height:16.0pt'>\n" +
                                        "  <td height=21 class=xl72 style='height:16.0pt;border-top:none'>" + cne + "</td>\n" +
                                        "  <td class=xl72 style='border-top:none;border-left:none'>" + cin + "</td>\n" +
                                        "  <td class=xl72 style='border-top:none;border-left:none'>" + nom + "</td>\n" +
                                        "  <td class=xl72 style='border-top:none;border-left:none'>" + prenom + "</td>\n" +
                                        "  <td class=xl72 style='border-top:none;border-left:none'>" + dn.split(" ")[0] + "</td>\n" +
                                        "  <td class=xl72 style='border-top:none;border-left:none'>" + notebac + "</td>\n" +
                                        "  <td class=xl72 style='border-top:none;border-left:none'>" + anneebac + "</td>\n" +
                                        " </tr>";
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        html += " <![if supportMisalignedColumns]>\n" +
                                " <tr height=0 style='display:none'>\n" +
                                "  <td width=103 style='width:77pt'></td>\n" +
                                "  <td width=83 style='width:62pt'></td>\n" +
                                "  <td width=97 style='width:73pt'></td>\n" +
                                "  <td width=95 style='width:71pt'></td>\n" +
                                "  <td width=99 style='width:74pt'></td>\n" +
                                "  <td width=68 style='width:51pt'></td>\n" +
                                "  <td width=115 style='width:86pt'></td>\n" +
                                " </tr>\n" +
                                " <![endif]>\n" +
                                "</table>\n" +
                                "\n" +
                                "</div>\n" +
                                "\n" +
                                "\n" +
                                "<!----------------------------->\n" +
                                "<!--END OF OUTPUT FROM EXCEL PUBLISH AS WEB PAGE WIZARD-->\n" +
                                "<!----------------------------->\n" +
                                "</body>\n" +
                                "\n" +
                                "</html>\n";

                        generateNoteOnSD(StudentsList.this, "liste.html", html);
                        try {
                            createPdf(Environment.getExternalStorageDirectory() + "/Notes/liste.html", Environment.getExternalStorageDirectory() + "/saxmono.ttf", Environment.getExternalStorageDirectory() + "/Notes/liste.pdf");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        viewPdf("liste");
                    }
                }.start();


            }


        });


    }

    private ArrayList<View> getAllChildren(View v) {

        if (!(v instanceof ViewGroup)) {
            ArrayList<View> viewArrayList = new ArrayList<>();
            viewArrayList.add(v);
            return viewArrayList;
        }

        ArrayList<View> result = new ArrayList<>();
        ViewGroup viewGroup = (ViewGroup) v;
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            View child = viewGroup.getChildAt(i);
            ArrayList<View> viewArrayList = new ArrayList<>();
            viewArrayList.add(v);
            viewArrayList.addAll(getAllChildren(child));
            result.addAll(viewArrayList);
        }
        return result;
    }


    public static final String FONT = "src/main/resources/fonts/cardo/Cardo-Regular.ttf";

    public void createPdf(String src, String font, String dest) throws IOException {
        ConverterProperties properties = new ConverterProperties();
        FontProvider fontProvider = new DefaultFontProvider();
        FontProgram fontProgram = FontProgramFactory.createFont(font);
        fontProvider.addFont(fontProgram);
        properties.setFontProvider(fontProvider);
        HtmlConverter.convertToPdf(new File(src), new File(dest), properties);
    }

    public void generateNoteOnSD(Context context, String sFileName, String sBody) {
        try {
            File root = new File(Environment.getExternalStorageDirectory(), "Notes");
            if (!root.exists()) {
                root.mkdirs();
            }
            File gpxfile = new File(root, sFileName);
            FileWriter writer = new FileWriter(gpxfile);
            writer.append(sBody);
            writer.flush();
            writer.close();
            Toast.makeText(context, "Saved", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void viewPdf(String file) {
        Intent intent;
        File pdfFile = new File(Environment.getExternalStorageDirectory() + "/Notes/" + file + ".pdf");

        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("file://" + pdfFile.getAbsolutePath()));
        startActivity(browserIntent);


    }


    public void showSnackBar(String message, String color, int visibilityTime) {
        final TextView alert = findViewById(R.id.Alert);
        alert.bringToFront();
        alert.setBackground(getDrawable(R.drawable.error_alert));
        TranslateAnimation animation = new TranslateAnimation(0, 0, 0, -140);
        animation.setDuration(600);
        alert.setElevation(18);
        alert.setText(message);
        alert.setTextColor(Color.parseColor(color));
        alert.startAnimation(animation);
        animation.setFillAfter(true);


        new CountDownTimer(visibilityTime, 1000) {
            public void onTick(long millisUntilFinished) {

            }

            public void onFinish() {
                TranslateAnimation animation = new TranslateAnimation(0, 0, -140, 0);
                animation.setDuration(600);
                alert.startAnimation(animation);
                animation.setFillAfter(true);

            }
        }.start();
    }

}


