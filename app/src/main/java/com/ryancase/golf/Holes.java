package com.ryancase.golf;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;


public class Holes extends FragmentActivity {

    //INSTANTIATE VARS//
    TextViewCust title;
    NumberPicker np, nptp;
    Checkbox p1, p2, p3, p4, fw, gir, ud;
    Button n, finish, exit;
    int numPutts = 0, score = 0, girInt = 0, fwInt = 0, udInt = 0, par = 0;

    //DISABLE BACK BUTTON//
    @Override
    public void onBackPressed()
    {
        // super.onBackPressed(); // Comment this super call to avoid calling finish()
    }

    //DO THE THINGS//
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_round);

        overridePendingTransition(R.anim.abc_slide_in_bottom, R.anim.abc_slide_out_top);
        //getWindow().getAttributes().windowAnimations = R.anim.abc_slide_in_bottom;

        //holeCountInc();

        title = (TextViewCust) findViewById(R.id.Hole1TV);

        ud = (Checkbox) findViewById(R.id.ud_box);
        fw = (Checkbox) findViewById(R.id.fairway_box);
        gir = (Checkbox) findViewById(R.id.gir_box);

        //CHECKBOX LOGIC
        p1 = (Checkbox) findViewById(R.id.putt1);
        p2 = (Checkbox) findViewById(R.id.putt2);
        p3 = (Checkbox) findViewById(R.id.putt3);
        p4 = (Checkbox) findViewById(R.id.putt4);

        //setParMethod();

        setupPuttListeners();

        //NUMBER PICKERS
        nptp = (NumberPicker) findViewById(R.id.scorePickerToPar);
        nptp.setEnabled(true);
        nptp.setMinValue(0);
        nptp.setMaxValue(2);
        nptp.setDisplayedValues(new String[]{"3", "4", "5"});
        nptp.setValue(1);
        nptp.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

        setDividerColor(nptp, Color.YELLOW);

        np = (NumberPicker) findViewById(R.id.scorePicker);
        np.setMinValue(1);
        np.setMaxValue(9);
        np.setValue(4);
        np.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

        setDividerColor(np, Color.YELLOW);


        //NEXT HOLE BUTTON//
        n = (Button) findViewById(R.id.next_button);

        OnClickListener listnr =new OnClickListener() {
            @Override
            public void onClick(View v) {

                getValues();

                //ArrayValues.addTheThings(girInt, score, fwInt, udInt, numPutts, par, 1);

                numPuttCheck();

                addLocalThings();
            }
        };
        n.setOnClickListener(listnr);
        //END NEXT HOLE BUTTON//


        //EXIT ROUND BUTTON CLICK//
        exit = (Button) findViewById(R.id.prev_button);

        OnClickListener exitList =new OnClickListener() {
            @Override
            public void onClick(View v) {
                //ArrayValues.intList.clear();
                finish();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));

            }
        };
        exit.setOnClickListener(exitList);
        //EXIT BUTTON END//
    }

    public void getValues() {
        if(p1.isChecked()) {
            numPutts = 1;
        }
        if(p2.isChecked()) {
            numPutts = 2;
        }
        if(p3.isChecked()) {
            numPutts = 3;
        }
        if(p4.isChecked()) {
            numPutts = 4;
        }
        score = np.getValue();

        getPar();
        //par = nptp.getValue();

        //1 means checked
        girInt = gir.checker();

        fwInt = fw.checker();

        udInt = ud.checker();
    }

    public void getPar() {
        par = nptp.getValue();
    }

    public void setParMethod(int x, boolean b) {
        if(b) {
            nptp.setEnabled(false);
            try {
                if ((Integer) ArrayValues.intList.get(x) == 4)
                    nptp.setValue(1);
                else if ((Integer) ArrayValues.intList.get(x) == 3)
                    nptp.setValue(0);
                else if ((Integer) ArrayValues.intList.get(x) == 5)
                    nptp.setValue(2);
                else if((Integer) ArrayValues.intList.get(x) == 10) {
                    nptp.setEnabled(true);
                }

            }
            catch (IndexOutOfBoundsException e) {
                Log.d("Index out of bounds", "" + true);
            }

            Log.d("PossibleFlag: true", "");
        }
        else
            Log.d("PossibleFlag: false", "");
    }


    public void setupPuttListeners() {
        p1.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                //is chkIos checked?
                if (((CheckBox) v).isChecked()) {
                    //Case 1
                    p2.setChecked(false);
                    p3.setChecked(false);
                    p4.setChecked(false);

                    np.setEnabled(false);
                    nptp.setEnabled(false);

                    if (np.getValue() - (nptp.getValue() + 3) >= 0)
                        gir.setChecked(false);
                    else if(np.getValue() - (nptp.getValue() + 3) <= -1)
                        gir.setChecked(true);

                } else {
                    gir.setChecked(false);
                    np.setEnabled(true);
                    nptp.setEnabled(true);
                    return;
                }

            }
        });

        p2.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                //is chkIos checked?
                if (((CheckBox) v).isChecked()) {
                    //Case 1
                    p1.setChecked(false);
                    p3.setChecked(false);
                    p4.setChecked(false);

                    np.setEnabled(false);
                    nptp.setEnabled(false);

                    if (np.getValue() == (nptp.getValue() + 3) || (np.getValue() - (nptp.getValue()+3) == -1))
                        gir.setChecked(true);
                    else
                        gir.setChecked(false);
                } else {
                    gir.setChecked(false);
                    np.setEnabled(true);
                    nptp.setEnabled(true);
                    return;
                }

            }
        });

        p3.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                //is chkIos checked?
                if (((CheckBox) v).isChecked()) {
                    //Case 1
                    p2.setChecked(false);
                    p1.setChecked(false);
                    p4.setChecked(false);

                    np.setEnabled(false);
                    nptp.setEnabled(false);

                    if (np.getValue() - (nptp.getValue() + 3) <= 0)
                        gir.setChecked(false);
                    else if(np.getValue() - (nptp.getValue() + 3) == 1)
                        gir.setChecked(true);

                } else {
                    //case 2
                    np.setEnabled(true);
                    nptp.setEnabled(true);
                    return;
                }

            }
        });

        p4.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                //is chkIos checked?
                if (((CheckBox) v).isChecked()) {
                    //Case 1
                    p2.setChecked(false);
                    p3.setChecked(false);
                    p1.setChecked(false);
                    gir.setChecked(false);

                    np.setEnabled(false);
                    nptp.setEnabled(false);
                } else {
                    //case 2
                    np.setEnabled(true);
                    nptp.setEnabled(true);
                    return;
                }

            }
        }); //END CHECKBOX SECTION
    }

    public void askForCourse() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Where are you playing?");
        builder.setCancelable(false);
        // Set up the input
        final EditText input = new EditText(this);
        // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        // Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });

        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
        alertDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean wantToCloseDialog = (input.getText().toString().trim().isEmpty());
                // if EditText is empty disable closing on positive button
                if (!wantToCloseDialog) {
                    ArrayValues.course = input.getText().toString();
                    alertDialog.dismiss();
                } else
                    Toast.makeText(getApplicationContext(), "Please enter a course name", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public int parseHole(String s) {
        int x = 0;
        Character l, m;
        for(int i = 0; i < s.length(); i++) {
            if(Character.isDigit(s.charAt(i))) {
                l = s.charAt(i);

                if(Character.isDigit(s.charAt(i + 1))) {
                    m = s.charAt(i + 1);
                    String d = l.toString() + m.toString();
                    Log.d("D", "" + d);
                    x = Integer.parseInt(d);
                    break;
                }

                x = Character.getNumericValue(l);
                break;
            }
            else x = 0;
        }
        Log.d("String", "" + s);
        return x;
    }

    public void setTitleText(int i) {
        title.setText("Hole " + i);
    }

    public void addLocalThings() {
        ArrayValues.addTheThings(girInt, score, fwInt, udInt, numPutts, par, 0);
    }

    public void numPuttCheck() {

        if(numPutts == 0) {
            Toast.makeText(getApplicationContext(), "Nice Hole Out!",
                    Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getApplicationContext(), Hole1V2.class));
        }
        else
            startActivity(new Intent(getApplicationContext(), Hole1V2.class));
    }

    private void setDividerColor(NumberPicker picker, int color) {

        java.lang.reflect.Field[] pickerFields = NumberPicker.class.getDeclaredFields();
        for (java.lang.reflect.Field pf : pickerFields) {
            if (pf.getName().equals("mSelectionDivider")) {
                pf.setAccessible(true);
                try {
                    ColorDrawable colorDrawable = new ColorDrawable(color);
                    pf.set(picker, colorDrawable);
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (Resources.NotFoundException e) {
                    e.printStackTrace();
                }
                catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }
}





