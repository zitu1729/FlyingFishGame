package com.example.flyingfish;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Home_menu extends AppCompatActivity {

    private Button newGamebutton;
    private Button aboutUsButton;
    private Button exitButton;
    private Button highScoreButton;
    private ImageView closeimageView, iconIV;
    private TextView titleTV, scoreTV;
    private int count;
    private DatabaseHelper db;
    private Dialog epicDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_menu);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_FULLSCREEN);

        db = new DatabaseHelper(this);
        epicDialog = new Dialog(this);

        newGamebutton = findViewById(R.id.New_game_Btn);
        aboutUsButton = findViewById(R.id.About_us_btn);
        exitButton = findViewById(R.id.Exit_Btn);
        highScoreButton = findViewById(R.id.High_score_Btn);


        newGamebutton.setOnClickListener(new View.OnClickListener() {    //Perform task when Button get clicked or pressed
            @Override
            public void onClick(View v) {
                Intent mainIntent = new Intent(Home_menu.this, MainActivity.class); //jump from Home_menu to MainActivity
                startActivity(mainIntent);                                                        //this will start the mainIntent
                finish();
            }
        });

        highScoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewScore();
            }
        });

        exitButton.setOnClickListener(new View.OnClickListener() {  //exit from the game
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        aboutUsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {                                                  //display a message about the developer
                Toast.makeText(getApplicationContext(), "Developer Jitendra mail:zitu1729@gmail.com", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void onBackPressed() {
        count++;
        if (count > 1) {
            finish();
        } else {
            Toast.makeText(this, "Press back again to Leave!", Toast.LENGTH_SHORT).show();

            // resetting the counter in 2s
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    count = 0;
                }
            }, 2000);
        }
    }

    public void showMessage(String title, String message, Boolean img) {
        epicDialog.setContentView(R.layout.popup_highscore);
        closeimageView = epicDialog.findViewById(R.id.closePopup);
        titleTV = epicDialog.findViewById(R.id.titleTextview);
        scoreTV = epicDialog.findViewById(R.id.scoreTextView);
        iconIV = epicDialog.findViewById(R.id.image_trophy);

        titleTV.setText(title);
        scoreTV.setText(message);

        if (img == true) iconIV.setBackgroundResource(R.drawable.icon_trophy);
        else iconIV.setBackgroundResource(R.drawable.play_icon);

        closeimageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                epicDialog.dismiss();
            }
        });

        epicDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        epicDialog.show();


    }

    public void viewScore() {
        Cursor data = db.descOrder();
        if (data.getCount() != 0) {
            StringBuffer stringBuffer = new StringBuffer();
            while (data.moveToNext())
                stringBuffer.append(data.getInt(1) + "\n");
            showMessage("HIGH SCORES", stringBuffer.toString(), true);
        } else {
            showMessage("EMPTY", "Play Now \n To Score First \n !!!! ", false);
        }

    }
}