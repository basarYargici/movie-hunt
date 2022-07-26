package com.basar.moviehunter.ui.player

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.MediaController
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.navArgs
import com.basar.moviehunter.databinding.ActivityLocalPlayerBinding

class LocalPlayerActivity : AppCompatActivity() {
    private val args: LocalPlayerActivityArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityLocalPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mediaController = MediaController(this)
        mediaController.setAnchorView(binding.vv)
        binding.vv.setMediaController(mediaController)

        binding.vv.setVideoPath(args.videoPath)
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        binding.vv.start()
    }

    // TODO: investigate on this
    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) {
            window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
        }
    }
}