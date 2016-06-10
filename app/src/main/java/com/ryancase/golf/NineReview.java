package com.ryancase.golf;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ryancase.golf.Helpers.ArrayValues;
import com.ryancase.golf.Holes.Hole10;

import java.util.Set;

/**
 * Created by ryan.case on 6/9/16.
 */
public class NineReview extends Activity{
    private TextView fairwaySt, girSt, puttSt, scoreSt, parSt, birdieSt, bogeySt, thePar, other, eagleSt, ud;
    private int fwCount = 0, udCount = 0, girCount = 0, puttCount = 0, score = 0, par = 0,
            birdies = 0, pars = 0, bogeys = 0, otherC = 0, eagles = 0;
    int[]aPar;
    String a, b, c, d, e, f, g, h, i, j, k;
    Button continueButton, finishButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.nine_review);

        aPar = new int[9];

        InitTextViews();
        NineVarAssign();
        //TallyTheStats();

        SetTextViews();

        continueButton = (Button) findViewById(R.id.continueRound);
        finishButton = (Button) findViewById(R.id.finishAtTheTurn);

        View.OnClickListener contListener =new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Hole10.class));
            }
        };
        continueButton.setOnClickListener(contListener);

        View.OnClickListener finishListener =new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle b = new Bundle();
                b.putString("nine", "front");

                Intent intent = new Intent(NineReview.this, roundFinishNine.class);
                intent.putExtras(b); //Put your id to your next Intent
                startActivity(intent);
            }
        };
        finishButton.setOnClickListener(finishListener);
    }



    //DISABLE BACK BUTTON//
    @Override
    public void onBackPressed() {
        // super.onBackPressed(); // Comment this super call to avoid calling finish()
    }

    public void NineVarAssign() {

        //FRONT NINE VARIABLE ASSIGNMENT
        for(int i = 0; i < 9; i++) {
            score += ArrayValues.scores[i];
            Log.d("Score: ", "" + score);
            puttCount += ArrayValues.putts[i];

            if(ArrayValues.par[i] == 0) {
                aPar[i] = 3;
            }
            if(ArrayValues.par[i] == 1) {
                aPar[i] = 4;
            }
            if(ArrayValues.par[i] == 2) {
                aPar[i] = 5;
            }
            par += aPar[i];

            if (ArrayValues.fairways[i] == 1) {
                fwCount++;
            }
            if (ArrayValues.girs[i] == 1) {
                girCount++;
            }
            if (ArrayValues.uds[i] == 1) {
                udCount++;
            }
            if ((ArrayValues.par[i] + 3) == ArrayValues.scores[i]) {
                pars++;
            }
            if (ArrayValues.scores[i] - (ArrayValues.par[i] + 3) == 1) {
                bogeys++;
            }
            if ((ArrayValues.scores[i] - (ArrayValues.par[i] + 3) > 1) || (ArrayValues.scores[i] - (ArrayValues.par[i] + 3) < -2)) {
                otherC++;
            }
            if (ArrayValues.scores[i] - (ArrayValues.par[i] + 3) == -1) {
                birdies++;
            }
            if (ArrayValues.scores[i] - (ArrayValues.par[i] + 3) == -2) {
                eagles++;
            }
        }
    }

    public void TallyTheStats() {
        for(int i = 0; i < 9; i++)
        {
            if(ArrayValues.fairways[i] == 1) {
                fwCount++;
            }
            if(ArrayValues.girs[i] == 1) {
                girCount++;
            }
            if(ArrayValues.uds[i] == 1) {
                udCount++;
            }
            if((ArrayValues.par[i] + 3) == ArrayValues.scores[i]) {
                pars++;
            }
            if(ArrayValues.scores[i] - (ArrayValues.par[i] + 3) == 1) {
                bogeys++;
            }
            if((ArrayValues.scores[i] - (ArrayValues.par[i] + 3) > 1) || (ArrayValues.scores[i] - (ArrayValues.par[i] + 3) < -2)) {
                otherC++;
            }
            if(ArrayValues.scores[i] - (ArrayValues.par[i] + 3) == -1) {
                birdies++;
            }
            if(ArrayValues.scores[i] - (ArrayValues.par[i] + 3) == -2) {
                eagles++;
            }
            if(ArrayValues.par[i] == 0) {
                aPar[i] = 3;
            }
            if(ArrayValues.par[i] == 1) {
                aPar[i] = 4;
            }
            if(ArrayValues.par[i] == 2) {
                aPar[i] = 5;
            }
        }
    }

    public void SetTextViews() {
        a = Integer.toString(fwCount);
        b = Integer.toString(girCount);
        c = Integer.toString(score);
        d = Integer.toString(puttCount);
        e = Integer.toString(pars);// # of pars
        f = Integer.toString(birdies);
        g = Integer.toString(par);// par of course
        h = Integer.toString(bogeys);
        i = Integer.toString(eagles);
        j = Integer.toString(otherC);
        k = Integer.toString(udCount);

        fairwaySt.setText(a);
        girSt.setText(b);
        scoreSt.setText(c);
        puttSt.setText(d);
        parSt.setText(e);
        birdieSt.setText(f);
        thePar.setText(g);
        bogeySt.setText(h);
        eagleSt.setText(i);
        other.setText(j);
        ud.setText(k);
    }

    public void InitTextViews() {
        fairwaySt = (TextView) findViewById(R.id.fwAtt);
        girSt = (TextView) findViewById(R.id.girAtt);
        puttSt = (TextView) findViewById(R.id.puttAtt);
        scoreSt = (TextView) findViewById(R.id.scoreAtt);
        parSt = (TextView) findViewById(R.id.parsAtt);
        birdieSt = (TextView) findViewById(R.id.birdiesAtt);
        bogeySt = (TextView) findViewById(R.id.bogeysAtt);
        thePar = (TextView) findViewById(R.id.theParAtt);
        other = (TextView) findViewById(R.id.otherAtt);
        eagleSt = (TextView) findViewById(R.id.eaglesAtt);
        ud = (TextView) findViewById(R.id.udAtt);
    }
}


