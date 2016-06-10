package com.ryancase.golf.Holes;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.ryancase.golf.Helpers.ArrayValues;
import com.ryancase.golf.NineReview;


public class Hole9_ extends Holes {

    //DISABLE BACK BUTTON//
    @Override
    public void onBackPressed() {
        // super.onBackPressed(); // Comment this super call to avoid calling finish()
    }

    //OVERRIDE THE THINGS//
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitleText(parseHole(this.getClass().getSimpleName()));

        setParMethod(8, ArrayValues.getFlag());
    }

    @Override
    public void numPuttCheck() {
        super.numPuttCheck();
        if(numPutts == 0) {
            Toast.makeText(getApplicationContext(), "Nice Hole Out!",
                    Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getApplicationContext(), NineReview.class));
        }
        else
            startActivity(new Intent(getApplicationContext(), NineReview.class));
    }

    @Override
    public void addLocalThings() {
        ArrayValues.addTheThings(girInt, score, fwInt, udInt, numPutts, par, thisHole - 1);
    }
}





