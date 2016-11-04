package io.github.dmitrikudrenko.kebab.data.model

class KebabShop(private val id: Long, private val name: String?, private val address: String?,
                private val latitude: Double?, private val longitude: Double?,
                private val rating: Double?, private val description: String?,
                private val image: String?) : IKebabShop {
    override fun getId(): Long {
        return id
    }

    override fun getName(): String? {
        return name
    }

    override fun getAddress(): String? {
        return address
    }

    override fun getLatitude(): Double? {
        return latitude
    }

    override fun getLongitude(): Double? {
        return longitude
    }

    override fun getRating(): Double? {
        return rating
    }

    override fun getDescription(): String? {
        return description
    }

    override fun getImage(): String? {
        return image
    }
}