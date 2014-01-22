package com.example.uniresthummingbird;

import java.util.Random;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

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
	Button button1;
	ImageView imageView1;
	String apiKey1 = "X-Mashape-Authorization";
	String apiKey2 = "teZUDs9Pu1SIUs0yiUsAIvqo41mTinxt";
	Gson gson;
	Anime anime;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		random = new Random();
		randomId = random.nextInt(7900);
		textview1 = (TextView) findViewById(R.id.textView1);
		button1 = (Button) findViewById(R.id.button1);
		imageView1 = (ImageView) findViewById(R.id.imageView1);
		gson = new Gson();
		System.out.println("Top");
	}

	public void nextAnime(View view){
		button1.setText(R.string.nextAnimeButtonLoading);
		button1.setEnabled(false);
		randomId = random.nextInt(2000);

		
		new AsyncTask<Void, Void, String>() {

			@Override
			protected String doInBackground(Void... params) {
				
				try {
					request = Unirest.get("https://hummingbirdv1.p.mashape.com/anime/"+ randomId)
								  .header(apiKey1, apiKey2)
								  .asJson();

				} catch (UnirestException e) {
					return "Failed to load data. Check internet connection";
				}

				anime = gson.fromJson(request.getBody().toString(), Anime.class);
				System.out.println(anime.getGenres());
				
				
				return anime.toString();
			}
			
			protected void onPostExecute(String result){
				System.out.println(anime.getImageURL());
				UrlImageViewHelper.setUrlDrawable(imageView1, anime.getImageURL());
				textview1.setText(result);
				button1.setText(R.string.nextAnimeButton);
				button1.setEnabled(true);
			}
			
		}.execute();
		
		System.gc();
	}
	
	public void clickImage(View viw){
		if(anime != null){
			Intent openBrowser = new Intent(Intent.ACTION_VIEW, Uri.parse(anime.getUrl()));
			startActivity(openBrowser);
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
