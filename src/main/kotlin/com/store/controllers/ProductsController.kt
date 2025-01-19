package com.store.controllers

import com.store.models.Id
import com.store.models.Product
import com.store.models.ProductType
import com.store.repo.ProductRepo
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/products")
class ProductsController(val productRepo: ProductRepo) {

    @GetMapping
    fun index(@RequestParam type: ProductType?): ResponseEntity<List<Product>> {
        return if (type != null) {
            val products = productRepo.getProductByType(type)
            ResponseEntity(products, HttpStatus.OK)
        } else {
            ResponseEntity(productRepo.getAllProducts(), HttpStatus.OK)
        }
    }


    @PostMapping
    fun create(@RequestBody @Validated product: Product): ResponseEntity<Id> {
        return ResponseEntity(productRepo.addProduct(product), HttpStatus.CREATED)
    }

}
