package me.yusufeka.moviecatalogue.tvseries

import com.google.gson.annotations.SerializedName
import me.yusufeka.moviecatalogue.tvseries.models.Series

data class SeriesResponse(

    @field:SerializedName("page")
	val page: Int,

    @field:SerializedName("total_pages")
	val totalPages: Int,

    @field:SerializedName("results")
	val results: List<Series>,

    @field:SerializedName("total_results")
	val totalResults: Int
)