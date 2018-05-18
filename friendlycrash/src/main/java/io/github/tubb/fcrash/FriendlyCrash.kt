package io.github.tubb.fcrash

import android.app.Application
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.ProcessLifecycleOwner
import android.os.Process.killProcess
import android.os.Process.myPid

/**
 * Friendly notify user when app crashed
 */
class FriendlyCrash private constructor(app: Application) {
    /**
     * App on foreground or background
     */
    private var appStatus: Short = FOREGROUND_STATUS
    /**
     * App lifecycle observer
     */
    private val appLifecycleObserver: LifecycleObserver by lazy {
        AppLifecycleListener()
    }

    companion object {
        private const val FOREGROUND_STATUS: Short = 1
        private const val BACKGROUND_STATUS: Short = 2
        /**
         * Arrow function for app lifecycle callback
         */
        private var appLifecycleCallback: ((Boolean) -> Unit)? = null
        private var instance: FriendlyCrash? = null

        internal fun instance(): FriendlyCrash? {
            return instance
        }

        /**
         * Build the FriendlyCrash
         * @param app Real Application
         * @param lifeCallback Callback If app lifecycle changed
         */
        fun build(app: Application, lifeCallback: (Boolean) -> Unit): FriendlyCrash {
            synchronized(this) {
                if (instance == null) {
                    instance = FriendlyCrash(app)
                }
                appLifecycleCallback = lifeCallback
            }
            return instance!!
        }

        fun build(app: Application, handler: AppLifecycleHandler): FriendlyCrash {
            return build(app) { isOnForeground ->
                if (isOnForeground) {
                    handler.movedToForeground()
                } else {
                    handler.movedToBackground()
                }
            }
        }

        fun build(app: Application): FriendlyCrash {
            return build(app) {}
        }
    }

    /**
     * Enable friendly crash
     * @param listener Callback If app crashed
     */
    fun enable(listener: ((Thread, Throwable) -> Unit)) {
        with(ProcessLifecycleOwner.get().lifecycle) {
            removeObserver(appLifecycleObserver)
            addObserver(appLifecycleObserver)
        }
        val defaultHandler = Thread.getDefaultUncaughtExceptionHandler()
        Thread.setDefaultUncaughtExceptionHandler { thread, ex ->
            listener.invoke(thread, ex)
            if (!isAppOnForeground()) {
                killProcess(myPid())
            } else {
                defaultHandler.uncaughtException(thread, ex)
            }
        }
    }

    fun enable() {
        enable { _, _ -> }
    }

    fun enable(handler: ExceptionHandler) {
        enable { thread, throwable ->
            handler.uncaughtException(thread, throwable)
        }
    }

    internal fun appMoveToForeground() {
        appStatus = FOREGROUND_STATUS
        appLifecycleCallback?.invoke(true)
    }

    internal fun appMoveToBackground() {
        appStatus = BACKGROUND_STATUS
        appLifecycleCallback?.invoke(false)
    }

    private fun isAppOnForeground(): Boolean {
        return appStatus == FOREGROUND_STATUS
    }
}