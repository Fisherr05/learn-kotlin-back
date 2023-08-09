package com.z.product.controller

import com.z.product.service.BasicCrud
import io.swagger.v3.oas.annotations.Operation
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

abstract class BasicController<T, ID>(
    private val basicCrud: BasicCrud<T, ID>
) {
    @Operation(
        summary = "Get all entities",
    )
    @GetMapping
    fun findAll() = basicCrud.findAll()

    @GetMapping("/{id}")
    fun findById(@PathVariable id: ID): ResponseEntity<T> {
        val entity = basicCrud.findByID(id)
        return ResponseEntity.status(
            if (entity != null)
                HttpStatus.OK
            else
                HttpStatus.NO_CONTENT
        ).body(entity)
    }

    @PostMapping
    fun save(@Valid @RequestBody body: T): ResponseEntity<Boolean> {
        val entity = basicCrud.save(body)
        return ResponseEntity.status(
            if (entity)
                HttpStatus.CREATED
            else
                HttpStatus.CONFLICT
        ).body(entity)
    }

    @PutMapping
    fun update(@RequestBody body: T): ResponseEntity<Boolean> {
        val entity = basicCrud.update(body)
        return ResponseEntity.status(
            if (entity)
                HttpStatus.OK
            else
                HttpStatus.CONFLICT
        ).body(entity)
    }

    @DeleteMapping("/{id}")
    fun deleteById(@PathVariable id: ID): ResponseEntity<Boolean> {
        val entity = basicCrud.deleteById(id)
        return ResponseEntity.status(
            if (entity)
                HttpStatus.OK
            else
                HttpStatus.NO_CONTENT
        ).body(entity)
    }
}