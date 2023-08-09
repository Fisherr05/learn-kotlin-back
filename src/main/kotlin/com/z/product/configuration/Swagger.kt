//package com.z.product.configuration
//
//import com.z.product.ProductApplication
//import org.springframework.context.annotation.Bean
//import org.springframework.context.annotation.Configuration
//
//
//@Configuration
//class Swagger {
//
//    @Bean
//    fun api(): Docket = Docket(DocumentationType.SWAGGER_2)
//        .select()
//        .apis(RequestHandlerSelectors.basePackage(ProductApplication::class.java.packageName))
//        .paths(PathSelectors.any())
//        .build()
//}