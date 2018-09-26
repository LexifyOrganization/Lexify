package parisnanterre.fr.lexify.application;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import parisnanterre.fr.lexify.R;

/**
 * Created by Lucas on 26/09/2018.
 */

public class SplashScreenActivity extends Activity {
    private static int SPLASH_TIME_OUT = 3000; //time of splashScreen

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i= new Intent(SplashScreenActivity.this,MainActivity.class);
                startActivity(i);
                finish();
            }
        },SPLASH_TIME_OUT);
    }

}
