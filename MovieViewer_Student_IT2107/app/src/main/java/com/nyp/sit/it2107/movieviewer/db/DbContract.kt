package com.nyp.sit.it2107.movieviewer.db

import android.net.Uri

class DbContract {
    companion object {
        val DATABASE_NAME = "movie.db"
        val DATABASE_VERSION = 1
        val TABLE_NAME = "movie"
        val COLUMN_POSTER_PATH = "poster_path"
        val COLUMN_ADULT = "adult"
        val COLUMN_OVERVIEW = "overview"
        val COLUMN_RELEASE_DATE = "release_date"
        val COLUMN_GENRE_IDS = "genre_ids"
        val COLUMN_ID = "_id"
        val COLUMN_ORIGIAL_TITLE = "original_title"
        val COLUMN_ORIGINAL_LANGUAGE = "original_langauge"
        val COLUMN_TITLE = "title"
        val COLUMN_BACKDROP_PATH = "backdrop_path"
        val COLUMN_POPULARITY = "popularity"
        val COLUMN_VOTE_COUNT = "vote_count"
        val COLUMN_VIDEO = "video"
        val COLUMN_VOTE_AVERAGE = "vote_average"

        val AUTHORITY = "com.nyp.sit.it2107.movieviewer"

        val CONTENT_URI = Uri.parse("content://$AUTHORITY")
        val MOVIE_URI = Uri.withAppendedPath(
            CONTENT_URI,
            TABLE_NAME
        )
    }
}