package com.dicoding.gamecatalog.detail

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.IntentCompat.getParcelableExtra
import com.bumptech.glide.Glide
import com.dicoding.gamecatalog.R
import com.dicoding.gamecatalog.core.ui.model.GameModel
import com.dicoding.gamecatalog.core.vo.Resource
import com.dicoding.gamecatalog.databinding.ActivityDetailGameBinding
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailGameActivity : AppCompatActivity() {

    private val detailGameViewModel: DetailGameViewModel by viewModel()
    private lateinit var binding: ActivityDetailGameBinding

    private var statusFavorite = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailGameBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val detailGame = getParcelableExtra(intent, EXTRA_DATA, GameModel::class.java)
        if (detailGame != null) {
            showDetailGame(detailGame)
        } else {
            finish()
        }
    }

    private fun showDetailGame(detailGame: GameModel) {
        var currentGame = detailGame

        binding.toolbarLayout.title = detailGame.name
        Glide.with(this@DetailGameActivity)
            .load(detailGame.backgroundImage)
            .into(binding.ivDetailImage)

        with(binding.contentDetailGame) {
            tvDetailName.text = detailGame.name
            tvDetailRating.text = getString(R.string.rating_format, detailGame.rating)
            tvDetailReleased.text = detailGame.released.ifEmpty { getString(R.string.tba) }
            tvDetailGenres.text = detailGame.genres.ifEmpty { "-" }
            tvDetailDescription.text = detailGame.description
        }

        detailGameViewModel.getDetailGame(detailGame.id).observe(this) { game ->
            if (game != null) {
                when (game) {
                    is Resource.Loading -> {
                        binding.contentDetailGame.progressBar.visibility = View.VISIBLE
                    }
                    is Resource.Success -> {
                        binding.contentDetailGame.progressBar.visibility = View.GONE
                        game.data?.let {
                            currentGame = it
                            binding.contentDetailGame.tvDetailGenres.text = it.genres.ifEmpty { "-" }
                            binding.contentDetailGame.tvDetailDescription.text =
                                it.description.ifEmpty { getString(R.string.no_description) }
                        }
                    }
                    is Resource.Error -> {
                        binding.contentDetailGame.progressBar.visibility = View.GONE
                        if (currentGame.description.isEmpty()) {
                            binding.contentDetailGame.tvDetailDescription.text =
                                getString(R.string.no_description)
                        }
                    }
                }
            }
        }

        detailGameViewModel.isFavoriteGame(detailGame.id).observe(this) { isFavorite ->
            statusFavorite = isFavorite
            setStatusFavorite(statusFavorite)
        }

        binding.fab.setOnClickListener {
            val newStatus = !statusFavorite
            detailGameViewModel.setFavoriteGame(currentGame, newStatus)
            val message = if (newStatus) R.string.added_to_favorite else R.string.removed_from_favorite
            Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
        }
    }

    private fun setStatusFavorite(statusFavorite: Boolean) {
        if (statusFavorite) {
            binding.fab.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_favorite_white))
        } else {
            binding.fab.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_not_favorite_white))
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    companion object {
        const val EXTRA_DATA = "extra_data"
    }
}
