package me.yusufeka.moviecatalogue.movies.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class MovieDetail(

	@field:SerializedName("original_language")
	@ColumnInfo(name = "original_language")
	val originalLanguage: String,

	@field:SerializedName("imdb_id")
	@ColumnInfo(name = "imdb_id")
	val imdbId: String,

	@field:SerializedName("video")
	@ColumnInfo(name = "video")
	val video: Boolean,

	@field:SerializedName("title")
	@ColumnInfo(name = "title")
	val title: String,

	@field:SerializedName("backdrop_path")
	@ColumnInfo(name = "backdrop_path")
	val backdropPath: String,

	@field:SerializedName("revenue")
	@ColumnInfo(name = "revenue")
	val revenue: Int,

	@field:SerializedName("genres")
	@ColumnInfo(name = "genres")
	val genres: List<Genre>,

	@field:SerializedName("popularity")
	@ColumnInfo(name = "popularity")
	val popularity: Double,

	@field:SerializedName("production_countries")
	@ColumnInfo(name = "production_countries")
	val productionCountries: List<ProductionCountry>,

	@field:SerializedName("id")
	@PrimaryKey
	@ColumnInfo(name = "id")
	val id: Int,

	@field:SerializedName("vote_count")
	@ColumnInfo(name = "vote_count")
	val voteCount: Int,

	@field:SerializedName("budget")
	@ColumnInfo(name = "budget")
	val budget: Int,

	@field:SerializedName("overview")
	@ColumnInfo(name = "overview")
	val overview: String,

	@field:SerializedName("original_title")
	@ColumnInfo(name = "original_title")
	val originalTitle: String,

	@field:SerializedName("runtime")
	@ColumnInfo(name = "runtime")
	val runtime: Int,

	@field:SerializedName("poster_path")
	@ColumnInfo(name = "poster_path")
	val posterPath: String,

	@field:SerializedName("spoken_languages")
	@ColumnInfo(name = "spoken_languages")
	val spokenLanguages: List<SpokenLanguage>,

	@field:SerializedName("production_companies")
	@ColumnInfo(name = "production_companies")
	val productionCompanies: List<ProductionCompany>,

	@field:SerializedName("release_date")
	@ColumnInfo(name = "release_date")
	val releaseDate: String,

	@field:SerializedName("vote_average")
	@ColumnInfo(name = "vote_average")
	val voteAverage: Double,

	@field:SerializedName("tagline")
	@ColumnInfo(name = "tagline")
	val tagline: String,

	@field:SerializedName("adult")
	@ColumnInfo(name = "adult")
	val adult: Boolean,

	@field:SerializedName("homepage")
	@ColumnInfo(name = "homepage")
	val homepage: String,

	@field:SerializedName("status")
	@ColumnInfo(name = "status")
	val status: String
)