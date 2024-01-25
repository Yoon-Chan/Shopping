package com.example.domain.usecase

import com.example.domain.model.AccountInfo
import com.example.domain.repository.AccountRepository
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class AccountUseCase @Inject constructor(
    private val accountRepository: AccountRepository
) {
    fun getAccountInfo(): StateFlow<AccountInfo?> = accountRepository.getAccountInfo()

    suspend fun signInGoogle(accountInfo: AccountInfo){
        accountRepository.signInGoogle(accountInfo)
    }

    suspend fun signOutGoogle() {
        accountRepository.signOutGoogle()
    }
}