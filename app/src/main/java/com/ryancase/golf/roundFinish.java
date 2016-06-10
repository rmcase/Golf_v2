package com.ryancase.golf;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.ryancase.golf.Helpers.ArrayValues;
import com.ryancase.golf.Holes.Hole18;

import java.util.List;

/**
 * Created by ryancase on 11/17/15.
 */
public class roundFinish extends FragmentActivity {
    TextView fairwaySt, girSt, puttSt, scoreSt, parSt, birdieSt, bogeySt, thePar, other, eagleSt, ud;
    int fwCount = 0, udCount = 0, girCount = 0, puttCount = 0, score = 0, par = 0,
            birdies = 0, pars = 0, bogeys = 0, otherC = 0, eagles = 0;

    int fwCountf9 = 0, udCountf9 = 0, girCountf9 = 0, puttCountf9 = 0, scoref9 = 0, parf9 = 0,
            birdiesf9 = 0, parsf9 = 0, bogeysf9 = 0, otherCf9 = 0, eaglesf9 = 0;

    int fwCountb9 = 0, udCountb9 = 0, girCountb9 = 0, puttCountb9 = 0, scoreb9 = 0, parb9 = 0,
            birdiesb9 = 0, parsb9 = 0, bogeysb9 = 0, otherCb9 = 0, eaglesb9 = 0;

    String a, b, c, d, e, f, g, h, i, j, k;
    Button end;

    boolean testFlag;
    int[]aPar;
    ParseObject object, test;

    @Override
    public void onBackPressed() {
        // super.onBackPressed(); // Comment this super call to avoid calling finish()
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.round_finish);

        object = new ParseObject("RoundStats");
        test = new ParseObject("Test");

        aPar = new int[18];

        fairwaySt = (TextView) findViewById(R.id.fwStat);
        girSt = (TextView) findViewById(R.id.girStat);
        puttSt = (TextView) findViewById(R.id.puttStat);
        scoreSt = (TextView) findViewById(R.id.scoreStat);
        parSt = (TextView) findViewById(R.id.pars);
        birdieSt = (TextView) findViewById(R.id.birdies);
        bogeySt = (TextView) findViewById(R.id.bogeys);
        thePar = (TextView) findViewById(R.id.thePar);
        other = (TextView) findViewById(R.id.other);
        eagleSt = (TextView) findViewById(R.id.eagles);
        ud = (TextView) findViewById(R.id.udStat);

        for(int i = 0; i < 18; i++)
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

            score += ArrayValues.scores[i];
            puttCount += ArrayValues.putts[i];
            par+= aPar[i];
        }

        //FRONT NINE VARIABLE ASSIGNMENT
        for(int i = 0; i < 9; i++) {
            scoref9 += ArrayValues.scores[i];
            puttCountf9 += ArrayValues.putts[i];
            parf9 += aPar[i];

            if(ArrayValues.fairways[i] == 1) {
                fwCountf9++;
            }
            if(ArrayValues.girs[i] == 1) {
                girCountf9++;
            }
            if(ArrayValues.uds[i] == 1) {
                udCountf9++;
            }
            if((ArrayValues.par[i] + 3) == ArrayValues.scores[i]) {
                parsf9++;
            }
            if(ArrayValues.scores[i] - (ArrayValues.par[i] + 3) == 1) {
                bogeysf9++;
            }
            if((ArrayValues.scores[i] - (ArrayValues.par[i] + 3) > 1) || (ArrayValues.scores[i] - (ArrayValues.par[i] + 3) < -2)) {
                otherCf9++;
            }
            if(ArrayValues.scores[i] - (ArrayValues.par[i] + 3) == -1) {
                birdiesf9++;
            }
            if(ArrayValues.scores[i] - (ArrayValues.par[i] + 3) == -2) {
                eaglesf9++;
            }
        }

        //BACK NINE VARIABLE ASSIGNMENT
        for(int i = 9; i < 18; i++) {
            scoreb9 += ArrayValues.scores[i];
            puttCountb9 += ArrayValues.putts[i];
            parb9 += aPar[i];

            if(ArrayValues.fairways[i] == 1) {
                fwCountb9++;
            }
            if(ArrayValues.girs[i] == 1) {
                girCountb9++;
            }
            if(ArrayValues.uds[i] == 1) {
                udCountb9++;
            }
            if((ArrayValues.par[i] + 3) == ArrayValues.scores[i]) {
                parsb9++;
            }
            if(ArrayValues.scores[i] - (ArrayValues.par[i] + 3) == 1) {
                bogeysb9++;
            }
            if((ArrayValues.scores[i] - (ArrayValues.par[i] + 3) > 1) || (ArrayValues.scores[i] - (ArrayValues.par[i] + 3) < -2)) {
                otherCb9++;
            }
            if(ArrayValues.scores[i] - (ArrayValues.par[i] + 3) == -1) {
                birdiesb9++;
            }
            if(ArrayValues.scores[i] - (ArrayValues.par[i] + 3) == -2) {
                eaglesb9++;
            }
        }

        if(ParseUser.getCurrentUser().getEmail() != null) {
            object.put("roundID", ParseUser.getCurrentUser().getEmail());
            test.put("roundID", ParseUser.getCurrentUser().getEmail());
        }
        else {
            Toast.makeText(getApplicationContext(), "Sorry, please try saving again",
                    Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getApplicationContext(), Hole18.class));
        }

        String[] numbers = {"One","Two","Three","Four","Five","Six","Seven","Eight","Nine",
            "Ten","Eleven","Twelve","Thirteen","Fourteen","Fifteen","Sixteen","Seventeen","Eighteen"};

        //assigning the current par values to test object
        for(int i = 0; i < numbers.length; i++)
            test.put(numbers[i], aPar[i]);

        //test.put("ParArray", Arrays.asList(aPar));
        test.put("Course", ArrayValues.course);

        object.put("Course", ArrayValues.course);
        object.put("Rating", ArrayValues.rating);
        object.put("Slope", ArrayValues.slope);
        object.put("nineTag", false);
        //18 HOLES
        object.put("Score", score);
        object.put("Putts", puttCount);
        object.put("GIRs", girCount);
        object.put("Fairways", fwCount);
        object.put("Par", par);
        object.put("Eagles", eagles);
        object.put("Birdies", birdies);
        object.put("Pars", pars);
        object.put("Bogeys", bogeys);
        object.put("Other", otherC);
        object.put("UpDown", udCount);

        //FRONT NINE
        object.put("Scoref9", scoref9);
        object.put("Puttsf9", puttCountf9);
        object.put("GIRsf9", girCountf9);
        object.put("Fairwaysf9", fwCountf9);
        object.put("Parf9", parf9);
        object.put("Eaglesf9", eaglesf9);
        object.put("Birdiesf9", birdiesf9);
        object.put("Parsf9", parsf9);
        object.put("Bogeysf9", bogeysf9);
        object.put("Otherf9", otherCf9);
        object.put("UpDownf9", udCountf9);

        //BACK NINE
        object.put("Scoreb9", scoreb9);
        object.put("Puttsb9", puttCountb9);
        object.put("GIRsb9", girCountb9);
        object.put("Fairwaysb9", fwCountb9);
        object.put("Parb9", parb9);
        object.put("Eaglesb9", eaglesb9);
        object.put("Birdiesb9", birdiesb9);
        object.put("Parsb9", parsb9);
        object.put("Bogeysb9", bogeysb9);
        object.put("Otherb9", otherCb9);
        object.put("UpDownb9", udCountb9);

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


        end = (Button) findViewById(R.id.returnToMainFromRF);

        View.OnClickListener listnerE =new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ArrayValues.objectArray.add(object);


                object.saveInBackground();

                //Only store the object if it is a unique course
                Log.d("CheckTest Course", ArrayValues.course);

                checkTest(ArrayValues.course);


                startActivity(new Intent(getApplicationContext(), MainActivity.class));

            }
        };
        end.setOnClickListener(listnerE);

    }

    public void checkTest(String s) {
        //Checking if Test already has an object with the same course name
        Log.d("User", ParseUser.getCurrentUser().getEmail());

        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Test");
        query.whereEqualTo("roundID", ParseUser.getCurrentUser().getEmail());
        query.whereEqualTo("Course", s); //Querying test for objects with course name matching the current course
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> parseObjects, ParseException e) {
                //boolean flag = false;
                String[] numbers = {"One","Two","Three","Four","Five","Six","Seven","Eight","Nine",
                        "Ten","Eleven","Twelve","Thirteen","Fourteen","Fifteen","Sixteen","Seventeen","Eighteen"};
                if (e == null) {
                    if (parseObjects.size() != 0) {//if an object is returned there is already an object with par values
                        testFlag = false;
                        boolean x = false;

                        //if the round that matches contains a 10 its a nine hole round and we can overwrite
                        for(int i = 0; i < numbers.length; i++)
                            if((Integer) parseObjects.get(0).getNumber(numbers[i]) == 10)
                                x = true;

                        if(x) {
                            //Delete old object replace with new 18 hole object
                            try {
                                parseObjects.get(0).delete();
                                parseObjects.get(0).saveInBackground();
                            }catch (ParseException h) {

                            }

                            test.saveInBackground();
                        }


                        Log.d("CheckTest", ""+false);
                    }
                    else {
                        test.saveInBackground();
                        Log.d("CheckTest", ""+true);
                    }

                } else {
                    Log.d("ERROR:", "" + e.getMessage());
                }
            }
        });
    }
}