package io.github.dmitrikudrenko.kebab.data.model

interface IKebabShop {
    fun getId(): Long
    fun getName(): String?
    fun getAddress(): String?
    fun getLatitude(): Double?
    fun getLongitude(): Double?
    fun getRating(): Double?
    fun getDescription(): String?
    fun getImage(): String?
}