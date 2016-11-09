package io.github.dmitrikudrenko.kebab.analytics

import android.content.Context
import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics


class FirebaseAnalyticsReporter(context: Context) : AnalyticsReporter {
    private val instance: FirebaseAnalytics

    init {
        instance = FirebaseAnalytics.getInstance(context)
    }

    override fun setUser(id: String) {
        instance.setUserId(id)
    }

    override fun setUserProperty(key: String, value: String?) {
        instance.setUserProperty(key, value)
    }

    override fun log(message: String, params: Map<String, String>?) {
        val bundle = Bundle()
        params?.let {
            for ((key, value) in params) {
                bundle.putString(key, value)
            }
        }
        instance.logEvent(message, bundle)
    }
}