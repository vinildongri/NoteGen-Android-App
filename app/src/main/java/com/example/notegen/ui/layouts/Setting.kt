package com.example.notegen.ui.layouts

import android.app.Activity
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.alpha
import androidx.core.view.WindowCompat

@Composable
fun Setting(
    userName: String,
    email: String,
    isDarkMode: Boolean,
    onDarkModeChange: (Boolean) -> Unit,
    onBackClick: () -> Unit,
    onLogoutClick: () -> Unit = {},
    onUpdateProfileClick: () -> Unit = {},
    onUpdatePasswordClick: () -> Unit = {},
    onSecurityClick: () -> Unit = {},
    onAboutClick: () -> Unit = {},
    onHelpClick: () -> Unit = {}
) {
    val scrollingState = rememberScrollState()

    Scaffold(
        topBar = {
            AppTopBar(
                title = "Settings",
                onBackClick = onBackClick
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(MaterialTheme.colorScheme.background)
                .padding(top = 4.dp, bottom = 4.dp, start = 16.dp, end = 16.dp)
                .verticalScroll(scrollingState)
        ) {

            val context = LocalContext.current
            val clipboardManager = LocalClipboardManager.current



            // 🔥 PROFILE HEADER
            ProfileHeader(userName)

            // Account
            Text(
                text = "Account",
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(Modifier.height(8.dp))

            CardGroup {
                SettingCardItem(
                    "Email", email,
                    Icons.Default.Mail,
                    onClick = {
                        clipboardManager.setText(AnnotatedString(email ?: ""))
                        ToastHelper(context, "📋 Email copied!", ToastStatus.SUCCESS)
                    }
                )

                SettingCardItem(
                    "Update Profile",
                    null,
                    Icons.Default.Person,
                    onClick = onUpdateProfileClick,
                )

                SettingCardItem(
                    "Update Password",
                    null,
                    Icons.Default.Lock,
                    onClick = onUpdatePasswordClick
                )
            }

            Spacer(Modifier.height(20.dp))

            Text(
                text = "Appearance",
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(Modifier.height(8.dp))

            CardGroup {
                SettingCardItem(
                    title = "Dark Mode",
                    subtitle = "System (Default)",
                    icon = Icons.Default.DarkMode,
                    trailing = {
                        Switch(
                            checked = isDarkMode,
                            onCheckedChange = onDarkModeChange
                        )
                    }
                )

                SettingCardItem(
                    title = "Accent color",
                    subtitle = "Default",
                    icon = Icons.Default.ColorLens,
                    trailing = {
                        Icon(
                            Icons.Default.ExpandMore,
                            contentDescription = null
                        )
                    }
                )
            }

            Spacer(Modifier.height(20.dp))

            CardGroup {
                SettingCardItem(
                    "Security",
                    null,
                    Icons.Default.Security,
                    onClick = onSecurityClick
                )

                SettingCardItem(
                    "About",
                    null,
                    Icons.Default.Info,
                    onClick = onAboutClick
                )
                SettingCardItem(
                    "Help",
                    null,
                    Icons.Default.Help,
                    onClick = onHelpClick
                )
            }

            Spacer(Modifier.height(20.dp))

            // 🔥 FIXED SECTION BELOW
            CardGroup {
                SettingCardItem(
                    title = "Log out",
                    subtitle = null,
                    icon = Icons.Default.Logout,
                    onClick = onLogoutClick, // Comma was missing here
                    textColor = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}


@Composable
fun SettingCardItem(
    title: String,
    subtitle: String? = null,
    icon: ImageVector,
    trailing: @Composable (() -> Unit)? = null,
    textColor: androidx.compose.ui.graphics.Color = MaterialTheme.colorScheme.onSurface,
    onClick: () -> Unit = {} // Ensure this parameter exists
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() } // Ensure this is called
            .padding(horizontal = 16.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        val iconColor = if (textColor == MaterialTheme.colorScheme.error) {
            MaterialTheme.colorScheme.error
        } else {
            MaterialTheme.colorScheme.onSurface
        }

        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = iconColor,
            modifier = Modifier.size(22.dp)
        )

        Spacer(Modifier.width(16.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                fontSize = 16.sp,
                color = textColor
            )

            if (subtitle != null) {
                Text(
                    text = subtitle,
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        trailing?.invoke()
    }
}

@Composable
fun CardGroup(content: @Composable ColumnScope.() -> Unit) {
    Surface(
        shape = RoundedCornerShape(18.dp),
        color = MaterialTheme.colorScheme.surface,
        tonalElevation = 2.dp
    ) {
        Column {
            content()
        }
    }
}

@Composable
fun ProfileHeader(userName: String) {
    val firstLetter = userName.firstOrNull()?.uppercase() ?: "?"

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // Circle Avatar
        Surface(
            shape = CircleShape,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(72.dp)
//                .background( Color(0xFF72A3FF))
        ) {
            Box(contentAlignment = Alignment.Center) {
                Text(
                    text = firstLetter,
                    fontSize = 28.sp,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Full Name
        Text(
            text = userName,
            fontSize = 18.sp,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

