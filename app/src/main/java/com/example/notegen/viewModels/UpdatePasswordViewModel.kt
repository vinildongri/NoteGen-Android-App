package com.example.notegen.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.notegen.data.repository.UpdatePasswordRepository
import com.example.notegen.utils.PreferenceManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class UpdatePasswordState {
    object Idle : UpdatePasswordState()
    object Loading : UpdatePasswordState()
    data class Success(val message: String) : UpdatePasswordState()
    data class Error(val message: String) : UpdatePasswordState()
}

class UpdatePasswordViewModel(application: Application) : AndroidViewModel(application) {

    private val prefs = PreferenceManager(application)
    private val repository = UpdatePasswordRepository(prefs)

    private val _updatePasswordState =
        MutableStateFlow<UpdatePasswordState>(UpdatePasswordState.Idle)
    val updatePasswordState: StateFlow<UpdatePasswordState> = _updatePasswordState

    fun updatePassword(oldPassword: String, newPassword: String) {
        viewModelScope.launch {
            _updatePasswordState.value = UpdatePasswordState.Loading
            Log.d("UpdatePassword", "Sending oldPassword: $oldPassword, newPassword: $newPassword")

            try {
                // 1. Get token
                val token = prefs.getToken() ?: throw Exception("No token found, please log in again")
                Log.d("UpdatePassword", "Using token: $token")

                // 2. Make network call
                val response = repository.updateUserPassword(token, oldPassword, newPassword)

                if (response.isSuccessful) {
                    Log.d("UpdatePassword", "Success code: ${response.code()}, body: ${response.body()}")
                    _updatePasswordState.value =
                        UpdatePasswordState.Success(response.body()?.message ?: "Password updated successfully")
                } else {
                    val errorBodyString = response.errorBody()?.string() ?: "No error body"
                    Log.e("UpdatePassword", "Error code: ${response.code()}, errorBody: $errorBodyString")
                    _updatePasswordState.value =
                        UpdatePasswordState.Error("Error ${response.code()}: $errorBodyString")
                }
            } catch (e: Exception) {
                Log.e("UpdatePassword", "Exception: ${e.message}", e)
                _updatePasswordState.value = UpdatePasswordState.Error("Error: ${e.message}")
            }
        }
    }

    fun resetState(){
        _updatePasswordState.value = UpdatePasswordState.Idle
    }
}
