package com.ryancase.golf;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.*;
import android.widget.Button;

import com.parse.ParseUser;
import com.ryancase.golf.Login.LoginSignupActivity;


public class MainActivity extends ActionBarActivity {

    Button nrButton, hButton, sButton, lButton, help;

    SharedPreferences prefs, mPrefs;


    @Override
    public void onBackPressed()
    {
        // super.onBackPressed(); // Comment this super call to avoid calling finish()
    }

    @Override
    protected void onResume() {
        super.onResume();
        ParseUser.getCurrentUser();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //ArrayValues.currentUser = ParseUser.getCurrentUser();
        addListenerOnButton();
        addListenerOnButtonHistory();
        addListenerOnButtonStats();
        addListenerOnButtonLogout();
        addListenerOnButtonHelp();

        if(ParseUser.getCurrentUser() != null)
            lButton.setText("Logout " + ParseUser.getCurrentUser().getString("name"));
    }

    /*@Override
    protected void onResume() {
        super.onResume();

        if (prefs.getBoolean("firstrun", true)) {
            // Do first run stuff here then set 'firstrun' as false
            ParseLoginBuilder builderr = new ParseLoginBuilder(this);
            startActivityForResult(builderr.build(), 0);


            // using the following line to edit/commit prefs
            prefs.edit().putBoolean("firstrun", false).commit();
        }
    }*/


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void addListenerOnButton() {
        nrButton = (Button) findViewById(R.id.new_round_button);

        final Context context = this;

        nrButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CourseSelect.class);
                startActivity(intent);
            }
        });

    }
    public void addListenerOnButtonHistory() {
        hButton = (Button) findViewById(R.id.history);

        final Context context = this;

        hButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, History.class);
                startActivity(intent);
            }
        });

    }
    public void addListenerOnButtonStats() {
        sButton = (Button) findViewById(R.id.stats);

        final Context context = this;

        sButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Stats.class);
                startActivity(intent);
            }
        });

    }
    public void addListenerOnButtonLogout() {
        lButton = (Button) findViewById(R.id.logout);

        final Context context = this;

        lButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseUser.getCurrentUser().logOutInBackground();
                Intent intent = new Intent(context, LoginSignupActivity.class);
                startActivity(intent);
            }
        });

    }
    public void addListenerOnButtonHelp() {
        help = (Button) findViewById(R.id.help);

        final Context context = this;

        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Help.class);
                startActivity(intent);
            }
        });

    }


}
