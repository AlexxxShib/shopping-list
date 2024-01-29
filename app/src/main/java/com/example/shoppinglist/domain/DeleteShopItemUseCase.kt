package com.example.shoppinglist.domain

class DeleteShopItemUseCase(private val shopListRepository: ShopListRepository) {

    suspend fun deleteShopItem(shopItemId: Int) {
        shopListRepository.deleteShopItem(shopItemId)
    }
}