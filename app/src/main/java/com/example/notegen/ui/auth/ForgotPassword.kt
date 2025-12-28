package com.example.notegen.ui.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.notegen.ui.layouts.AppTopBar
import com.example.notegen.ui.layouts.ToastHelper
import com.example.notegen.ui.layouts.ToastStatus
import com.example.notegen.viewModels.ForgotPasswordState
import com.example.notegen.viewModels.ForgotPasswordViewModel

@Composable
fun ForgotPassword(
    modifier: Modifier = Modifier,
    viewModel: ForgotPasswordViewModel,
    onBackClick: () -> Unit = {}
) {

    var email by remember { mutableStateOf("") }

    val lightBlueColor = Color(0xFF72A3FF)
    val primaryBlue = Color(0xFF72A3FF)


    // 🔥 OBSERVE STATE
    val uiState = viewModel.state.value
    val context = LocalContext.current

    // 🔥 STATE FEEDBACK (NO UI CHANGE)
    when (uiState) {
        is ForgotPasswordState.Success -> {
            ToastHelper(context, uiState.message, ToastStatus.SUCCESS)
        }

        is ForgotPasswordState.Error -> {
            ToastHelper(context, uiState.message, ToastStatus.ERROR)
        }

        else -> Unit
    }

    Scaffold(
        topBar = {
            AppTopBar(
                title = "Reset your password",
                onBackClick = onBackClick
            )
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 24.dp)
                .imePadding()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.Start
        ) {

            Spacer(modifier = Modifier.height(48.dp))

            Text(
                text = "Forgot your password?",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = primaryBlue
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Enter the email associated with your account and we’ll send you a link to reset your password.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
            )

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "Email address",
                style = MaterialTheme.typography.labelMedium
            )

            Spacer(modifier = Modifier.height(6.dp))

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                placeholder = { Text("Enter your email") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = primaryBlue,
                    cursorColor = primaryBlue
                ),
                shape = RoundedCornerShape(10.dp)
            )

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = {
                    viewModel.forgotPassword(
                        email = email,
                        token = "token=YOUR_JWT_TOKEN"
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = primaryBlue
                ),
                shape = MaterialTheme.shapes.medium,
                enabled = uiState !is ForgotPasswordState.Loading
            ) {
                if (uiState is ForgotPasswordState.Loading) {
                    CircularProgressIndicator(
                        strokeWidth = 2.dp,
                        modifier = Modifier.size(20.dp),
                        color = Color.White
                    )
                } else {
                    Text(
                        text = "Send reset link",
                        modifier = Modifier.background(Color(0xFF72A3FF))
                    )
                }
            }
        }
    }
}
