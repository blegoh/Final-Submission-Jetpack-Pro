package me.yusufeka.moviecatalogue.favorites.series

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
import me.yusufeka.moviecatalogue.tvseries.models.SeriesDetail
import org.jetbrains.anko.startActivity

class SeriesFavAdapter(private val context: Context) :
    PagedListAdapter<SeriesDetail, SeriesFavAdapter.SeriesFavViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK: DiffUtil.ItemCallback<SeriesDetail> =
            object : DiffUtil.ItemCallback<SeriesDetail>() {
                override fun areItemsTheSame(
                    oldSeries: SeriesDetail,
                    newSeries: SeriesDetail
                ): Boolean {
                    return oldSeries.id == newSeries.id
                }

                @SuppressLint("DiffUtilEquals")
                override fun areContentsTheSame(
                    oldSeries: SeriesDetail,
                    newSeries: SeriesDetail
                ): Boolean {
                    return oldSeries == newSeries
                }
            }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SeriesFavAdapter.SeriesFavViewHolder {
        val binding = MovieItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SeriesFavViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SeriesFavViewHolder, position: Int) {
        holder.bind(getItem(position) as SeriesDetail)
    }

    inner class SeriesFavViewHolder(private val binding: MovieItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(series: SeriesDetail) {
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