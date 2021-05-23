package me.yusufeka.moviecatalogue.movies.model

import com.google.gson.annotations.SerializedName

data class SpokenLanguage(

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("iso_639_1")
	val iso6391: String
)