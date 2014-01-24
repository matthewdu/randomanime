package com.example.uniresthummingbird;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ShowDatabaseActivity extends Activity {

	AnimeDatabaseHelper animeDbHelper;
	List<Anime> animelist;
	LinearLayout linearLayout;
	TextView animeTextView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_database);
		
		linearLayout = (LinearLayout) findViewById(R.id.scrollingLinearLayout);
		animeDbHelper = new AnimeDatabaseHelper(this);		

		
		new AsyncTask<Void, Void, String>() {

			@Override
			protected String doInBackground(Void... params) {
				
				animelist = animeDbHelper.getAllAnime();
				return "success";
			}
			
			protected void onPostExecute(String result){
				for(final Anime anime: animelist){
					LayoutParams lparams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
					animeTextView = new TextView(getBaseContext());
					animeTextView.setLayoutParams(lparams);
					animeTextView.setId(anime.getSlug().hashCode());
					animeTextView.setText(anime.toString());
					animeTextView.setTextColor(Color.BLACK);

							
					
					OnClickListener oclTextView = new OnClickListener() {
						public void onClick(View v) {
							Intent openBrowser = new Intent(Intent.ACTION_VIEW, Uri.parse(anime.getURL()));
							startActivity(openBrowser);
						}
					};

					animeTextView.setOnClickListener(oclTextView);
					linearLayout.addView(animeTextView);

				}
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
