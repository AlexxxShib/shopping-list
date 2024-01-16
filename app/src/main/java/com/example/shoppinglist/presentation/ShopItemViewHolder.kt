package com.example.shoppinglist.presentation

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinglist.R

class ShopItemViewHolder(view: View): RecyclerView.ViewHolder(view) {
    val itemName = view.findViewById<TextView>(R.id.textViewItemName)
    val itemCount = view.findViewById<TextView>(R.id.textViewItemCount)
}