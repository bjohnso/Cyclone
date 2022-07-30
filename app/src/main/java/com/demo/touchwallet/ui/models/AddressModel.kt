package com.demo.touchwallet.ui.models

data class AddressModel(
    val address: String,
    val name: String,
    val type: AddressListModel.Type
)
