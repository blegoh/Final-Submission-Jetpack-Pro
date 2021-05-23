package me.yusufeka.moviecatalogue.tvseries.models

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import me.yusufeka.moviecatalogue.BuildConfig
import me.yusufeka.moviecatalogue.utils.ApiService
import me.yusufeka.moviecatalogue.utils.EspressoIdlingResource

class SeriesSearchDataSource(
    private val service: ApiService,
    private val query: String
) : PageKeyedDataSource<Int, Series>() {

    private val loadingState = MutableLiveData<Boolean>()
    private val isEmptyState = MutableLiveData(false)

    fun getLoadingState() = loadingState

    fun getIsEmptyState() = isEmptyState

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Series>
    ) {
        loadingState.postValue(true)
        EspressoIdlingResource.increment()
        GlobalScope.launch {
            try {
                val response = service.seriesSearch(BuildConfig.API_KEY, query)
                callback.onResult(response.results, null, response.page + 1)
                if (response.results.isEmpty())
                    isEmptyState.postValue(true)
            } catch (exception: Exception) {
                Log.e("PostsDataSource", exception.message ?: "error")
            }
            if (!EspressoIdlingResource.idlingResource.isIdleNow) {
                EspressoIdlingResource.decrement()
            }
            loadingState.postValue(false)
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Series>) {

    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Series>) {
        loadingState.postValue(true)
        GlobalScope.launch {
            try {
                val response = service.seriesSearch(BuildConfig.API_KEY, query, page = params.key)
                callback.onResult(response.results, response.page + 1)

            } catch (exception: Exception) {
                Log.e("PostsDataSource", exception.message ?: "error")
            }
            loadingState.postValue(false)
        }
    }
}