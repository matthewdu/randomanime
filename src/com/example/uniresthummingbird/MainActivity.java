package com.example.uniresthummingbird;

import java.util.Random;

import org.json.JSONException;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

public class MainActivity extends Activity {

	HttpResponse<JsonNode> request;
	Anime anime;
	Random random;
	int randomId;
	TextView textview1;
	Button button1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		random = new Random();
		randomId = random.nextInt(2000);
		textview1 = (TextView) findViewById(R.id.textView1);
		button1 = (Button) findViewById(R.id.button1);
		System.out.println("Top");
	}

	public void nextAnime(View view){
		button1.setText(R.string.nextAnimeButtonLoading);
		button1.setEnabled(false);
		randomId = random.nextInt(2000);
				
		new AsyncTask<Void, Void, Anime>() {

			@Override
			protected Anime doInBackground(Void... params) {
				

				try {
					request = Unirest.get("https://hummingbirdv1.p.mashape.com/anime/"+ randomId)
								  .header("X-Mashape-Authorization", "teZUDs9Pu1SIUs0yiUsAIvqo41mTinxt")
								  .asJson();
					System.out.println("finished unirest request");
				} catch (UnirestException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				
					try {
						anime = new Anime(request.getBody());
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	
				System.out.println("End of background");	
					
				System.out.println(anime.toString());
				
				return anime;
			}
			
			protected void onPostExecute(Anime result){
				System.out.println("beginning of post");
				textview1.setText(result.toString());
				button1.setText(R.string.nextAnimeButton);
				button1.setEnabled(true);
				
			}
		}.execute();
		
	}
	
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
