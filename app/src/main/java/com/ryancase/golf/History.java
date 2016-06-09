package com.ryancase.golf;

/**
 * Created by ryancase on 11/19/15.
 */

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class History extends Activity {

    ListView roundList;
    //ImageButton home;

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history);

        //home = (ImageButton) findViewById(R.id.historyHomeButton);

        roundList = (ListView) findViewById(R.id.roundList);

        final List values = new ArrayList();
        final List dates = new ArrayList();
        final List nine = new ArrayList();

        EmptyViewSet();

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.custom_list_layout, dates);
        roundList.setAdapter(adapter);

        if(ParseUser.getCurrentUser().getEmail() != null) {
            ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("RoundStats");
            query.whereEqualTo("roundID", ParseUser.getCurrentUser().getEmail());
            query.orderByDescending("createdAt");
            query.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> parseObjects, com.parse.ParseException e) {
                    if (e == null) {

                        Log.d("THE OBJECT", "" + parseObjects.size());
                        //objIds = new String[parseObjects.size()];

                    /*for(int i = 0; i < parseObjects.size(); i++) {
                        objIds[i] = parseObjects.get(i).getObjectId();

                    }
                    one.setText(objIds[0]);*/
                        SimpleDateFormat formatter = new SimpleDateFormat("M/d/yyyy");

                        for (int i = 0; i < parseObjects.size(); i++) {
                            boolean x = parseObjects.get(i).getBoolean("nineTag");
                            values.add(i, parseObjects.get(i).getObjectId());
                            nine.add(i, x);
                            if(x)
                                dates.add(i, "   " + parseObjects.get(i).getString("Course") + "(9)"+ "  \t" + formatter.format(parseObjects.get(i).getCreatedAt()));
                            else
                                dates.add(i, "   " + parseObjects.get(i).getString("Course") + " \t" + formatter.format(parseObjects.get(i).getCreatedAt()));
                        }

                        adapter.notifyDataSetChanged();


                    } else {
                        Log.d("ERROR:", "" + e.getMessage());
                    }
                }
            });
        }
        else {
            Toast.makeText(getApplicationContext(), "Sorry, please try again",
                    Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getApplicationContext(), History.class));
        }


        roundList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // When clicked, show a toast with the TextView text
                Intent intent = new Intent(History.this, TabHolder.class);
                Bundle b = new Bundle();
                b.putString("obid", values.get(position).toString());//Your id
                b.putBoolean("nine", (boolean) nine.get(position));
                intent.putExtras(b); //Put your id to your next Intent
                startActivity(intent);
                finish();
            }
        });

        roundList.setLongClickable(true);
        roundList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            public boolean onItemLongClick(final AdapterView<?> parent, View view, final int position, long id) {
                // When clicked, show a toast with the TextView text
                AlertDialog.Builder b =  new  AlertDialog.Builder(History.this)
                        .setTitle("Delete this round?")
                        .setPositiveButton("Yes",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int whichButton) {
                                        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("RoundStats");
                                        query.whereEqualTo("roundID", ParseUser.getCurrentUser().getEmail());
                                        query.findInBackground(new FindCallback<ParseObject>() {
                                            @Override
                                            public void done(List<ParseObject> objects, ParseException e) {
                                                if(e == null) {
                                                    try {
                                                        objects.get(position).delete();
                                                        values.remove(position);
                                                        dates.remove(position);
                                                    } catch (ParseException e1) {
                                                        e1.printStackTrace();
                                                    }
                                                }
                                                else
                                                    e.printStackTrace();

                                                adapter.notifyDataSetChanged();
                                            }
                                        });
                                    }
                                }
                        )
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int whichButton) {
                                        dialog.dismiss();
                                    }
                                }
                        );
                b.show();

                return true;
            }
        });

        //HomeButtonClick();

    }

    public void EmptyViewSet() {
        View empty = getLayoutInflater().inflate(R.layout.empty_view, null, false);
        addContentView(empty, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        roundList.setEmptyView(empty);
    }

    public void HomeButtonClick() {
        View.OnClickListener submitListener =new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(History.this, MainActivity.class);
                startActivity(intent);
            }
        };
       // home.setOnClickListener(submitListener);
    }

    /*public void onClick(View view) {
        // we're going to add directly to the adapter, so declare it within the method
        ArrayAdapter<String> adapter = (ArrayAdapter<String>) getListAdapter();
        String item;
        //as with the adapter, we're going to add to the List, so declare it
        List myList = new ArrayList();
        switch (view.getId()) {
            case R.id.addItem:
                item = et.getText().toString();
                myList.add(item);
                adapter.add(item);
                et.setText("");
                break;
            case R.id.deleteItem:
                if (getListAdapter().getCount() > 0) {
                    // remove the last item added to the list
                    item = (String) getListAdapter().getItem(getListAdapter().getCount()-1);
                    myList.remove(item);
                    adapter.remove(item);
                }
                break;
        }
        adapter.notifyDataSetChanged();
    }*/



}
