package com.myorientation;

import java.sql.SQLException;
import java.util.Random;

import android.graphics.Typeface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;

public class ForgetPassword extends AppCompatActivity {
    boolean SuggestedWordListenerJustStarted = true;
    public static int CurrentQuestion = 1;
    public static int CurrentLevel = 1;
    public static int LevelQuestionsNumber = 50;
    public static int[] LevelsDurations = {16, 12, 8};
    CountDownTimer WaitingTheWordCountDownTimer;
    CountDownTimer QuestionCountDownTimer;
    int WaitingTheWordCounter = 1;

    EditText mEmail;
    EditText mTel;
    String mMessage;
    String mSubject;
    RadioGroup radioGroup;
    RadioButton radioTel, radioEmail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.forget_password);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);


        Typeface custom_font = Typeface.createFromAsset(getAssets(), "fonts/font6.ttf");
        RelativeLayout StartLayout = findViewById(R.id.Layout);
        mEmail = findViewById(R.id.Email);
        mTel = findViewById(R.id.Password);

        mMessage = generateRandomPassword(12, true, true, true, true);
        mSubject = "password";

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


        radioGroup = findViewById(R.id.radiogroup);
        radioTel = findViewById(R.id.radioTel);
        radioEmail = findViewById(R.id.radioEmail);
        Button Send = findViewById(R.id.Validate);
        Send.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if ((mEmail.getText().length() == 0) && (mTel.getText().length() == 0)) {
                    message();
                }
                if (!(radioTel.isChecked()) && !(radioEmail.isChecked())) {
                    openDialog();
                } else {
                    int radiochecked = radioGroup.getCheckedRadioButtonId();
                    String email = mEmail.getText().toString();
                    String[] tab = email.split("@");
                    String cne = tab[0];
                    String Tel = mTel.getText().toString();
                    String phone = Tel.substring((1));
                    try {
                        Database.executeQuery("update \"ETUDIANT\" set \"password\"='" + mMessage + "' WHERE \"cne\"= '" + cne + "'");
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                    if (radioTel.getId() == radiochecked) {
                        Log.d("estsb", "212" + phone);
                        Document doc = null;
                        try {
                            doc = Jsoup.connect("https://rest.nexmo.com/sms/json").ignoreContentType(true)
                                    .data("from", "MY ORIENTATION")
                                    .data("text", "Your new password is:" + mMessage)
                                    .data("to", "212" + phone)
                                    .data("api_key", "9c4546f1")
                                    .data("api_secret", "0KgDtJ5a2DsScdDx")
                                    // and other hidden fields which are being passed in post request.
                                    .userAgent("Mozilla")
                                    .post();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        System.out.println(doc);
                    } else if (radioEmail.getId() == radiochecked) {
                        sendMail();
                    }
                }
            }


        });

    }

    public void openDialog() {
        logOpen exampleDialog = new logOpen(" shoisissez sms or email");
        exampleDialog.show(getSupportFragmentManager(), "message d'erreur");
    }

    public void message() {
        logOpen exampleDialog = new logOpen("entrer numéro de tél et email");
        exampleDialog.show(getSupportFragmentManager(), "message d'erreur");
    }

    private void sendMail() {

        String mail = mEmail.getText().toString().trim();
        String message = mMessage;
        String subject = mSubject;

        //Send Mail
        Gmail javaMailAPI = new Gmail(this, mail, subject, "your new password is:" + message);

        javaMailAPI.execute();

    }

    private static String generateRandomPassword(int max_length, boolean upperCase, boolean lowerCase, boolean numbers, boolean specialCharacters) {
        String upperCaseChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lowerCaseChars = "abcdefghijklmnopqrstuvwxyz";
        String numberChars = "0123456789";
        String specialChars = "!@#$%^&*[]()_-+=<>?/{}~|";
        String allowedChars = "";

        Random rn;
        rn = new Random();
        StringBuilder sb = new StringBuilder(max_length);

        //this will fulfill the requirements of atleast one character of a type.
        if (upperCase) {
            allowedChars += upperCaseChars;
            sb.append(upperCaseChars.charAt(rn.nextInt(upperCaseChars.length() - 1)));
        }

        if (lowerCase) {
            allowedChars += lowerCaseChars;
            sb.append(lowerCaseChars.charAt(rn.nextInt(lowerCaseChars.length() - 1)));
        }

        if (numbers) {
            allowedChars += numberChars;
            sb.append(numberChars.charAt(rn.nextInt(numberChars.length() - 1)));
        }

        if (specialCharacters) {
            allowedChars += specialChars;
            sb.append(specialChars.charAt(rn.nextInt(specialChars.length() - 1)));
        }


        //fill the allowed length from different chars now.
        for (int i = sb.length(); i < max_length; ++i) {
            sb.append(allowedChars.charAt(rn.nextInt(allowedChars.length())));
        }

        return sb.toString();
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
