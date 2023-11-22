package com.raveendra.finalproject_binar.data.dummy

import com.raveendra.finalproject_binar.model.Product

interface ProductDataSource {
    fun getProductList(): List<Product>
}

class ProductDataSourceImpl() : ProductDataSource {
    override fun getProductList(): List<Product> = listOf(
        Product(
            name = "Intro to Basic of User Interaction Design",
            imgUrl = "https://raw.githubusercontent.com/ryhanhxx/img_asset_final/main/Thumbnail.png",
            author = "Maman",
            rating = 4.8,
            level = "Intermediate Level",
            modul = "11 Modul",
            duration = "120 Menit"
        ),
        Product(
            name = "Intro to Basic of User Interaction Design",
            imgUrl = "https://raw.githubusercontent.com/ryhanhxx/img_asset_final/main/Thumbnail.png",
            author = "Jack",
            rating = 4.3,
            level = "Advanced Level",
            modul = "8 Modul",
            duration = "60 Menit"
        ),
        Product(
            name = "Intro to Basic of User Interaction Design",
            imgUrl = "https://raw.githubusercontent.com/ryhanhxx/img_asset_final/main/Thumbnail.png",
            author = "Maman",
            rating = 4.5,
            level = "Intermediate Level",
            modul = "11 Modul",
            duration = "120 Menit"
        )
    )
}