package com.hamza.printingpress;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.hamza.printingpress.Api.UserAuth;
import com.hamza.printingpress.Dashboard.HomeActivity;

@SuppressLint("CustomSplashScreen")
public class SplashActivity extends AppCompatActivity {
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        if (UserAuth.isShopAdminLoggedIn(getApplicationContext())) {
            handler = new Handler();
            handler.postDelayed(() -> {
                Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }, 500);
        } else {
            handler = new Handler();
            handler.postDelayed(() -> {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }, 2000);
        }


    }
}