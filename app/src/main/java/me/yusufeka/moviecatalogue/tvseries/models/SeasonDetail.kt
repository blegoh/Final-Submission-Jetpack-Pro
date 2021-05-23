package me.yusufeka.moviecatalogue.tvseries.models

import com.google.gson.annotations.SerializedName

data class SeasonDetail(

    @field:SerializedName("air_date")
	val airDate: String,

    @field:SerializedName("overview")
	val overview: String,

    @field:SerializedName("name")
	val name: String,

    @field:SerializedName("season_number")
	val seasonNumber: Int,

    @field:SerializedName("_id")
	val _id: String,

    @field:SerializedName("id")
	val id: Int,

    @field:SerializedName("episodes")
	val episodes: List<Episode>,

    @field:SerializedName("poster_path")
	val posterPath: String
)