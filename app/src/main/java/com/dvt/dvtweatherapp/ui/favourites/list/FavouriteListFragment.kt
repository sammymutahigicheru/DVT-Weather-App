package com.dvt.dvtweatherapp.ui.favourites.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.dvt.data.database.entities.Favourites
import com.dvt.dvtweatherapp.databinding.FragmentFavouriteListBinding
import timber.log.Timber

class FavouriteListFragment(
    private val favourites: List<Favourites> = listOf()
) : Fragment() {

    private val binding: FragmentFavouriteListBinding by lazy {
        FragmentFavouriteListBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpView()
    }

    private fun setUpView() {
        Timber.e("Favourites: $favourites")
        val favouriteListAdapter = FavouriteListAdapter()
        binding.favouritesRecyclerview.apply {
            adapter = favouriteListAdapter
            hasFixedSize()
        }
        favouriteListAdapter.submitList(favourites)
    }


}