package parisnanterre.fr.lexify.application;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import parisnanterre.fr.lexify.R;

/**
 * Created by Lucas on 02/10/2018.
 */

public class SplashScreenActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        Intent i= new Intent(SplashScreenActivity.this,MainActivity.class);
        startActivity(i);
        finish();
    }
}
