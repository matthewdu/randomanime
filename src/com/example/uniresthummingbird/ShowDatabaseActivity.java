package com.example.uniresthummingbird;

import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

public class ShowDatabaseActivity extends Activity {

	AnimeDatabaseHelper animeDbHelper;
	List<Anime> animelist;
	LinearLayout linearLayout;
	TextView animeTextView;
	private SharedPreferences loginCredentials;
	String authToken;
	final Context context = this;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_database);
		
		linearLayout = (LinearLayout) findViewById(R.id.scrollingLinearLayout);
		animeDbHelper = new AnimeDatabaseHelper(this);		
		loginCredentials = getSharedPreferences(LoginActivity.PREF_NAME, 0);
        authToken = loginCredentials.getString("authToken", "Failed");
		
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
					
					OnLongClickListener olclTextView = new OnLongClickListener() {
						@Override
						public boolean onLongClick(View v) {
							new AlertDialog.Builder(context)
							.setTitle(R.string.updateLibraryAlertDialogTitle)
							.setMessage("Add \"" + anime.getTitle() + "\" to Plan to Watch?")
							.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog, int which) {
									new updateLibrary().execute(anime.getSlug());
									
								}
							})
							.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
								
								@Override
								public void onClick(DialogInterface dialog, int which) {
									dialog.cancel();									
								}
							})
							.show();
							return false;
						}
					};

					animeTextView.setOnClickListener(oclTextView);
					animeTextView.setOnLongClickListener(olclTextView);
					linearLayout.addView(animeTextView);

				}
			}
		}.execute();
		
		
		
		
	}
	
	private class updateLibrary extends AsyncTask<String, Void, String>{
		protected String doInBackground(String... animeName){
			String request = "failed";
			System.out.println(animeName[0]);
			try {
				request = Unirest.post("https://hummingbirdv1.p.mashape.com/libraries/" + animeName[0])
					  .header("X-Mashape-Authorization", "teZUDs9Pu1SIUs0yiUsAIvqo41mTinxt")
					  .field("auth_token", authToken)
					  .field("status", "plan-to-watch")
					  .field("privacy", "private")
					  .asString().getBody();
			} catch (UnirestException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
			} finally {
				return request;
			}			
		}
		
		protected void onPostExecute(String result){
			System.out.println(result);
			Toast.makeText(getApplicationContext(), "Added", Toast.LENGTH_SHORT).show();
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.show_database, menu);
		return true;
	}

}
