package com.example.notegen.viewModels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notegen.data.repository.ForgotPasswordRepository
import kotlinx.coroutines.launch


sealed class ForgotPasswordState {

    object Idle : ForgotPasswordState()

    object Loading : ForgotPasswordState()

    data class Success(
        val message: String
    ) : ForgotPasswordState()

    data class Error(
        val message: String
    ) : ForgotPasswordState()
}



class ForgotPasswordViewModel(
    private val repository: ForgotPasswordRepository
) : ViewModel() {

    // Single source of truth for UI
    var state = mutableStateOf<ForgotPasswordState>(ForgotPasswordState.Idle)
        private set

    fun forgotPassword(
        email: String,
        token: String
    ) {

        // Move UI to loading state
        state.value = ForgotPasswordState.Loading

        viewModelScope.launch {
            try {
                val response = repository.forgotPassword(
                    email = email,
                    token = token
                )

                if (response.isSuccessful) {
                    state.value = ForgotPasswordState.Success(
                        message = response.body()?.message
                            ?: "Reset link sent successfully"
                    )
                } else {
                    state.value = ForgotPasswordState.Error(
                        message = response.body()?.message
                        ?: "Failed to send reset link"
                    )
                }

            } catch (e: Exception) {
                state.value = ForgotPasswordState.Error(
                    message = e.message ?: "Network error"
                )
            }
        }
    }
}
