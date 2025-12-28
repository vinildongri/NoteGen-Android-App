package com.example.notegen.ui.navigation

sealed class AppRoute(val route: String){
    object Chat : AppRoute("chat")
    object SideBar : AppRoute("sidebar")
    object Setting: AppRoute("setting")
    object Login: AppRoute("login")
    object Register: AppRoute("register")
    object UpdateProfile: AppRoute("updateProfile")
    object UpdatePassword: AppRoute("updatePassword")
    object ForgotPassword: AppRoute("forgotPassword")
    object Security: AppRoute("security")
    object About: AppRoute("about")
    object Help: AppRoute("hellp")
}