package com.example.uniresthummingbird;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Anime {
	private String slug;
	private String title;
	private String show_type;
	private List<Genre> genres;	
	private int episode_count;
	private String status;
	private String synopsis;
	private String url;
	private String cover_image;
	private String alternate_title;
	

	
/*	public Anime(JsonNode myNode) throws JSONException{
		this.title = myNode.getObject().get("title").toString();
		this.status = myNode.getObject().get("status").toString();
		this.url = myNode.getObject().get("url").toString();
		this.episode_count = Integer.parseInt(myNode.getObject().get("episode_count").toString());
		this.cover_image = myNode.getObject().get("cover_image").toString();
		this.show_type = myNode.getObject().get("show_type").toString();
		this.synopsis = myNode.getObject().get("synopsis").toString();
	}*/
	
	
	public String toString(){
		return title + "\n"
			+	"Episodes: " + episode_count + "\n"
			+	"Status: " + status + "\n"
			+	"Type: " + show_type + "\n"
			+	"Genres: " + getGenres() + "\n"
			+	"Synopsis: " + synopsis + "\n";
	}
	
	public Anime(String slug, String title, String show_type, String genres,
			int episode_count, String status, String synopsis, String url, String cover_image) {
		this.slug = slug;
		this.status = status;
		this.url = url;
		this.title = title;
		this.episode_count = episode_count;
		this.cover_image = cover_image;
		this.synopsis = synopsis;
		this.show_type = show_type;
		List<String> list = Arrays.asList(genres.split(",\\s"));
		this.genres = setGenreList(list);
	}
	
	private List<Genre> setGenreList(List<String> list){
		List<Genre> temp = new ArrayList<Genre>();
		for(int i = 0; i < list.size(); i++){
			Genre temp2 = new Genre(list.get(i));
			temp.add(temp2);
		}		
		return temp;
	}

	public String getSlug(){
		return this.slug;
	}
	
	public String getStatus(){
		return this.status;
	}
	
	public String getURL(){
		return this.url;
	}
	
	public String getTitle(){
		return this.title;
	}
	
	public int getEpisode_count(){
		return this.episode_count;
	}
	
	public String getGenres(){
		String temp = "";
		for(Genre genre: genres)
			temp += genre.getGenre() + ", ";
		return temp.substring(0, temp.length()-2);
	}
	
	public String getSynopsis(){
		return this.synopsis;
	}
	
	public String getShow_type(){
		return this.show_type;
	}
	
	public String getImageURL(){
		return this.cover_image;
	}

}
