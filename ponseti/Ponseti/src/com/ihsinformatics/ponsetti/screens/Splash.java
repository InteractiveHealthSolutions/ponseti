package com.ihsinformatics.ponsetti.screens;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;

import com.ihsinformatics.ponsetti.R;

public class Splash extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		
		new Timer().schedule(new TimerTask() {

			@Override
			public void run() {
				startActivity(new Intent(Splash.this, Login.class));
				finish();

			}
		}, 3000);
	}

	

}
