package me.yusufeka.moviecatalogue.tvseries.models

import com.google.gson.annotations.SerializedName

data class CrewItem(

	@field:SerializedName("gender")
	val gender: Int,

	@field:SerializedName("credit_id")
	val creditId: String,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("profile_path")
	val profilePath: Any,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("department")
	val department: String,

	@field:SerializedName("job")
	val job: String
)