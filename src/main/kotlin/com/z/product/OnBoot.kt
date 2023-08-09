package com.z.product

import com.z.product.domain.Product
import com.z.product.service.ProductService
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.stereotype.Component

@Component
class OnBoot(private val productService: ProductService):ApplicationRunner {
    override fun run(args: ApplicationArguments?) {
        listOf(
            Product(name = "Apple", price = 22.2, stock = 5),
            Product(stock = 1, price = 33.8, name = "Orange")
        ).forEach {
            println("Saving -> ${it.name}")
            productService.save(it)
        }
    }
}