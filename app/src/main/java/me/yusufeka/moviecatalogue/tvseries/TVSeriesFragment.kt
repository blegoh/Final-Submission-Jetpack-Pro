package me.yusufeka.moviecatalogue.tvseries

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import es.dmoral.toasty.Toasty
import me.yusufeka.moviecatalogue.R
import me.yusufeka.moviecatalogue.databinding.MovieFragmentBinding
import me.yusufeka.moviecatalogue.tvseries.models.Series

@AndroidEntryPoint
class TVSeriesFragment : Fragment() {

    companion object {
        fun newInstance() = TVSeriesFragment()
    }

    private val viewModel: TVSeriesViewModel by viewModels()
    private lateinit var seriesAdapter: SeriesAdapter
    private lateinit var seriesSearchAdapter: SeriesAdapter
    private var _binding: MovieFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

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

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        context?.let {
            seriesAdapter = SeriesAdapter(it)
            seriesSearchAdapter = SeriesAdapter(it)
        }
        viewModel.getSeries().observe(viewLifecycleOwner, seriesObserver)
        viewModel.isLoading().observe(viewLifecycleOwner, {
            showLoading(it)
        })

        binding.rvMovies.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = seriesAdapter
        }
    }

    private val seriesObserver = Observer<PagedList<Series>> { series ->
        if (series != null) {
            seriesAdapter.submitList(series)
        }
    }

    private val seriesSearchObserver = Observer<PagedList<Series>> { series ->
        if (series != null) {
            seriesSearchAdapter.submitList(series)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressAnimationView.visibility = View.VISIBLE
            binding.progressAnimationView.playAnimation()
        } else {
            binding.progressAnimationView.cancelAnimation()
            binding.progressAnimationView.visibility = View.INVISIBLE
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView

        searchView.queryHint = getString(R.string.series_hint)

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(s: String): Boolean {
                binding.rvMovies.adapter = seriesSearchAdapter
                viewModel.searchSeries(s)
                viewModel.getSeasonSearch()?.observe(viewLifecycleOwner, seriesSearchObserver)
                viewModel.isSearchLoading()?.observe(viewLifecycleOwner, {
                    showLoading(it)
                })
                viewModel.getIsEmptySearch()?.observe(viewLifecycleOwner, {
                    if (it) {
                        binding.message.text = getString(R.string.empty_search)
                        binding.message.visibility = View.VISIBLE
                    } else {
                        binding.message.visibility = View.GONE
                    }
                })
                return false
            }

            override fun onQueryTextChange(s: String): Boolean {
                return false
            }
        })

        searchItem.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {

            override fun onMenuItemActionCollapse(item: MenuItem): Boolean {
                binding.message.visibility = View.GONE
                binding.rvMovies.adapter = seriesAdapter
                return true
            }

            override fun onMenuItemActionExpand(item: MenuItem): Boolean {
                return true
            }
        })
    }

}