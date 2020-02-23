package com.nyp.sit.it2107.movieviewer.entity

import java.io.Serializable


class MovieItem(var poster_path: String? = null, var adult: Boolean? = null,var overview: String? = null,
                var release_date: String? = null, var genre_ids: String? = null, var id: Int = 0,
                var original_title: String? = null, var original_langauge: String? = null,
                var title: String? = null, var backdrop_path: String? = null, var popularity: Double = 0.0,
                var vote_count: Int = 0, var video: Boolean? = null, var vote_average: Double = 0.0)
    : Serializable {





   init{

       this.poster_path = poster_path
       this.adult = adult
       this.overview= overview
       this.release_date= release_date
       this.genre_ids = genre_ids
       this.id = id
       this.original_title = original_title
       this.original_langauge = original_langauge
       this.title = title
       this.backdrop_path = backdrop_path
       this.popularity = popularity
       this.vote_count = vote_count
       this.video = video
       this.vote_average = vote_average

   }
}
