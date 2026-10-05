package com.example.article

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
fun LessonScreen(modifier: Modifier = Modifier) {
    Column(modifier.fillMaxSize().verticalScroll(rememberScrollState())) {
        Image(painterResource(R.drawable.bg_compose_background), contentDescription = null,
            modifier = Modifier.fillMaxWidth(), contentScale = ContentScale.FillWidth)
        Text(stringResource(R.string.title_jetpack_compose_tutorial), fontSize = 24.sp, modifier = Modifier.padding(16.dp))
        Text(stringResource(R.string.compose_short_desc), textAlign = TextAlign.Justify, modifier = Modifier.padding(horizontal = 16.dp))
        Text(stringResource(R.string.compose_long_desc), textAlign = TextAlign.Justify, modifier = Modifier.padding(16.dp))
    }
}
