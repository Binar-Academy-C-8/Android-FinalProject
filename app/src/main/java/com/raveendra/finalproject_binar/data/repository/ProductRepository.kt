package com.raveendra.finalproject_binar.data.repository


import com.raveendra.finalproject_binar.data.dummy.ProductDataSource
import com.raveendra.finalproject_binar.model.Product

interface ProductRepository {
    fun getProducts(): List<Product>
}

class ProductRepositoryImpl(
    private val productDataSource: ProductDataSource
) : ProductRepository {

    override fun getProducts(): List<Product> {
        return productDataSource.getProductList()
    }
}