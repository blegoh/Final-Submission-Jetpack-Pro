package me.yusufeka.moviecatalogue.favorites.series

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import dagger.hilt.android.lifecycle.HiltViewModel
import me.yusufeka.moviecatalogue.tvseries.SeriesRepository
import me.yusufeka.moviecatalogue.tvseries.models.SeriesDetail
import javax.inject.Inject

@HiltViewModel
class SeriesFavViewModel @Inject constructor(
    private val seriesRepository: SeriesRepository
) : ViewModel() {

    fun getSeries(): LiveData<PagedList<SeriesDetail>> =
        LivePagedListBuilder(seriesRepository.getFavorites(), 20).build()


}