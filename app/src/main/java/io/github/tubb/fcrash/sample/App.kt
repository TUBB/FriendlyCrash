package io.github.tubb.fcrash.sample

import android.app.Application
import android.util.Log
import android.widget.Toast
import io.github.tubb.fcrash.FriendlyCrash

class App: Application() {

    companion object {
        private const val TAG = "FriendlyCrash"
    }

    override fun onCreate() {
        super.onCreate()
        // friendly crash when app on background
        FriendlyCrash.build(this, ::appMovedTo)
                .friendlyOnForeground(true)
                .enable(::appCrashed)
    }

    private fun appCrashed(onForeground: Boolean, thread: Thread, ex: Throwable) {
        val msg = if (onForeground) {
            "when app on foreground"
        } else {
            "when app on background"
        }
        Log.e(TAG, "App crashed $msg", ex)
    }

    private fun appMovedTo(isOnForeground: Boolean) {
        if (isOnForeground) {
            Toast.makeText(this, "App moved to foreground", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(this, "App moved to background", Toast.LENGTH_LONG).show()
        }
    }
}