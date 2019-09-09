package com.esell.esellbannerdemo;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.esell.esellbanner.EsellBanner;

/**
 * 主页
 */
public class MainActivity extends AppCompatActivity {

    private EsellBanner mEsellBanner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEsellBanner = findViewById(R.id.eb_content);
        mEsellBanner.setDuration(5);
    }
}
