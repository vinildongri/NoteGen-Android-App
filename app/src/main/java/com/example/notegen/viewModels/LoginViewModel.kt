package com.example.notegen.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notegen.data.repository.LoginRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch



class LoginViewModel(
    private val repository: LoginRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<LoginState>(LoginState.Idle)
    val uiState: StateFlow<LoginState> = _uiState

    fun login(email: String, password: String) {
        _uiState.value = LoginState.Loading

        viewModelScope.launch {
            try {
                val response = repository.login(email, password)

                if (response.isSuccessful && response.body() != null) {
                    val body = response.body()!!
                    _uiState.value = LoginState.Success(
                        token = body.token,
                        userName = body.user.name,
                        email = body.user.email
                    )
                } else {
                    _uiState.value = LoginState.Error("Invalid email or password")
                }
            } catch (e: Exception) {
                _uiState.value = LoginState.Error(e.message ?: "Network error")
            }
        }
    }

    fun resetState() {
        _uiState.value = LoginState.Idle
    }
}

sealed class LoginState {
    object Idle : LoginState()
    object Loading : LoginState()
    data class Success(
        val token: String,
        val userName: String,
        val email: String
    ) : LoginState()
    data class Error(val message: String) : LoginState()
}
