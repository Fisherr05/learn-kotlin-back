package com.z.product

import com.fasterxml.jackson.databind.ObjectMapper
import com.z.product.domain.Product
import com.z.product.service.ProductService
import org.hamcrest.MatcherAssert
import org.hamcrest.Matchers
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestMethodOrder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import java.util.*

@SpringBootTest
@TestMethodOrder(OrderAnnotation::class)
class ProductApplicationTests {

    @Autowired
    private lateinit var webApplicationContext: WebApplicationContext


    private val mockMvc: MockMvc by lazy {
        MockMvcBuilders.webAppContextSetup(webApplicationContext)
            .alwaysDo<DefaultMockMvcBuilder>(MockMvcResultHandlers.print()).build()
    }

    @Autowired
    private lateinit var mapper: ObjectMapper

    @Autowired
    private lateinit var productService: ProductService

    private val URL_API = "/api/v1/product"

    @Test
    @Order(1)
    fun findAll() {
        val productsFromService = productService.findAll()
        val products: List<Product> = mockMvc.perform(MockMvcRequestBuilders.get(URL_API))
            .andExpect(status().isOk)
            .bodyTo(mapper)
        MatcherAssert.assertThat(productsFromService, Matchers.`is`(Matchers.equalTo(products)))
    }

    @Test
    @Order(2)
    fun findById() {
        val productsFromService = productService.findAll()
        assert(!productsFromService.isEmpty()) { "Should not be empty" }
        val product = productsFromService.first()
        mockMvc.perform(MockMvcRequestBuilders.get("$URL_API/${product.name}"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.name", Matchers.`is`(product.name)))
    }

    @Test
    @Order(3)
    fun findByIdEmpty() {
        mockMvc.perform(MockMvcRequestBuilders.get("$URL_API/${UUID.randomUUID()}"))
            .andExpect(status().isNoContent)
            .andExpect(jsonPath("$").doesNotExist())
    }

    @Test
    @Order(4)
    fun saveCheckRules() {
        mockMvc.perform(
            MockMvcRequestBuilders.post(URL_API)
                .body(data = Product(name = "", price = -50.0 ), mapper = mapper)
        )
            .andExpect(status().isBadRequest)
            .andExpect(jsonPath("$.name").exists())
            .andExpect(jsonPath("$.price").exists())
    }

    @Test
    @Order(5)
    fun saveSuccesfully() {
        val product = Product(name = "PineApple", price = 50.0)

        val result: Boolean = mockMvc.perform(
            MockMvcRequestBuilders.post(URL_API)
                .body(data = product, mapper = mapper)
        )
            .andExpect(status().isCreated)
            .bodyTo(mapper)
        assert(result)
    }

    @Test
    @Order(6)
    fun saveFail() {
        val productsFromService = productService.findAll()
        assert(!productsFromService.isEmpty()) { "Should not be empty" }
        val product = productsFromService.first()

        val result: Boolean = mockMvc.perform(
            MockMvcRequestBuilders.post(URL_API)
                .body(data = product, mapper = mapper)
        )
            .andExpect(status().isConflict)
            .bodyTo(mapper)
        assert(!result) { "Should be false" }
    }

    @Test
    @Order(7)
    fun updateSuccesfully() {
        val productsFromService = productService.findAll()
        assert(!productsFromService.isEmpty()) { "Should not be empty" }
        val product = productsFromService.first().copy(price = 44.23)

        val result: Boolean = mockMvc.perform(
            MockMvcRequestBuilders.put(URL_API)
                .body(data = product, mapper = mapper)
        )
            .andExpect(status().isOk)
            .bodyTo(mapper)
        assert(result)
    }

    @Test
    @Order(8)
    fun updateFail() {
        val product = Product(name = UUID.randomUUID().toString(), price = 123.123)

        val result: Boolean = mockMvc.perform(
            MockMvcRequestBuilders.put(URL_API)
                .body(data = product, mapper = mapper)
        )
            .andExpect(status().isConflict)
            .bodyTo(mapper)
        assert(!result) { "Should be false" }
    }

    @Test
    @Order(9)
    fun deleteById() {
        val productsFromService = productService.findAll()
        assert(!productsFromService.isEmpty()) { "Should not be empty" }
        val product = productsFromService.last()

        val result: Boolean = mockMvc.perform(
            MockMvcRequestBuilders.delete("$URL_API/${product.name}")
        )
            .andExpect(status().isOk)
            .bodyTo(mapper)
        assert(result)
        assert(!productService.findAll().contains(product))
    }

    @Test
    @Order(10)
    fun deleteByIdFail() {
        val result: Boolean = mockMvc.perform(
            MockMvcRequestBuilders.delete("$URL_API/${UUID.randomUUID()}")
        )
            .andExpect(status().isNoContent)
            .bodyTo(mapper)
        assert(!result) { "Should be false" }
    }
}
