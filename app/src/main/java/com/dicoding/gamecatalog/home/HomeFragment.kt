package com.dicoding.gamecatalog.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.gamecatalog.R
import com.dicoding.gamecatalog.core.ui.GameAdapter
import com.dicoding.gamecatalog.core.vo.Resource
import com.dicoding.gamecatalog.databinding.FragmentHomeBinding
import com.dicoding.gamecatalog.detail.DetailGameActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val homeViewModel: HomeViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity != null) {

            val gameAdapter = GameAdapter()
            gameAdapter.onItemClick = { selectedData ->
                val intent = Intent(activity, DetailGameActivity::class.java)
                intent.putExtra(DetailGameActivity.EXTRA_DATA, selectedData)
                startActivity(intent)
            }

            binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    binding.searchView.clearFocus()
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    homeViewModel.setSearchQuery(newText.orEmpty())
                    return true
                }
            })

            homeViewModel.game.observe(viewLifecycleOwner) { game ->
                if (game != null) {
                    when (game) {
                        is Resource.Loading -> {
                            binding.progressBar.visibility = View.VISIBLE
                            binding.viewError.root.visibility = View.GONE
                            binding.viewEmpty.root.visibility = View.GONE
                        }
                        is Resource.Success -> {
                            binding.progressBar.visibility = View.GONE
                            gameAdapter.submitList(game.data)
                            binding.viewEmpty.root.visibility =
                                if (game.data.isNullOrEmpty()) View.VISIBLE else View.GONE
                        }
                        is Resource.Error -> {
                            binding.progressBar.visibility = View.GONE
                            gameAdapter.submitList(emptyList())
                            binding.viewError.root.visibility = View.VISIBLE
                            binding.viewError.tvError.text =
                                game.message ?: getString(R.string.something_wrong)
                        }
                    }
                }
            }

            with(binding.rvGame) {
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                adapter = gameAdapter
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.rvGame.adapter = null
        _binding = null
    }
}
