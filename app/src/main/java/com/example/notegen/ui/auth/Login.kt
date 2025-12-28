package com.example.notegen.ui.auth

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.example.notegen.ui.layouts.AppTopBar
import com.example.notegen.ui.layouts.ToastHelper
import com.example.notegen.ui.layouts.ToastStatus
import com.example.notegen.utils.PreferenceManager
import com.example.notegen.viewModels.LoginState
import com.example.notegen.viewModels.LoginViewModel


@Composable
fun Login(
    onClickNewUser: () -> Unit = {},
    onBackClick: () -> Unit = {},
    viewModel: LoginViewModel,
    onLoginSuccess:(userName: String, email: String) -> Unit,
    onForgotPasswordClick: ()-> Unit = {}
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(uiState) {
        when (uiState) {
            is LoginState.Error -> {
                ToastHelper(context, (uiState as LoginState.Error).message, ToastStatus.ERROR)
                viewModel.resetState()
            }
            is LoginState.Success -> {
                val success = uiState as LoginState.Success
                ToastHelper(context, "Login Successful", ToastStatus.SUCCESS)

                // ✅ Save login data safely using PreferenceManager
                val prefs = PreferenceManager(context)
                prefs.saveToken(success.token)
                prefs.saveUserName(success.userName)
                prefs.saveUserEmail(success.email)

                onLoginSuccess(success.userName, success.email)
                viewModel.resetState()
            }
            else -> {}
        }
    }


    Scaffold(
        topBar = {
            AppTopBar(
                title = "Login",
                onBackClick = onBackClick
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 20.dp)
                .imePadding()                     // ✅ keyboard safe
                .verticalScroll(rememberScrollState()), // ✅ scroll enabled,
            verticalArrangement = Arrangement.Center
        ) {
            // EMAIL
            Text(text = "Email:")
            Spacer(modifier = Modifier.height(6.dp))

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                placeholder = { Text("Enter your email") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                shape = RoundedCornerShape(10.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // PASSWORD
            Text(text = "Password:")
            Spacer(modifier = Modifier.height(6.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                placeholder = { Text("Enter your password") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                shape = RoundedCornerShape(10.dp),

                visualTransformation = if (passwordVisible)
                    VisualTransformation.None
                else
                    PasswordVisualTransformation(),

                trailingIcon ={
                    IconButton(onClick = { passwordVisible = !passwordVisible}) {
                        Icon(
                            imageVector = if (passwordVisible)
                                Icons.Default.Visibility
                            else
                                Icons.Default.VisibilityOff,
                            contentDescription = "Toggle password visibility"
                        )
                    }
                }
            )

            Spacer(modifier = Modifier.height(8.dp))

            // FORGOT PASSWORD
            Text(
                text = "Forgot Password",
                modifier = Modifier
                    .align(Alignment.End)
                    .clickable { onForgotPasswordClick()},
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.bodySmall.copy(
                    textDecoration = TextDecoration.Underline
                )
            )

            Spacer(modifier = Modifier.height(20.dp))

            // LOGIN BUTTON
            Button(
                onClick = { viewModel.login(email, password) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(12.dp),
                enabled = uiState !is LoginState.Loading
            ) {
                if (uiState is LoginState.Loading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(22.dp),
                        strokeWidth = 2.dp
                    )
                } else {
                    Text(text = "Login")
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // NEW USER
            Text(
                text = "New User",
                modifier = Modifier
                    .align(Alignment.End)
                    .clickable { onClickNewUser() },
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.bodySmall.copy(
                    textDecoration = TextDecoration.Underline
                )
            )
        }
    }
}
