package com.demo.touchwallet.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import com.demo.touchwallet.repository.SolanaRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SolanaViewModel: ViewModel() {
    suspend fun createWallet(context: Context) = withContext(Dispatchers.IO) {
        SolanaRepository.getInstance(context).generateKeyPair(context)
    }
}