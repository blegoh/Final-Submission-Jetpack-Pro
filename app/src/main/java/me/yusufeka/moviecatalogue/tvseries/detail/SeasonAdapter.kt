package me.yusufeka.moviecatalogue.tvseries.detail

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import me.yusufeka.moviecatalogue.R
import me.yusufeka.moviecatalogue.databinding.SeasonItemBinding
import me.yusufeka.moviecatalogue.tvseries.models.Season

class SeasonAdapter(private val context: Context,private val listener: (season: Season) -> Unit) :
    RecyclerView.Adapter<SeasonAdapter.ViewHolder>() {

    var seasons: List<Season> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var activeSeason: Int = 0
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    inner class ViewHolder(private val binding: SeasonItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(season: Season, active: Boolean) {
            binding.seasonTitle.text = season.name
            if (active) {
                binding.seasonTitle.setTextColor(
                    ContextCompat.getColor(context, R.color.white)
                )
                binding.seasonTitle.setChipBackgroundColorResource(R.color.colorPrimary)
            } else {
                binding.seasonTitle.setTextColor(
                    ContextCompat.getColor(context, R.color.black)
                )
                binding.seasonTitle.setChipBackgroundColorResource(R.color.disable)
            }
            binding.seasonTitle.setOnClickListener {
                listener.invoke(season)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = SeasonItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(seasons[position], position == activeSeason)
    }

    override fun getItemCount(): Int {
        return seasons.size
    }
}