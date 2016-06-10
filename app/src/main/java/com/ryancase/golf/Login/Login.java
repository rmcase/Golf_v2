package com.ryancase.golf.Login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.parse.ParseUser;
import com.ryancase.golf.MainActivity;

/**
 * Created by ryancase on 1/19/16.
 */
public class Login extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*if(ParseAnonymousUtils.isLinked(ParseUser.getCurrentUser())) {

            Intent intent = new Intent(Login.this, LoginSignupActivity.class);
            startActivity(intent);
            finish();

        } else {*/

            ParseUser currentUser = ParseUser.getCurrentUser();

            if(currentUser != null) {
                Intent intent = new Intent(Login.this, MainActivity.class);
                startActivity(intent);
                finish();

            } else {

                Intent intent = new Intent(Login.this, LoginSignupActivity.class);
                startActivity(intent);
                finish();

            }

        //}
    }
}
