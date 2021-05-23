package me.yusufeka.moviecatalogue.favorites.series

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import me.yusufeka.moviecatalogue.R
import me.yusufeka.moviecatalogue.databinding.MovieFragmentBinding
import me.yusufeka.moviecatalogue.tvseries.models.SeriesDetail
import me.yusufeka.moviecatalogue.utils.EspressoIdlingResource

@AndroidEntryPoint
class SeriesFavFragment : Fragment() {

    private var _binding: MovieFragmentBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SeriesFavViewModel by viewModels()
    private lateinit var seriesAdapter: SeriesFavAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MovieFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance() = SeriesFavFragment()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.getSeries().observe(viewLifecycleOwner, seriesObserver)
        context?.let {
            seriesAdapter = SeriesFavAdapter(it)
        }

        binding.rvMovies.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = seriesAdapter
        }
    }

    private val seriesObserver = Observer<PagedList<SeriesDetail>> { series ->
        seriesAdapter.submitList(series)
        if (series.size == 0){
            binding.message.text = getString(R.string.series_fav_empty)
            binding.message.visibility = View.VISIBLE
        }else{
            binding.message.visibility = View.GONE
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressAnimationView.visibility = View.VISIBLE
            binding.progressAnimationView.playAnimation()
            EspressoIdlingResource.increment()
        } else {
            binding.progressAnimationView.cancelAnimation()
            binding.progressAnimationView.visibility = View.INVISIBLE
            if (!EspressoIdlingResource.idlingResource.isIdleNow) {
                EspressoIdlingResource.decrement()
            }
        }
    }
}