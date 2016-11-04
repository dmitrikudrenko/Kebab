package io.github.dmitrikudrenko.kebab.data.internal

import android.content.SharedPreferences
import javax.inject.Inject

class InternalSharedPreferences @Inject constructor(private val sharedPreferences: SharedPreferences) :
        SharedPreferences by sharedPreferences, PositionStorage {

    override fun savePosition(position: Position) {
        edit()
                .putFloat(KEY_LATITUDE, position.latitude.toFloat())
                .putFloat(KEY_LONGITUDE, position.longitude.toFloat())
                .putFloat(KEY_ZOOM, position.zoom)
                .apply()
    }

    override fun restorePosition(): Position? {
        if (contains(KEY_LATITUDE) && contains(KEY_LONGITUDE) && contains(KEY_ZOOM))
            return Position(
                    getFloat(KEY_LATITUDE, 0F).toDouble(),
                    getFloat(KEY_LONGITUDE, 0F).toDouble(),
                    getFloat(KEY_ZOOM, 0F)
            )
        return null
    }

    companion object {
        val KEY_LATITUDE = "latitude"
        val KEY_LONGITUDE = "longitude"
        val KEY_ZOOM = "zoom"
    }
}