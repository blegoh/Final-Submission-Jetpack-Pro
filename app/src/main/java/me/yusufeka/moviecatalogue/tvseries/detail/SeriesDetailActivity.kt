package me.yusufeka.moviecatalogue.tvseries.detail

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
import me.yusufeka.moviecatalogue.databinding.ActivitySeriesDetailBinding
import me.yusufeka.moviecatalogue.tvseries.models.SeriesDetail
import me.yusufeka.moviecatalogue.utils.EspressoIdlingResource

@AndroidEntryPoint
class SeriesDetailActivity : AppCompatActivity() {

    companion object {
        const val SERIES_KEY = "series"
    }

    private var ratingAdapter = RatingAdapter {

    }
    private lateinit var seasonAdapter: SeasonAdapter
    private lateinit var episodeAdapter: EpisodeAdapter
    private var shareText = ""
    private var seriesId = 0

    private val viewModel: SeriesDetailViewModel by viewModels()

    private lateinit var binding: ActivitySeriesDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySeriesDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        hideLabel()

        seriesId = intent.getIntExtra(SERIES_KEY, 0)
        viewModel.updateSeriesDetail(seriesId)

        seasonAdapter = SeasonAdapter(this@SeriesDetailActivity) {
            viewModel.changeActiveSeason(seriesId, it)
        }
        episodeAdapter = EpisodeAdapter(this@SeriesDetailActivity)

        viewModel.isLoading().observe(this, {
            showLoading(it)
        })
        viewModel.getInfoMessage().observe(this, {
            if (it.isNotEmpty())
                Toasty.success(this, it, Toast.LENGTH_SHORT, true).show()
        })
        viewModel.getSeriesDetail().observe(this, {
            it?.let { series ->
                shareText = series.name
                setDetail(series)
                ratingAdapter.rating = series.voteAverage
                seasonAdapter.seasons = series.seasons
                viewModel.updateSeasonDetail(series.id, series.seasons[0].seasonNumber)
                series.genres.map { genre ->
                    binding.contentSeriesDetail.chipGroup.addView(addChip(genre.name))
                }
            }
        })
        viewModel.getActiveSeason().observe(this, { activeSeason ->
            activeSeason?.let {
                seasonAdapter.activeSeason = it
            }
        })
        viewModel.getEpisodes().observe(this, {
            episodeAdapter.episodes = it
        })

        binding.contentSeriesDetail.rvEpisode.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = episodeAdapter
        }

        binding.contentSeriesDetail.rvRating.apply {
            adapter = ratingAdapter
            layoutManager = LinearLayoutManager(
                this@SeriesDetailActivity,
                LinearLayoutManager.HORIZONTAL,
                false
            )
        }

        binding.contentSeriesDetail.rvSeason.apply {
            adapter = seasonAdapter
            layoutManager = LinearLayoutManager(
                this@SeriesDetailActivity,
                LinearLayoutManager.HORIZONTAL,
                false
            )
        }
    }

    private fun hideLabel() {
        binding.contentSeriesDetail.apply {
            poster.visibility = View.INVISIBLE
            languageLabel.visibility = View.INVISIBLE
            countryLabel.visibility = View.INVISIBLE
            releaseLabel.visibility = View.INVISIBLE
        }
    }

    private fun showLabel() {
        binding.contentSeriesDetail.apply {
            poster.visibility = View.VISIBLE
            languageLabel.visibility = View.VISIBLE
            countryLabel.visibility = View.VISIBLE
            releaseLabel.visibility = View.VISIBLE
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.contentSeriesDetail.apply {
                progressAnimationView.visibility = View.VISIBLE
                progressAnimationView.playAnimation()
            }
            EspressoIdlingResource.increment()
        } else {
            showLabel()
            binding.contentSeriesDetail.apply {
                progressAnimationView.cancelAnimation()
                progressAnimationView.visibility = View.INVISIBLE
            }
            if (!EspressoIdlingResource.idlingResource.isIdleNow) {
                EspressoIdlingResource.decrement()
            }
        }
    }

    private fun setDetail(series: SeriesDetail) {
        binding.contentSeriesDetail.apply {
            val imageUrl = BuildConfig.BASE_IMAGE_URL + series.posterPath
            Glide.with(this@SeriesDetailActivity).load(imageUrl)
                .into(poster)
            seriesTitle.text = series.name
            language.text = series.originalLanguage
            country.text = series.originCountry[0]
            year.text = series.firstAirDate
            rating.text = series.voteAverage.toString()
        }
    }

    private fun addChip(genre: String): Chip {
        val chip = Chip(binding.contentSeriesDetail.chipGroup.context)
        chip.text = genre
        chip.isClickable = false
        chip.isCheckable = false
        chip.setTextColor(ContextCompat.getColor(this, R.color.white))
        chip.setChipBackgroundColorResource(R.color.colorPrimary)
        return chip
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
            R.id.favorite -> {
                viewModel.toggleBookmark()
            }
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