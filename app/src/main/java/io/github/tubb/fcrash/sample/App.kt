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
        FriendlyCrash.build(this) { isOnForeground ->
            if (isOnForeground) {
                Toast.makeText(this, "App moved to foreground", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "App moved to background", Toast.LENGTH_LONG).show()
            }
        }.enable { _, ex ->
            Log.e(TAG, "App crashed", ex)
        }
    }
}