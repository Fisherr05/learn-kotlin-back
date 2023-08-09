package com.z.product.controller

import com.z.product.domain.Product
import com.z.product.service.ProductService
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.*
import java.io.FileNotFoundException

@RestController
@RequestMapping("/api/v1/product")
@Tag(name = "Products Controller")
class ProductController(
    productService: ProductService
) : BasicController<Product,String>(productService){

    @GetMapping("/test")
    fun fileNotFoudTest(){
        throw FileNotFoundException("just test")
    }
}