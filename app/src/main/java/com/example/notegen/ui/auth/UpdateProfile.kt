package com.example.notegen.ui.auth

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.notegen.ui.layouts.AppTopBar
import com.example.notegen.ui.layouts.ToastHelper
import com.example.notegen.ui.layouts.ToastStatus
import com.example.notegen.viewModels.UpdateProfileState
import com.example.notegen.viewModels.UpdateProfileViewModel

@Composable
fun UpdateProfile(
    onBackClick: () -> Unit = {},
    userName: String,
    email: String,
    viewModel: UpdateProfileViewModel = viewModel(),
    onProfileUpdateSuccess: (String, String) -> Unit
) {
    var name by remember { mutableStateOf(userName) }
    var emailState by remember { mutableStateOf(email) }

    val context = LocalContext.current
    val state by viewModel.updateProfileState.collectAsStateWithLifecycle()
    val isLoading = state is UpdateProfileState.Loading

    // 🔥 SIDE EFFECT FOR TOASTS
    LaunchedEffect(state) {
        when (state) {
            is UpdateProfileState.Success -> {
                ToastHelper(
                    context,
                    (state as UpdateProfileState.Success).message,
                    ToastStatus.SUCCESS
                )
                onProfileUpdateSuccess(name, emailState)
                viewModel.resetState()
            }

            is UpdateProfileState.Error -> {
                ToastHelper(
                    context,
                    (state as UpdateProfileState.Error).message,
                    ToastStatus.ERROR
                )
                viewModel.resetState()
            }

            else -> Unit
        }
    }

    Scaffold(
        topBar = {
            AppTopBar(
                title = "Update Profile",
                onBackClick = onBackClick
            )
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 20.dp)
                .imePadding()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Center
        ) {

            Text("Name")
            Spacer(modifier = Modifier.height(6.dp))

            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                shape = RoundedCornerShape(10.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text("Email")
            Spacer(modifier = Modifier.height(6.dp))

            OutlinedTextField(
                value = emailState,
                onValueChange = { emailState = it },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                shape = RoundedCornerShape(10.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    when {
                        name.isBlank() || emailState.isBlank() -> {
                            ToastHelper(
                                context,
                                "All fields are required",
                                ToastStatus.ERROR
                            )
                        }

                        !android.util.Patterns.EMAIL_ADDRESS
                            .matcher(emailState)
                            .matches() -> {
                            ToastHelper(
                                context,
                                "Enter a valid email",
                                ToastStatus.ERROR
                            )
                        }

                        else -> {
                            viewModel.updateProfile(
                                name = name.trim(),
                                email = emailState.trim()
                            )
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(12.dp),
                enabled = !isLoading
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(22.dp),
                        strokeWidth = 2.dp
                    )
                    Spacer(Modifier.width(8.dp))
                    Text("Updating...")
                } else {
                    Text("Update Profile")
                }
            }
        }
    }
}
