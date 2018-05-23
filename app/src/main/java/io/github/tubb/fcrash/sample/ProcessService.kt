package io.github.tubb.fcrash.sample

import android.app.Service
import android.content.Intent
import android.os.Handler
import android.os.IBinder

class ProcessService : Service() {

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Handler().postDelayed({
            throw RuntimeException("Background service crashed")
        }, 1000)
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(intent: Intent?): IBinder {
        throw RuntimeException("This service not support bind yet!")
    }

}