package com.example.uniresthummingbird;

import java.util.Random;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.uniresthummingbird.AnimeDatabaseContract.AnimeEntry;
import com.google.gson.Gson;
import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

public class MainActivity extends Activity {

	HttpResponse<JsonNode> request;

	Random random;
	int randomId;
	TextView textview1;
	Button button1; //nextAnime button
	Button buttonDatabase;
	Button buttonResetDatabase;
	Button buttonStore;
	ImageView imageView1;
	String apiKey1 = "X-Mashape-Authorization";
	String apiKey2 = "teZUDs9Pu1SIUs0yiUsAIvqo41mTinxt";
	Gson gson;
	Anime anime;
	AnimeDatabaseHelper animeDbHelper;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		random = new Random();
		randomId = random.nextInt(7900);
		textview1 = (TextView) findViewById(R.id.textView1);
		button1 = (Button) findViewById(R.id.button1);
		buttonDatabase = (Button) findViewById(R.id.buttonDatabase);
		buttonResetDatabase = (Button) findViewById(R.id.buttonResetDatabase);
		buttonStore = (Button) findViewById(R.id.buttonStore);
		imageView1 = (ImageView) findViewById(R.id.imageView1);
		gson = new Gson();
		System.out.println("Top");
		animeDbHelper = new AnimeDatabaseHelper(this);
		
		
	}

	public void nextAnime(View view){
		button1.setText(R.string.nextAnimeButtonLoading); //Changes nextAnime button text to "Loading"
		button1.setEnabled(false); //Disables nextAnime button
		randomId = random.nextInt(2000);
		buttonDatabase.setEnabled(false);
		buttonStore.setEnabled(false);
		buttonResetDatabase.setEnabled(false);
		
		new AsyncTask<Void, Void, String>() {

			@Override
			protected String doInBackground(Void... params) {
				
				try {
					request = Unirest.get("https://hummingbirdv1.p.mashape.com/anime/"+ randomId)
								  .header(apiKey1, apiKey2)
								  .asJson(); //Pulls JSON file from hummingbird.me

				} catch (UnirestException e) {
					return "Failed to load data. Check internet connection";
				}

				anime = gson.fromJson(request.getBody().toString(), Anime.class); //Creates Anime Object from JSON file
				return anime.toString();
			}
			
			protected void onPostExecute(String result){
				System.out.println(anime.getImageURL());
				UrlImageViewHelper.setUrlDrawable(imageView1, anime.getImageURL()); //Set imageView1 to anime image url
				textview1.setText(result); //Sets textView1 to anime information
				button1.setText(R.string.nextAnimeButton); //Changes nextAnime button text to "Next Anime"
				button1.setEnabled(true); //Enables nextAnime button
				buttonDatabase.setEnabled(true);
				buttonStore.setEnabled(true);
				buttonResetDatabase.setEnabled(true);
				buttonStore.setText(R.string.store);
			}
			
		}.execute();
		
		System.gc();
	}
	
	public void clickImage(View view){
		if(anime != null){
			Intent openBrowser = new Intent(Intent.ACTION_VIEW, Uri.parse(anime.getURL()));
			startActivity(openBrowser);
		}
	}
	
	public void storeAnime(View view){
				buttonDatabase.setEnabled(true);
				animeDbHelper.addAnime(anime);
				buttonStore.setText(R.string.added);
				buttonStore.setEnabled(false);
				buttonResetDatabase.setEnabled(true);
	}
	
	public void startDatabaseActivity(View view){
		animeDbHelper.close();
		Intent startDatabase = new Intent(this, ShowDatabaseActivity.class);
		startActivity(startDatabase);
	}
	
	public void resetDatabase(View view){
		buttonDatabase.setEnabled(false);
		buttonResetDatabase.setEnabled(false);
		SQLiteDatabase db = animeDbHelper.getWritableDatabase();
		db.delete(AnimeEntry.TABLE_NAME, null, null);
		System.out.println("Database Reset");
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
