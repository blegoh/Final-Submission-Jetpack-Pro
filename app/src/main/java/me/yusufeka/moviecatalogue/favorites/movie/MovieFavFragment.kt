package me.yusufeka.moviecatalogue.favorites.movie

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import es.dmoral.toasty.Toasty
import me.yusufeka.moviecatalogue.R
import me.yusufeka.moviecatalogue.databinding.MovieFragmentBinding
import me.yusufeka.moviecatalogue.movies.model.MovieDetail
import me.yusufeka.moviecatalogue.utils.EspressoIdlingResource

@AndroidEntryPoint
class MovieFavFragment : Fragment() {

    private val viewModel: MovieFavViewModel by viewModels()
    private lateinit var movieAdapter: MovieFavAdapter
    private var _binding: MovieFragmentBinding? = null
    private val binding get() = _binding!!

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
        fun newInstance() = MovieFavFragment()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.getMovies().observe(viewLifecycleOwner, movieObserver)
        context?.let {
            movieAdapter = MovieFavAdapter(it)
        }
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

    private val movieObserver = Observer<PagedList<MovieDetail>> { movieList ->
        movieAdapter.submitList(movieList)
        if (movieList.size == 0){
            binding.message.text = getString(R.string.movie_fav_empty)
            binding.message.visibility = View.VISIBLE
        }else{
            binding.message.visibility = View.GONE
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
}