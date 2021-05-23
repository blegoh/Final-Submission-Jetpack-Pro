package me.yusufeka.moviecatalogue.tvseries.models

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import me.yusufeka.moviecatalogue.BuildConfig
import me.yusufeka.moviecatalogue.utils.ApiService
import me.yusufeka.moviecatalogue.utils.EspressoIdlingResource

class SeriesDataSource(
    private val service: ApiService
) : PageKeyedDataSource<Int, Series>() {

    private val loadingState = MutableLiveData<Boolean>()
    private val errorState = MutableLiveData<String>()

    fun getLoadingState() = loadingState
    fun getErrorState() = errorState

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Series>
    ) {
        loadingState.postValue(true)
        EspressoIdlingResource.increment()
        GlobalScope.launch {
            try {
                val response = service.series(BuildConfig.API_KEY)
                callback.onResult(response.results, null, response.page + 1)
            } catch (exception: Exception) {
                Log.e("PostsDataSource", exception.message ?: "error")
                errorState.postValue(exception.message ?: "error")
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
                val response = service.series(BuildConfig.API_KEY, page = params.key)
                callback.onResult(response.results, response.page + 1)

            } catch (exception: Exception) {
                Log.e("PostsDataSource", exception.message ?: "error")
                errorState.postValue(exception.message ?: "error")
            }
            loadingState.postValue(false)
        }
    }
}