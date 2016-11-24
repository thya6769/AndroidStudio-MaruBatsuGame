package com.caqmei.marubatsugame;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    //played 0 = maru, played 1 = batsu

    private int played = 0;

    boolean gameIsActive = true;

    private TextView winnerMessage;


    // 2 is not played
    private int[] gameState = {2,2,2,2,2,2,2,2,2};

    private int[][] winningStates = {{0,1,2}, {3,4,5}, {6,7,8}, {0,3,6}, {1,4,7}, {2,5,8},{0,4,8}, {2,4,6}};

    private LinearLayout playAgain;


    public void dropIn(View view) {

        ImageView image = (ImageView) view;
        image.setTranslationY(-1000f);

        int tag = Integer.parseInt(image.getTag().toString());

        if(gameState[tag] == 2) {
            gameState[tag] = played;

            if (played == 0) {
                image.setImageResource(R.drawable.maru);
                played = 1;
            } else {
                image.setImageResource(R.drawable.batsu);
                played = 0;
            }
            image.animate().rotation(360).translationYBy(1000f).setDuration(360);
        }

        for(int[] winningState: winningStates)  {
            boolean thereIsWinner = false;

            // checks if there are 2 in a row
            if(gameState[winningState[0]] == gameState[winningState[1]]
                    && gameState[winningState[1]] == gameState[winningState[2]]
                    && gameState[winningState[0]] != 2){
                //System.out.println(gameState[winningState[0]] + "has won");

                String winner = "Batsu";
                if(gameState[winningState[0]] == 0) {
                    winner = "Maru";
                }
                thereIsWinner = true;
                winnerMessage.setText(winner + " has Won!");

                playAgain.setVisibility(View.VISIBLE);
            } else {
                boolean isGameOver = true;

                for(int state: gameState){
                    if(state == 2){
                        isGameOver = false;
                    }
                }

                if(isGameOver && !thereIsWinner) {
                    winnerMessage.setText("Its a draw!");
                    playAgain.setVisibility(View.VISIBLE);

                }
            }
        }
    }

    public void playAgain(View view) {
        playAgain.setVisibility(View.INVISIBLE);

        GridLayout gridLayout = (GridLayout) findViewById(R.id.gridLayout);

        played = 0;

        // reset the gameState
        for(int i = 0; i < gameState.length; i++){
            gameState[i] = 2;
        }

        for(int i = 0; i < gridLayout.getChildCount(); i++){
            ((ImageView) gridLayout.getChildAt(i)).setImageDrawable(null);
        }


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        playAgain = (LinearLayout) findViewById(R.id.playAgainLayout);
        winnerMessage = (TextView) findViewById(R.id.winnerMessage);

    }
}
