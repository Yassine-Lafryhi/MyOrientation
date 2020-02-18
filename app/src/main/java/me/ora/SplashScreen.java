package me.ora;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.StrictMode;
import android.view.Display;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;


public class SplashScreen extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.splash_screen);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        new CountDownTimer(1900, 1000) {
            public void onTick(long millisUntilFinished) {

            }

            public void onFinish() {
                startActivity(new Intent(SplashScreen.this, LoginPage.class));

                finish();
            }
        }.start();


        final ImageView splash = findViewById(R.id.logo);
        Display display = getWindowManager().getDefaultDisplay();
        float h = display.getHeight();
        TranslateAnimation animation = new TranslateAnimation(0, 0, -300, 0);
        animation.setDuration(2000);
        splash.startAnimation(animation);


    }

}
