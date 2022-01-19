package com.fabledt5.moviescleanarchitecture.presentation.ui.movie

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.fabledt5.moviescleanarchitecture.R
import com.fabledt5.moviescleanarchitecture.databinding.FragmentTrailerBinding
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener

@SuppressLint("SourceLockedOrientationActivity")
class TrailerFragment : Fragment(R.layout.fragment_trailer) {

    private val binding: FragmentTrailerBinding by viewBinding()
    private val navArgs: TrailerFragmentArgs by navArgs()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initUi()
    }

    private fun initUi() {
        lifecycle.addObserver(binding.youtubePlayer)

        binding.youtubePlayer.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                youTubePlayer.loadVideo(navArgs.trailerPath, 0f)
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.youtubePlayer.release()
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }
}