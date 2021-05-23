package me.yusufeka.moviecatalogue.tvseries

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import kotlinx.coroutines.runBlocking
import me.yusufeka.moviecatalogue.tvseries.models.Series
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner.Silent::class)
class TVSeriesViewModelTest {

    private lateinit var viewModel: TVSeriesViewModel

    @Mock
    lateinit var seriesRepository: SeriesRepository

    @Mock
    lateinit var observerSeries: Observer<List<Series>>

    @Mock
    lateinit var observerErrorMessage: Observer<String>

    @Mock
    private lateinit var pagedList: PagedList<Series>

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()


    @Before
    fun setUp() {
        viewModel = TVSeriesViewModel(
            seriesRepository
        )
    }


    @Test
    fun getSeries() {
        val dummySeries = pagedList

        Mockito.`when`(dummySeries.size).thenReturn(5)

        val series = MutableLiveData<PagedList<Series>>()
        series.value = dummySeries

        runBlocking {
            Mockito.`when`(seriesRepository.getSeries())
                .thenReturn(series)
            viewModel.getSeries().observeForever(observerSeries)
            Mockito.verify(observerSeries).onChanged(dummySeries)
        }
    }

    @Test
    fun getSeriesWithException() {
        val message = "http error"
        val error = MutableLiveData(message)

        runBlocking {
            Mockito.`when`(seriesRepository.getError())
                .thenReturn(error)

            viewModel.getErrorMessage().observeForever(observerErrorMessage)
            Mockito.verify(observerErrorMessage).onChanged(message)
        }
    }
}