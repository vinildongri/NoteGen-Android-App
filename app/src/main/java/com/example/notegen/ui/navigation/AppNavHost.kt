package com.example.notegen.ui.navigation

import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.notegen.data.remote.RetrofitInstance
import com.example.notegen.data.remote.api.AuthApi
import com.example.notegen.data.repository.LoginRepository
import com.example.notegen.data.repository.RegisterRepository
import com.example.notegen.ui.auth.Login
import com.example.notegen.ui.auth.Register
import com.example.notegen.ui.auth.UpdatePassword
import com.example.notegen.ui.auth.UpdateProfile
import com.example.notegen.ui.chat.ChatScreen
import com.example.notegen.ui.layouts.Setting
import com.example.notegen.utils.PreferenceManager
import com.example.notegen.viewModels.LoginViewModel
import com.example.notegen.viewModels.NoteViewModel
import com.example.notegen.viewModels.RegisterViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.notegen.data.repository.ForgotPasswordRepository
import com.example.notegen.ui.auth.ForgotPassword
import com.example.notegen.ui.layouts.About
import com.example.notegen.ui.layouts.Help
import com.example.notegen.ui.layouts.Security
import com.example.notegen.viewModels.ForgotPasswordViewModel
import com.example.notegen.viewModels.UpdateProfileViewModel
import com.example.notegen.viewmodel.UpdatePasswordViewModel
import kotlinx.coroutines.launch

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: String = AppRoute.Chat.route,
    isDarkMode: Boolean,
    onDarkModeChange: (Boolean) -> Unit
) {
    val context = LocalContext.current
    val prefs = remember { PreferenceManager(context) }
    var sharedUserName by remember { mutableStateOf<String?>(null) }
    var sharedUserEmail by remember { mutableStateOf<String?>(null) }

    // Load saved username/email from prefs when the app starts
    LaunchedEffect(Unit) {
        sharedUserName = prefs.getUserName() ?: ""
        sharedUserEmail = prefs.getUserEmail() ?: ""
    }

    val scope = rememberCoroutineScope()

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(AppRoute.Chat.route) { backStackEntry ->
            val chatViewModel: NoteViewModel =
                viewModel(backStackEntry)
            ChatScreen(
                viewModel = chatViewModel,
                isDarkMode = isDarkMode,
                onOpenSettings = { navController.navigate(AppRoute.Setting.route) },
                onLoginClick = { navController.navigate(AppRoute.Login.route) },
                userName = sharedUserName
            )
        }

        composable(AppRoute.Setting.route) {
            Setting(
                userName = sharedUserName ?: "",
                email = sharedUserEmail.takeIf { !it.isNullOrEmpty() } ?: "No email",
                isDarkMode = isDarkMode,
                onDarkModeChange = onDarkModeChange,
                onBackClick = { navController.popBackStack() },
                onLogoutClick = {
                    scope.launch {
                        prefs.clearAll()
                        sharedUserName = null
                        sharedUserEmail = null
                        navController.navigate(AppRoute.Chat.route) {
                            popUpTo(0) { inclusive = true }
                        }
                    }
                },
                onUpdateProfileClick = { navController.navigate(AppRoute.UpdateProfile.route) },
                onUpdatePasswordClick = { navController.navigate(AppRoute.UpdatePassword.route) },
                onSecurityClick = {navController.navigate(AppRoute.Security.route)},
                onAboutClick = {navController.navigate(AppRoute.About.route)},
                onHelpClick = {navController.navigate(AppRoute.Help.route)}
            )
        }

        composable(AppRoute.Login.route) {
            val api = remember { RetrofitInstance.authApi }
            val repository = remember { LoginRepository(api) }
            val loginViewModel = remember { LoginViewModel(repository) }

            Login(
                viewModel = loginViewModel,
                onBackClick = { navController.popBackStack() },
                onClickNewUser = { navController.navigate(AppRoute.Register.route) },
                onLoginSuccess = { userName, email ->
                    sharedUserName = userName
                    sharedUserEmail = email
                    prefs.saveUserName(userName)
                    prefs.saveUserEmail(email)
                    navController.popBackStack()
                },
                onForgotPasswordClick = {navController.navigate(AppRoute.ForgotPassword.route)}
            )
        }

        composable(AppRoute.Register.route) {
            val api = remember { RetrofitInstance.authApi }
            val repository = remember { RegisterRepository(api) }
            val registerViewModel = remember { RegisterViewModel(repository) }

            Register(
                onBackClick = { navController.popBackStack() },
                viewModel = registerViewModel,
                onRegisterSuccess = { navController.popBackStack() }
            )
        }

        composable(AppRoute.UpdateProfile.route) {
            val viewModel: UpdateProfileViewModel = viewModel()

            UpdateProfile(
                onBackClick = { navController.popBackStack() },
                userName = sharedUserName ?: "",
                email = sharedUserEmail ?: "",
                viewModel = viewModel,
                onProfileUpdateSuccess = { newName, newEmail ->
                    // 1. Update the state immediately so UI refreshes
                    sharedUserName = newName
                    sharedUserEmail = newEmail

                    // 2. Ensure prefs are synced (redundant safety)
                    prefs.saveUserName(newName)
                    prefs.saveUserEmail(newEmail)

                    // 3. Navigate back to Settings
                    navController.popBackStack()
                }
            )
        }

        composable(AppRoute.UpdatePassword.route) {
            val viewModel: UpdatePasswordViewModel = viewModel() // ✅ AndroidViewModel
            UpdatePassword(
                onBackClick = { navController.popBackStack() },
                viewModel = viewModel
            )
        }

        composable(AppRoute.ForgotPassword.route){
            // 1. Create the dependencies manually (same as you did for Login)
            val api = remember { RetrofitInstance.authApi }
            val repository = remember { ForgotPasswordRepository(api) }
            val viewModel = remember { ForgotPasswordViewModel(repository) }

            // 2. Pass the viewModel to the screen
            ForgotPassword(
                viewModel = viewModel,
                onBackClick = {navController.popBackStack()}
            )
        }

        composable(AppRoute.Security.route){
            Security(
                onBackClick = {navController.popBackStack()}
            )
        }

        composable(AppRoute.About.route){
            About(
                onBackClick = {navController.popBackStack()}
            )
        }

        composable(AppRoute.Help.route){
            Help(
                onBackClick = {navController.popBackStack()}
            )
        }
    }
}
