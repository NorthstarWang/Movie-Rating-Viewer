package com.nyp.sit.it2107.movieviewer

import android.app.LoaderManager
import android.content.*
import android.database.Cursor
import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.*
import android.widget.*
import com.nyp.sit.it2107.movieviewer.db.DbContract
import com.nyp.sit.it2107.movieviewer.entity.MovieItem
import com.squareup.picasso.Picasso
import java.net.URL

class ViewListOfMoviesActivity : AppCompatActivity() {


    val SHOW_BY_TOP_RATED = 1
    val SHOW_BY_POPULAR = 2

    private var displayType = SHOW_BY_TOP_RATED

    var data: ArrayList<MovieItem>? = null
    var lvMovie: ListView? = null
    var cursorAdapter: MovieCursorAdapter? = null

    override fun onSaveInstanceState(outState: Bundle?) {
        outState?.putInt("DISPLAY_TYPE", displayType)
        super.onSaveInstanceState(outState)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_list_of_movies)

        lvMovie = findViewById(R.id.movielist)
        cursorAdapter = MovieCursorAdapter(this, null)
        lvMovie?.adapter = cursorAdapter
        lvMovie?.setOnItemClickListener(object : AdapterView.OnItemClickListener {
            override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                val intent = Intent(this@ViewListOfMoviesActivity, ItemDetailActivity::class.java)
                intent.putExtra("item", data?.get(p2))
                startActivity(intent)
            }

        })

        loaderManager.initLoader(0, null, object : LoaderManager.LoaderCallbacks<Cursor> {
            override fun onCreateLoader(p0: Int, p1: Bundle?): Loader<Cursor> {
                val projection = arrayOf(
                    DbContract.COLUMN_ID,
                    DbContract.COLUMN_TITLE,
                    DbContract.COLUMN_OVERVIEW,
                    DbContract.COLUMN_RELEASE_DATE,
                    DbContract.COLUMN_POPULARITY,
                    DbContract.COLUMN_BACKDROP_PATH)
                return CursorLoader(this@ViewListOfMoviesActivity, DbContract.CONTENT_URI, projection, null, null, null)
            }

            override fun onLoaderReset(p0: Loader<Cursor>?) {
                cursorAdapter!!.swapCursor(null)
            }

            override fun onLoadFinished(p0: Loader<Cursor>?, p1: Cursor?) {
                cursorAdapter!!.swapCursor(p1)
            }

        })
        if (savedInstanceState != null) {
            loadMovieData(savedInstanceState.getInt("DISPLAY_TYPE"))
        }
    }


    override fun onStart() {
        super.onStart()
        loadMovieData(displayType)
    }


    fun loadMovieData(viewType: Int) {

        var showTypeStr: String? = null
        when (viewType) {
            SHOW_BY_TOP_RATED -> showTypeStr = NetworkUtils.TOP_RATED_PARAM
            SHOW_BY_POPULAR -> showTypeStr = NetworkUtils.POPULAR_PARAM

        }

        if (showTypeStr != null) {
            displayType = viewType
        }
        GetMoviePageAsyncTask().execute(showTypeStr)
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        when (item?.itemId) {

            R.id.sortPopular -> {
                loadMovieData(SHOW_BY_POPULAR)
            }
            R.id.sortTopRated -> {
                loadMovieData(SHOW_BY_TOP_RATED)
            }

        }

        return super.onOptionsItemSelected(item)
    }

    inner class MovieCursorAdapter(context: Context, cursor: Cursor?, flag: Int = 0
    ) : CursorAdapter(context, cursor, flag) {

        override fun newView(p0: Context?, p1: Cursor?, p2: ViewGroup?): View {
            return LayoutInflater.from(p0).inflate(R.layout.item_movie_list, p2, false)
        }

        override fun bindView(p0: View, p1: Context?, p2: Cursor?) {
            val imageView: ImageView = p0.findViewById(R.id.imageView)
            val tvTitle: TextView = p0.findViewById(R.id.tv_title)
            val tvOverview = p0.findViewById<TextView>(R.id.tv_overview)
            val tvPopularity = p0.findViewById<TextView>(R.id.tv_rate)
            val tvReleaseDate = p0.findViewById<TextView>(R.id.tv_date)
            val title = p2?.getString(p2?.getColumnIndex(DbContract.COLUMN_TITLE))
            val cover = p2?.getString(p2.getColumnIndex(DbContract.COLUMN_BACKDROP_PATH))

            val overview = p2?.getString(p2.getColumnIndex(DbContract.COLUMN_OVERVIEW))
            val popularity = p2?.getString(p2.getColumnIndex(DbContract.COLUMN_POPULARITY))
            val releaseDate = p2?.getString(p2.getColumnIndex(DbContract.COLUMN_RELEASE_DATE))
            tvTitle?.text = title
            tvOverview.text = overview
            tvPopularity.text = popularity
            tvReleaseDate.text = releaseDate

            Picasso.with(this@ViewListOfMoviesActivity)
                .load(NetworkUtils.buildImageUrl(cover).toString()).placeholder(R.drawable.defaultimage)
                .into(imageView)
        }
    }

    inner class GetMoviePageAsyncTask : AsyncTask<String, Void, String>() {

        override fun doInBackground(vararg p0: String?): String {
            val url: URL = NetworkUtils.buildUrl(p0.get(0)!!, getString(R.string.moviedb_api_key))!!
            return NetworkUtils.getResponseFromHttpUrl(url)!!
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            data = movieDBJsonUtils.getMovieDetailsFromJson(result!!)

            val cursor = contentResolver.query(DbContract.MOVIE_URI, arrayOf(DbContract.COLUMN_ID), null, null, null)
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    val id = cursor.getInt(cursor.getColumnIndex(DbContract.COLUMN_ID))
                    contentResolver.delete(DbContract.MOVIE_URI, "${DbContract.COLUMN_ID} = ?", arrayOf(id.toString()))
                }
            }
            cursor?.close()

            for (item in data!!) {
                val cvs = ContentValues()
                cvs.put(DbContract.COLUMN_ID, item.id)
                cvs.put(DbContract.COLUMN_POSTER_PATH, item.poster_path)
                cvs.put(DbContract.COLUMN_ADULT, item.adult)
                cvs.put(DbContract.COLUMN_OVERVIEW, item.overview)
                cvs.put(DbContract.COLUMN_RELEASE_DATE, item.release_date)
                cvs.put(DbContract.COLUMN_GENRE_IDS, item.genre_ids)
                cvs.put(DbContract.COLUMN_ORIGIAL_TITLE, item.original_title)
                cvs.put(DbContract.COLUMN_ORIGINAL_LANGUAGE, item.original_langauge)
                cvs.put(DbContract.COLUMN_TITLE, item.title)
                cvs.put(DbContract.COLUMN_BACKDROP_PATH, item.backdrop_path)
                cvs.put(DbContract.COLUMN_POPULARITY, item.popularity)
                cvs.put(DbContract.COLUMN_VOTE_COUNT, item.vote_count)
                cvs.put(DbContract.COLUMN_VIDEO, item.video)
                cvs.put(DbContract.COLUMN_VOTE_AVERAGE, item.vote_average)
                contentResolver.insert(DbContract.MOVIE_URI, cvs)
            }
        }
    }
}
