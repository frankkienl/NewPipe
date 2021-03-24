package org.schabi.newpipe.car

import android.graphics.Rect
import android.view.Surface
import androidx.car.app.AppManager
import androidx.car.app.CarContext
import androidx.car.app.SurfaceCallback
import androidx.car.app.SurfaceContainer
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner

class NPSurface(val carContext: CarContext, lifecycle: Lifecycle) {

    private val myLifecycleObserver = object : DefaultLifecycleObserver {
        override fun onCreate(owner: LifecycleOwner) {
            carContext.getCarService(AppManager::class.java).setSurfaceCallback(mySurfaceCallback)
        }
    }

    val mySurfaceCallback = object : SurfaceCallback {
        override fun onSurfaceAvailable(surfaceContainer: SurfaceContainer) {
            surface = surfaceContainer.surface
        }

        override fun onVisibleAreaChanged(visibleArea: Rect) {
        }

        override fun onStableAreaChanged(stableArea: Rect) {
        }

        override fun onSurfaceDestroyed(surfaceContainer: SurfaceContainer) {
            surface = null
        }
    }

    init {
        lifecycle.addObserver(myLifecycleObserver)
    }

    companion object {
        var surface: Surface? = null
    }
}
