package com.example.notegen.ui.chat

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.notegen.ui.layouts.Header
import com.example.notegen.ui.layouts.SideBar
import com.example.notegen.viewModels.NoteViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.delay // Make sure to import this


@Composable
fun ChatScreen(
    viewModel: NoteViewModel = viewModel(),
    isDarkMode: Boolean,
    onOpenSettings: () -> Unit,
    onLoginClick: () -> Unit,
    userName: String?
) {
    val messages by viewModel.messages.collectAsState()
    var messageText by rememberSaveable { mutableStateOf("") }
    val listState = rememberLazyListState()

    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    BackHandler(enabled = drawerState.isOpen) {
        scope.launch {
            drawerState.close()
        }
    }

    LaunchedEffect(messages.size) {
        if (messages.isNotEmpty()) {
            listState.animateScrollToItem(messages.lastIndex)
        }
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                modifier = Modifier
                    .width(300.dp)
                    .consumeWindowInsets(WindowInsets.navigationBars),
                drawerShape = RoundedCornerShape(topEnd = 16.dp, bottomEnd = 16.dp),
                drawerContainerColor =
                    if (isDarkMode) Color(0xFF1E1F20) else Color(0xFFF0F4F9)
            ) {
                SideBar(
                    isDarkMode = isDarkMode,
                    modifier = Modifier.fillMaxSize(),
                    isUserLoggedIn = !userName.isNullOrBlank(),
                    onClickSetting = onOpenSettings,
                    onLoginClick = onLoginClick,
                    onClosesidebar = {
                        scope.launch{ drawerState.close() }
                    }
                )
            }
        }
    ) {
        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .imePadding(),
            topBar = {
                Header(
                    userName = userName,
                    onMenuClick = { scope.launch { drawerState.open() } },
                    onLoginClick = onLoginClick
                )
            },
            bottomBar = {
                TextInput(
                    inputText = messageText,
                    onTextChange = { messageText = it },
                    onSendClick = {
                        viewModel.sendUserMessage(messageText)
                        messageText = ""
                    },
                    isDarkMode = isDarkMode
                )
            }
        ) { padding ->

            // --- UPDATED CONTENT LOGIC START ---
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                if (messages.isEmpty()) {
                    // 1. Show Greeting if messages are empty
                    GreetingMessage(userName = userName)
                } else {
                    // 2. Show Chat List if messages exist
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 12.dp),
                        state = listState,
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        contentPadding = PaddingValues(bottom = 16.dp)
                    ) {
                        items(messages) { (msg, isUser) ->
                            if (isUser) {
                                UserMessageItem(msg)
                            } else {
                                BotMessageItem(
                                    isDarkMode = isDarkMode,
                                    message = msg
                                )
                            }
                        }
                    }
                }
            }
            // --- UPDATED CONTENT LOGIC END ---
        }
    }
}

// --- Helper Composable for the Gemini-style Greeting ---

@Composable
fun GreetingMessage(userName: String?) {
    // 1. Define the full text we want to show
    val targetText = if (userName.isNullOrBlank()) {
        "How can I help you today?"
    } else {
        "How can I help you today,\n$userName?" // \n forces the name to the second line
    }

    // 2. State to hold the text being typed out
    var displayedText by remember { mutableStateOf("") }

    // 3. State for the cursor visibility (blinking effect)
    var showCursor by remember { mutableStateOf(true) }

    // 4. The Typing Animation Logic
    LaunchedEffect(targetText) {
        displayedText = "" // Reset
        targetText.forEach { char ->
            displayedText += char
            delay(50) // Adjust speed here (lower = faster)
        }
        // Stop blinking cursor shortly after typing finishes
        delay(1000)
        showCursor = false
    }

    // 5. Blinking Cursor Animation Loop
    LaunchedEffect(showCursor) {
        while (showCursor) {
            delay(500)
            // We'll handle the visual toggle in the UI code below
        }
    }

    // Gradient Brush (Same as you liked)
    val gradientBrush = Brush.linearGradient(
        colors = listOf(
            Color(0xFF4285F4), // Blue
            Color(0xFF9C27B0)  // Purple
        )
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp), // Keep the padding that worked for 2 lines
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            // Append a cursor symbol "|" if showCursor is true, but make it toggle?
            // Simpler approach: Just typing effect is usually cleaner for gradients.
            text = displayedText,
            style = TextStyle(
                brush = gradientBrush,
                fontSize = 28.sp,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Start
            ),
            lineHeight = 38.sp
        )
    }
}