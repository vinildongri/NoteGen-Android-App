package com.example.notegen.data.remote.api

import com.example.notegen.data.model.NoteRequest
import com.example.notegen.data.model.NoteResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface NoteApi {

    @POST("notes")
    suspend fun createNote(
        @Body request: NoteRequest
    ): Response<NoteResponse>
}