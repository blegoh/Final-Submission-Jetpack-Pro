package me.yusufeka.moviecatalogue.tvseries.models

import androidx.paging.DataSource

class SeriesDataSourceFactory(
    private val seriesDataSource: SeriesDataSource
) : DataSource.Factory<Int, Series>() {
    override fun create(): DataSource<Int, Series> {
        return seriesDataSource
    }
}