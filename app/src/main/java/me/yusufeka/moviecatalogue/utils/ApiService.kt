package me.yusufeka.moviecatalogue.utils

import me.yusufeka.moviecatalogue.movies.MovieResponse
import me.yusufeka.moviecatalogue.movies.model.MovieDetail
import me.yusufeka.moviecatalogue.tvseries.SeriesResponse
import me.yusufeka.moviecatalogue.tvseries.models.SeasonDetail
import me.yusufeka.moviecatalogue.tvseries.models.SeriesDetail
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("movie/popular")
    suspend fun movies(@Query("api_key") apiKey: String): MovieResponse

    @GET("movie/popular")
    suspend fun movies(
        @Query("api_key") apiKey: String,
        @Query("page") page: Int
    ): MovieResponse

    @GET("search/movie")
    suspend fun moviesSearch(
        @Query("api_key") apiKey: String,
        @Query("query") query: String
    ): MovieResponse

    @GET("search/movie")
    suspend fun moviesSearch(
        @Query("api_key") apiKey: String,
        @Query("query") query: String,
        @Query("page") page: Int
    ): MovieResponse

    @GET("movie/{id}")
    suspend fun movieDetail(
        @Path("id") id: Int,
        @Query("api_key") apiKey: String
    ): MovieDetail

    @GET("tv/popular")
    suspend fun series(@Query("api_key") apiKey: String): SeriesResponse

    @GET("tv/popular")
    suspend fun series(
        @Query("api_key") apiKey: String,
        @Query("page") page: Int
    ): SeriesResponse

    @GET("search/tv")
    suspend fun seriesSearch(
        @Query("api_key") apiKey: String,
        @Query("query") query: String
    ): SeriesResponse

    @GET("search/tv")
    suspend fun seriesSearch(
        @Query("api_key") apiKey: String,
        @Query("query") query: String,
        @Query("page") page: Int
    ): SeriesResponse

    @GET("tv/{id}")
    suspend fun seriesDetail(
        @Path("id") id: Int,
        @Query("api_key") apiKey: String
    ): SeriesDetail

    @GET("tv/{id}/season/{season}")
    suspend fun seasonDetail(
        @Path("id") id: Int,
        @Path("season") season: Int,
        @Query("api_key") apiKey: String
    ): SeasonDetail
}