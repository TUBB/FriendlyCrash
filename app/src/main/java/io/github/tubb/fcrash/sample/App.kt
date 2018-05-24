package io.github.tubb.fcrash.sample

import android.app.Application
import android.util.Log
import android.widget.Toast
import io.github.tubb.fcrash.FriendlyCrash
import android.app.ActivityManager
import android.content.Context

class App: Application() {

    companion object {
        private const val TAG = "FriendlyCrash"
    }

    override fun onCreate() {
        super.onCreate()
        val processName = getProcessName(this, android.os.Process.myPid())
        processName?.let { name ->
            if (name == packageName) { // main process
                FriendlyCrash.build(this, ::appMovedTo)
                        .friendlyOnForeground(false)
                        .enable(::appCrashed)
            } else { // background process
                FriendlyCrash.build(this)
                        .friendlyOnForeground(true)
                        .enable()
            }
        }
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

    private fun getProcessName(cxt: Context, pid: Int): String? {
        val am = cxt.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val runningApps = am.runningAppProcesses ?: return null
        return runningApps
                .firstOrNull { it.pid == pid }
                ?.processName
    }
}