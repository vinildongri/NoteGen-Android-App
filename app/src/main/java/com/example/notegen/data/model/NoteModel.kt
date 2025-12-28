package com.example.notegen.data.model

data class NoteRequest(
    val prompt: String
)
data class NoteResponse(
    val success: Boolean,
    val message: String
)