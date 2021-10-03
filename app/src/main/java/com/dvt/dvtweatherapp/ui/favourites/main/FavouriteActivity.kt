package com.dvt.dvtweatherapp.ui.favourites.main

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.dvt.dvtweatherapp.databinding.ActivityFavouriteBinding
import com.dvt.dvtweatherapp.ui.favourites.viewmodel.FavouritesViewModel
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.flow.collect
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class FavouriteActivity : AppCompatActivity() {

    private val binding: ActivityFavouriteBinding by lazy {
        ActivityFavouriteBinding.inflate(layoutInflater)
    }

    private val viewModel: FavouritesViewModel by viewModel()

    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setSupportActionBar(binding.favouritesToolbar)

        supportActionBar?.setDefaultDisplayHomeAsUpEnabled(true)
        setUpViewPager()
    }

    private fun setUpViewPager() {

        lifecycleScope.launchWhenStarted {
            viewModel.fetchfavourites().collect { favourites ->
                Timber.e("Favourites: $favourites")
                val pagerAdapter =
                    FavouritesPagerAdapter(supportFragmentManager, lifecycle, favourites)

                with(binding) {
                    favouritesPager.adapter = pagerAdapter
                    TabLayoutMediator(tabs, favouritesPager) { tab, position ->
                        if (position == 0) {
                            tab.text = "Favourites"
                        } else {
                            tab.text = "Map"
                        }
                    }.attach()
                }
            }
        }
    }
}