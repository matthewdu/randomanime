package com.example.uniresthummingbird;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.uniresthummingbird.AnimeDatabaseContract.AnimeEntry;

public class AnimeDatabaseHelper extends SQLiteOpenHelper {
	public static final int DATABASE_VERSION = 1;
	public static final String DATABASE_NAME = "Anime.db";
	
	public AnimeDatabaseHelper(Context context){
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	public void onCreate(SQLiteDatabase db){
		db.execSQL(AnimeDatabaseContract.SQL_CREATE_ENTRIES);
	}
	
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
		db.execSQL(AnimeDatabaseContract.SQL_DELETE_ENTRIES);
		onCreate(db);
	}
	
	public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion){
		onUpgrade(db, oldVersion, newVersion);
	}
	
	public void addAnime(Anime anime){
				
		SQLiteDatabase db = this.getWritableDatabase();
		
		ContentValues values = new ContentValues();
		values.put(AnimeEntry.COLUMN_NAME_SLUG, anime.getSlug());
		values.put(AnimeEntry.COLUMN_NAME_TITLE, anime.getTitle());
		values.put(AnimeEntry.COLUMN_NAME_SHOW_TYPE, anime.getShow_type());
		values.put(AnimeEntry.COLUMN_NAME_GENRES, anime.getGenres());
		values.put(AnimeEntry.COLUMN_NAME_EPISODE_COUNT, anime.getEpisode_count());
		values.put(AnimeEntry.COLUMN_NAME_STATUS, anime.getStatus());
		values.put(AnimeEntry.COLUMN_NAME_SYNOPSIS, anime.getSynopsis());
		values.put(AnimeEntry.COLUMN_NAME_URL, anime.getURL());
		values.put(AnimeEntry.COLUMN_NAME_COVER_IMAGE, anime.getImageURL());
		
		db.insert(AnimeEntry.TABLE_NAME, null, values);
		System.out.println("Added Anime to database");
	}
	
	public List<Anime> getAllAnime(){
		List<Anime> list = new ArrayList<Anime>();
		String selectQuery = "SELECT * FROM " + AnimeEntry.TABLE_NAME +
							 " ORDER BY " + AnimeEntry.COLUMN_NAME_TITLE + " DESC";
		
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery(selectQuery, null);
		
		System.out.println("Finished Reading Database in getAllAnime method");
		
		if (c.moveToFirst()) {
            do {
                Anime anime = new Anime(c.getString(1), c.getString(2), c.getString(3), c.getString(4), Integer.parseInt(c.getString(5)), c.getString(6), c.getString(7), c.getString(8), c.getString(9));
                list.add(anime);
                System.out.println("bye");
            } while (c.moveToNext());
            
        }
		System.out.println("Added all anime from database to list");
		return list;			
	}
	
}