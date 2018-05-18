package io.github.tubb.fcrash

interface ExceptionHandler {
    fun uncaughtException(t: Thread, e: Throwable)
}