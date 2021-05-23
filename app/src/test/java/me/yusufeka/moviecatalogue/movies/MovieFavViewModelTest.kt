package me.yusufeka.moviecatalogue.movies

import androidx.paging.DataSource
import com.nhaarman.mockitokotlin2.verify
import me.yusufeka.moviecatalogue.favorites.movie.MovieFavViewModel
import me.yusufeka.moviecatalogue.movies.model.MovieDetail
import me.yusufeka.moviecatalogue.utils.PagedListUtil
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner.Silent::class)
class MovieFavViewModelTest {

    private lateinit var viewModel: MovieFavViewModel

    @Mock
    lateinit var movieRepository: MovieRepository

    @Before
    fun setUp() {
        viewModel = MovieFavViewModel(movieRepository)
    }

    @Test
    fun getFavoritedMovie() {
        val dataSourceFactory =
            Mockito.mock(DataSource.Factory::class.java) as DataSource.Factory<Int, MovieDetail>
        Mockito.`when`(movieRepository.getFavorites()).thenReturn(dataSourceFactory)

        viewModel.getMovies()
        val movies = PagedListUtil.mockPagedList(getDummyMovie())
        verify(movieRepository).getFavorites()
        assertNotNull(movies)
    }

    private fun getDummyMovie(): List<MovieDetail> {
        return listOf(
            MovieDetail(
                "", "", false, "", "", 0, listOf(), 0.1,
                listOf(), 1, 1, 1, "", "", 0,
                "", listOf(), listOf(), "", 1.1,
                "", false, "", ""
            ), MovieDetail(
                "", "", false, "", "", 0, listOf(), 0.1,
                listOf(), 1, 1, 1, "", "", 0,
                "", listOf(), listOf(), "", 1.1,
                "", false, "", ""
            ), MovieDetail(
                "", "", false, "", "", 0, listOf(), 0.1,
                listOf(), 1, 1, 1, "", "", 0,
                "", listOf(), listOf(), "", 1.1,
                "", false, "", ""
            )
        )
    }
}