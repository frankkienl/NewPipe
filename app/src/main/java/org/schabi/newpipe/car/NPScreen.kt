package org.schabi.newpipe.car

import android.content.Context
import android.media.AudioManager
import android.view.KeyEvent
import androidx.car.app.CarContext
import androidx.car.app.Screen
import androidx.car.app.model.Action
import androidx.car.app.model.ActionStrip
import androidx.car.app.model.CarColor
import androidx.car.app.model.Template
import androidx.car.app.navigation.model.NavigationTemplate

class NPScreen(carContext: CarContext) : Screen(carContext) {
    override fun onGetTemplate(): Template {
        val builder = NavigationTemplate.Builder()
        builder.setBackgroundColor(CarColor.RED)
        builder.setActionStrip(
            ActionStrip.Builder()
                .addAction(
                    Action.Builder()
                        .setTitle("Play/pause")
                        .setOnClickListener {
                            clickedPlayPause()
                        }.build()
                )
                .build()
        )
        builder.build()
        return builder.build()
    }

    private fun clickedPlayPause() {
        val audio = carContext.getSystemService(Context.AUDIO_SERVICE) as AudioManager
        audio.dispatchMediaKeyEvent(
            KeyEvent(
                KeyEvent.ACTION_DOWN,
                KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE
            )
        )
        audio.dispatchMediaKeyEvent(
            KeyEvent(
                KeyEvent.ACTION_UP,
                KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE
            )
        )
    }
}
