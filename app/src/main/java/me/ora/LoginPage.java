package me.ora;

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
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.chootdev.csnackbar.Duration;
import com.chootdev.csnackbar.Snackbar;
import com.chootdev.csnackbar.Type;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class LoginPage extends AppCompatActivity {
    public static String adminEmail = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.login_page);
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


        Database.connect();


        final EditText email = findViewById(R.id.Email);
        final EditText password = findViewById(R.id.Password);


        Button forget = findViewById(R.id.Forget);


        forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginPage.this, ForgetPassword.class));


            }
        });


        Button logIn = findViewById(R.id.LogIn);
        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Toast.makeText(getApplicationContext(), "Connecting ...", Toast.LENGTH_LONG).show();

                try {


                    if (!email.getText().toString().isEmpty() && !password.getText().toString().isEmpty()
                            && !email.getText().toString().startsWith(" ") && !password.getText().toString().startsWith(" ")
                            && !email.getText().toString().endsWith(" ") && !password.getText().toString().endsWith(" ")
                    ) {


                        if (email.getText().toString().endsWith("@taalim.ma") && email.getText().toString().split("@")[0].length() == 10) {

                            ResultSet resultSet = Database.executeQuery("SELECT \"password\" FROM ETUDIANT WHERE \"cne\" = '" + email.getText().toString().split("@")[0] + "'");
                            if (resultSet.next()) {
                                String retrievedPassword = resultSet.getString(1);
                                if (password.getText().toString().equals(retrievedPassword)) {
                                    //Toast.makeText(getApplicationContext(), "Connected !", Toast.LENGTH_LONG).show();


                                    showSnackBar("Connected successfully !", "#009688", 1000);


                                    new CountDownTimer(1000, 1000) {
                                        public void onTick(long millisUntilFinished) {

                                        }

                                        public void onFinish() {
                                            startActivity(new Intent(LoginPage.this, FillingInformations.class));

                                            finish();
                                        }
                                    }.start();


                                } else {
                                    showSnackBar("The informations are incorrect !", "#E91E63", 2000);
                                }
                            } else {
                                //Toast.makeText(getApplicationContext(), "The informations are incorrect !", Toast.LENGTH_LONG).show();
                                showSnackBar("The informations are incorrect !", "#E91E63", 2000);

                            }
                        } else if (email.getText().toString().endsWith("@ucd.ac.ma")) {


                            ResultSet resultSet = Database.executeQuery("SELECT \"password\" FROM ADMIN WHERE \"email\" = '" + email.getText().toString() + "'");
                            if (resultSet.next()) {
                                String retrievedPassword = resultSet.getString(1);
                                if (password.getText().toString().equals(retrievedPassword)) {
                                    //Toast.makeText(getApplicationContext(), "Connected !", Toast.LENGTH_LONG).show();


                                    showSnackBar("Connected successfully !", "#009688", 1000);


                                    new CountDownTimer(2200, 1000) {
                                        public void onTick(long millisUntilFinished) {

                                        }

                                        public void onFinish() {
                                            adminEmail = email.getText().toString();
                                            startActivity(new Intent(LoginPage.this, AdminDashboard.class));

                                            finish();
                                        }
                                    }.start();


                                } else {
                                    showSnackBar("The informations are incorrect !", "#E91E63", 2000);
                                }
                            } else {
                                //Toast.makeText(getApplicationContext(), "The informations are incorrect !", Toast.LENGTH_LONG).show();
                                showSnackBar("The informations are incorrect !", "#E91E63", 2000);

                            }


                        } else {
                            showSnackBar("The given email is invalid !", "#FFC107", 2000);
                        }
                    } else {

                        showSnackBar("Please fill in all the blanks correctly !", "#FFC107", 2000);
                    }


                } catch (SQLException e) {
                    e.printStackTrace();
                }


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


    /** @Override public void onBackPressed() {

    }**/


}
