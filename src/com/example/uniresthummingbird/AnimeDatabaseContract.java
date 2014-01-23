package com.example.uniresthummingbird;

import android.provider.BaseColumns;

public final class AnimeDatabaseContract {

	public AnimeDatabaseContract() {}
	
	public static abstract class AnimeEntry implements BaseColumns{
		public static final String TABLE_NAME = "animetable";
		public static final String COLUMN_NAME_SLUG = "slug";
		public static final String COLUMN_NAME_TITLE = "title";
		public static final String COLUMN_NAME_SHOW_TYPE = "show_type";
		public static final String COLUMN_NAME_GENRES = "genres";
		public static final String COLUMN_NAME_EPISODE_COUNT = "episode_count";
		public static final String COLUMN_NAME_STATUS = "status";
		public static final String COLUMN_NAME_SYNOPSIS = "synopsis";
		public static final String COLUMN_NAME_URL = "url";
		public static final String COLUMN_NAME_COVER_IMAGE = "cover_image";
	}
	
	public static final String TEXT_TYPE = " TEXT";
	public static final String SMALLINT_TYPE = " SMALLINT";
	public static final String COMMA_SEP = ",";
	public static final String SQL_CREATE_ENTRIES =
			"CREATE TABLE " + AnimeEntry.TABLE_NAME + " (" +
			AnimeEntry._ID + " INTEGER PRIMARY KEY," +
			AnimeEntry.COLUMN_NAME_SLUG + TEXT_TYPE + COMMA_SEP +
			AnimeEntry.COLUMN_NAME_TITLE + TEXT_TYPE + COMMA_SEP +
			AnimeEntry.COLUMN_NAME_SHOW_TYPE + TEXT_TYPE + COMMA_SEP +
			AnimeEntry.COLUMN_NAME_GENRES + TEXT_TYPE + COMMA_SEP +
			AnimeEntry.COLUMN_NAME_EPISODE_COUNT + SMALLINT_TYPE + COMMA_SEP +
			AnimeEntry.COLUMN_NAME_STATUS + TEXT_TYPE + COMMA_SEP +
			AnimeEntry.COLUMN_NAME_SYNOPSIS + TEXT_TYPE + COMMA_SEP +
			AnimeEntry.COLUMN_NAME_URL + TEXT_TYPE + COMMA_SEP +
			AnimeEntry.COLUMN_NAME_COVER_IMAGE + TEXT_TYPE +
			")";
	public static final String SQL_DELETE_ENTRIES = 
			"DROP TABLE IF EXISTS " + AnimeEntry.TABLE_NAME;	
}
