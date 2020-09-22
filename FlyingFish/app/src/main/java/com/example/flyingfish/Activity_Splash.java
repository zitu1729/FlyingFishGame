package com.example.flyingfish;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;

public class Activity_Splash extends AppCompatActivity {

    private ProgressBar simpleProgressBar;                                                  //object for progressbar
    private int progressStatus = 0;                                                         //variable for progress values
    //private MediaPlayer mediaPlayer = new MediaPlayer();                                    //media_player object for sound effect

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__splash);                                          //locate to the xml design of the splash screen

        //for hiding status bar and navigation bar,,
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_FULLSCREEN);


        simpleProgressBar = findViewById(R.id.progressBar);                           //initiate progress bar and start button
        // mediaPlayer = MediaPlayer.create(Activity_Splash.this, R.raw.splashsound);  //getting the resource file
        //  mediaPlayer.start();                                                                //starts the media_player

        Thread thread1 = new Thread() {                 //this thread is for the progress bar
            @Override
            public void run() {
                while (progressStatus <= 100) {                                             //max of progress = 100
                    try {
                        progressStatus++;                                                   //incrementing progress by 1 in every step
                        Thread.sleep(50);
                    } catch (Exception e) {
                        e.printStackTrace();                                                //for any exception might caught
                    }
                    simpleProgressBar.setProgress(progressStatus);                          //sets the progress bar according to integer value passed
                }
            }
        };
        thread1.start();                                                                    //starts the thread1

        Thread thread = new Thread() {
            @Override
            public void run() {                                                          //lightweight process for displaying the splash_activity
                try {
                    sleep(5000);                                                     //for 5 seconds
                } catch (Exception e) {
                    e.printStackTrace();                                                   //for any exception might caught
                } finally {
                    Intent mainIntent = new Intent(Activity_Splash.this, Home_menu.class);  //after 5sec move to main_activity
                    startActivity(mainIntent);                                             //method will start the main_activity
                }
            }
        };
        thread.start();                                                                    //for starting the thread variable

    }

    @Override
    protected void onPause() {
        super.onPause();                                                                   //finish or we can say destroy the splash_activity after 5sec when it get paused
        // mediaPlayer.release();                                                             //releases the media player
        finish();                                                                          //so that a user while pressing back button again it is not get displayed
    }

    @Override
    public void onBackPressed() {
        System.exit(1);                                                             //when we press back button then this will forcefully close the app
    }
}
