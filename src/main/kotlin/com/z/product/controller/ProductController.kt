package com.z.product.controller

import com.z.product.domain.Product
import com.z.product.service.ProductService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/product")
class ProductController(
    productService: ProductService
) : BasicController<Product,String>(productService)