package me.yusufeka.moviecatalogue.movies

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
import me.yusufeka.moviecatalogue.movies.model.Movie
import me.yusufeka.moviecatalogue.movies.detail.MovieDetailActivity
import org.jetbrains.anko.startActivity

class MovieAdapter(private val context: Context) :
    PagedListAdapter<Movie, MovieAdapter.MovieFavViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK: DiffUtil.ItemCallback<Movie> =
            object : DiffUtil.ItemCallback<Movie>() {
                override fun areItemsTheSame(
                    oldMovie: Movie,
                    newMovie: Movie
                ): Boolean {
                    return oldMovie.id == newMovie.id
                }

                @SuppressLint("DiffUtilEquals")
                override fun areContentsTheSame(
                    oldMovie: Movie,
                    newMovie: Movie
                ): Boolean {
                    return oldMovie == newMovie
                }
            }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MovieAdapter.MovieFavViewHolder {
        val binding = MovieItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieFavViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieFavViewHolder, position: Int) {
        holder.bind(getItem(position) as Movie)
    }

    inner class MovieFavViewHolder(private val binding: MovieItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Movie) {
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