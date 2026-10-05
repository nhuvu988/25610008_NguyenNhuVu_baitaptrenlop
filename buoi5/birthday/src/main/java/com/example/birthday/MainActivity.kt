package com.example.birthday

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MaterialTheme(colorScheme = lightColorScheme()) {
                Surface(Modifier.fillMaxSize().safeDrawingPadding()) { LessonScreen() }
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 740)
@Composable
fun LessonPreview() { MaterialTheme(colorScheme = lightColorScheme()) { LessonScreen() } }

@Composable
fun LessonScreen() {
    GreetingImage(stringResource(R.string.birthday_message), stringResource(R.string.signature))
}

@Composable
fun GreetingText(message: String, from: String, modifier: Modifier = Modifier) {
    Column(modifier.verticalScroll(rememberScrollState()), verticalArrangement = Arrangement.Center) {
        Text(message, fontSize = 100.sp, lineHeight = 116.sp, textAlign = TextAlign.Center)
        Text(from, fontSize = 36.sp, modifier = Modifier.padding(16.dp).align(Alignment.End))
    }
}

@Composable
fun GreetingImage(message: String, from: String, modifier: Modifier = Modifier) {
    Box(modifier.fillMaxSize()) {
        Image(painterResource(R.drawable.androidparty), contentDescription = null,
            contentScale = ContentScale.Crop, alpha = 0.5f, modifier = Modifier.matchParentSize())
        GreetingText(message, from, Modifier.fillMaxSize().padding(8.dp))
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 740)
@Composable
fun BirthdayTextPreview() {
    MaterialTheme { GreetingText(stringResource(R.string.birthday_message), stringResource(R.string.signature), Modifier.fillMaxSize()) }
}
