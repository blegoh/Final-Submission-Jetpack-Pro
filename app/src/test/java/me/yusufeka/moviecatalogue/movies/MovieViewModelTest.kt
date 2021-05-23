package me.yusufeka.moviecatalogue.movies

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import kotlinx.coroutines.runBlocking
import me.yusufeka.moviecatalogue.movies.model.Movie
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner.Silent::class)
class MovieViewModelTest {

    private lateinit var viewModel: MovieViewModel

    @Mock
    lateinit var movieRepository: MovieRepository

    @Mock
    lateinit var observerErrorMessage: Observer<String>

    @Mock
    lateinit var observerMovies: Observer<PagedList<Movie>>

    @Mock
    private lateinit var pagedList: PagedList<Movie>

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()


    @Before
    fun setUp() {
        viewModel = MovieViewModel(movieRepository)
    }

    @Test
    fun getMovies() {

        val dummyMovies = pagedList

        `when`(dummyMovies.size).thenReturn(5)

        val movies = MutableLiveData<PagedList<Movie>>()
        movies.value = dummyMovies

        runBlocking {
            `when`(movieRepository.getMovies())
                .thenReturn(movies)
            viewModel.getMovies().observeForever(observerMovies)
            verify(observerMovies).onChanged(dummyMovies)
        }
    }

    @Test
    fun getMoviesWithException() {

        val message = "http error"
        val error = MutableLiveData(message)

        runBlocking {
            `when`(movieRepository.getError())
                .thenReturn(error)

            viewModel.getErrorMessage().observeForever(observerErrorMessage)
            verify(observerErrorMessage).onChanged(message)
        }
    }

}