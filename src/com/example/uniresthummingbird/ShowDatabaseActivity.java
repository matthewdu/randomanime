package com.example.uniresthummingbird;

import java.util.List;

import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ShowDatabaseActivity extends Activity {

	AnimeDatabaseHelper animeDbHelper;
	List<Anime> animelist;
	LinearLayout linearLayout;
	TextView tv;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_database);
		
		linearLayout = (LinearLayout) findViewById(R.id.scrollingLinearLayout);
		animeDbHelper = new AnimeDatabaseHelper(this);		
		animelist = animeDbHelper.getAllAnime();
		
		new AsyncTask<Void, Void, String>() {

			@Override
			protected String doInBackground(Void... params) {
				
				for(final Anime anime: animelist){
					LayoutParams lparams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
					tv= new TextView(getBaseContext());
					tv.setLayoutParams(lparams);
					tv.setText(anime.toString());
					tv.setTextColor(Color.BLACK);
							
					
					OnClickListener oclTextView = new OnClickListener() {
						public void onClick(View v) {
							Intent openBrowser = new Intent(Intent.ACTION_VIEW, Uri.parse(anime.getURL()));
							startActivity(openBrowser);
						}
					};

					tv.setOnClickListener(oclTextView);
					linearLayout.addView(tv);
				}
				return "success";
			}
		}.execute();
		
		
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.show_database, menu);
		return true;
	}

}
