package io.github.tubb.fcrash

interface ExceptionHandler {
    fun uncaughtException(onForeground: Boolean, thread: Thread, ex: Throwable)
}