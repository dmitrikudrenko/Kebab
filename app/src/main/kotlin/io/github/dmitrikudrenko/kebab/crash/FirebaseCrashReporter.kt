package io.github.dmitrikudrenko.kebab.crash

import com.google.firebase.crash.FirebaseCrash


class FirebaseCrashReporter: CrashReporter {
    override fun log(message: String) {
        FirebaseCrash.log(message)
    }

    override fun log(throwable: Throwable) {
        FirebaseCrash.report(throwable)
    }

    override fun log(level: Int, tag: String, message: String) {
        FirebaseCrash.logcat(level, tag, message)
    }
}