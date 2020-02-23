package com.nyp.sit.it2107.movieviewer

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ImageView
import android.widget.TextView
import com.nyp.sit.it2107.movieviewer.entity.MovieItem
import com.squareup.picasso.Picasso


class ItemDetailActivity : AppCompatActivity() {

    var ivPoster: ImageView? = null
    var tvOverview: TextView? = null
    var tvReaseDate: TextView? = null
    var tvPopularity: TextView? = null
    var tvVoteCount: TextView? = null
    var tvVoteAvg: TextView? = null
    var tvLanguage: TextView? = null
    var tvIsAdault: TextView? = null
    var tvHasVideo: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_detail)

        ivPoster = findViewById(R.id.posterIV)
        tvOverview = findViewById(R.id.movie_overview)
        tvReaseDate = findViewById(R.id.movie_release_date)
        tvPopularity = findViewById(R.id.movie_popularity)
        tvVoteCount = findViewById(R.id.movie_vote_count)
        tvVoteAvg = findViewById(R.id.movie_vote_avg)
        tvLanguage = findViewById(R.id.movie_langauge)
        tvIsAdault = findViewById(R.id.movie_is_adult)
        tvHasVideo = findViewById(R.id.movie_hasvideo)

        val data: MovieItem = intent.getSerializableExtra("item") as MovieItem
        Picasso.with(this)
            .load(NetworkUtils.buildImageUrl(data.backdrop_path).toString()).placeholder(R.drawable.defaultimage)
            .into(ivPoster)
        tvOverview?.setText(data.overview)
        tvReaseDate?.setText(data.release_date)
        tvLanguage?.setText(data.original_langauge)
        tvPopularity?.setText(data.popularity.toString())
        tvVoteCount?.setText(data.vote_count.toString())
        tvVoteAvg?.setText(data.vote_average.toString())
        tvIsAdault?.setText(data.adult.toString())
        tvHasVideo?.setText(data.video.toString())
    }

}
