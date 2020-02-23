package com.nyp.sit.it2107.movieviewer

import android.content.Context
import com.nyp.sit.it2107.movieviewer.entity.MovieItem
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.lang.StringBuilder
import java.util.ArrayList

class movieDBJsonUtils() {


    companion object {


        @Throws(JSONException::class)
        fun getMovieDetailsFromJson(movieDetailsJsonStr: String): ArrayList<MovieItem>? {

            val parsedMovieData = ArrayList<MovieItem>()

            val jsonObj = JSONObject(movieDetailsJsonStr)
            val resultArr = jsonObj.getJSONArray("results")
            for (i in 0..resultArr.length() - 1) {
                val itemObj = resultArr.getJSONObject(i)
                val movieItem = MovieItem()
                movieItem.poster_path = itemObj.getString("poster_path")
                movieItem.adult = itemObj.getBoolean("adult")
                movieItem.overview = itemObj.getString("overview")

                movieItem.release_date = itemObj.getString("release_date")
                val genreIdsArr = itemObj.getJSONArray("genre_ids")
                val genreIds = StringBuilder("[")
                for (j in 0..genreIdsArr.length() - 1) {
                    val id = genreIdsArr.get(j)
                    genreIds.append(id)
                    if (j < genreIdsArr.length() - 1) {
                        genreIds.append(",")
                    }
                }
                genreIds.append("]")
                movieItem.genre_ids = genreIds.toString()
                movieItem.id = itemObj.getInt("id")

                movieItem.original_title = itemObj.getString("original_title")
                movieItem.original_langauge = itemObj.getString("original_language")

                movieItem.title = itemObj.getString("title")
                movieItem.backdrop_path = itemObj.getString("backdrop_path")
                movieItem.popularity = itemObj.getDouble("popularity")

                movieItem.vote_count = itemObj.getInt("vote_count")
                movieItem.video = itemObj.getBoolean("video")
                movieItem.vote_average = itemObj.getDouble("vote_average")

                parsedMovieData.add(movieItem)
            }

            return parsedMovieData
        }



    }

}