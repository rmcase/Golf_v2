package com.ryancase.golf.Helpers;

import android.app.Application;

import com.parse.Parse;

/**
 * Created by ryancase on 11/10/15.
 */
public class ParseApp extends Application{
    @Override
    public void onCreate() {
        super.onCreate();


        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "1IKEmMgPXgmCobqi2bubZM9wEAfPgPlELKxbdW4J", "4nW20dDZz4ij6864F2eyPjXENWVCNAIw6EsUsdQk");
    }
}
