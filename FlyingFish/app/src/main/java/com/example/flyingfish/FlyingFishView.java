package com.example.flyingfish;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.view.MotionEvent;                        //all the necessary files which have to import to use the methods
import android.view.View;



public class FlyingFishView extends View {                   //this class draws all the contents of the gaming window..

    private Bitmap fish[] = new Bitmap[2];                   //array objects used for fish drawings flips and non flips
    private int fishX = 10;                                  //variable used for x-coordinate of the fish,,
    private int fishY;                                       //variable used for y-coordinate of th fish..
    private int fishSpeed;                                   //variable for vertical upward and downward speed of the fish..
    private int canvasWidth, canvasHeight;                   //variable used for fish canvas
    private Boolean touch = false;                           //to detect the touches on the screen while tapping by the player

    private int yellowX, yellowY, yellowSpeed = 8;          //variable & const for yellow ball to feed the fish
    private Paint yellowPaint = new Paint();                 //object yellowPaint is used to paint the ball inside the canvas

    private int greenX, greenY, greenSpeed = 6;             //variable & const for green ball to feed the fish
    private Paint greenPaint = new Paint();                  //object greenPaint is used to paint the ball inside the canvas

    private int blueX, blueY, blueSpeed = 9;               //variable & const for green ball to feed the fish
    private Paint bluePaint = new Paint();                  //object greenPaint is used to paint the ball inside the canvas

    private int redX, redY, redSpeed = 7;                   //variable & const for red ball to feed the fish
    private Paint redPaint = new Paint();                    //object redPaint is used to paint the ball inside the canvas

    private int score, bgSpeedX = 0;                                      //variable for getting or counting scores and life remains
    public int LifeCounter;


    private Rect rect, rect1;

    private Bitmap backGround;                              //object for background image to draw
    private Paint scorePaint = new Paint();                 //object of class type paint to display the score
    private Bitmap life[] = new Bitmap[2];                  //array objects for display hearts


    public FlyingFishView(Context context) {                //this class Flying_FishView will get the resources of the objects
        super(context);


        fish[0] = BitmapFactory.decodeResource(getResources(), R.drawable.fish1);    //getting resource of the fish1
        fish[1] = BitmapFactory.decodeResource(getResources(), R.drawable.fish2);    //getting resource of the fish2

        backGround = BitmapFactory.decodeResource(getResources(), R.drawable.bg2);  //getting resource of the background underwater image

        yellowPaint.setColor(Color.YELLOW);                                              //Set the paint's color with a ColorLong
        yellowPaint.setAntiAlias(true);                                                  //Helper for setFlags(), setting or clearing the ANTI_ALIAS_FLAG bit
        //AntiAliasing smooths out the edges of what is being drawn,
        //but is has no impact on the interior of the shape

        greenPaint.setColor(Color.GREEN);                                                //similarly we do for all balls
        greenPaint.setAntiAlias(true);

        bluePaint.setColor(Color.BLUE);                                                //similarly we do for all balls
        bluePaint.setAntiAlias(true);

        redPaint.setColor(Color.RED);                                                //similarly we do for all balls
        redPaint.setAntiAlias(true);


        scorePaint.setColor(Color.DKGRAY);                                               //Set the paint's color with a ColorLong.
        scorePaint.setTextSize(70);                                                     //Set the paint's text size.
        scorePaint.setTypeface(Typeface.DEFAULT_BOLD);                                  //Set or clear the typeface object,here it is BOLD
        scorePaint.setAntiAlias(true);                                                  //smooths the edges
        life[0] = BitmapFactory.decodeResource(getResources(), R.drawable.hearts);      //getting resource of red heart
        life[1] = BitmapFactory.decodeResource(getResources(), R.drawable.heart_grey);  //getting resource of grey heart

        fishY = 650;                                                                    //initial y-coordinate for the fish
        score = 0;                                                                      //initial score
        LifeCounter = 3;                                                                //initially life of player is 3
    }

    @Override
    protected void onDraw(final Canvas canvas) {                                              //this method will draw each and every object line by line as given
        super.onDraw(canvas);

        canvasWidth = canvas.getWidth();                                                //get the width of the canvas drawn
        canvasHeight = canvas.getHeight();                                              //get the height of the canvas drawn

        if (bgSpeedX == -canvasWidth)
            bgSpeedX = 0;                           //moving the background horizontally

        rect = new Rect(bgSpeedX, 0, canvasWidth+ bgSpeedX, canvasHeight);                     //draw the background image for the game_view
        canvas.drawBitmap(backGround, null, rect, null);
        rect1 = new Rect(canvasWidth + bgSpeedX, 0, 2*canvasWidth+ bgSpeedX, canvasHeight);  //for the second bg img
        canvas.drawBitmap(backGround, null, rect1, null);
        bgSpeedX -= 6;

        int minFishY = fish[0].getHeight() - 50;                                        //variable for getting min_height or jumping height or TOP
        int maxFishY = canvasHeight - fish[0].getHeight();                             //variable for getting max_height or falling height or BOTTOM
        fishY = fishY + fishSpeed;                                                      //set the y-coordinate according to fish's speed while moving downwards

        if (fishY < minFishY) {                                                         //conditions for checking heights min and max for fish
            fishY = minFishY;
        }
        if (fishY > maxFishY) {
            fishY = maxFishY;
        }

        fishSpeed = fishSpeed + 2;                                                     //here in this line,when fish falls downward we increases the speed

        if (touch) {                                                                   //checking if touch is true
            canvas.drawBitmap(fish[1], fishX, fishY, null);                      //then draw the flip fish2
            touch = false;                                                             //make the touch false so that again user have to touch
        } else {
            canvas.drawBitmap(fish[0], fishX, fishY, null);                      //for fish1 normal fish
        }

        yellowX = yellowX - (yellowSpeed + (score / 40));                             //this line moves the ball from right to left reducing the x-axis
        if (hitBallChecker(yellowX, yellowY))                                          //if fish hit any ball
        {
            score = score + 10;                                                        //then increases the score for the player by 10,,
            yellowX = -100;                                                            //moves the yellow ball towards -x axis which is out of display and hence ball will disappear
        }
        if (yellowX < 0) {
            yellowX = canvasWidth + 21;                                                   //resetting the new ball x-axis
            yellowY = (int) Math.floor(Math.random() * (maxFishY - minFishY)) + minFishY + 30; //resetting the new ball y-axis using random math func()
        }
        canvas.drawCircle(yellowX, yellowY, 27, yellowPaint);                   //display the yellow ball with given radius


        greenX = greenX - (greenSpeed + (score / 40));                                 //this line moves the ball from right to left,reducing the x-axis
        if (hitBallChecker(greenX, greenY))                                            //if fish hit any ball
        {
            score = score + 20;                                                        //then increases the score for the player by 20,,
            greenX = -100;                                                             //moves the yellow ball towards -x axis which is out of display and hence ball will disappear
        }
        if (greenX < 0) {
            greenX = canvasWidth + 21;                                                   //resetting the new ball x-axis
            greenY = (int) Math.floor(Math.random() * (maxFishY - minFishY)) + minFishY + 30; //resetting the new ball y-axis using random math func()
        }
        canvas.drawCircle(greenX, greenY, 32, greenPaint);                        //display the green ball with given radius

        blueX = blueX - (blueSpeed + (score / 40));                                 //this line moves the ball from right to left,reducing the x-axis
        if (hitBallChecker(blueX, blueY))                                            //if fish hit any ball
        {
            score = score + 30;                                                        //then increases the score for the player by 20,,
            blueX = -100;                                                             //moves the yellow ball towards -x axis which is out of display and hence ball will disappear
        }
        if (blueX < 0) {
            blueX = canvasWidth + 21;                                                   //resetting the new ball x-axis
            blueY = (int) Math.floor(Math.random() * (maxFishY - minFishY)) + minFishY + 30; //resetting the new ball y-axis using random math func()
        }
        canvas.drawCircle(blueX, blueY, 38, bluePaint);                        //display the green ball with given radius


        redX = redX - (redSpeed + (score / 40));                                       //this line moves the ball from right to left,reducing the x-axis
        if (hitBallChecker(redX, redY))                                                 //if fish hit any ball
        {
            redX = -100;                                                                //moves the red ball towards -x axis which is out of display and hence ball will disappear
            LifeCounter--;                                                              //post_decrement the Life_counter variable when fish eat red bubble
            if (LifeCounter == 0) {
                //Toast.makeText(getContext(), " Game Over ", Toast.LENGTH_SHORT).show();  //display message to the user when game end
                Intent gameOverIntent = new Intent(getContext(), GameOverActivity.class);
                gameOverIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                gameOverIntent.putExtra("score", score);                           //putting the score to "score" or sends the score
                getContext().startActivity(gameOverIntent);                              //starts the game_over activity
            }
        }

        if (redX < 0) {
            redX = canvasWidth + 21;                                                    //resetting the new ball x-axis
            redY = (int) Math.floor(Math.random() * (maxFishY - minFishY)) + minFishY + 30;  //resetting the new ball y-axis using random math func()
        }
        canvas.drawCircle(redX, redY, 41, redPaint);                            //display the red ball with given radius

        canvas.drawText("Score : " + score, 40, 90, scorePaint);           //display the name "score : " with calculated score variable

        for (int i = 0; i < 3; i++) {                                                 //loop for drawing the red and grey hearts
            int x = (int) (700 + life[0].getWidth() * 1.2 * i);
            int y = 30;
            if (i < LifeCounter)
                canvas.drawBitmap(life[0], x, y, null);
            else canvas.drawBitmap(life[1], x, y, null);
        }
    }

    public boolean hitBallChecker(int x, int y) {                                    //this method will check the position of the fish and the ball
        if (fishX < x && x < (fishX + fish[0].getWidth()) && fishY < y && y < (fishY + fish[0].getHeight())) {
            return true;
        }
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {                                 //check user's touches on the screen
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            touch = true;                                                            //touch will be true
            fishSpeed = -22;                                                         //here we can adjust the fish speed
        }
        return true;                                                                 //returns true when user touches on screen
    }
}
