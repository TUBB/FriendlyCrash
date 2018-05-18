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
                Log.d(TAG, "App moved to foreground")
            } else {
                Toast.makeText(this, "App moved to background", Toast.LENGTH_LONG).show()
                Log.d(TAG, "App moved to background")
            }
        }.enable { thread, ex ->
            ex.printStackTrace()
        }
    }
}