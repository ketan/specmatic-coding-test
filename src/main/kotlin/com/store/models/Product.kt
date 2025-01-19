package com.store.models

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.store.StrictStringDeserializer
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import java.math.BigDecimal
import java.util.concurrent.atomic.AtomicInteger


enum class ProductType {
    book, food, gadget, other
}

data class Id(val id: Int)

data class Product(

    val id: Int = idGenerator.getAndIncrement(),

    @field:JsonDeserialize(using = StrictStringDeserializer::class)
    @field:NotBlank(message = "name must not be blank")
    val name: String,

    @field:NotNull(message = "type must not be null")
    val type: ProductType,

    @field:Min(value = 1, message = "inventory must be greater than 1")
    @field:Max(value = 9999, message = "inventory must be less or equal to 9999")
    val inventory: Int,

    val cost: BigDecimal = BigDecimal.ZERO
) {

    companion object {
        val idGenerator: AtomicInteger = AtomicInteger()
    }
}
