package com.example.notegen.data.repository

import android.util.Log
import com.example.notegen.data.model.UpdatePasswordRequest
import com.example.notegen.data.model.UpdatePasswordResponse
import com.example.notegen.data.remote.RetrofitInstance
import com.example.notegen.utils.PreferenceManager
import retrofit2.Response

//suspend fun updateUserProfile(name: String, email: String): Response<BasicResponse> {
//    val request = UpdateProfileRequest(name, email)
//    val token = prefs.getToken() ?: ""
//
//    Log.d("AuthRepository", "Updating profile with token: $token")
//
//    return authApi.updateUserProfile(
//        token = "token=$token",  // ✅ match Postman format
//        request = request
//    )
//}}


class UpdatePasswordRepository(
    private val prefs: PreferenceManager
) {

    suspend fun updateUserPassword(
        token: String,
        oldPassword: String,
        newPassword: String
    ): Response<UpdatePasswordResponse> {
        val body = UpdatePasswordRequest(
            oldPassword = oldPassword,
            password = newPassword // backend expects "password"
        )

        Log.d("UpdatePasswordRepo", "Body: $body, token=$token")

        return RetrofitInstance.authApi.updatePassword(
            token = "token=$token", // exact backend header format
            body = body
        )
    }
}
