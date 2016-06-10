package com.ryancase.golf.Login;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;
import com.ryancase.golf.MainActivity;
import com.ryancase.golf.R;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginSignupActivity extends ActionBarActivity {

	Button loginButton;
	Button signupButton;
	String usernametxt;
	String passwordtxt;
	EditText password;
	EditText username;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login_signup);

		if(ParseUser.getCurrentUser() != null)
			Log.d("Current User:", "" + ParseUser.getCurrentUser().getEmail());
		else
			Log.d("No User", "");

		username = (EditText) findViewById(R.id.username);
		password = (EditText) findViewById(R.id.password);

		loginButton = (Button) findViewById(R.id.login);
		signupButton = (Button) findViewById(R.id.signup);

		loginButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				usernametxt = username.getText().toString().trim();
				passwordtxt = password.getText().toString();

				ParseUser.logInInBackground(usernametxt, passwordtxt,
						new LogInCallback() {

							@Override
							public void done(ParseUser user, ParseException e) {
								if (user != null) {

									Intent intent = new Intent(
											LoginSignupActivity.this,
											MainActivity.class);
									startActivity(intent);
									finish();

								} else {
									Toast.makeText(
											getApplicationContext(),
											"Login Error: Please signup or try again.",
											Toast.LENGTH_SHORT).show();
								}
							}
						});
			}
		});

		signupButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				usernametxt = username.getText().toString().trim();
				passwordtxt = password.getText().toString();


				if (usernametxt.equals("") || passwordtxt.equals("")) {

					Toast.makeText(getApplicationContext(),
							"Please complete the sign up form",
							Toast.LENGTH_SHORT).show();

				}
				else if(!emailValidator(usernametxt)){
					Toast.makeText(getApplicationContext(),
							"Please enter a valid email",
							Toast.LENGTH_SHORT).show();
				}
				else if(passwordtxt.length() < 6){
					Toast.makeText(getApplicationContext(),
							"Password must be at least 6 characters",
							Toast.LENGTH_SHORT).show();
				}else {

					ParseUser user = new ParseUser();
					user.setUsername(usernametxt);
					user.setEmail(usernametxt);
					user.setPassword(passwordtxt);
					user.put("name", parseUsername());
					user.signUpInBackground(new SignUpCallback() {

						@Override
						public void done(ParseException e) {
							if (e == null) {

								Toast.makeText(getApplicationContext(),
										"Successfully Signed up!",
										Toast.LENGTH_SHORT).show();

							} else {

								Toast.makeText(getApplicationContext(),
										"Sign up error", Toast.LENGTH_SHORT)
										.show();

							}
						}
					});

				}
			}
		});
	}

	public String parseUsername() {
		String name = "";
		for(int i = 0; i < usernametxt.length(); i++) {
			if(usernametxt.charAt(i) != '@') {
				name += usernametxt.charAt(i);
			}
			else
				break;
		}
		return name;
	}

	public boolean emailValidator(String email)
	{
		Pattern pattern;
		Matcher matcher;
		final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
		pattern = Pattern.compile(EMAIL_PATTERN);
		matcher = pattern.matcher(email);
		return matcher.matches();
	}

}