package com.example.view;

import static android.view.WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowInsetsController;

import com.example.notesapp.R;

public class SplashScreen extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        Setting_Action_Bar_Status_Bar();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashScreen.this,MainActivity.class));
                finish();
            }
        },4000);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void Setting_Action_Bar_Status_Bar() {

        //Hiding action bar
        getSupportActionBar().hide();
        // setting the keyboard

        this.getWindow().setStatusBarColor(getColor(R.color.back));

        // to change the color of the icons in status bar to dark
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
//            this.getWindow().getDecorView().getWindowInsetsController().setSystemBarsAppearance(APPEARANCE_LIGHT_STATUS_BARS, APPEARANCE_LIGHT_STATUS_BARS);
//        }
        // to change the color of the icons in the navigation bar to dark
        this.getWindow().setNavigationBarColor(getColor(R.color.back)); //setting bar color
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
//            this.getWindow().getDecorView().getWindowInsetsController().setSystemBarsAppearance(WindowInsetsController.APPEARANCE_LIGHT_NAVIGATION_BARS, WindowInsetsController.APPEARANCE_LIGHT_NAVIGATION_BARS);
//        }
    }
}