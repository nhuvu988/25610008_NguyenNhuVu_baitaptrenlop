package com.example.quadrant

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
    Column(modifier.fillMaxSize()) {
        Row(Modifier.weight(1f)) {
            InfoQuadrant(stringResource(R.string.first_title), stringResource(R.string.first_description), Color(0xFFEADDFF), Modifier.weight(1f))
            InfoQuadrant(stringResource(R.string.second_title), stringResource(R.string.second_description), Color(0xFFD0BCFF), Modifier.weight(1f))
        }
        Row(Modifier.weight(1f)) {
            InfoQuadrant(stringResource(R.string.third_title), stringResource(R.string.third_description), Color(0xFFB69DF8), Modifier.weight(1f))
            InfoQuadrant(stringResource(R.string.fourth_title), stringResource(R.string.fourth_description), Color(0xFFF6EDFF), Modifier.weight(1f))
        }
    }
}

@Composable
fun InfoQuadrant(title: String, description: String, background: Color, modifier: Modifier = Modifier) {
    Column(modifier.fillMaxHeight().background(background).padding(16.dp).verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
        Text(title, fontWeight = FontWeight.Bold, modifier = Modifier.padding(bottom = 16.dp))
        Text(description, textAlign = TextAlign.Justify)
    }
}
