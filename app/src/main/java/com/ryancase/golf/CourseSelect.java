package com.ryancase.golf;

/**
 * Created by ryancase on 11/19/15.
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.ryancase.golf.Helpers.ArrayValues;
import com.ryancase.golf.Holes.Hole1;

import java.util.ArrayList;
import java.util.List;

public class CourseSelect extends Activity {

    ListView courseList;
    Button courseSubmitButton;
    EditText cName, cSlope, cRating;

    //HoleOneValues hVal;

    //final List values = new ArrayList();
    final List courses = new ArrayList();
    //final List dateCourse = new ArrayList();

    //ArrayList<Number> parList;

    static boolean parBool;
    static int h1;
    Bundle b;

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.course_select);

        cName = (EditText) findViewById(R.id.courseEditText);
        cSlope = (EditText) findViewById(R.id.slopeEditText);
        cRating = (EditText) findViewById(R.id.ratingEditText);
        courseList = (ListView) findViewById(R.id.courseList);
        courseSubmitButton = (Button) findViewById(R.id.courseSubmit);

        //InitEmptyView();
        //ArrayValues.possibleFlag = true;
        //SubmitNewCourse();
        ArrayValues.intList = new ArrayList<>();

        //-----POPULATING LIST VIEW-----//
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.custom_list_layout, courses);
        courseList.setAdapter(adapter);

        //-----------QUERY ALL USER ROUNDS------------//
        if(ParseUser.getCurrentUser().getEmail() != null) {
            ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("RoundStats");
            query.whereEqualTo("roundID", ParseUser.getCurrentUser().getEmail());
            query.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> parseObjects, ParseException e) {
                    if (e == null) {

                        Log.d("# of RoundStats Objects", "" + parseObjects.size());

                        for (int i = 0; i < parseObjects.size(); i++) {
                            //values.add(i, parseObjects.get(i).getObjectId());
                            if (courses.contains(parseObjects.get(i).getString("Course"))){
                                //ParQuery(parseObjects.get(i).getString("Course"));
                            }
                            else {
                                courses.add(parseObjects.get(i).getString("Course"));
                            }
                        }
                        adapter.notifyDataSetChanged();


                    } else {
                        Log.d("ERROR:", "" + e.getMessage());
                    }
                }
            });
            //--------END QUERY-------//
        }
        else {
            Toast.makeText(getApplicationContext(), "Sorry, please try again",
                    Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getApplicationContext(), CourseSelect.class));
        }
        //-----END POPULATING LIST VIEW-----//


        //-----SUBMIT LISTENER-----//
        View.OnClickListener submitListener =new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(getApplicationContext(), MainActivity.class));
                try {
                    if (!cSlope.getText().toString().isEmpty() && !cRating.getText().toString().isEmpty()
                            && !cName.getText().toString().isEmpty()) {

                        ArrayValues.slope = Integer.parseInt(cSlope.getText().toString());

                        ArrayValues.rating = Float.parseFloat(cRating.getText().toString());

                        for (int i = 0; i < courses.size(); i++) {
                            if (cName.getText().toString().equals(courses.get(i))) {
                                Toast.makeText(getApplicationContext(), "Course already exists",
                                        Toast.LENGTH_SHORT).show();
                                ClearEditTexts();
                                break;
                            } else {
                                courses.add(cName.getText().toString());
                                adapter.notifyDataSetChanged();
                                ClearEditTexts();
                                break;
                            }
                        }
                        if (courses.size() == 0) {
                            courses.add(cName.getText().toString());
                            adapter.notifyDataSetChanged();
                        }

                    } else {
                        Toast.makeText(getApplicationContext(), "Please fill out all fields",
                                Toast.LENGTH_SHORT).show();
                        ClearEditTexts();
                    }
                }catch (NumberFormatException e) {
                    Toast.makeText(getApplicationContext(), "Please enter a valid slope",
                            Toast.LENGTH_SHORT).show();
                    ClearEditTexts();
                }



            }
        };
        courseSubmitButton.setOnClickListener(submitListener);
        //-----END SUBMIT LISTENER-----//

        //-----COURSELIST CLICKABLES-----//
        courseList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                final String[] numbers = {"One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine",
                        "Ten", "Eleven", "Twelve", "Thirteen", "Fourteen", "Fifteen", "Sixteen", "Seventeen", "Eighteen"};

                //Log.d("PF", "" + ArrayValues.possibleFlag);
                try {
                    ParQuery(courses.get(position).toString());
                }catch (NullPointerException e) {
                    Log.d("Error", "" + e);
                }
                //Log.d("PFA", "" + ArrayValues.possibleFlag);
                //------------------------------------------------
                Log.d("h1", "" + h1);
                Log.d("parBool", "" + parBool);
                Log.d("setFlag", "" + ArrayValues.getFlag());
                //-------------------------------------------------
                //Intent intent = new Intent(CourseSelect.this, Hole1.class);

                ArrayValues.course = courses.get(position).toString();
                //ArrayValues.setCourse(courses.get(position).toString());
                //ArrayValues.possibleFlag = parBool;

                //startActivity(intent);
                //startActivity(theOne);
            }
        });
        //-----END COURSELIST CLICKABLES-----//

    }

    public void ClearEditTexts() {
        cName.setText("");
        cSlope.setText("");
        cRating.setText("");
    }

    public void InitEmptyView() {
        View empty = getLayoutInflater().inflate(R.layout.empty_view, null, false);
        addContentView(empty, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        courseList.setEmptyView(empty);
    }

    public void ParQuery(String s) {
        final String[] numbers = {"One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine",
                "Ten", "Eleven", "Twelve", "Thirteen", "Fourteen", "Fifteen", "Sixteen", "Seventeen", "Eighteen"};

        final String st = s;

        //––––––––––––––––––––––––––––––––––––––––––––––––––––––//

        //Query test to return objects with matching roundID to current user
        //and matching course to course that was clicked in the roundList
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Test");
        query.whereEqualTo("roundID", ParseUser.getCurrentUser().getEmail());
        query.whereEqualTo("Course", s);
        query.findInBackground(new FindCallback<ParseObject>() {
            public boolean innerBool;
            int x;
            String co = st;
            Intent t;

            @Override
            public void done(List<ParseObject> parseObjects, ParseException e) { //Returns all objects in test with matching round id and course name
                if (e == null) {

                    t = new Intent(CourseSelect.this, Hole1.class);

                    if (parseObjects.size() == 1) { //If an object is returned

                        Log.d("# of Rounds matching", "" + parseObjects.size());


                        Log.d("Object ", "" + parseObjects.get(0).getString("Course"));

                        for (int z = 0; z < numbers.length; z++) { //assign the par values to intlist
                            ArrayValues.intList.add(z, parseObjects.get(0).getNumber(numbers[z]));
                            x = (Integer) parseObjects.get(0).getNumber("One");
                        }
                        Log.d("IntList: ", "" + ArrayValues.intList.isEmpty());


                        /*for (int i = 0; i < parseObjects.size(); i++) { //Iterate to the object

                            Log.d("Object at ", i + ": " + parseObjects.get(i).getString("Course"));

                            for (int z = 0; z < numbers.length; z++) { //assign the par values to intlist
                                ArrayValues.intList.add(z, parseObjects.get(i).getNumber(numbers[z]));
                            }
                            Log.d("IntList: ", "" + ArrayValues.intList.isEmpty());
                            //}
                        }*/

                        ArrayValues.setFlag(true);
                        innerBool = true;

                    } else { //More than one object or no objects

                        ArrayValues.setFlag(false);
                        innerBool = false;
                    }

                    Log.d("x Done", "" + x);

                    t.putExtra("h1", x);
                    t.putExtra("flag", innerBool);

                } else {
                    Log.d("ERROR:", "" + e.getMessage());
                }

                ArrayValues.course = co;
                startActivity(t);
            }
        });
    }



}
