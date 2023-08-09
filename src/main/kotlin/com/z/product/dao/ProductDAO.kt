package com.z.product.dao

import com.z.product.domain.Product
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ProdutDAO: JpaRepository<Product,String>