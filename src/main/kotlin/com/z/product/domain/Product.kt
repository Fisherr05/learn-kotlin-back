package com.z.product.domain

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.Size

@Entity
data class Product(
    @Id
    @get:Size(min = 3, max = 20)
    val name: String,

    @get:Min(0)
    var price: Double? = 55.5,

    @get:Min(0)
    var stock:Int = 0
) {
    override fun equals(other: Any?): Boolean {
        other ?: return false
        if (other === this) return true
        if (this.javaClass != other.javaClass) return false
        other as Product

        return this.name == other.name
    }

    override fun hashCode(): Int {
        return name.hashCode()
    }
}