package me.yusufeka.moviecatalogue.favorites.movie

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
import me.yusufeka.moviecatalogue.movies.model.MovieDetail
import me.yusufeka.moviecatalogue.movies.detail.MovieDetailActivity
import org.jetbrains.anko.startActivity

class MovieFavAdapter(private val context: Context) :
    PagedListAdapter<MovieDetail, MovieFavAdapter.MovieFavViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK: DiffUtil.ItemCallback<MovieDetail> =
            object : DiffUtil.ItemCallback<MovieDetail>() {
                override fun areItemsTheSame(
                    oldMovie: MovieDetail,
                    newMovie: MovieDetail
                ): Boolean {
                    return oldMovie.id == newMovie.id
                }

                @SuppressLint("DiffUtilEquals")
                override fun areContentsTheSame(
                    oldMovie: MovieDetail,
                    newMovie: MovieDetail
                ): Boolean {
                    return oldMovie == newMovie
                }
            }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MovieFavAdapter.MovieFavViewHolder {
        val binding = MovieItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieFavViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieFavViewHolder, position: Int) {
        holder.bind(getItem(position) as MovieDetail)
    }

    inner class MovieFavViewHolder(private val binding: MovieItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: MovieDetail) {
            with(binding) {
                movieTitle.text = movie.title
                rating.text = movie.voteAverage.toString()
                releaseDate.text = movie.releaseDate
                val imageUrl = BuildConfig.BASE_IMAGE_URL + movie.posterPath
                Glide.with(context).load(imageUrl)
                    .into(poster)
                val ratingAdapter = RatingAdapter {
                    context.startActivity<MovieDetailActivity>(
                        MovieDetailActivity.MOVIE_KEY to movie.id
                    )
                }
                ratingAdapter.rating = movie.voteAverage
                rvRating.apply {
                    adapter = ratingAdapter
                    layoutManager = LinearLayoutManager(
                        context,
                        LinearLayoutManager.HORIZONTAL,
                        false
                    )
                }
                movieCard.setOnClickListener {
                    context.startActivity<MovieDetailActivity>(
                        MovieDetailActivity.MOVIE_KEY to movie.id
                    )
                }
            }
        }
    }
}