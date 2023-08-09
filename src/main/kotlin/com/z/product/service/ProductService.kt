package com.z.product.service

import com.z.product.dao.ProdutDAO
import com.z.product.domain.Product
import jakarta.persistence.EntityNotFoundException
import org.springframework.dao.DuplicateKeyException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class ProductService(private  val productDAO: ProdutDAO) : BasicCrud<Product, String> {
    override fun findAll(): List<Product> = this.productDAO.findAll()
    override fun deleteById(id: String): Product {
        return this.findByID(id)?.apply {
            this@ProductService.productDAO.deleteById(this.name)
        } ?: throw EntityNotFoundException("$id does not exist")
    }
    override fun update(t: Product): Product {
        return if(this.productDAO.existsById(t.name))
            this.productDAO.save(t)
        else
            throw EntityNotFoundException("${t.name} does not exist")
    }
    override fun save(t: Product): Product {
        return if(!this.productDAO.existsById(t.name))
            this.productDAO.save(t)
        else
            throw DuplicateKeyException("${t.name} does exist")
    }
    override fun findByID(id: String): Product? = this.productDAO.findByIdOrNull(id)
}