package com.yasuion.openglsquare;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private EGLView eglView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        eglView = (EGLView) findViewById(R.id.eglview);
    }

    @Override
    protected void onPause() {
        super.onPause();
        eglView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        eglView.onResume();
    }
}
