package me.yusufeka.moviecatalogue.movies.model

import androidx.paging.DataSource

class MovieDataSourceFactory(
    private val movieDataSource: MovieDataSource
) : DataSource.Factory<Int, Movie>() {
    override fun create(): DataSource<Int, Movie> {
        return movieDataSource
    }
}