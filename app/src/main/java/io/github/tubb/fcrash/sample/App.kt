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
        var appFriendly: Boolean = false
        var bgmFriendly: Boolean = false
        lateinit var appFriendlyCrash: FriendlyCrash
        lateinit var bgmFriendlyCrash: FriendlyCrash
    }

    override fun onCreate() {
        super.onCreate()
        initFriendlyCrash()
    }

    private fun initFriendlyCrash() {
        val processName = getProcessName(this, android.os.Process.myPid())
        processName?.let { name ->
            if (name == packageName) { // main process
                appFriendlyCrash = FriendlyCrash.build(this, ::appMovedTo)
                appFriendlyCrash.friendlyOnForeground(appFriendly).enable(::appCrashed)
            } else { // background process
                bgmFriendlyCrash = FriendlyCrash.build(this)
                bgmFriendlyCrash.friendlyOnForeground(bgmFriendly).enable()
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

    private fun appMovedTo(foreground: Boolean) {
        if (foreground) {
            Toast.makeText(this, "App moved to foreground", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(this, "App moved to background", Toast.LENGTH_LONG).show()
        }
    }

    private fun getProcessName(cxt: Context, pid: Int): String? {
        val am = cxt.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val runningApps = am.runningAppProcesses ?: return null
        for (processInfo in runningApps) {
            if (processInfo.pid == pid) {
                return processInfo.processName
            }
        }
        return null
    }

    fun changeAppFriendly(friendly: Boolean) {
        if (friendly != appFriendly) {
            appFriendly = friendly
            appFriendlyCrash.friendlyOnForeground(friendly)
        }
    }

    fun changeBgmFriendly(friendly: Boolean) {
        if (friendly != bgmFriendly) {
            bgmFriendly = friendly
            bgmFriendlyCrash.friendlyOnForeground(friendly)
        }
    }
}