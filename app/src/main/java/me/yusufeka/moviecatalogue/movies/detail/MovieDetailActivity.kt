package me.yusufeka.moviecatalogue.movies.detail

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint
import es.dmoral.toasty.Toasty
import me.yusufeka.moviecatalogue.BuildConfig
import me.yusufeka.moviecatalogue.R
import me.yusufeka.moviecatalogue.RatingAdapter
import me.yusufeka.moviecatalogue.databinding.ActivityDetailBinding
import me.yusufeka.moviecatalogue.movies.model.MovieDetail
import me.yusufeka.moviecatalogue.utils.EspressoIdlingResource

@AndroidEntryPoint
class MovieDetailActivity : AppCompatActivity() {

    companion object {
        const val MOVIE_KEY = "movie"
    }

    private val viewModel: MovieDetailViewModel by viewModels()

    private var ratingAdapter = RatingAdapter{

    }
    private var shareText = ""

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        hideLabel()

        viewModel.isLoading().observe(this, {
            showLoading(it)
        })
        viewModel.getInfoMessage().observe(this, {
            if (it.isNotEmpty())
                Toasty.success(this, it, Toast.LENGTH_SHORT, true).show()
        })
        viewModel.getMovieDetail().observe(this, {
            it?.let { movie ->
                setDetail(movie)
                shareText = movie.title
                movie.genres.map { genre ->
                    binding.contentDetail.chipGroup.addView(addChip(genre.name))
                }
                ratingAdapter.rating = movie.voteAverage
            }
        })

        val id = intent.getIntExtra(MOVIE_KEY, 0)
        viewModel.updateMovieDetail(id)

        binding.contentDetail.rvRating.apply {
            adapter = ratingAdapter
            layoutManager = LinearLayoutManager(
                this@MovieDetailActivity,
                LinearLayoutManager.HORIZONTAL,
                false
            )
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.contentDetail.progressAnimationView.visibility = View.VISIBLE
            binding.contentDetail.progressAnimationView.playAnimation()
            EspressoIdlingResource.increment()
        } else {
            showLabel()
            binding.contentDetail.progressAnimationView.cancelAnimation()
            binding.contentDetail.progressAnimationView.visibility = View.INVISIBLE
            if (!EspressoIdlingResource.idlingResource.isIdleNow) {
                EspressoIdlingResource.decrement()
            }
        }
    }

    private fun showLabel() {
        binding.contentDetail.poster.visibility = View.VISIBLE
        binding.contentDetail.languageLabel.visibility = View.VISIBLE
        binding.contentDetail.revenueLabel.visibility = View.VISIBLE
        binding.contentDetail.releaseLabel.visibility = View.VISIBLE
        binding.contentDetail.overviewLabel.visibility = View.VISIBLE
    }

    private fun hideLabel() {
        binding.contentDetail.poster.visibility = View.INVISIBLE
        binding.contentDetail.languageLabel.visibility = View.INVISIBLE
        binding.contentDetail.revenueLabel.visibility = View.INVISIBLE
        binding.contentDetail.releaseLabel.visibility = View.INVISIBLE
        binding.contentDetail.overviewLabel.visibility = View.INVISIBLE
    }

    private fun setDetail(movie: MovieDetail) {
        val imageUrl = BuildConfig.BASE_IMAGE_URL + movie.posterPath
        Glide.with(this).load(imageUrl)
            .into(binding.contentDetail.poster)
        binding.contentDetail.movieTitle.text = movie.title
        binding.contentDetail.rating.text = movie.voteAverage.toString()
        binding.contentDetail.language.text = movie.originalLanguage
        binding.contentDetail.revenue.text = movie.revenue.toString()
        binding.contentDetail.storyline.text = movie.overview
        binding.contentDetail.year.text = movie.releaseDate
    }

    private fun addChip(genre: String): Chip {
        val chip = Chip(binding.contentDetail.chipGroup.context)
        chip.text = genre
        chip.isClickable = false
        chip.isCheckable = false
        chip.setTextColor(ContextCompat.getColor(this, R.color.white))
        chip.setChipBackgroundColorResource(R.color.colorPrimary)
        return chip
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.favorite -> {
                viewModel.toggleBookmark()
            }
            android.R.id.home -> finish()
            R.id.share -> {
                val sendIntent: Intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, shareText)
                    type = "text/plain"
                }

                val shareIntent = Intent.createChooser(sendIntent, null)
                startActivity(shareIntent)
            }
        }
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.share, menu)
        viewModel.isFavorited().observe(this, {
            if (it) {
                menu.getItem(0).icon = ContextCompat.getDrawable(
                    this,
                    R.drawable.ic_baseline_favorite_24_white
                )
            } else {
                menu.getItem(0).icon = ContextCompat.getDrawable(
                    this,
                    R.drawable.ic_baseline_favorite_border_24_white
                )
            }
        })
        return true
    }
}