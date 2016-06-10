package com.ryancase.golf.Holes;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.ryancase.golf.Helpers.ArrayValues;


public class Hole1 extends Holes {

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

        int z = getIntent().getExtras().getInt("h1");
        boolean f = getIntent().getExtras().getBoolean("flag");

        Log.d("Co", ArrayValues.course);
        Log.d("h1", "" + z);


        setParMethod(f, z);
    }

    public void setParMethod(boolean b, int val) {
        if(b) {
            nptp.setEnabled(false);
            if (val == 4)
                nptp.setValue(1);
            else if (val == 3)
                nptp.setValue(0);
            else if (val == 5)
                nptp.setValue(2);
            else if (val == 10)
                nptp.setEnabled(true);

            Log.d("PossibleFlag", "" + true);
        }
        else
            Log.d("PossibleFlag: ", "" + false);
    }

    @Override
    public void numPuttCheck() {
        super.numPuttCheck();
        if(numPutts == 0) {
            Toast.makeText(getApplicationContext(), "Nice Hole Out!",
                    Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getApplicationContext(), Hole2.class));
        }
        else
            startActivity(new Intent(getApplicationContext(), Hole2.class));
    }

    @Override
    public void addLocalThings() {
        ArrayValues.addTheThings(girInt, score, fwInt, udInt, numPutts, par, parseHole(this.getClass().getSimpleName()) - 1);
    }

}





