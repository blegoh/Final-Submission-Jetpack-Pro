package me.yusufeka.moviecatalogue.movies

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import me.yusufeka.moviecatalogue.BuildConfig
import me.yusufeka.moviecatalogue.database.AppDatabase
import me.yusufeka.moviecatalogue.movies.model.*
import me.yusufeka.moviecatalogue.utils.ApiService
import java.util.concurrent.Executors
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val service: ApiService,
    private val db: AppDatabase
) {

    private val movieDataSource = MovieDataSource(service)
    private val movieDataSourceFactory = MovieDataSourceFactory(movieDataSource)

    private var movieSearchDataSource = MovieSearchDataSource(service,"")
    private var movieSearchDataSourceFactory = MovieSearchDataSourceFactory(movieSearchDataSource)

    fun getMovies(): LiveData<PagedList<Movie>> {
        val dataBuilder = LivePagedListBuilder(
            movieDataSourceFactory,
            createConfig()
        )
        return dataBuilder.setFetchExecutor(Executors.newFixedThreadPool(5)).build()
    }

    fun getMoviesSearch(query: String): LiveData<PagedList<Movie>> {
        movieSearchDataSource = MovieSearchDataSource(service,query)
        movieSearchDataSourceFactory = MovieSearchDataSourceFactory(movieSearchDataSource)
        val dataBuilder = LivePagedListBuilder(
            movieSearchDataSourceFactory,
            createConfig()
        )
        return dataBuilder.setFetchExecutor(Executors.newFixedThreadPool(5)).build()
    }

    fun getLoading() = movieDataSource.getLoadingState()


    fun getError() = movieDataSource.getErrorState()

    fun getSearchLoading() = movieSearchDataSource.getLoadingState()

    fun getIsEmptySearch() = movieSearchDataSource.getIsEmptyState()

    suspend fun getMovieDetail(id: Int) = service.movieDetail(id, BuildConfig.API_KEY)

    fun getFavorites() = db.movieDAO().getAll()

    suspend fun getFavorite(id: Int) = db.movieDAO().findById(id)

    suspend fun addToBookmarks(movie: MovieDetail) = db.movieDAO().insertAll(movie)

    suspend fun removeFromBookmarks(movie: MovieDetail) = db.movieDAO().delete(movie)

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