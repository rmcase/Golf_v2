package com.ryancase.golf;

/**
 * Created by ryancase on 12/1/15.
 */

import android.app.TabActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TabHost;

public class TabHolder extends TabActivity {

    Bundle b;
    boolean s;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tabs);

        b = getIntent().getExtras();
        s = b.getBoolean("nine");

        TabHost tabHost = getTabHost();

        tabHost.getTabWidget().setDividerDrawable(R.drawable.tabindicator);
        // Tab for Photos
        TabHost.TabSpec frontNineSpec = tabHost.newTabSpec("Front Nine");
        // setting Title and Icon for the Tab
        frontNineSpec.setIndicator("Front Nine");
        Intent photosIntent = new Intent(this, nineHoleTab.class);
        photosIntent.putExtras(b);
        frontNineSpec.setContent(photosIntent);

        // Tab for Songs
        TabHost.TabSpec backNineSpec = tabHost.newTabSpec("Back Nine");
        backNineSpec.setIndicator("Back Nine");
        Intent songsIntent = new Intent(this, nineHoleTabBack.class);
        songsIntent.putExtras(b);
        backNineSpec.setContent(songsIntent);

        // Tab for Videos
        TabHost.TabSpec fullRoundSpec = tabHost.newTabSpec("Full Round");
            fullRoundSpec.setIndicator("Full Round");

        Intent videosIntent = new Intent(this, ListViewClick.class);
        videosIntent.putExtras(b);
        fullRoundSpec.setContent(videosIntent);

        // Adding all TabSpec to TabHost
        if(s) {
            fullRoundSpec.setIndicator("Nine Hole Round");
            tabHost.addTab(fullRoundSpec);
        }
        else {
            tabHost.addTab(fullRoundSpec);
            tabHost.addTab(frontNineSpec);
            tabHost.addTab(backNineSpec);
        }
    }
}