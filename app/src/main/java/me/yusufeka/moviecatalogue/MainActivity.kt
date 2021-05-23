package me.yusufeka.moviecatalogue

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager
import dagger.hilt.android.AndroidEntryPoint
import me.yusufeka.moviecatalogue.databinding.ActivityMainBinding
import me.yusufeka.moviecatalogue.favorites.FavoriteFragment
import me.yusufeka.moviecatalogue.movies.MovieFragment
import me.yusufeka.moviecatalogue.tvseries.TVSeriesFragment
import me.yusufeka.moviecatalogue.utils.PageAdapter

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var pageAdapter: PageAdapter
    private lateinit var binding: ActivityMainBinding
    private val titles = listOf("Movies", "TV Series", "Favorites")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        pageAdapter = PageAdapter(
            supportFragmentManager,
            FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
        )
        pageAdapter.titles = titles
        title = titles[0]
        pageAdapter.pages = listOf(
            MovieFragment.newInstance(),
            TVSeriesFragment.newInstance(),
            FavoriteFragment.newInstance()
        )
        binding.viewPager.adapter = pageAdapter
        binding.bottomNavigation.setOnNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.movie -> {
                    binding.viewPager.currentItem = 0
                    title = titles[0]
                    true
                }
                R.id.series -> {
                    binding.viewPager.currentItem = 1
                    title = titles[1]
                    true
                }
                R.id.favorite -> {
                    binding.viewPager.currentItem = 2
                    title = titles[2]
                    true
                }
                else -> false
            }
        }

        binding.viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {

            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }
            override fun onPageSelected(position: Int) {
                val ids = listOf(R.id.movie,R.id.series,R.id.favorite)
                title = titles[position]
                binding.bottomNavigation.selectedItemId = ids[position]
            }

        })
    }
}