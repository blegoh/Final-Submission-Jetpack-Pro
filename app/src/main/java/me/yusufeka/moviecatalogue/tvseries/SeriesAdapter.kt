package me.yusufeka.moviecatalogue.tvseries

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import me.yusufeka.moviecatalogue.BuildConfig
import me.yusufeka.moviecatalogue.RatingAdapter
import me.yusufeka.moviecatalogue.databinding.MovieItemBinding
import me.yusufeka.moviecatalogue.tvseries.detail.SeriesDetailActivity
import me.yusufeka.moviecatalogue.tvseries.models.Series
import org.jetbrains.anko.startActivity

class SeriesAdapter(private val context: Context) :
    PagedListAdapter<Series, SeriesAdapter.MovieFavViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK: DiffUtil.ItemCallback<Series> =
            object : DiffUtil.ItemCallback<Series>() {
                override fun areItemsTheSame(
                    oldSeries: Series,
                    newSeries: Series
                ): Boolean {
                    return oldSeries.id == newSeries.id
                }

                @SuppressLint("DiffUtilEquals")
                override fun areContentsTheSame(
                    oldSeries: Series,
                    newSeries: Series
                ): Boolean {
                    return oldSeries == newSeries
                }
            }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SeriesAdapter.MovieFavViewHolder {
        val binding = MovieItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieFavViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieFavViewHolder, position: Int) {
        holder.bind(getItem(position) as Series)
    }

    inner class MovieFavViewHolder(private val binding: MovieItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(series: Series) {
            with(binding) {
                movieTitle.text = series.name
                rating.text = series.voteAverage.toString()
                releaseDate.text = series.firstAirDate
                val imageUrl = BuildConfig.BASE_IMAGE_URL + series.posterPath
                Glide.with(context).load(imageUrl)
                    .into(poster)
                val ratingAdapter = RatingAdapter{
                    context.startActivity<SeriesDetailActivity>(
                        SeriesDetailActivity.SERIES_KEY to series.id
                    )
                }
                ratingAdapter.rating = series.voteAverage
                rvRating.apply {
                    adapter = ratingAdapter
                    layoutManager = LinearLayoutManager(
                        context,
                        LinearLayoutManager.HORIZONTAL,
                        false
                    )
                }
                movieCard.setOnClickListener {
                    context.startActivity<SeriesDetailActivity>(
                        SeriesDetailActivity.SERIES_KEY to series.id
                    )
                }
            }
        }
    }
}