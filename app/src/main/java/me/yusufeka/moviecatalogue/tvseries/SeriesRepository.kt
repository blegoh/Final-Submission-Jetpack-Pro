package me.yusufeka.moviecatalogue.tvseries

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import me.yusufeka.moviecatalogue.BuildConfig
import me.yusufeka.moviecatalogue.database.AppDatabase
import me.yusufeka.moviecatalogue.tvseries.models.*
import me.yusufeka.moviecatalogue.utils.ApiService
import java.util.concurrent.Executors
import javax.inject.Inject

class SeriesRepository @Inject constructor(
    private val service: ApiService,
    private val db: AppDatabase
) {

    private val seriesDataSource = SeriesDataSource(service)
    private val seriesDataSourceFactory = SeriesDataSourceFactory(seriesDataSource)

    private var seriesSearchDataSource = SeriesSearchDataSource(service, "")
    private var seriesSearchDataSourceFactory =
        SeriesSearchDataSourceFactory(seriesSearchDataSource)

    fun getSeries(): LiveData<PagedList<Series>> {
        val dataBuilder = LivePagedListBuilder(
            seriesDataSourceFactory,
            createConfig()
        )
        return dataBuilder.setFetchExecutor(Executors.newFixedThreadPool(5)).build()
    }

    fun getSeriesSearch(query: String): LiveData<PagedList<Series>> {
        seriesSearchDataSource = SeriesSearchDataSource(service, query)
        seriesSearchDataSourceFactory = SeriesSearchDataSourceFactory(seriesSearchDataSource)
        val dataBuilder = LivePagedListBuilder(
            seriesSearchDataSourceFactory,
            createConfig()
        )
        return dataBuilder.setFetchExecutor(Executors.newFixedThreadPool(5)).build()
    }

    fun getLoading() = seriesDataSource.getLoadingState()

    fun getError() = seriesDataSource.getErrorState()

    fun getSearchLoading() = seriesSearchDataSource.getLoadingState()

    fun getIsEmptySearch() = seriesSearchDataSource.getIsEmptyState()

    suspend fun getSeriesDetail(id: Int) = service.seriesDetail(id, BuildConfig.API_KEY)

    suspend fun getSeasonDetail(id: Int, season: Int) =
        service.seasonDetail(id, season, BuildConfig.API_KEY)

    fun getFavorites() = db.seriesDAO().getAll()

    suspend fun getFavorite(id: Int) = db.seriesDAO().findById(id)

    suspend fun addToBookmarks(series: SeriesDetail) = db.seriesDAO().insertAll(series)

    suspend fun removeFromBookmarks(series: SeriesDetail) = db.seriesDAO().delete(series)

    private fun createConfig(): PagedList.Config {
        val configBuilder = PagedList.Config.Builder()
        return configBuilder
            .setEnablePlaceholders(true)
            .setInitialLoadSizeHint(1)
            .setPrefetchDistance(10)
            .setPageSize(10)
            .build()
    }
}