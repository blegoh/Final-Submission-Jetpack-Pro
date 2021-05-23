package me.yusufeka.moviecatalogue.tvseries.models

import com.google.gson.annotations.SerializedName

data class GuestStar(

	@field:SerializedName("character")
	val character: String,

	@field:SerializedName("gender")
	val gender: Int,

	@field:SerializedName("credit_id")
	val creditId: String,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("profile_path")
	val profilePath: String,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("order")
	val order: Int
)