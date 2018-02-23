package mab.taif_university_guidance;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import mab.taif_university_guidance.user.SelectionUserActivity;

public class SplashActivity extends AppCompatActivity {
    private static int SPLASH_TIMEOUT = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        runSplash();
    }



  private  void runSplash(){

      Thread background = new Thread() {
          public void run() {
              try {
                  sleep(SPLASH_TIMEOUT * 1000);


                 startActivity(new Intent(SplashActivity.this , SelectionUserActivity.class));
                  finish();

              } catch (InterruptedException e) {
                  e.printStackTrace();
              }
          }
      };

      background.start();
  }



}
