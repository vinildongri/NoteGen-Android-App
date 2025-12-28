package com.example.notegen.ui.layouts

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Gavel
import androidx.compose.material.icons.filled.Policy
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun About(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit = {}
) {
    val uriHandler = LocalUriHandler.current

    // Use MaterialTheme colors so it changes automatically
    val backgroundColor = MaterialTheme.colorScheme.background
    val cardColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
    val primaryTextColor = MaterialTheme.colorScheme.onBackground
    val secondaryTextColor = MaterialTheme.colorScheme.onSurfaceVariant
    val linkColor = MaterialTheme.colorScheme.primary

    Scaffold(
        topBar = {
            AppTopBar(
                title = "About",
                onBackClick = onBackClick
            )
        },
        containerColor = backgroundColor
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            // --- 1. Logo & App Name ---
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(
                        Brush.linearGradient(
                            colors = listOf(Color(0xFF4285F4), Color(0xFF8AB4F8))
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Description,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(40.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "NoteGen",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = primaryTextColor
            )

            Spacer(modifier = Modifier.height(4.dp))

            // --- CHANGED SECTION START: Clickable "Vinil" ---
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "NoteGen by ",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = primaryTextColor
                )
                Text(
                    text = "Vinil",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = linkColor, // Uses the primary link color
                    textDecoration = TextDecoration.Underline,
                    modifier = Modifier.clickable {
                        uriHandler.openUri("https://portfolio-bay-phi-hojhzpleqf.vercel.app/")
                    }
                )
            }
            // --- CHANGED SECTION END ---

            Text(
                text = "Version 1.0.2 (Build 2025.12)",
                fontSize = 14.sp,
                color = secondaryTextColor,
                modifier = Modifier.padding(top = 4.dp)
            )

            Spacer(modifier = Modifier.height(32.dp))

            // --- 2. Developer Credit Card ---
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(cardColor)
                    .padding(16.dp)
            ) {
                Text(
                    text = "Developer Credit",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = primaryTextColor
                )
                Spacer(modifier = Modifier.height(8.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "Designed and Engineered by ",
                        fontSize = 14.sp,
                        color = secondaryTextColor
                    )
                    Text(
                        text = "Vinil",
                        fontSize = 14.sp,
                        color = linkColor,
                        textDecoration = TextDecoration.Underline,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.clickable {
                            uriHandler.openUri("https://portfolio-bay-phi-hojhzpleqf.vercel.app/")
                        }
                    )
                    Text(
                        text = ".",
                        fontSize = 14.sp,
                        color = secondaryTextColor
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // --- 3. Legal Section ---
            Text(
                text = "Legal",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = primaryTextColor,
                modifier = Modifier.fillMaxWidth().padding(start = 4.dp, bottom = 8.dp)
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(cardColor)
            ) {
                AboutMenuItem(
                    icon = Icons.Default.Description,
                    text = "Terms of Service",
                    textColor = primaryTextColor,
                    iconColor = secondaryTextColor,
                    onClick = { /* Open Terms */ }
                )

                Divider(
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f),
                    thickness = 1.dp
                )

                AboutMenuItem(
                    icon = Icons.Default.Policy,
                    text = "Privacy Policy",
                    textColor = primaryTextColor,
                    iconColor = secondaryTextColor,
                    onClick = { /* Open Privacy */ }
                )

                Divider(
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f),
                    thickness = 1.dp
                )

                AboutMenuItem(
                    icon = Icons.Default.Gavel,
                    text = "Open Source Licenses",
                    textColor = primaryTextColor,
                    iconColor = secondaryTextColor,
                    onClick = { /* Open Licenses */ }
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // --- 4. Copyright Footer ---
            Text(
                text = "Copyright © 2025 Yandu. All rights reserved.",
                fontSize = 13.sp,
                color = secondaryTextColor,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 24.dp)
            )
        }
    }
}

@Composable
fun AboutMenuItem(
    icon: ImageVector,
    text: String,
    textColor: Color,
    iconColor: Color,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 16.dp, horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = iconColor,
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = text,
            fontSize = 15.sp,
            color = textColor,
            modifier = Modifier.weight(1f)
        )
    }
}