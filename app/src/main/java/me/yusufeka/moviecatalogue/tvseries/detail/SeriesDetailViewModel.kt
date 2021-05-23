package me.yusufeka.moviecatalogue.tvseries.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import me.yusufeka.moviecatalogue.tvseries.SeriesRepository
import me.yusufeka.moviecatalogue.tvseries.models.Episode
import me.yusufeka.moviecatalogue.tvseries.models.Season
import me.yusufeka.moviecatalogue.tvseries.models.SeriesDetail
import javax.inject.Inject

@HiltViewModel
class SeriesDetailViewModel @Inject constructor(
    private val seriesRepository: SeriesRepository
) : ViewModel() {

    private var series: MutableLiveData<SeriesDetail?> = MutableLiveData(null)

    private var episodes: MutableLiveData<List<Episode>> = MutableLiveData()

    private var favorited: MutableLiveData<Boolean> = MutableLiveData(false)

    private var loading: MutableLiveData<Boolean> = MutableLiveData(false)

    private var activeSeason: MutableLiveData<Int?> = MutableLiveData(null)

    private var errorMessage: MutableLiveData<String> = MutableLiveData("")

    private var infoMessage: MutableLiveData<String> = MutableLiveData("")

    fun isLoading(): LiveData<Boolean> = loading

    fun isFavorited(): LiveData<Boolean> = favorited

    fun getSeriesDetail(): LiveData<SeriesDetail?> = series

    fun getEpisodes(): LiveData<List<Episode>> = episodes

    fun getActiveSeason(): LiveData<Int?> = activeSeason

    fun getErrorMessage(): LiveData<String> = errorMessage

    fun getInfoMessage(): LiveData<String> = infoMessage

    fun changeActiveSeason(id: Int, season: Season) {
        activeSeason.value = season.seasonNumber
        updateSeasonDetail(id, season.seasonNumber)
    }

    suspend fun updateBookmarkStatus() {
        series.value?.let {
            val movie = seriesRepository.getFavorite(it.id)
            favorited.value = movie.isNotEmpty()
        }
    }

    fun toggleBookmark() = viewModelScope.launch {
        series.value?.let {
            if (favorited.value!!) {
                seriesRepository.removeFromBookmarks(it)
                infoMessage.value = "Removed from bookmarks"
            } else {
                seriesRepository.addToBookmarks(it)
                infoMessage.value = "Added to bookmarks"
            }
        }

        favorited.value = !favorited.value!!
    }

    fun updateSeriesDetail(id: Int) = viewModelScope.launch {
        loading.value = true
        try {
            val res = seriesRepository.getSeriesDetail(id)
            series.value = res
            if (res.seasons.isNotEmpty())
                activeSeason.value = res.seasons[0].seasonNumber
            updateBookmarkStatus()
        } catch (ex: Exception) {
            errorMessage.value = ex.message
        }
        loading.value = false
    }

    fun updateSeasonDetail(id: Int, season: Int) = viewModelScope.launch {
        episodes.value = listOf()
        try {
            val res = seriesRepository.getSeasonDetail(id, season)
            episodes.value = res.episodes
        } catch (ex: Exception) {
            errorMessage.value = ex.message
        }
    }
}