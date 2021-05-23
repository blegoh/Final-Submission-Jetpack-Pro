package me.yusufeka.moviecatalogue.movies.model

import com.google.gson.annotations.SerializedName

data class ProductionCountry(

	@field:SerializedName("iso_3166_1")
	val iso31661: String,

	@field:SerializedName("name")
	val name: String
)