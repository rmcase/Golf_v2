package com.ryancase.golf.Holes;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.ryancase.golf.Helpers.ArrayValues;
import com.ryancase.golf.roundFinish;


public class Hole18 extends Holes {

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

        setParMethod(17, ArrayValues.getFlag());
    }

    @Override
    public void numPuttCheck() {
        super.numPuttCheck();
        if(numPutts == 0) {
            Toast.makeText(getApplicationContext(), "Nice Hole Out!",
                    Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getApplicationContext(), roundFinish.class));
        }
        else
            startActivity(new Intent(getApplicationContext(), roundFinish.class));
    }

    @Override
    public void addLocalThings() {
        ArrayValues.addTheThings(girInt, score, fwInt, udInt, numPutts, par, parseHole(this.getClass().getSimpleName()) - 1);
    }

}




