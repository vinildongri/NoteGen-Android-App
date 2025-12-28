package com.example.notegen.viewModels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.notegen.data.repository.UpdateProfileRepository
import com.example.notegen.utils.PreferenceManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

// ---------- UI STATE ----------
sealed class UpdateProfileState {
    object Idle : UpdateProfileState()
    object Loading : UpdateProfileState()
    data class Success(val message: String) : UpdateProfileState()
    data class Error(val message: String) : UpdateProfileState()
}

// ---------- VIEWMODEL ----------
class UpdateProfileViewModel(application: Application) : AndroidViewModel(application) {

    private val prefs = PreferenceManager(application)
    private val repository = UpdateProfileRepository(prefs)

    private val _updateProfileState =
        MutableStateFlow<UpdateProfileState>(UpdateProfileState.Idle)

    val updateProfileState: StateFlow<UpdateProfileState> = _updateProfileState

    fun updateProfile(name: String, email: String) {
        viewModelScope.launch {
            _updateProfileState.value = UpdateProfileState.Loading
            Log.d("UpdateProfile", "Updating profile: $name, $email")

            try {
                prefs.getToken()
                    ?: throw Exception("Session expired. Please login again.")

                val response = repository.updateUserProfile(name, email)

                if (response.isSuccessful) {
                    val message =
                        response.body()?.message ?: "Profile updated successfully"

                    // Save updated values locally
//                    prefs.saveUserName(name)
//                    prefs.saveUserEmail(email)

                    _updateProfileState.value = UpdateProfileState.Success(message)

                } else {
                    val error =
                        response.errorBody()?.string() ?: "Something went wrong"

                    Log.e("UpdateProfile", "API Error: $error")

                    _updateProfileState.value =
                        UpdateProfileState.Error(error)
                }

            } catch (e: Exception) {
                Log.e("UpdateProfile", "Exception", e)
                _updateProfileState.value =
                    UpdateProfileState.Error(e.message ?: "Unexpected error")
            }
        }
    }

    fun resetState() {
        _updateProfileState.value = UpdateProfileState.Idle
    }
}
