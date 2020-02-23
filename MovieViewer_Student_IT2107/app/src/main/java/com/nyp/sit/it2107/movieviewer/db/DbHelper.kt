package com.nyp.sit.it2107.movieviewer.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.nyp.sit.it2107.movieviewer.entity.MovieItem

class DbHelper(c: Context) : SQLiteOpenHelper(c,
    DbContract.DATABASE_NAME, null,
    DbContract.DATABASE_VERSION
) {

    override fun onCreate(p0: SQLiteDatabase?) {
        val sql = "CREATE TABLE " +
                "${DbContract.TABLE_NAME}(" +
                "${DbContract.COLUMN_ID} INTEGER PRIMARY KEY, " +
                "${DbContract.COLUMN_POSTER_PATH} TEXT, " +
                "${DbContract.COLUMN_ADULT} INTEGER, " +
                "${DbContract.COLUMN_OVERVIEW} TEXT, " +
                "${DbContract.COLUMN_RELEASE_DATE} TEXT, " +
                "${DbContract.COLUMN_GENRE_IDS} TEXT, " +
                "${DbContract.COLUMN_ORIGIAL_TITLE} TEXT, " +
                "${DbContract.COLUMN_ORIGINAL_LANGUAGE} TEXT, " +
                "${DbContract.COLUMN_TITLE} TEXT, " +
                "${DbContract.COLUMN_BACKDROP_PATH} TEXT, " +
                "${DbContract.COLUMN_POPULARITY} DOUBLE, " +
                "${DbContract.COLUMN_VOTE_COUNT} INTEGER, " +
                "${DbContract.COLUMN_VIDEO} INTEGER, " +
                "${DbContract.COLUMN_VOTE_AVERAGE} DOUBLE" +
                ")"
        Log.d("DEBUG", sql)
        p0?.execSQL(sql)


    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {

    }

}