package io.github.tubb.fcrash.sample

import android.app.Service
import android.content.Intent
import android.os.Handler
import android.os.IBinder

class ProcessService : Service() {

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val bgmFriendlyCrash: Boolean? = intent?.getBooleanExtra("bgmFriendlyCrash", false)
        bgmFriendlyCrash?.let { friendly ->
            val app: App = application as App
            app.changeBgmFriendly(friendly)
        }
        Handler().postDelayed({
            throw RuntimeException("Background service crashed")
        }, 5000)
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(intent: Intent?): IBinder {
        throw RuntimeException("This service not support bind yet!")
    }

}