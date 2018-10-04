package com.gitjaipur.univ;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_splash );

        isNetworkAvailable();
        if (isNetworkAvailable() == true) {
            new Handler().postDelayed( new Runnable() {
                @Override
                public void run() {
                    final Intent anotherpage = new Intent(splash.this, MainActivity.class);
                    splash.this.startActivity(anotherpage);

                    splash.this.finish();
                }
            }, 3000);
        } else {

            final Toast toast = Toast.makeText(getApplicationContext(), "NO NETWOK CONNECTION " +
                    "PLEASE CHECK YOUR INTERNET CONNECTION", Toast.LENGTH_SHORT);
            toast.show();
            new CountDownTimer(5000, 1000) {
                public void onTick(long millisUntilFinished) {
                    toast.show();
                }

                public void onFinish() {
                    toast.cancel();
                }
            }.start();

        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService( Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

}


