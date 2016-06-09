package com.ryancase.golf;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;


public class Hole2V2 extends Holes {

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

        Log.d("Intlist size:", "" + ArrayValues.intList.size());

        Log.d("Flagtwo", "" + ArrayValues.getFlag());

        setParMethod(1, ArrayValues.getFlag());
    }

    @Override
    public void numPuttCheck() {
        super.numPuttCheck();
        if(numPutts == 0) {
            Toast.makeText(getApplicationContext(), "Nice Hole Out!",
                    Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getApplicationContext(), Hole3V2.class));
        }
        else
            startActivity(new Intent(getApplicationContext(), Hole3V2.class));
    }

    @Override
    public void addLocalThings() {
        ArrayValues.addTheThings(girInt, score, fwInt, udInt, numPutts, par, parseHole(this.getClass().getSimpleName()) - 1);
    }

}





