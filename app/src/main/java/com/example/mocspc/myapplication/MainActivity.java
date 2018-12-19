package com.example.mocspc.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    // cells in the grid
    private Button[][] grid = new Button[3][3];
    private boolean player1 = true; //check which player turn
    private int countRound; //check which round
    private int pointP1; //keep track of games won for player 1
    private int pointP2; ////keep track of games won for player 2
    private TextView textViewP1;
    private TextView textViewP2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        linkToLayout();
    }

    public void linkToLayout() {
        textViewP1 = findViewById(R.id.text_p1);
        textViewP2 = findViewById(R.id.text_p2);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                String buttonID = "button" + i + j;
                int resultID = getResources().getIdentifier(buttonID, "id",
                        getPackageName());
                grid[i][j] = findViewById(resultID);
                grid[i][j].setOnClickListener(this);
            }
        }
        Button resetButton = findViewById(R.id.resetButton);
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetButtonGame();
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (!((Button) v).getText().toString().equals("")) {
            return;
        }
        if (player1) {
            ((Button) v).setText("X");
        } else {
            ((Button) v).setText("O");
        }
        countRound++;

        if (checkWinner()) {
            if (player1) {
                player1Win();
            } else {
                player2Win();
            }
        } else if (countRound == 9) {
            drawGame();
        } else {
            player1 = !player1;
        }

    }

    private boolean checkWinner() {
        String[][] field = new String[3][3];
        for (int i = 0; i < 3; i++) { //map x or o onto the grid
            for (int j = 0; j < 3; j++) {
                field[i][j] = grid[i][j].getText().toString();
            }
        }
        for (int i = 0; i < 3; i++) { //check if user has a winning row
            if (field[i][0].equals(field[i][1]) &&
                    field[i][0].equals(field[i][2])
                    && !field[i][0].equals("")) {
                return true;
            }
        }
        for (int i = 0; i < 3; i++) { //check if user has a winning column
            if (field[0][i].equals(field[1][i]) &&
                    field[0][i].equals(field[2][i])
                    && !field[0][i].equals("")) {
                return true;
            }
        }
        // check if left diagonal is a winning row
        if (field[0][0].equals(field[1][1]) &&
                field[0][0].equals(field[2][2])
                && !field[0][0].equals("")) {
            return true;
        }
        // check if right diagonal is a winning row
        if (field[0][2].equals(field[1][1]) &&
                field[0][2].equals(field[2][0])
                && !field[0][2].equals("")) {
            return true;
        }
        return false;
    }

    private void player1Win() {
        pointP1++;
        Toast.makeText(this, "Player 1 won!", Toast.LENGTH_LONG).show();
        updatePointScore();
        resetGame();
    }

    private void player2Win() {
        pointP2++;
        Toast.makeText(this, "Player 2 won!", Toast.LENGTH_LONG).show();
        updatePointScore();
        resetGame();
    }

    private void drawGame() {
        Toast.makeText(this, "Draw!", Toast.LENGTH_LONG).show();
        resetGame();
    }

    private void updatePointScore() { //keep track of how many games won by each player
        textViewP1.setText("Player 1: " + pointP1);
        textViewP2.setText("Player 2: " + pointP2);
    }

    private void resetGame() { //Make whole board empty again
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                grid[i][j].setText("");
            }
        }
        countRound = 0;
        player1 = true;
    }

    private void resetButtonGame() {//reset both current game and score of each player
        pointP1 = 0;
        pointP2 = 0;
        updatePointScore();
        resetGame();
    }

    @Override
    //to keep track of turns when changing device orientation
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("countRound", countRound);
        outState.putInt("pointP1", pointP1);
        outState.putInt("pointP2", pointP2);
        outState.putBoolean("player1", player1);
    }

    @Override
    //to keep track of turns when changing device orientation
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        countRound = savedInstanceState.getInt("countRound");
        pointP1 = savedInstanceState.getInt("pointP1");
        pointP2 = savedInstanceState.getInt("pointP2");
        player1 = savedInstanceState.getBoolean("player1");

    }
}
