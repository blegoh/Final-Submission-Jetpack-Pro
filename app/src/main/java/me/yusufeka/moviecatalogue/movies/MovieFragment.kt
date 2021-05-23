package me.yusufeka.moviecatalogue.movies

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
import me.yusufeka.moviecatalogue.movies.model.Movie

@AndroidEntryPoint
class MovieFragment : Fragment() {

    private val viewModel: MovieViewModel by viewModels()
    private lateinit var movieAdapter: MovieAdapter
    private lateinit var movieSearchAdapter: MovieAdapter
    private var _binding: MovieFragmentBinding? = null
    private val binding get() = _binding!!

    companion object {
        fun newInstance() = MovieFragment()
    }

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
            movieAdapter = MovieAdapter(it)
            movieSearchAdapter = MovieAdapter(it)
        }
        viewModel.getMovies().observe(viewLifecycleOwner, movieObserver)
        viewModel.isLoading().observe(viewLifecycleOwner, {
            showLoading(it)
        })

        viewModel.getErrorMessage().observe(viewLifecycleOwner, {
            if (it.isNotBlank()) {
                context?.let { ctx ->
                    Toasty.error(ctx, it, Toast.LENGTH_SHORT, true).show()
                }
            }
        })

        binding.rvMovies.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = movieAdapter
        }
    }

    private val movieObserver = Observer<PagedList<Movie>> { movies ->
        if (movies != null) {
            movieAdapter.submitList(movies)
        }
    }

    private val movieSearchObserver = Observer<PagedList<Movie>> { movies ->
        if (movies != null) {
            movieSearchAdapter.submitList(movies)
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

        searchView.queryHint = getString(R.string.movie_hint)

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(s: String): Boolean {
                binding.rvMovies.adapter = movieSearchAdapter
                viewModel.searchMovie(s)
                viewModel.getMoviesSearch()?.observe(viewLifecycleOwner, movieSearchObserver)
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
                binding.rvMovies.adapter = movieAdapter
                return true // Return true to collapse action view
            }

            override fun onMenuItemActionExpand(item: MenuItem): Boolean {
                return true
            }
        })
    }
}