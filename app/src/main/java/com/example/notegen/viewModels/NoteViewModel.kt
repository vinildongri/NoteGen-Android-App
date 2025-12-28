package com.example.notegen.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notegen.data.model.NoteRequest
import com.example.notegen.data.remote.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


sealed class NoteUiState {
    object Idle : NoteUiState()
    object Loading : NoteUiState()

    data class Success( val data: String ): NoteUiState()
    data class Error( val message: String ): NoteUiState()

}


class NoteViewModel : ViewModel() {

    private val _uiState = MutableStateFlow<NoteUiState>(NoteUiState.Idle)
    val uiState: StateFlow<NoteUiState> = _uiState

    private val _messages =
        MutableStateFlow<List<Pair<String, Boolean>>>(emptyList())
    val messages: StateFlow<List<Pair<String, Boolean>>> = _messages

    fun sendUserMessage(text: String) {
        if (text.isBlank()) return

        // add user message
        _messages.value = _messages.value + (text to true)

        _uiState.value = NoteUiState.Loading

        viewModelScope.launch {
            try {
                val response = RetrofitInstance.noteApi.createNote(
                    NoteRequest(text)
                )

                if (response.isSuccessful) {
                    val botReply = response.body()?.message.orEmpty()
                    _messages.value = _messages.value + (botReply to false)
                    _uiState.value = NoteUiState.Idle
                } else {
                    addError("Something went wrong")
                }
            } catch (e: Exception) {
                addError("Network error")
            }
        }
    }

    private fun addError(message: String) {
        _messages.value = _messages.value + (message to false)
        _uiState.value = NoteUiState.Idle
    }
}
