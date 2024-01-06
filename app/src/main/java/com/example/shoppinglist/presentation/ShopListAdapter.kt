package com.example.shoppinglist.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinglist.R
import com.example.shoppinglist.domain.ShopItem

class ShopListAdapter: RecyclerView.Adapter<ShopListAdapter.ShopItemViewHolder>() {

    inner class ShopItemViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val itemName = view.findViewById<TextView>(R.id.textViewItemName)
        val itemCount = view.findViewById<TextView>(R.id.textViewItemCount)
    }

    companion object {
        const val VIEW_TYPE_ENABLED = 0
        const val VIEW_TYPE_DISABLED = 1

        const val POOL_SIZE = 15
    }

    var shopList = listOf<ShopItem>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopItemViewHolder {
        val layout = when (viewType) {
            VIEW_TYPE_ENABLED -> R.layout.item_shop_enabled
            VIEW_TYPE_DISABLED -> R.layout.item_shop_disabled
            else -> throw RuntimeException("Unknown view type $viewType")
        }
        val view = LayoutInflater
            .from(parent.context)
            .inflate(layout, parent, false)
        return ShopItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return shopList.count()
    }

    override fun getItemViewType(position: Int): Int {
        return if (shopList[position].enabled) VIEW_TYPE_ENABLED else VIEW_TYPE_DISABLED
    }

    override fun onBindViewHolder(holder: ShopItemViewHolder, position: Int) {
        val item = shopList[position]
        with(holder) {
            itemName.text = item.name
            itemCount.text = item.count.toString()
        }
    }
}