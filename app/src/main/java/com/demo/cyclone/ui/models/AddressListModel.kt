package com.demo.cyclone.ui.models

data class AddressListModel(
    val listHeader: HeaderModel? = null,
    val addresses: List<AddressModel>,
    val listType: Type
) {
    enum class Type {
        CLIPBOARD,
        ADDRESS_BOOK
    }
}
