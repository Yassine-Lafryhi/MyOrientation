package com.myorientation;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.StrictMode;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.chootdev.csnackbar.Align;
import com.chootdev.csnackbar.Duration;
import com.chootdev.csnackbar.Snackbar;
import com.chootdev.csnackbar.Type;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class FillingInformation<simpleSpinner> extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Spinner etablissementSpinner, filiereSpinner;
    TextView textPopup;
    EditText editTextNom, editTextPrenom, editTextCin, editTextCne, editTextEtablissement, editTextDateNaissance, editTextNoteBac, editTextDateBac, editTextFiliere, editTextTel, editTextEmail;
    Button btnEnregistrer, btnValider;
    String itemSElected1, itemSElected2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //textPopup= findViewById(R.id.popup);
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.filling_information);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Typeface custom_font = Typeface.createFromAsset(getAssets(), "fonts/font6.ttf");

        Context contex = null;


        editTextNom = findViewById(R.id.nom);
        editTextPrenom = findViewById(R.id.prenom);
        editTextCin = findViewById(R.id.cin);
        editTextCne = findViewById(R.id.cne);
        //editTextEtablissement =(EditText) findViewById(R.id.etablissement);
        editTextDateNaissance = findViewById(R.id.datenaissance);
        editTextNoteBac = findViewById(R.id.notebac);
        editTextDateBac = findViewById(R.id.datebac);
        // editTextFiliere = findViewById(R.id.filiere);
        editTextTel = findViewById(R.id.tel);
        editTextEmail = findViewById(R.id.email);


        btnValider = findViewById(R.id.valider);
        btnValider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (editTextNom.getText().toString().isEmpty() || editTextPrenom.getText().toString().isEmpty()
                        || editTextCin.getText().toString().isEmpty() || editTextCne.getText().toString().isEmpty()
                        || editTextDateNaissance.getText().toString().isEmpty() || editTextNoteBac.getText().toString().isEmpty()
                        || editTextDateBac.getText().toString().isEmpty() || editTextTel.getText().toString().isEmpty()
                        || editTextTel.getText().toString().isEmpty()) {
                    showSnackBar("Rien pour enregistrer !", "#FFC107", 1500);
                } else {

                    //openDialog();}
                }
            }
        });
        btnEnregistrer = (Button)

                findViewById(R.id.enregistrer);
        btnEnregistrer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText[] arrayOfEditTexts = {editTextNom, editTextPrenom, editTextCin, editTextCne, editTextEtablissement, editTextDateNaissance, editTextNoteBac, editTextDateBac, editTextFiliere, editTextTel, editTextEmail};

                if (editTextNom.getText().toString().isEmpty() || editTextPrenom.getText().toString().isEmpty() || editTextCin.getText().toString().isEmpty() || editTextCne.getText().toString().isEmpty() || editTextDateNaissance.getText().toString().isEmpty() || editTextNoteBac.getText().toString().isEmpty() || editTextDateBac.getText().toString().isEmpty() || editTextTel.getText().toString().isEmpty() || editTextTel.getText().toString().isEmpty())
                    Snackbar.with(FillingInformation.this, null)
                            .type(Type.ERROR)
                            .message("Quelque informations manquantes ! faites attention!! ")
                            .duration(Duration.LONG)
                            .fillParent(true)
                            .textAlign(Align.LEFT)
                            .show();

                    // showSnackBar("Please fill in all the blanks correctly !", "#FFC107", 1500);


                else {
                    String nomvalue = editTextNom.getText().toString();
                    String prenomvalue = editTextPrenom.getText().toString();
                    String cinvalue = editTextCin.getText().toString();
                    String cnevalue = editTextCne.getText().toString();
                    //String etablissementvalue= editTextEtablissement.getText().toString();
                    String datenaissancevalue = editTextDateNaissance.getText().toString();
                    String notebacvalue = editTextNoteBac.getText().toString();
                    String datebacvalue = editTextDateBac.getText().toString();
                    // String filierevalue= editTextFiliere.getText().toString();
                    String telvalue = editTextTel.getText().toString();
                    String emailvalue = editTextEmail.getText().toString();
                    Intent intent = new Intent(FillingInformation.this, GenerateCopy.class);
                    intent.putExtra("nom", nomvalue);
                    intent.putExtra("prenom", prenomvalue);
                    intent.putExtra("cin", cinvalue);
                    intent.putExtra("cne", cnevalue);
                    // intent.putExtra("etablissement",etablissementvalue);
                    intent.putExtra("datenaissance", datenaissancevalue);
                    intent.putExtra("notebac", notebacvalue);
                    intent.putExtra("datebac", datebacvalue);
                    //intent.putExtra("filiere",filierevalue);
                    intent.putExtra("tel", telvalue);
                    intent.putExtra("email", emailvalue);
                    intent.putExtra("etablissement", itemSElected1);
                    intent.putExtra("filiere", itemSElected2);
                    startActivity(intent);


                    try {


                        //Database.connect();
                        ResultSet resultSet = Database.executeQuery("INSERT INTO  \"ETUDIANT\"    (\"nom\", \"prenom\", \"cne\", \"etablissement\", \"date_de_naissance\",\"annee_bac\", \"Tel\", \"noteBac\", EMAIL, CIN)       VALUES(\'" + nomvalue + "\',\'" + prenomvalue + "\',\'" + cnevalue + "\',\'" + itemSElected1 + "\'," + "to_date(\'" + datenaissancevalue + "\', 'yyyy-mm-dd')" + ",\'" + datebacvalue + "\',\'" + telvalue + "\',\'" + notebacvalue + "\',\'" + emailvalue + "\',\'" + cinvalue + "\')");
                        resultSet = Database.executeQuery("UPDATE ETUDIANT set \"id_filiere\"=(select \"id_filiere\" from FILIERE where \"nomfiliere\" = \'" + itemSElected2 + "\') where CIN = \'" + cinvalue + "\'");

                        resultSet = Database.executeQuery("commit");


                    } catch (SQLException e) {
                        e.printStackTrace();
                        showSnackBar(e.getErrorCode() + "", "#FFC107", 1500);

                    }


                }
            }

        });

        final ArrayList<String> etablissements = new ArrayList<>();
        final ArrayList<String> filieres = new ArrayList<>();
        try {

            ResultSet resultSet = Database.executeQuery("SELECT \"nometablissement\" FROM \"ETABLISSEMENT\"");
            while (resultSet.next()) {
                etablissements.add(resultSet.getString(1));


            }

        } catch (
                Exception e) {
            e.printStackTrace();
        }
        try {
            ResultSet resultSet = Database.executeQuery("SELECT \"nomfiliere\" FROM \"FILIERE\"");
            while (resultSet.next()) {
                filieres.add(resultSet.getString(1));


            }


        } catch (
                Exception e) {
            e.printStackTrace();
        }

        etablissementSpinner = (Spinner)

                findViewById(R.id.etablissement);

        filiereSpinner = (Spinner)

                findViewById(R.id.filiere);


        ArrayAdapter etablissementAdapter = new ArrayAdapter<>(FillingInformation.this, R.layout.support_simple_spinner_dropdown_item, etablissements);

        ArrayAdapter filiereAdapter = new ArrayAdapter<>(FillingInformation.this, R.layout.support_simple_spinner_dropdown_item, filieres);
        etablissementSpinner.setAdapter(etablissementAdapter);
        filiereSpinner.setAdapter(filiereAdapter);

        etablissementSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position,
                                       long id) {
                // Toast.makeText(FillingInformation.this, etablissements.get(position), Toast.LENGTH_LONG).show();
                itemSElected1 = etablissements.get(position).toString();


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        filiereSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position,
                                       long id) {

                itemSElected2 = filieres.get(position).toString();


                // Toast.makeText(FillingInformation.this, filieres.get(position), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        RelativeLayout StartLayout = findViewById(R.id.Layout);

        ArrayList<View> clds = getAllChildren(StartLayout);
        for (
                int i = 0; i < clds.size(); i += 1) {

            if (clds.get(i) instanceof TextView) {
                ((TextView) clds.get(i)).setTypeface(custom_font);
            }

            if (clds.get(i) instanceof Button) {
                ((Button) clds.get(i)).setTypeface(custom_font);
            }
        }


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


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(), "Great Choice", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

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
