package com.z.product.service

import com.z.product.domain.Product
import com.z.product.utils.update
import org.springframework.stereotype.Service

@Service
class ProductService : BasicCrud<Product, String> {
    private val products: MutableSet<Product> = mutableSetOf(
        Product("Apple", 22.2), Product(price = 34.3, name = "Banana")
    )

    override fun findAll(): List<Product> = products.toList()
    override fun deleteById(id: String): Boolean = this.products.remove(this.findByID(id))
    override fun update(t: Product): Boolean = this.products.update(t)
    override fun save(t: Product): Boolean = this.products.add(t)
    override fun findByID(id: String): Product? = this.products.find { it.name == id }
}