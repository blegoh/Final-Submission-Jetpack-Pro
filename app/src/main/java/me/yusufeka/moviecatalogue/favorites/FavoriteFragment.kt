package me.yusufeka.moviecatalogue.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentStatePagerAdapter
import me.yusufeka.moviecatalogue.databinding.FragmentFavoriteBinding
import me.yusufeka.moviecatalogue.favorites.movie.MovieFavFragment
import me.yusufeka.moviecatalogue.favorites.series.SeriesFavFragment
import me.yusufeka.moviecatalogue.utils.PageAdapter

class FavoriteFragment : Fragment() {

    private lateinit var pageAdapter: PageAdapter

    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pageAdapter = PageAdapter(
            childFragmentManager,
            FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
        )
        pageAdapter.titles = listOf("Movies", "TV Series")
        pageAdapter.pages = listOf(
            MovieFavFragment.newInstance(),
            SeriesFavFragment.newInstance()
        )
        binding.viewPagerFavorite.adapter = pageAdapter
        binding.tabs.setupWithViewPager(binding.viewPagerFavorite)
    }

    companion object {
        @JvmStatic
        fun newInstance() = FavoriteFragment()
    }
}