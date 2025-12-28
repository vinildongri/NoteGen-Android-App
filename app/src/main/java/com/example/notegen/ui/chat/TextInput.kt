package com.example.notegen.ui.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun TextInput(
//    modifier: Modifier,
    inputText: String,
    onTextChange: (String) -> Unit,
    onSendClick: () -> Unit,
    isDarkMode: Boolean
) {

    val backgrooundColor = if(isDarkMode) Color(0xFF262A34) else Color(0xFFF4F7FF)
    val toolsColor = if(isDarkMode) Color(0xFFBDE6FF) else Color(0xFF262A34)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .border(
                width = 1.dp,
                color = Color(0xFFDBE8FD),
                shape = RoundedCornerShape(18.dp)
            )
            .background(
                color = backgrooundColor,
                shape = RoundedCornerShape(18.dp)
            )
    ) {

        // TextField
        TextField(
            value = inputText,
            onValueChange = onTextChange,
            placeholder = { Text("Ask NoteGen") },
            modifier = Modifier.fillMaxWidth(),
            maxLines = 5,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            )
        )

        // Icons row
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            IconButton(onClick = {}) {
                Icon(
                    Icons.Default.Add,
                    contentDescription = "Add",
                    tint = toolsColor
                )
            }

            IconButton(onClick = {}) {
                Icon(
                    Icons.Default.Tune,
                    contentDescription = "Tool",
                    tint = toolsColor
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            if (inputText.isBlank()) {
                IconButton(onClick = {}) {
                    Icon(
                        Icons.Default.Mic,
                        contentDescription = "Mic",
                        tint = toolsColor
                    )
                }
            } else {
                IconButton(onClick = onSendClick) {
                    Icon(
                        Icons.Default.ArrowUpward,
                        contentDescription = "Send",
                        tint = toolsColor
                    )
                }
            }
        }
    }
}
