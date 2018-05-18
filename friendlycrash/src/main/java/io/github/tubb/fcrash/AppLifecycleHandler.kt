package io.github.tubb.fcrash

interface AppLifecycleHandler {
    fun movedToForeground()
    fun movedToBackground()
}