package com.ryancase.golf;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.NumberPicker;
import android.widget.Toast;


public class Hole9V2 extends FragmentActivity {

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

        title = (TextViewCust) findViewById(R.id.Hole1TV);
        title.setText("Hole 9");

        ud = (Checkbox) findViewById(R.id.ud_box);
        fw = (Checkbox) findViewById(R.id.fairway_box);
        gir = (Checkbox) findViewById(R.id.gir_box);

        //PUTTING LOGIC//
        p1 = (Checkbox) findViewById(R.id.putt1);
        p2 = (Checkbox) findViewById(R.id.putt2);
        p3 = (Checkbox) findViewById(R.id.putt3);
        p4 = (Checkbox) findViewById(R.id.putt4);

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
                    else if (np.getValue() - (nptp.getValue() + 3) <= -1)
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

                    if (np.getValue() == (nptp.getValue() + 3) || (np.getValue() - (nptp.getValue() + 3) == -1))
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
                    else if (np.getValue() - (nptp.getValue() + 3) == 1)
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

                    np.setEnabled(false);
                    nptp.setEnabled(false);
                } else {
                    //case 2
                    np.setEnabled(true);
                    nptp.setEnabled(true);
                    return;
                }
            }
        });
        //END PUTTING SECTION//


        //SET UP SCORE PICKER//
        nptp = (NumberPicker) findViewById(R.id.scorePickerToPar);
        nptp.setMinValue(0);
        nptp.setMaxValue(2);
        nptp.setValue(1);
        nptp.setDisplayedValues(new String[]{"3", "4", "5"});
        nptp.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);



        if(ArrayValues.getFlag()) {
            nptp.setEnabled(false);

            try {
                if ((Integer) ArrayValues.intList.get(8) == 4)
                    nptp.setValue(1);
                else if ((Integer) ArrayValues.intList.get(8) == 3)
                    nptp.setValue(0);
                else if ((Integer) ArrayValues.intList.get(8) == 5)
                    nptp.setValue(2);
                else if ((Integer) ArrayValues.intList.get(8) == 10)
                    nptp.setEnabled(true);


                Log.d("PossibleFlag: ", "" + true);
            }
            catch (IndexOutOfBoundsException e) {
                Log.d("IndexOutOfBounds Caught", "");

                ArrayValues.setFlag(false);
                nptp.setEnabled(true);
            }
        }


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

                par = nptp.getValue();

                //1 means checked
                girInt = gir.checker();

                fwInt = fw.checker();

                udInt = ud.checker();

                ArrayValues.addTheThings(girInt, score, fwInt, udInt, numPutts, par, 8);

                if(numPutts == 0) {
                    Toast.makeText(getApplicationContext(), "Nice Hole Out!",
                            Toast.LENGTH_SHORT).show();
                }

                AlertDialog.Builder builder = new AlertDialog.Builder(Hole9V2.this);
                builder.setMessage("Finish Round?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                AlertDialog.Builder builder = new AlertDialog.Builder(Hole9V2.this);
                                builder.setMessage("Which Nine?")
                                        .setCancelable(false)
                                        .setPositiveButton("Front", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                //startActivity(new Intent(getApplicationContext(), roundFinishNine.class));
                                                Intent intent = new Intent(Hole9V2.this, roundFinishNine.class);
                                                Bundle b = new Bundle();
                                                b.putString("nine", "front");

                                                intent.putExtras(b); //Put your id to your next Intent
                                                startActivity(intent);
                                            }
                                        })
                                        .setNegativeButton("Back", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                //dialog.cancel();
                                                //startActivity(new Intent(getApplicationContext(), roundFinishNine.class));
                                                Intent intent = new Intent(Hole9V2.this, roundFinishNine.class);
                                                Bundle b = new Bundle();
                                                b.putString("nine", "back");

                                                intent.putExtras(b); //Put your id to your next Intent
                                                startActivity(intent);

                                            }
                                        });
                                AlertDialog alert = builder.create();
                                alert.show();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                                startActivity(new Intent(getApplicationContext(), Hole10.class));

                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();

            }
        };
        n.setOnClickListener(listnr);
        //END NEXT HOLE BUTTON//


        //EXIT ROUND BUTTON CLICK//
        exit = (Button) findViewById(R.id.prev_button);

        OnClickListener exitList =new OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));

            }
        };
        exit.setOnClickListener(exitList);
        //EXIT BUTTON END//

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