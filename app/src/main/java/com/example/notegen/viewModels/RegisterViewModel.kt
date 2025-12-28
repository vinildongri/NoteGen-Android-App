package com.example.notegen.viewModels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notegen.data.model.RegisterResponse
import com.example.notegen.data.repository.RegisterRepository
import kotlinx.coroutines.launch
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue

class RegisterViewModel(
    private val repository: RegisterRepository
): ViewModel(){
    var isLoading by mutableStateOf(false)
        private set

    fun register(
        name: String,
        email: String,
        password: String,
        onSuccess: (RegisterResponse) -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {
            isLoading = true
            try {
                val res = repository.register(name, email, password)
                onSuccess(res)
            } catch (e: Exception) {
                onError(e.message ?: "Something went wrong")
            } finally {
                isLoading = false
            }
        }
    }
}