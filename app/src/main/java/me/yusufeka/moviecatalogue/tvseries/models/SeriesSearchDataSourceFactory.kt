package me.yusufeka.moviecatalogue.tvseries.models

import androidx.paging.DataSource

class SeriesSearchDataSourceFactory(
    private val seriesSearchDataSource: SeriesSearchDataSource
) : DataSource.Factory<Int, Series>() {
    override fun create(): DataSource<Int, Series> {
        return seriesSearchDataSource
    }
}