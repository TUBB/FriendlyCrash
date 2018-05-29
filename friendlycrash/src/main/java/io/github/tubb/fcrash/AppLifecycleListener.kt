package io.github.tubb.fcrash

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.OnLifecycleEvent

internal class AppLifecycleListener: LifecycleObserver {
    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onMoveToForeground() {
        FriendlyCrash.instance().appMoveToForeground()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onMoveToBackground() {
        FriendlyCrash.instance().appMoveToBackground()
    }
}