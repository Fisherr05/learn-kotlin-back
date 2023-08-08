package com.z.product.controller

import com.z.product.service.BasicCrud
import org.springframework.web.bind.annotation.*

abstract class BasicController<T,ID>(
    private val basicCrud: BasicCrud<T,ID>
){
    @GetMapping
    fun findAll() = basicCrud.findAll()

    @GetMapping("/{id}")
    fun findById(@PathVariable id: ID) = basicCrud.findByID(id)

    @PostMapping
    fun save(@RequestBody product: T) = basicCrud.save(product)

    @PutMapping
    fun update(@RequestBody product: T) = basicCrud.update(product)

    @DeleteMapping("/{id}")
    fun deleteById(@PathVariable id: ID) = basicCrud.deleteById(id)
}