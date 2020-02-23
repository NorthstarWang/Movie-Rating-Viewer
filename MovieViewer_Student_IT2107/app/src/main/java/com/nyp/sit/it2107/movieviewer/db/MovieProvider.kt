package com.nyp.sit.it2107.movieviewer.db

import android.content.ContentProvider
import android.content.ContentResolver
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.net.Uri
import android.util.Log
import com.nyp.sit.it2107.movieviewer.db.DbContract.Companion.MOVIE_URI
import com.nyp.sit.it2107.movieviewer.db.DbContract.Companion.TABLE_NAME
import com.nyp.sit.it2107.movieviewer.entity.MovieItem

class MovieProvider: ContentProvider() {

    private var db: SQLiteDatabase? = null
    private var dbHelper: DbHelper? = null

    override fun onCreate(): Boolean {
        dbHelper = DbHelper(context)
        db = dbHelper?.writableDatabase
        return true
    }

    override fun insert(p0: Uri?, p1: ContentValues?): Uri {
        db!!.insert(TABLE_NAME, null, p1)
        context.contentResolver.notifyChange(MOVIE_URI, null)
        return p0!!
    }

    override fun query(
        p0: Uri?,
        p1: Array<out String>?,
        p2: String?,
        p3: Array<out String>?,
        p4: String?
    ): Cursor {
        val cursor = db!!.query(TABLE_NAME, p1, p2, p3, null, null, p4)
        cursor.setNotificationUri(context.contentResolver, MOVIE_URI)
        return cursor
    }

    override fun update(p0: Uri?, p1: ContentValues?, p2: String?, p3: Array<out String>?): Int {
        val res = db!!.update(TABLE_NAME, p1, p2, p3)
        context.contentResolver?.notifyChange(MOVIE_URI, null)
        return res
    }

    override fun delete(p0: Uri?, p1: String?, p2: Array<out String>?): Int {
        val res = db!!.delete(TABLE_NAME, p1, p2)
        context.contentResolver.notifyChange(MOVIE_URI, null)
        return res
    }

    override fun getType(p0: Uri?): String? {
        return null
    }
}