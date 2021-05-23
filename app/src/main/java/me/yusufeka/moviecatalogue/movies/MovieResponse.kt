package me.yusufeka.moviecatalogue.movies

import com.google.gson.annotations.SerializedName
import me.yusufeka.moviecatalogue.movies.model.Movie

data class MovieResponse(

    @field:SerializedName("page")
	val page: Int,

    @field:SerializedName("total_pages")
	val totalPages: Int,

    @field:SerializedName("results")
	val results: List<Movie>,

    @field:SerializedName("total_results")
	val totalResults: Int
)