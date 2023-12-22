package com.example.shoppinglist.domain

class EditShopItemUseCase(private val shopListRepository: ShopListRepository) {

    fun editShopItem(shopItemId: Int) {
        shopListRepository.editShopItem(shopItemId)
    }
}