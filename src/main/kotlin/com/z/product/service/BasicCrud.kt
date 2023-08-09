package com.z.product.service

interface BasicCrud<T, ID> {
    fun findAll(): List<T>
    fun findByID(id: ID): T?
    fun save(t: T): T
    fun update(t: T): T
    fun deleteById(id: ID): T

}