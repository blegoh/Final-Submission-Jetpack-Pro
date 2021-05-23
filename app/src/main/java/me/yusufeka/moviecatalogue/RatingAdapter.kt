package me.yusufeka.moviecatalogue

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import me.yusufeka.moviecatalogue.databinding.StarItemBinding
import kotlin.math.ceil

class RatingAdapter(
    private val listener: () -> Unit
) : RecyclerView.Adapter<RatingAdapter.ViewHolder>() {

    var rating: Double = 0.0
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    inner class ViewHolder(private val binding: StarItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun create(isFullStar: Boolean) {
            if (isFullStar)
                binding.star.setImageResource(R.drawable.ic_baseline_star_24)
            else
                binding.star.setImageResource(R.drawable.ic_baseline_star_half_24)
            binding.star.setOnClickListener {
                listener.invoke()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = StarItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (position + 1 > rating / 2)
            holder.create(false)
        else
            holder.create(true)
    }

    override fun getItemCount(): Int {
        return ceil(rating / 2).toInt()
    }
}