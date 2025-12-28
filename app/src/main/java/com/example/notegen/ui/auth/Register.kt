package com.example.notegen.ui.auth


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.notegen.ui.layouts.AppTopBar
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.example.notegen.ui.layouts.ToastHelper
import com.example.notegen.ui.layouts.ToastStatus
import com.example.notegen.viewModels.RegisterViewModel

@Composable
fun Register(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit = {},
    viewModel: RegisterViewModel,
    onRegisterSuccess: () -> Unit = {}
    ) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    val context = LocalContext.current


    Scaffold(
        topBar = {
            AppTopBar(
                title = "Register",
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
                .verticalScroll(rememberScrollState()), // ✅ scroll enabled
            verticalArrangement = Arrangement.Center
        ) {
            val isLoading = viewModel.isLoading
            var errorMessage by remember { mutableStateOf("") }

            // NAME
            Text(text = "Name")
            Spacer(modifier = Modifier.height(6.dp))

            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                placeholder = { Text("Enter your full name") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                shape = RoundedCornerShape(10.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            // EMAIL
            Text(text = "Email")
            Spacer(modifier = Modifier.height(6.dp))

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                placeholder = { Text("Enter your email") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                shape = RoundedCornerShape(10.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            // PASSWORD
            Text(text = "Password")
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

                trailingIcon = {
                    IconButton (onClick = { passwordVisible = !passwordVisible }) {
                        Icon(
                            imageVector = if (passwordVisible)
                                Icons.Filled.Visibility
                            else
                                Icons.Filled.VisibilityOff,
                            contentDescription = "Toggle password visibility"
                        )
                    }
                }
            )

            Spacer(modifier = Modifier.height(20.dp))

            // REGISTER BUTTON
            Button(
                onClick = {
                    if (name.isBlank() || email.isBlank() || password.isBlank()) {
                        ToastHelper(context, "Fill all fields", ToastStatus.INFO)
                        return@Button
                    }

                    val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
                    if (!email.matches(emailPattern.toRegex())) {
//                        Toast.makeText(context, "Enter a valid email", Toast.LENGTH_SHORT).show()
                        ToastHelper(
                            context,
                            "Enter a valid email",
                            ToastStatus.ERROR
                        )
                        return@Button
                    }

                    viewModel.register(
                        name = name,
                        email = email,
                        password = password,
                        onSuccess = {response ->
                            ToastHelper(
                                context,
                                "Register Successfully",
                                ToastStatus.SUCCESS
                            )
                            onRegisterSuccess()
                        },
                        onError = {error ->
                            ToastHelper(context, error, ToastStatus.ERROR)
                        }
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                if(isLoading){
                    Text(text = "Registering...")
                }else{
                    Text(text = "Register")
                }
            }
        }
    }
}
