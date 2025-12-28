package com.example.notegen.ui.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun BotMessageItem(
    message: String,
    isDarkMode: Boolean
) {
    // ✅ YOUR COLORS — UNCHANGED
    val bubbleBackgroundColor =
        if (isDarkMode) Color(0xFF262A34) else Color(0xFFE3EDFF)

    val bubbleTextColor =
        if (isDarkMode) Color.White else Color.Black

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.Top
    ) {

        // Avatar
        Box(
            modifier = Modifier
                .size(36.dp)
                .clip(CircleShape)
                .background(bubbleBackgroundColor),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "N",
                color = Color(0xFF0D47A1), // YOUR COLOR
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.width(8.dp))

        // Message bubble
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(4.dp, 20.dp, 20.dp, 20.dp))
                .background(bubbleBackgroundColor)
                .padding(horizontal = 16.dp, vertical = 12.dp)
                .widthIn(max = 300.dp)
        ) {
            Text(
                text = message,
                color = bubbleTextColor,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}
