package com.store.repo

import com.store.models.Id
import com.store.models.Product
import com.store.models.ProductType
import org.springframework.stereotype.Repository


@Repository
class ProductRepo {
    private val products = arrayListOf(Product(1, "Book", ProductType.book, 10))

    fun addProduct(product: Product): Id {
        products.add(product)
        return Id(product.id)
    }

    fun getProductByType(type: ProductType): List<Product> {
        return products.filter { it.type == type }
    }

    fun getAllProducts(): List<Product> {
        return products.toList()
    }

}