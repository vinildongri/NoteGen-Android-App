package com.example.notegen.ui.layouts

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.notegen.R

@Composable
fun SideBar(
    isDarkMode: Boolean,
    isUserLoggedIn: Boolean,
    modifier: Modifier = Modifier,
    onClickSetting: () -> Unit,
    onLoginClick: () -> Unit,
    onClosesidebar: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxHeight()
            .padding(start = 12.dp, end = 12.dp, top = 8.dp)
    ) {

        // ☰ Menu
        IconButton(onClick = onClosesidebar) {
            Icon(
                imageVector = Icons.Default.Menu,
                contentDescription = "Menu"
            )
        }

        Spacer(modifier = Modifier.height(18.dp))

        // ✏️ New chat (UI ONLY)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable{}
                .padding(vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.edit_icon),
                contentDescription = "New chat",
                modifier = Modifier.size(30.dp)
            )
            Spacer(modifier = Modifier.width(14.dp))
            Text(
                text = "New chat",
                fontSize = 18.sp
            )
        }

        Spacer(modifier = Modifier.height(70.dp))

        // 📌 Recent
        Text(
            text = "Recent",
            fontSize = 18.sp,
            modifier = Modifier.padding(start = 4.dp, bottom = 10.dp),
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
        )

        // UI-only recent items
        listOf(
            "Gemini-Style Sidebar Fronte...",
            "Building a Gemini-Style Side...",
            "Gemini-Style Sidebar Code",
            "React Search Bar Code Revi..."
        ).forEach {
            Text(
                text = it,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .clickable{},
                fontSize = 15.sp,
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        HorizontalDivider()

        // Bottom action (previous behavior)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    if (isUserLoggedIn) onClickSetting()
                    else onLoginClick()
                }
                .padding(vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = if (isUserLoggedIn)
                    Icons.Default.Settings
                else
                    Icons.Default.Person,
                contentDescription = "Action"
            )
            Spacer(modifier = Modifier.width(14.dp))
            Text(
                text = if (isUserLoggedIn)
                    "Settings & help"
                else
                    "Login",
                fontSize = 16.sp
            )
        }
    }
}
