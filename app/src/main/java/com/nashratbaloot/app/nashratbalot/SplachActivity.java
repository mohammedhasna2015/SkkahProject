package com.nashratbaloot.app.nashratbalot;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;

import com.nashratbaloot.app.R;

import java.util.Locale;

public class SplachActivity extends AppCompatActivity {
    int displaySplash = 2000;

    Locale currentAppLocale;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splach);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                updateResources(SplachActivity.this,"en");
                Intent intent= new Intent(SplachActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, displaySplash);

    }


    private  void updateResources(Context context, String language) {
        currentAppLocale = new Locale(language);
        Resources res=context.getResources();
        DisplayMetrics dm =res.getDisplayMetrics();
        Configuration conf = context.getResources().getConfiguration();
        conf.locale= currentAppLocale;

        res.updateConfiguration(conf,dm);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            conf.setLayoutDirection(currentAppLocale);
        }

        res.updateConfiguration(conf,dm);
    }


}