package com.example.uniresthummingbird;

import java.util.List;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
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
		for(Anime anime: animelist){
			System.out.println(anime.toString());
		}
		
		for(Anime anime: animelist){
			LayoutParams lparams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
					tv= new TextView(getBaseContext());
					tv.setLayoutParams(lparams);
					tv.setText(anime.toString());
					linearLayout.addView(tv);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.show_database, menu);
		return true;
	}

}
