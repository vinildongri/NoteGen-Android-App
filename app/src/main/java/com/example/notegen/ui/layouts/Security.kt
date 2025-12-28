package com.example.notegen.ui.layouts

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Security
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Security(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit = {}
) {
    Scaffold(
        topBar = {
            // Ensure your AppTopBar also uses MaterialTheme colors internally!
            AppTopBar(
                title = "Security",
                onBackClick = onBackClick
            )
        },
        // Adapts to white in Light Mode, dark grey in Dark Mode
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            // --- Section 1: Data & Privacy ---
            SectionHeader(
                title = "Data & Privacy",
                icon = Icons.Default.Security,
                color = MaterialTheme.colorScheme.onBackground
            )

            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                // Card 1: Encryption
                SecurityInfoCard(
                    icon = Icons.Default.Lock,
                    title = "Data Encryption",
                    description = "All data transmitted to and from NoteGen is encrypted using industry-standard TLS protocols.",
                    // Surface Variant is slightly grey in Light Mode, lighter grey in Dark Mode
                    backgroundColor = MaterialTheme.colorScheme.surfaceVariant,
                    titleColor = MaterialTheme.colorScheme.onSurface,
                    descColor = MaterialTheme.colorScheme.onSurfaceVariant
                )

                // Card 2: Retention
                SecurityInfoCard(
                    icon = Icons.Default.Schedule,
                    title = "Data Retention Policy",
                    description = "Uploaded documents are processed transiently and automatically purged from our servers after 24 hours.",
                    backgroundColor = MaterialTheme.colorScheme.surfaceVariant,
                    titleColor = MaterialTheme.colorScheme.onSurface,
                    descColor = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            // --- Section 2: Account ---
            SectionHeader(
                title = "Account",
                icon = null,
                color = MaterialTheme.colorScheme.onBackground
            )

            Button(
                onClick = { /* Handle Account Deletion Logic */ },
                colors = ButtonDefaults.buttonColors(
                    // Use the theme's error color (usually Red)
                    containerColor = MaterialTheme.colorScheme.error,
                    contentColor = MaterialTheme.colorScheme.onError
                ),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Delete Account & Data",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }

            // Disclaimer text below button
            Text(
                text = "This action is irreversible. All your generated notes and history will be permanently removed.",
                color = MaterialTheme.colorScheme.onSurfaceVariant, // Greyish text in both modes
                fontSize = 12.sp,
                modifier = Modifier.padding(horizontal = 4.dp)
            )
        }
    }
}

// --- Helper Components ---

@Composable
fun SectionHeader(title: String, icon: ImageVector?, color: Color) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        if (icon != null) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = color,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
        }
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            color = color,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun SecurityInfoCard(
    icon: ImageVector,
    title: String,
    description: String,
    backgroundColor: Color,
    titleColor: Color,
    descColor: Color
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.Top
        ) {
            // Icon Box
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = titleColor,
                modifier = Modifier
                    .size(28.dp)
                    .padding(top = 2.dp)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column {
                Text(
                    text = title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = titleColor
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = description,
                    fontSize = 14.sp,
                    color = descColor,
                    lineHeight = 20.sp
                )
            }
        }
    }
}