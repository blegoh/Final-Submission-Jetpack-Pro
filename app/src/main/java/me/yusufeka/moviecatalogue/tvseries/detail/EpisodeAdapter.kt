package me.yusufeka.moviecatalogue.tvseries.detail

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import me.yusufeka.moviecatalogue.BuildConfig
import me.yusufeka.moviecatalogue.RatingAdapter
import me.yusufeka.moviecatalogue.databinding.EpisodeItemBinding
import me.yusufeka.moviecatalogue.tvseries.models.Episode

class EpisodeAdapter(private val context: Context) :
    RecyclerView.Adapter<EpisodeAdapter.ViewHolder>() {

    var episodes: List<Episode> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    inner class ViewHolder(private val binding: EpisodeItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(episode: Episode) {
            binding.episodeTitle.text = episode.name
            binding.rating.text = episode.voteAverage.toString()
            binding.date.text = episode.airDate
            val imageUrl = BuildConfig.BASE_IMAGE_URL + episode.stillPath
            Glide.with(context).load(imageUrl)
                .into(binding.poster)
            val ratingAdapter = RatingAdapter{

            }
            ratingAdapter.rating = episode.voteAverage
            binding.rvRating.apply {
                adapter = ratingAdapter
                layoutManager = LinearLayoutManager(
                    context, LinearLayoutManager.HORIZONTAL, false
                )
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = EpisodeItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(episodes[position])
    }

    override fun getItemCount() = episodes.size
}