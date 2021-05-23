package me.yusufeka.moviecatalogue.movies

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.setMain
import me.yusufeka.moviecatalogue.movies.detail.MovieDetailViewModel
import me.yusufeka.moviecatalogue.movies.model.MovieDetail
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MovieDetailViewModelTest {

    private lateinit var viewModel: MovieDetailViewModel

    @Mock
    lateinit var movieRepository: MovieRepository

    @Mock
    lateinit var observerLoading: Observer<Boolean>

    @Mock
    lateinit var observerFav: Observer<Boolean>

    @Mock
    lateinit var observerMovieDetail: Observer<MovieDetail?>

    @Mock
    lateinit var observerErrorMessage: Observer<String>

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()


    @Before
    fun setUp() {
        Dispatchers.setMain(Dispatchers.Unconfined)
        viewModel = MovieDetailViewModel(
            movieRepository
        )
        viewModel.isLoading().observeForever(observerLoading)
        viewModel.getMovieDetail().observeForever(observerMovieDetail)
        viewModel.getErrorMessage().observeForever(observerErrorMessage)
        viewModel.isFavorited().observeForever(observerFav)
    }


    @Test
    fun getMovieDetail() {
        assertTrue(viewModel.isLoading().hasObservers())
        assertTrue(viewModel.getMovieDetail().hasObservers())
        assertTrue(viewModel.isFavorited().hasObservers())

        val response = MovieDetail(
            "", "", false, "", "", 0, listOf(), 0.1,
            listOf(), 1, 1, 1, "", "", 0,
            "", listOf(), listOf(), "", 1.1,
            "", false, "", ""
        )

        runBlocking {
            `when`(movieRepository.getMovieDetail(anyInt()))
                .thenReturn(response)

            `when`(movieRepository.getFavorite(anyInt()))
                .thenReturn(listOf())

            viewModel.updateMovieDetail(anyInt())

            verify(observerLoading, times(1)).onChanged(true)
            verify(observerMovieDetail).onChanged(response)
            verify(observerLoading, times(2)).onChanged(false)
            verify(observerFav, times(2)).onChanged(false)

            viewModel.toggleBookmark()

            verify(observerFav).onChanged(true)

        }
    }

    @Test
    fun getMovieDetailWithException() {
        assertTrue(viewModel.isLoading().hasObservers())
        assertTrue(viewModel.getErrorMessage().hasObservers())
        val exception = RuntimeException("Exception")

        runBlocking {
            `when`(movieRepository.getMovieDetail(anyInt()))
                .thenThrow(exception)

            viewModel.updateMovieDetail(anyInt())

            verify(observerLoading, times(1))?.onChanged(true)
            verify(observerErrorMessage).onChanged(exception.message)
            verify(observerLoading, times(2))?.onChanged(false)
        }
    }
}