package com.example.flyingfish;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class GameOverActivity extends AppCompatActivity {

    private Button startAgainButton;    //object declaration for the play again button
    private TextView displayScore;      //object declaration for the text_view score
    private int score;                  //variable declaration for the fetching score
    private DatabaseHelper db;          //object for DatabaseHelper class

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);                //xml view of the game over page

        db = new DatabaseHelper(this);                      //constructor of DatabaseHelper class

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); //hides the notification bar
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION                          //hides the bottom navigation bar
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_FULLSCREEN);

        score = (int) getIntent().getExtras().get("score");         //reads the integer score as string type

        Cursor data = db.getListContents();                         //reads all the table contents

        if ((data.getCount() == 0) || (data.getCount() == 1) || (data.getCount() == 2))    //checks if first 3 high scores added or empty any slot
            db.addData(score);                                                             //store the score to the database
        else if (data.getCount() == 3) {                                                   //if existing table contents is 3 then
            while (data.moveToNext()) {                                                    //moves cursor to first row of the my_list_data table
                if (score > data.getInt(1)) {                                           //checks if current score is more than existing score
                    Cursor id = db.minElementID();                                         //returns min value to the cursor id
                    if (id.moveToNext()) {                                                 //moves the cursor to the first row
                        db.updateDatabse(id.getInt(0), score);                          //call update method which update the least score among the three scores
                    }
                    break;                                                                 //this will break the outer while() loop no need to check further
                }
            }
        }


        startAgainButton = (Button) findViewById(R.id.play_again_btn);                    //getting the id of the Play again Button R.id
        displayScore = (TextView) findViewById(R.id.score_textview);                      //getting the id of the Text view score R.id

        startAgainButton.setOnClickListener(new View.OnClickListener() {    //Perform task when Button get clicked or pressed
            @Override
            public void onClick(View v) {
                Intent mainIntent = new Intent(GameOverActivity.this, MainActivity.class);  //jump from GameOverActivity to MainActivity
                startActivity(mainIntent);                                                                //this will start the mainIntent
                finish();                                                                                 //finish or destroy this activity
            }
        });

        displayScore.setText("Score : " + getIntent().getExtras().get("score").toString());               //displays the score when the game over
    }

    @Override
    public void onBackPressed() {
        Intent home_menu = new Intent(GameOverActivity.this, Home_menu.class);            //while pressing the back button we go to home activity
        startActivity(home_menu);                                                                       //starts the intent
        finish();                                                                                       //finish or destroy this activity
    }
}
