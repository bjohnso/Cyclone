package com.demo.cyclone.repository

import android.content.Context
import com.demo.cyclone.database.CycloneDatabase
import com.demo.cyclone.extensions.ContextExtensions.touchWalletApplication

class SavedAddressRepository(context: Context) {
    private var db = CycloneDatabase(context.touchWalletApplication())

    companion object {
        private var instance: SavedAddressRepository? = null

        fun getInstance(context: Context): SavedAddressRepository {
            return instance ?: SavedAddressRepository(context).also {
                instance = it
            }
        }
    }
}