package me.yusufeka.moviecatalogue.movies.model

import androidx.paging.DataSource

class MovieSearchDataSourceFactory(
    private val movieSearchDataSource: MovieSearchDataSource
) : DataSource.Factory<Int, Movie>() {
    override fun create(): DataSource<Int, Movie> {
        return movieSearchDataSource
    }
}