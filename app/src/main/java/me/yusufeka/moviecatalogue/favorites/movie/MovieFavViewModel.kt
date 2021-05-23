package me.yusufeka.moviecatalogue.favorites.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import dagger.hilt.android.lifecycle.HiltViewModel
import me.yusufeka.moviecatalogue.movies.MovieRepository
import me.yusufeka.moviecatalogue.movies.model.MovieDetail
import javax.inject.Inject

@HiltViewModel
class MovieFavViewModel @Inject constructor(
    private val movieRepository: MovieRepository
) : ViewModel() {

    private var loading: MutableLiveData<Boolean> = MutableLiveData(false)

    private var errorMessage: MutableLiveData<String> = MutableLiveData("")

    fun isLoading(): LiveData<Boolean> = loading

    fun getErrorMessage(): LiveData<String> = errorMessage

    fun getMovies(): LiveData<PagedList<MovieDetail>> = LivePagedListBuilder(movieRepository.getFavorites(), 20).build()



}