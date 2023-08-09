package com.z.product.service

import com.z.product.dao.ProdutDAO
import com.z.product.domain.Product
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class ProductService(private  val productDAO: ProdutDAO) : BasicCrud<Product, String> {
    override fun findAll(): List<Product> = this.productDAO.findAll()
    override fun deleteById(id: String): Boolean = this.productDAO.deleteById(id).let { return true }
    override fun update(t: Product): Boolean = this.productDAO.save(t).let { return true }
    override fun save(t: Product): Boolean = this.productDAO.save(t).let { return true }
    override fun findByID(id: String): Product? = this.productDAO.findByIdOrNull(id)
}