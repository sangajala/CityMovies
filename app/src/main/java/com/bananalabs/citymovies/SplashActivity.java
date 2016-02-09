package com.bananalabs.citymovies;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Thread background = new Thread() {

            @Override
            public void run() {

                try {
                    // Thread will sleep for 5 seconds
                    sleep(3 * 1000);

                    // After 5 seconds redirect to another intent

                    Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
                    startActivity(intent);


                    finish();

                } catch (Exception e) {

                }
            }
        };

        // start thread
        background.start();
    }
}
