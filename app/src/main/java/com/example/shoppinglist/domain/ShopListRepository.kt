package com.example.shoppinglist.domain

interface ShopListRepository {

    fun addShopItem(shopItem: ShopItem)

    fun deleteShopItem(shopItemId: Int)

    fun editShopItem(shopItemId: Int)

    fun getShopItem(shopItemId: Int): ShopItem

    fun getShopList(): List<ShopItem>
}