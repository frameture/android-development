package com.study.scorekeeper;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private int intScoreA, intScoreB; // state variables - hold scores for two teams

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView scoreA = (TextView) findViewById(R.id.score_a);
        final TextView scoreB = (TextView) findViewById(R.id.score_b);

        initializeButton(R.id.btn_1, 1, scoreA, 1);
        initializeButton(R.id.btn_2, 1, scoreA, 2);
        initializeButton(R.id.btn_3, 1, scoreA, 3);
        initializeButton(R.id.btn_1_b, 2, scoreB, 1);
        initializeButton(R.id.btn_2_b, 2, scoreB, 2);
        initializeButton(R.id.btn_3_b, 2, scoreB, 3);

        findViewById(R.id.btn_reset).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intScoreA = 0;
                intScoreB = 0;
                scoreA.setText("" + intScoreA);
                scoreA.invalidate();
                scoreB.setText("" + intScoreB);
                scoreB.invalidate();
            }
        });
    }

    /**
     * Method initializes all buttons associated with the program.
     * @param id Id of the given Button
     * @param team To which team the button should be associated
     * @param score Assigning score keeping variable to each button
     * @param points Assigning number of points that the button is intended to add
     */
    private void initializeButton(int id, final int team, final TextView score, final int points) {

        findViewById(id).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (team == 1) {
                    intScoreA += points;
                    score.setText("" + intScoreA);
                } else {
                    intScoreB += points;
                    score.setText("" + intScoreB);
                }
                score.invalidate();
            }
        });
    }
}
