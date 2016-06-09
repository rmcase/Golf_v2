package com.ryancase.golf;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

/**
 * Created by ryancase on 4/18/16.
 */
public class Handicap extends Activity {

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stats);


        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("RoundStats");
        query.whereEqualTo("roundID", ParseUser.getCurrentUser().getEmail());
        query.whereEqualTo("nineTag", false);
        query.orderByDescending("createdAt");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(final List<ParseObject> parseObjects, com.parse.ParseException e) {
                if (e == null) {

                    double handicap;
                    double[] handicaps = new double[2];
                    double total = 0;

                    for (int i = 0; i < parseObjects.size(); i++) {
                        int slope = (Integer) parseObjects.get(i).getNumber("Slope");

                        if (slope == 0)
                            slope = help(parseObjects.get(i).getString("Course"));

                        double rating = (double) parseObjects.get(i).getNumber("Rating");

                        if (rating == 0)
                            rating = helpF(parseObjects.get(i).getString("Course"));


                        int score = (Integer) parseObjects.get(i).getNumber("Score");

                        handicap = ((score - rating) * 113) / slope;
                        handicaps[i] = handicap;
                    }

                    for (int i = 0; i < handicaps.length; i++) {
                        total += handicaps[i];
                    }
                    total = total / handicaps.length;

                    Log.d("handicap", "" + total);
                } else {
                    //dialog handicap cannot be created.
                    Log.d("", "");
                }
            }
            int slop;
            public int help(String s) {
                ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("RoundStats");
                query.whereEqualTo("roundID", ParseUser.getCurrentUser().getEmail());
                query.whereEqualTo("Course", s);
                query.findInBackground(new FindCallback<ParseObject>() {
                    @Override
                    public void done(final List<ParseObject> parseObjects, com.parse.ParseException e) {
                        if (e == null) {

                            for (int i = 0; i < parseObjects.size(); i++) {
                                int r = (Integer) parseObjects.get(i).getNumber("Slope");
                                if (r != 0) {
                                    slop = r;
                                }
                            }
                        }
                    }
                });
                return slop;
            }
            double rat;
            public double helpF(String s) {
                ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("RoundStats");
                query.whereEqualTo("roundID", ParseUser.getCurrentUser().getEmail());
                query.whereEqualTo("Course", s);
                query.findInBackground(new FindCallback<ParseObject>() {
                    @Override
                    public void done(final List<ParseObject> parseObjects, com.parse.ParseException e) {
                        if (e == null) {

                            for (int i = 0; i < parseObjects.size(); i++) {
                                double s = (double) parseObjects.get(i).getNumber("Rating");
                                if (s != 0) {
                                    rat = s;
                                }
                            }
                        }
                    }
                });
                return rat;
            }
        });
    }
}