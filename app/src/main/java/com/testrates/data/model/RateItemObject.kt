package com.testrates.data.model

@Suppress("DEPRECATED_IDENTITY_EQUALS")
data class RateItemObject(
    var countryImage: Int, var countryName: String,
    var countryCurrencyName: Int, var countryRate: Double
) : Comparable<RateItemObject>, Cloneable {

    override fun compareTo(other: RateItemObject): Int {

        return if (other.countryRate === this.countryRate) {
            0
        } else 1
    }


    public override fun clone(): RateItemObject {

        val clone: RateItemObject
        try {
            clone = super.clone() as RateItemObject

        } catch (e: CloneNotSupportedException) {
            throw RuntimeException(e)
        }

        return clone
    }

}