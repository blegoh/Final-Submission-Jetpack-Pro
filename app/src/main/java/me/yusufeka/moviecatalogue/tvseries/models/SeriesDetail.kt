package me.yusufeka.moviecatalogue.tvseries.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import me.yusufeka.moviecatalogue.movies.model.Genre
import me.yusufeka.moviecatalogue.movies.model.ProductionCompany

@Entity
data class SeriesDetail(

    @field:SerializedName("original_language")
	@ColumnInfo(name = "original_language")
    val originalLanguage: String,

    @field:SerializedName("number_of_episodes")
	@ColumnInfo(name = "number_of_episodes")
    val numberOfEpisodes: Int,

    @field:SerializedName("networks")
	@ColumnInfo(name = "networks")
    val networks: List<Network>,

    @field:SerializedName("type")
	@ColumnInfo(name = "type")
    val type: String,

    @field:SerializedName("backdrop_path")
	@ColumnInfo(name = "backdrop_path")
    val backdropPath: String,

    @field:SerializedName("genres")
	@ColumnInfo(name = "genres")
    val genres: List<Genre>,

    @field:SerializedName("popularity")
	@ColumnInfo(name = "popularity")
    val popularity: Double,

    @field:SerializedName("id")
	@ColumnInfo(name = "id")
    @PrimaryKey
    val id: Int,

    @field:SerializedName("number_of_seasons")
	@ColumnInfo(name = "number_of_seasons")
    val numberOfSeasons: Int,

    @field:SerializedName("vote_count")
	@ColumnInfo(name = "vote_count")
    val voteCount: Int,

    @field:SerializedName("first_air_date")
	@ColumnInfo(name = "first_air_date")
    val firstAirDate: String,

    @field:SerializedName("overview")
	@ColumnInfo(name = "overview")
    val overview: String,

    @field:SerializedName("seasons")
	@ColumnInfo(name = "seasons")
    val seasons: List<Season>,

    @field:SerializedName("languages")
	@ColumnInfo(name = "languages")
    val languages: List<String>,

    @field:SerializedName("created_by")
	@ColumnInfo(name = "created_by")
    val createdBy: List<CreatedBy>,

    @field:SerializedName("last_episode_to_air")
	@ColumnInfo(name = "last_episode_to_air")
    val lastEpisodeToAir: LastEpisodeToAir,

    @field:SerializedName("poster_path")
	@ColumnInfo(name = "poster_path")
    val posterPath: String,

    @field:SerializedName("origin_country")
	@ColumnInfo(name = "origin_country")
    val originCountry: List<String>,

    @field:SerializedName("production_companies")
	@ColumnInfo(name = "production_companies")
    val productionCompanies: List<ProductionCompany>,

    @field:SerializedName("original_name")
	@ColumnInfo(name = "original_name")
    val originalName: String,

    @field:SerializedName("vote_average")
	@ColumnInfo(name = "vote_average")
    val voteAverage: Double,

    @field:SerializedName("name")
	@ColumnInfo(name = "name")
    val name: String,

    @field:SerializedName("episode_run_time")
	@ColumnInfo(name = "episode_run_time")
    val episodeRunTime: List<Int>,

    @field:SerializedName("in_production")
	@ColumnInfo(name = "in_production")
    val inProduction: Boolean,

    @field:SerializedName("last_air_date")
	@ColumnInfo(name = "last_air_date")
    val lastAirDate: String,

    @field:SerializedName("homepage")
	@ColumnInfo(name = "homepage")
    val homepage: String,

    @field:SerializedName("status")
	@ColumnInfo(name = "status")
    val status: String
)