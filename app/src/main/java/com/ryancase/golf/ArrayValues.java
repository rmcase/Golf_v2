package com.ryancase.golf;

import android.app.AlertDialog;
import android.content.DialogInterface;

import com.parse.ParseObject;
import com.parse.ParseUser;

import java.sql.Array;
import java.sql.Struct;
import java.util.ArrayList;

/**
 * Created by ryancase on 11/9/15.
 */
public class ArrayValues {

    public static int[] fairways = new int[18];

    public static int[] girs = new int[18];

    public static int[] uds = new int[18];

    public static int[] scores = new int[18];

    public static int[] putts = new int[18];

    public static int[] par = new int[18];

    public static boolean f = false, b = false;

    public static String course;
    private static String c2;

    public static void setCourse(String s) {
        c2 = s;
    }
    public static String getCourse() {
        return c2;
    }
    public static int slope;
    public static float rating;

    public static void resetArray(int arr[]) {
        for(int i=0; i < arr.length; i++)
            arr[i] = 0;
    }

    public static void addTheThings(int a, int b, int c, int d, int e, int f, int num) {
        ArrayValues.girs[num] = a;
        ArrayValues.scores[num] = b;
        ArrayValues.fairways[num] = c;
        ArrayValues.uds[num] = d;
        ArrayValues.putts[num] = e;
        ArrayValues.par[num] = f;
    }

    public static ParseUser currentUser;

    public static ArrayList<ParseObject> objectArray = new ArrayList<ParseObject>();

    public static ArrayList<Number> intList;

    public static boolean possibleFlag;

    private static boolean flagTwo;

    public static void setFlag(boolean x) {
        flagTwo = x;
    }

    public static boolean getFlag() {
        return flagTwo;
    }


}

