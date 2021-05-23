package me.yusufeka.moviecatalogue.tvseries.models

import com.google.gson.annotations.SerializedName

data class Episode(

    @field:SerializedName("production_code")
	val productionCode: String,

    @field:SerializedName("overview")
	val overview: String,

    @field:SerializedName("show_id")
	val showId: Int,

    @field:SerializedName("season_number")
	val seasonNumber: Int,

    @field:SerializedName("still_path")
	val stillPath: String,

    @field:SerializedName("crew")
	val crew: List<CrewItem>,

    @field:SerializedName("guest_stars")
	val guestStars: List<GuestStar>,

    @field:SerializedName("air_date")
	val airDate: String,

    @field:SerializedName("episode_number")
	val episodeNumber: Int,

    @field:SerializedName("vote_average")
	val voteAverage: Double,

    @field:SerializedName("name")
	val name: String,

    @field:SerializedName("id")
	val id: Int,

    @field:SerializedName("vote_count")
	val voteCount: Int
)