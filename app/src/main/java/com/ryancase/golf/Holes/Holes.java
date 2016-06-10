package com.ryancase.golf.Holes;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.NumberPicker;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.ryancase.golf.Helpers.ArrayValues;
import com.ryancase.golf.Helpers.Checkbox;
import com.ryancase.golf.MainActivity;
import com.ryancase.golf.R;
import com.ryancase.golf.Helpers.TextViewCust;


public class Holes extends FragmentActivity {

    //INSTANTIATE VARS//
    TextViewCust title;
    TextView curr_score, curr_putts, curr_plusMinus;
    NumberPicker np, nptp;
    Checkbox p1, p2, p3, p4, fw, gir, ud;
    Button n, finish, exit;
    TableRow titleRow;
    int numPutts = 0, score = 0, girInt = 0, fwInt = 0, udInt = 0, par = 0;
    int currPutts=0;
    int currPlusMinus=0;
    int currScore=0;

    public int thisHole = parseHole(this.getClass().getSimpleName()) - 1;

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

        overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_in);
        //getWindow().getAttributes().windowAnimations = R.anim.abc_slide_in_bottom;

        currPutts=0;
        currPlusMinus=0;
        currScore=0;

        title = (TextViewCust) findViewById(R.id.Hole1TV);

        titleRow = (TableRow) findViewById(R.id.titleRow);
        OnClickListener titleListener =new OnClickListener() {
            @Override
            public void onClick(View v) {
                showCurrentStats();
            }
        };
        title.setOnClickListener(titleListener);
        titleRow.setOnClickListener(titleListener);

        int[] placeHolder = new int[18];

        for(int i = 0; i< ArrayValues.scores.length; i++) {
            placeHolder[i] = ArrayValues.par[i] + 3;

            currScore += ArrayValues.scores[i];
            if(ArrayValues.scores[i] != 0) {
                currPlusMinus += (ArrayValues.scores[i] - placeHolder[i]);
            }
            currPutts += ArrayValues.putts[i];
        }

        //setParMethod(parseHole(getClass().getSimpleName()), ArrayValues.getFlag());

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
                ArrayValues.resetArray(ArrayValues.scores);
                ArrayValues.resetArray(ArrayValues.putts);
                ArrayValues.resetArray(ArrayValues.par);

                currScore = 0;
                currPutts = 0;
                currPlusMinus = 0;
                curr_score = null;
                curr_putts = null;
                curr_plusMinus = null;


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

    public void showCurrentStats() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //builder.setTitle("Current Stats");
        builder.setCancelable(true);


        LayoutInflater inflater = getLayoutInflater();
        View dialoglayout = inflater.inflate(R.layout.current_stats, null);
        builder.setView(dialoglayout);

        curr_score = (TextView) dialoglayout.findViewById(R.id.curr_score);
        curr_putts = (TextView) dialoglayout.findViewById(R.id.curr_putts);
        curr_plusMinus = (TextView) dialoglayout.findViewById(R.id.curr_plusMinus);
        String plus = "+";

        try {
            curr_score.setText(String.valueOf(currScore));
            curr_putts.setText(String.valueOf(currPutts));
            if(currPlusMinus > 0)
                curr_plusMinus.setText(plus + String.valueOf(currPlusMinus));
            else
                curr_plusMinus.setText(String.valueOf(currPlusMinus));
        } catch (NullPointerException e) {
            Log.d("Error: ", "" + e);
        }


        AlertDialog alert = builder.create();
        alert.getWindow().setDimAmount(.2f);
        alert.getWindow().getAttributes().y = -425;

        alert.show();
    }

    public int parseHole(String s) {
        int x = 0;
        Character l, m;
        for(int i = 0; i < s.length(); i++) {
            if(Character.isDigit(s.charAt(i))) {
                l = s.charAt(i);

                try {
                    if (Character.isDigit(s.charAt(i + 1))) {
                        m = s.charAt(i + 1);
                        String d = l.toString() + m.toString();
                        Log.d("D", "" + d);
                        x = Integer.parseInt(d);
                        break;
                    }
                } catch (IndexOutOfBoundsException e) {
                    Log.d("", "" + e);
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
            startActivity(new Intent(getApplicationContext(), Hole1.class));
        }
        else
            startActivity(new Intent(getApplicationContext(), Hole1.class));
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





