package com.example.flyingfish;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private FlyingFishView gameview;            //object for calling FlyingFishActivity which is game_view
    private Handler handler = new Handler();
    private final static long Interval = 30;     //timer interval value

    public MediaPlayer mediaPlayer = new MediaPlayer();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gameview = new FlyingFishView(this);
        setContentView(gameview);                               //set the content of the game to flying fish view

        mediaPlayer = MediaPlayer.create(this, R.raw.backgroundmusic);       //reads the background sound from raw

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); //for full screen
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_FULLSCREEN);


        Timer timer = new Timer();                                             //thread for updating the gameview in every 30 ms
        timer.schedule(new TimerTask() {                                       // for repeated execution at regular intervals
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {                     //Generally, invalidate() means 'redraw on screen' and results to a call of the view's onDraw() method.
                        gameview.invalidate();              //So if something changes and it needs to be reflected on screen, you need to call invalidate()
                    }
                });
            }
        }, 0, Interval);

        if (mediaPlayer.isPlaying() == false)              //checks if the mediaplayer is already playing
            mediaPlayer.start();                           //if returns true then start the media the media player
    }

    @Override
    protected void onResume() {                             //when we resume the activity then
        super.onResume();
        try {                                               //try catch is use for any exception caught
            mediaPlayer.start();                            //resume the mediaplayer
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {                             //when the activity is get destroyed then
        super.onDestroy();                                   //calls the onDestroy() method which is already implemented in its parent class AppCompactActivity
        mediaPlayer.stop();                                  //stop the mediaplayer
    }

    @Override
    protected void onPause() {                               //when we pause the activity
        super.onPause();
        mediaPlayer.pause();                                 //pause the mediaplayer
    }

    @Override
    public void onBackPressed() {                            //when we do press the back button then
        mediaPlayer.stop();                                  // stop the mediaplayer
        Intent intent = new Intent(MainActivity.this, Home_menu.class);    //Intent class is use for opening /jumping to another activity
        startActivity(intent);                                //function call to start the new activity by passing the object intent
        finish();                                              // this will finish the Mainactivity
    }
}


