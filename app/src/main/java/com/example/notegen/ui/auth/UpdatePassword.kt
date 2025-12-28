package com.example.notegen.ui.auth

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.notegen.ui.layouts.AppTopBar
import com.example.notegen.ui.layouts.ToastHelper
import com.example.notegen.ui.layouts.ToastStatus
import com.example.notegen.utils.PreferenceManager
import com.example.notegen.viewmodel.UpdatePasswordState
import com.example.notegen.viewmodel.UpdatePasswordViewModel

@Composable
fun UpdatePassword(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit = {},
    viewModel: UpdatePasswordViewModel = viewModel()
) {
    val context = LocalContext.current
    val prefs = PreferenceManager(context) // ✅ Use PreferenceManager

    var oldPassword by remember { mutableStateOf("") }
    var newPassword by remember { mutableStateOf("") }
    var oldPasswordVisible by remember { mutableStateOf(false) }
    var newPasswordVisible by remember { mutableStateOf(false) }

    val updateState by viewModel.updatePasswordState.collectAsState()

    LaunchedEffect(updateState) {
        when(updateState){
            is UpdatePasswordState.Success ->{
                ToastHelper(
                    context,
                    (updateState as UpdatePasswordState.Success).message,
                    ToastStatus.SUCCESS
                )
                viewModel.resetState()
            }

            is UpdatePasswordState.Error -> {
                ToastHelper(
                    context,
                    (updateState as UpdatePasswordState.Error).message,
                    ToastStatus.ERROR
                )
                viewModel.resetState()
            }
            else -> Unit
        }
    }

    Scaffold(
        topBar = {
            AppTopBar(title = "Update Password", onBackClick = onBackClick)
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 20.dp)
                .imePadding()                     // ✅ keyboard safe
                .verticalScroll(rememberScrollState()), // ✅ scroll enabled
            verticalArrangement = Arrangement.Center
        ) {

            // OLD PASSWORD
            Text("Old Password:")
            Spacer(Modifier.height(6.dp))
            OutlinedTextField(
                value = oldPassword,
                onValueChange = { oldPassword = it },
                placeholder = { Text("Enter your old password") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                shape = RoundedCornerShape(10.dp),
                visualTransformation =
                    if (oldPasswordVisible) VisualTransformation.None
                    else PasswordVisualTransformation(),
                trailingIcon = {
                    IconButton(
                        onClick = { oldPasswordVisible = !oldPasswordVisible },
                    ) {
                        Icon(
                            imageVector =
                                if (oldPasswordVisible) Icons.Default.Visibility
                                else Icons.Default.VisibilityOff,
                            contentDescription = null
                        )
                    }
                }
            )

            Spacer(Modifier.height(16.dp))

            // NEW PASSWORD
            Text("New Password:")
            Spacer(Modifier.height(6.dp))
            OutlinedTextField(
                value = newPassword,
                onValueChange = { newPassword = it },
                placeholder = { Text("Enter new password") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                shape = RoundedCornerShape(10.dp),
                visualTransformation =
                    if (newPasswordVisible) VisualTransformation.None
                    else PasswordVisualTransformation(),
                trailingIcon = {
                    IconButton(onClick = { newPasswordVisible = !newPasswordVisible }) {
                        Icon(
                            imageVector =
                                if (newPasswordVisible) Icons.Default.Visibility
                                else Icons.Default.VisibilityOff,
                            contentDescription = null
                        )
                    }
                }
            )

            Spacer(Modifier.height(20.dp))

            // UPDATE BUTTON
            val isLoading = updateState is UpdatePasswordState.Loading
            Button(
                onClick = {
                    val currentToken = prefs.getToken() ?: ""

                    when {
                        oldPassword.isBlank() || newPassword.isBlank() -> {
                            ToastHelper(context, "All fields are required", ToastStatus.ERROR)
                        }
                        currentToken.isEmpty() -> {
                            ToastHelper(context, "Session expired. Please login again.", ToastStatus.ERROR)
                        }
                        else -> {
                            // ✅ Call ViewModel method (no lambda)
                            viewModel.updatePassword(oldPassword, newPassword)
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(12.dp),
                enabled = !isLoading
            ) {
                if(isLoading){
                    Text(text = "Updatting...")
                }else{
                    Text(text = "Update Password")
                }
            }


        }
    }
}
