package com.ryancase.golf;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseSession;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ryancase on 11/29/15.
 */

public class Stats extends Activity {

    TextView ltBirdies, ltRounds, ltGreens, ltFairways, ltPutts, ltScore, ltPars, ltEagles, ltBogeys, ltScoreToPar,
    ltUpDown;
    Switch sw;

    int numOfRounds, birdTotal = 0, scoreTotal = 0, fairwayTotal = 0, puttTotal = 0, greenTotal = 0,
            parTotal = 0, eagleTotal = 0, bogTotal = 0, xInt, theParTotal = 0, udTotal = 0,
            birdTotalB = 0, bogTotalB = 0, fairwayTotalB = 0, scoreTotalB = 0, puttTotalB = 0,
            greenTotalB = 0, parTotalB = 0, eagleTotalB = 0, theParTotalB = 0, numOfRoundsB = 0, udTotalB = 0;

    int[] birdInt, scoreInt, fairwayInt, puttInt, greenInt, eagleInt, parInt, bogInt, toParInt, udInt;

    final List birdies = new ArrayList();
    final List scores = new ArrayList();
    final List fairways = new ArrayList();
    final List putts = new ArrayList();
    final List greens = new ArrayList();
    final List eagles = new ArrayList();
    final List pars = new ArrayList();
    final List bogeys = new ArrayList();
    final List par = new ArrayList();
    final List uds = new ArrayList();

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stats);

        ltBirdies = (TextView) findViewById(R.id.lifeStBirdies);
        ltRounds = (TextView) findViewById(R.id.numRoundsStat);
        ltGreens = (TextView) findViewById(R.id.lifeStGreens);
        ltFairways = (TextView) findViewById(R.id.lifeStFWs);
        ltPutts = (TextView) findViewById(R.id.lifeStPutts);
        ltScore = (TextView) findViewById(R.id.lifeStScore);
        ltEagles = (TextView) findViewById(R.id.lifeStEagless);
        ltPars = (TextView) findViewById(R.id.lifeStPars);
        ltBogeys = (TextView) findViewById(R.id.lifeStBogeys);
        ltScoreToPar = (TextView) findViewById(R.id.lifeStScoreToPar);
        ltUpDown = (TextView) findViewById(R.id.lifeStUDs);

        sw = (Switch) findViewById(R.id.switch1);


        birdInt = new int[18];
        fairwayInt = new int[18];
        scoreInt = new int[18];
        puttInt = new int[18];
        greenInt = new int[18];
        parInt = new int[18];
        eagleInt = new int[18];
        bogInt = new int[18];
        toParInt = new int[18];
        udInt = new int[18];

        sw.setChecked(false);


        callQuery();
        callQueryG();
        setText();

        View.OnClickListener sListener =new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sw.isChecked()) {
                    setTextB();
                }
                else {
                    Log.d("All good", "");
                    setText();
                }
            }
        };
        sw.setOnClickListener(sListener);
    }

    public void callQueryG () {
        if(!ParseUser.getCurrentUser().getEmail().equals("")) {
            ParseQuery<ParseObject> query =
                    new ParseQuery<ParseObject>("RoundStats");
            query.whereLessThan("Par", 40);
            query.whereEqualTo("roundID", ParseUser.getCurrentUser().getEmail());
            query.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(final List<ParseObject> parseObjects, com.parse.ParseException e) {
                    if (e == null) {

                        numOfRoundsB = parseObjects.size();
                        Log.d("THE numofrounds", "" + numOfRounds);


                        for (int i = 0; i < parseObjects.size(); i++) {

                            par.add(i, parseObjects.get(i).getNumber("Par"));
                            //xInt = (Integer) par.get(i);

                            birdies.add(i, parseObjects.get(i).getNumber("Birdies"));
                            scores.add(i, parseObjects.get(i).getNumber("Score"));
                            fairways.add(i, parseObjects.get(i).getNumber("Fairways"));
                            putts.add(i, parseObjects.get(i).getNumber("Putts"));
                            greens.add(i, parseObjects.get(i).getNumber("GIRs"));
                            eagles.add(i, parseObjects.get(i).getNumber("Eagles"));
                            pars.add(i, parseObjects.get(i).getNumber("Pars"));
                            bogeys.add(i, parseObjects.get(i).getNumber("Bogeys"));
                            uds.add(i, parseObjects.get(i).getNumber("UpDown"));

                            //Up Down Total
                            udInt[i] = (Integer) uds.get(i);
                            udTotalB += udInt[i];

                            //Birdie total
                            birdInt[i] = (Integer) birdies.get(i);
                            birdTotalB += birdInt[i];

                            //Fairway total
                            fairwayInt[i] = (Integer) fairways.get(i);
                            fairwayTotalB += fairwayInt[i];

                            //Score total
                            scoreInt[i] = (Integer) scores.get(i);
                            scoreTotalB += scoreInt[i];

                            //Putt total
                            puttInt[i] = (Integer) putts.get(i);
                            puttTotalB += puttInt[i];

                            //Green total
                            greenInt[i] = (Integer) greens.get(i);
                            greenTotalB += greenInt[i];

                            //Par total
                            parInt[i] = (Integer) pars.get(i);
                            parTotalB += parInt[i];

                            //Bogey total
                            bogInt[i] = (Integer) bogeys.get(i);
                            bogTotalB += bogInt[i];

                            //Eagle total
                            eagleInt[i] = (Integer) eagles.get(i);
                            eagleTotalB += eagleInt[i];

                            toParInt[i] = (Integer) par.get(i);
                            theParTotalB += toParInt[i];
                        }

                        Log.d("Birdie Total", "" + birdTotalB);
                        Log.d("Putt Total", "" + puttTotalB);

                        setText();

                    } else {
                        Log.d("ERROR:", "" + e.getMessage());
                    }
                }
            });
        }

        else {
            Toast.makeText(getApplicationContext(), "Sorry, please try again",
                    Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }
    }

    public void callQuery () {
        if(!ParseUser.getCurrentUser().getEmail().equals("")){
            ParseQuery<ParseObject> query  =
                    new ParseQuery<ParseObject>("RoundStats");
            query.whereGreaterThan("Par", 60);
            query.whereEqualTo("roundID", ParseUser.getCurrentUser().getEmail());
            query.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(final List<ParseObject> parseObjects, com.parse.ParseException e) {
                    if (e == null) {

                        numOfRounds = parseObjects.size();
                        Log.d("THE numofrounds", "" + numOfRounds);

                        for (int i = 0; i < parseObjects.size(); i++) {

                            par.add(i, parseObjects.get(i).getNumber("Par"));
                            xInt = (Integer) par.get(i);

                            birdies.add(i, parseObjects.get(i).getNumber("Birdies"));
                            scores.add(i, parseObjects.get(i).getNumber("Score"));
                            fairways.add(i, parseObjects.get(i).getNumber("Fairways"));
                            putts.add(i, parseObjects.get(i).getNumber("Putts"));
                            greens.add(i, parseObjects.get(i).getNumber("GIRs"));
                            eagles.add(i, parseObjects.get(i).getNumber("Eagles"));
                            pars.add(i, parseObjects.get(i).getNumber("Pars"));
                            bogeys.add(i, parseObjects.get(i).getNumber("Bogeys"));
                            uds.add(i, parseObjects.get(i).getNumber("UpDown"));

                            //Up Down Total
                            udInt[i] = (Integer) uds.get(i);
                            udTotal += udInt[i];

                            //Birdie total
                            birdInt[i] = (Integer) birdies.get(i);
                            birdTotal += birdInt[i];

                            //Fairway total
                            fairwayInt[i] = (Integer) fairways.get(i);
                            fairwayTotal += fairwayInt[i];

                            //Score total
                            scoreInt[i] = (Integer) scores.get(i);
                            scoreTotal += scoreInt[i];

                            //Putt total
                            puttInt[i] = (Integer) putts.get(i);
                            puttTotal += puttInt[i];

                            //Green total
                            greenInt[i] = (Integer) greens.get(i);
                            greenTotal += greenInt[i];

                            //Par total
                            parInt[i] = (Integer) pars.get(i);
                            parTotal += parInt[i];

                            //Bogey total
                            bogInt[i] = (Integer) bogeys.get(i);
                            bogTotal += bogInt[i];

                            //Eagle total
                            eagleInt[i] = (Integer) eagles.get(i);
                            eagleTotal += eagleInt[i];

                            toParInt[i] = (Integer) par.get(i);
                            theParTotal += toParInt[i];
                        }

                        Log.d("Birdie Total", "" + birdTotal);
                        Log.d("Putt Total", "" + puttTotal);

                        setText();


                    } else {
                        Log.d("ERROR:", "" + e.getMessage());
                    }
                }
            });
        }
        else {
            Toast.makeText(getApplicationContext(), "Sorry, please try again",
                    Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }

    }

    public void setText() {
        ltRounds.setText((String.format("%d", numOfRounds)));

        ltBirdies.setText(String.format("%.2f", calculateAverages(birdTotal)));

        ltPutts.setText(String.format("%.2f", calculateAverages(puttTotal)));

        ltGreens.setText(String.format("%.2f", calculateAverages(greenTotal)));

        ltFairways.setText(String.format("%.2f", calculateAverages(fairwayTotal)));

        ltScore.setText(String.format("%.2f", calculateAverages(scoreTotal)));

        ltEagles.setText(String.format("%.2f", calculateAverages(eagleTotal)));

        ltBogeys.setText(String.format("%.2f", calculateAverages(bogTotal)));

        ltPars.setText(String.format("%.2f", calculateAverages(parTotal)));

        ltScoreToPar.setText(String.format("%.2f", calculateAverages(scoreTotal - theParTotal)));

        ltUpDown.setText(String.format("%.2f", calculateAverages(udTotal)));
    }

    public void setTextB() {
        ltRounds.setText((String.format("%d", numOfRoundsB)));

        ltBirdies.setText(String.format("%.2f", calculateAveragesB(birdTotalB)));

        ltPutts.setText(String.format("%.2f", calculateAveragesB(puttTotalB)));

        ltGreens.setText(String.format("%.2f", calculateAveragesB(greenTotalB)));

        ltFairways.setText(String.format("%.2f", calculateAveragesB(fairwayTotalB)));

        ltScore.setText(String.format("%.2f", calculateAveragesB(scoreTotalB)));

        ltEagles.setText(String.format("%.2f", calculateAveragesB(eagleTotalB)));

        ltBogeys.setText(String.format("%.2f", calculateAveragesB(bogTotalB)));

        ltPars.setText(String.format("%.2f", calculateAveragesB(parTotalB)));

        ltScoreToPar.setText(String.format("%.2f", calculateAveragesB(scoreTotalB - theParTotalB)));

        ltUpDown.setText(String.format("%.2f", calculateAveragesB(udTotalB)));
    }

    public float calculateAverages(int x) {

        float f, nR;

        nR = (float) numOfRounds;
        f = x / nR;

        Log.d("Calc Avg Val:", "" + f);

        if(Float.isNaN(f)) {
            return 0.0f;
        }

        return f;

    }
    public float calculateAveragesB(int x) {

        float f, nR;

        nR = (float) numOfRoundsB;
        f = x / nR;

        Log.d("Calc Avg Val:", "" + f);

        if(Float.isNaN(f)) {
            return 0.0f;
        }

        return f;

    }

    public void totalCalc(int[] x, List y, int z) {

        for(int i = 0; i < numOfRounds; i++) {
            x[i] = (Integer) y.get(i);
            z += x[i];
        }
    }


}