package io.github.dmitrikudrenko.kebab.data.internal

interface PositionStorage {
    fun savePosition(position: Position)
    fun restorePosition(): Position?
}