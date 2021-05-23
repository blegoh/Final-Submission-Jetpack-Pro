package me.yusufeka.moviecatalogue.movies

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import dagger.hilt.android.lifecycle.HiltViewModel
import me.yusufeka.moviecatalogue.movies.model.Movie
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val movieRepository: MovieRepository
) : ViewModel() {

    private var movies: LiveData<PagedList<Movie>>? = null

    private var searchLoading: LiveData<Boolean>? = null

    private var isEmptySearch: LiveData<Boolean>? = null

    fun isLoading() = movieRepository.getLoading()

    fun isSearchLoading() = searchLoading

    fun getIsEmptySearch() = isEmptySearch

    fun getErrorMessage() = movieRepository.getError()

    fun getMovies() = movieRepository.getMovies()

    fun searchMovie(query: String) {
        movies = movieRepository.getMoviesSearch(query)
        searchLoading = movieRepository.getSearchLoading()
        isEmptySearch = movieRepository.getIsEmptySearch()
    }

    fun getMoviesSearch() = movies

}