package com.example.shoppinglist.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import com.example.shoppinglist.R
import com.example.shoppinglist.databinding.ActivityShopItemBinding
import com.example.shoppinglist.domain.ShopItem

class ShopItemActivity : ComponentActivity() {

    private lateinit var binding: ActivityShopItemBinding
    private lateinit var viewModel: ShopItemViewModel

    private var screenMode = MODE_UNKNOWN
    private var shopItemId = ShopItem.UNDEFINED_ID

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShopItemBinding.inflate(layoutInflater)
        setContentView(binding.root)
        parseIntent()
        viewModel = ViewModelProvider(this)[ShopItemViewModel::class.java]

        when (screenMode) {
            MODE_ADD -> launchAddMode()
            MODE_EDIT -> launchEditMode()
        }
        observeInputErrors()
        viewModel.shouldCloseScreen.observeForever {
            finish()
        }
    }

    private fun launchAddMode() {
        binding.btnSave.setOnClickListener {
            val inputName = binding.etName.text?.toString()
            val inputCount = binding.etCount.text?.toString()
            viewModel.addShopItem(inputName, inputCount)
        }
    }

    private fun launchEditMode() {
        viewModel.getShopItem(shopItemId)
        viewModel.shopItem.observe(this) {
            binding.etName.setText(it.name)
            binding.etCount.setText(it.count.toString())
        }
        binding.btnSave.setOnClickListener {
            val inputName = binding.etName.text?.toString()
            val inputCount = binding.etCount.text?.toString()
            viewModel.editShopItem(inputName, inputCount)
        }
    }

    private fun observeInputErrors() {
        viewModel.errorInputName.observe(this) {
            binding.tilName.error = if (it) getString(R.string.shop_item_input_error) else null
        }
        binding.etName.addTextChangedListener {
            viewModel.resetErrorInputName()
        }
        viewModel.errorInputCount.observe(this) {
            binding.tilCount.error = if (it) getString(R.string.shop_item_input_error) else null
        }
        binding.etCount.addTextChangedListener {
            viewModel.resetErrorInputCount()
        }
    }

    private fun parseIntent() {
        if (!intent.hasExtra(EXTRA_SCREEN_MODE)) {
            throw RuntimeException("Extra screen mode is absent")
        }
        val mode = intent.getStringExtra(EXTRA_SCREEN_MODE)
        if (mode != MODE_ADD && mode != MODE_EDIT) {
            throw RuntimeException("Unknown screen mode $mode")
        }
        screenMode = mode

        if (mode == MODE_EDIT) {
            if (!intent.hasExtra(EXTRA_ITEM_ID)) {
                throw RuntimeException("Extra shop item id is absent")
            }
            shopItemId = intent.getIntExtra(EXTRA_ITEM_ID, ShopItem.UNDEFINED_ID)
        }
    }

    companion object {

        private const val EXTRA_SCREEN_MODE = "extra_screen_mode"
        private const val EXTRA_ITEM_ID = "extra_item_id"

        private const val MODE_ADD = "mode_add"
        private const val MODE_EDIT = "mode_edit"
        private const val MODE_UNKNOWN = ""

        fun newIntentAddItem(context: Context): Intent {
            val intent = Intent(context, ShopItemActivity::class.java)
            intent.putExtra(EXTRA_SCREEN_MODE, MODE_ADD)
            return intent
        }

        fun newIntentEditItem(context: Context, shopItemId: Int): Intent {
            val intent = Intent(context, ShopItemActivity::class.java)
            intent.putExtra(EXTRA_SCREEN_MODE, MODE_EDIT)
            intent.putExtra(EXTRA_ITEM_ID, shopItemId)
            return intent
        }
    }
}