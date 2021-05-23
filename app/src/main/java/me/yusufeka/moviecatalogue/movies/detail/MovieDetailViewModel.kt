package me.yusufeka.moviecatalogue.movies.detail

import android.accounts.NetworkErrorException
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import me.yusufeka.moviecatalogue.movies.MovieRepository
import me.yusufeka.moviecatalogue.movies.model.MovieDetail
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val movieRepository: MovieRepository
) : ViewModel() {

    private var movie: MutableLiveData<MovieDetail?> = MutableLiveData(null)

    private var favorited: MutableLiveData<Boolean> = MutableLiveData(false)

    private var loading: MutableLiveData<Boolean> = MutableLiveData(false)

    private var errorMessage: MutableLiveData<String> = MutableLiveData("")

    private var infoMessage: MutableLiveData<String> = MutableLiveData("")

    fun isLoading(): LiveData<Boolean> = loading

    fun isFavorited(): LiveData<Boolean> = favorited

    fun getErrorMessage(): LiveData<String> = errorMessage

    fun getInfoMessage(): LiveData<String> = infoMessage

    fun getMovieDetail(): LiveData<MovieDetail?> = movie

    suspend fun updateBookmarkStatus() {
        movie.value?.let {
            val movie = movieRepository.getFavorite(it.id)
            favorited.value = movie.isNotEmpty()
        }
    }

    fun toggleBookmark() = viewModelScope.launch {
        movie.value?.let {
            if (favorited.value!!) {
                movieRepository.removeFromBookmarks(it)
                infoMessage.value = "Removed from bookmarks"
            } else {
                movieRepository.addToBookmarks(it)
                infoMessage.value = "Added to bookmarks"
            }
        }

        favorited.value = !favorited.value!!
    }


    fun updateMovieDetail(id: Int) = viewModelScope.launch {
        loading.value = true
        try {
            val res = movieRepository.getMovieDetail(id)
            movie.value = res
            updateBookmarkStatus()
        } catch (ex: NetworkErrorException) {
            errorMessage.value = ex.message
        } catch (ex: Exception) {
            errorMessage.value = ex.message
        }
        loading.value = false
    }
}