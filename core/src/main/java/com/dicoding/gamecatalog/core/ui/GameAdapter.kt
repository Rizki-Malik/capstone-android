package com.dicoding.gamecatalog.core.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.gamecatalog.core.R
import com.dicoding.gamecatalog.core.databinding.ItemListGameBinding
import com.dicoding.gamecatalog.core.ui.model.GameModel

class GameAdapter : ListAdapter<GameModel, GameAdapter.ListViewHolder>(DIFF_CALLBACK) {

    var onItemClick: ((GameModel) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ListViewHolder(
            ItemListGameBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val data = getItem(position)
        holder.bind(data)
    }

    inner class ListViewHolder(private var binding: ItemListGameBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: GameModel) {
            Glide.with(itemView.context)
                .load(data.backgroundImage)
                .placeholder(R.color.image_placeholder)
                .into(binding.ivItemImage)
            binding.tvItemTitle.text = data.name
            binding.tvItemRating.text = itemView.context.getString(R.string.rating_format, data.rating)
            binding.tvItemReleased.text = data.released.ifEmpty {
                itemView.context.getString(R.string.tba)
            }
            binding.tvItemGenres.text = data.genres
            binding.tvItemGenres.visibility = if (data.genres.isEmpty()) View.GONE else View.VISIBLE
        }

        init {
            binding.root.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onItemClick?.invoke(getItem(position))
                }
            }
        }
    }

    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<GameModel> =
            object : DiffUtil.ItemCallback<GameModel>() {
                override fun areItemsTheSame(oldItem: GameModel, newItem: GameModel): Boolean {
                    return oldItem.id == newItem.id
                }

                override fun areContentsTheSame(oldItem: GameModel, newItem: GameModel): Boolean {
                    return oldItem == newItem
                }
            }
    }
}
