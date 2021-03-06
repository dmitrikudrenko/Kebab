package io.github.dmitrikudrenko.kebab.crash


interface CrashReporter {
    fun init()
    fun log(message: String)
    fun log(throwable: Throwable)
    fun log(level: Int, tag: String, message: String)
}