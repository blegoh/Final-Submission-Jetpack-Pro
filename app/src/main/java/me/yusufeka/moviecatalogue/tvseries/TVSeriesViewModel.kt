package me.yusufeka.moviecatalogue.tvseries

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import dagger.hilt.android.lifecycle.HiltViewModel
import me.yusufeka.moviecatalogue.tvseries.models.Series
import javax.inject.Inject

@HiltViewModel
class TVSeriesViewModel @Inject constructor(
    private val seriesRepository: SeriesRepository
) : ViewModel() {

    private var series: LiveData<PagedList<Series>>? = null

    private var searchLoading: LiveData<Boolean>? = null

    private var isEmptySearch: LiveData<Boolean>? = null

    fun isLoading() = seriesRepository.getLoading()

    fun isSearchLoading() = searchLoading

    fun getIsEmptySearch() = isEmptySearch

    fun getSeries() = seriesRepository.getSeries()

    fun getErrorMessage() = seriesRepository.getError()

    fun searchSeries(query: String) {
        series = seriesRepository.getSeriesSearch(query)
        searchLoading = seriesRepository.getSearchLoading()
        isEmptySearch = seriesRepository.getIsEmptySearch()
    }

    fun getSeasonSearch() = series


}