package com.ryancase.golf;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

/**
 * Created by ryancase on 11/25/15.
 */
public class nineHoleTabBack extends Activity {

    TextView scoreLC, puttLC, girLC, fwLC, bogeyLC, parLC, parsLC, birdiesLC, eagleLC, otherLC, udLC;
    String s;
    Bundle b;
    Button returnButton;

    @Override
    public void onBackPressed() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_view_click);

        scoreLC = (TextView) findViewById(R.id.historyScore);
        puttLC = (TextView) findViewById(R.id.historyPutt);
        fwLC = (TextView) findViewById(R.id.historyFW);
        girLC = (TextView) findViewById(R.id.historyGIR);
        bogeyLC = (TextView) findViewById(R.id.historyBogeys);
        parLC = (TextView) findViewById(R.id.historyThePar);
        parsLC = (TextView) findViewById(R.id.historyPars);
        eagleLC = (TextView) findViewById(R.id.historyEagles);
        birdiesLC = (TextView) findViewById(R.id.historyBirdies);
        otherLC = (TextView) findViewById(R.id.historyOther);
        udLC = (TextView) findViewById(R.id.historyUpDown);

        returnButton = (Button) findViewById(R.id.returnToRoundList);

        b = getIntent().getExtras();
        s = b.getString("obid");

        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("RoundStats");
        query.whereEqualTo("objectId", s);
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    //List contain object with specific user id.
                    for (int i = 0; i < objects.size(); i++) {
                        scoreLC.setText(objects.get(i).getNumber("Scoreb9").toString());
                        puttLC.setText(objects.get(i).getNumber("Puttsb9").toString());
                        fwLC.setText(objects.get(i).getNumber("Fairwaysb9").toString());
                        girLC.setText(objects.get(i).getNumber("GIRsb9").toString());
                        birdiesLC.setText(objects.get(i).getNumber("Birdiesb9").toString());
                        parLC.setText(objects.get(i).getNumber("Parb9").toString());
                        parsLC.setText(objects.get(i).getNumber("Parsb9").toString());
                        bogeyLC.setText(objects.get(i).getNumber("Bogeysb9").toString());
                        eagleLC.setText(objects.get(i).getNumber("Eaglesb9").toString());
                        otherLC.setText(objects.get(i).getNumber("Otherb9").toString());
                        udLC.setText(objects.get(i).getNumber("UpDownb9").toString());


                    }
                } else {
                    // error
                }
            }
        });


        View.OnClickListener retListener =new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), History.class));
            }
        };
        returnButton.setOnClickListener(retListener);

    }

}