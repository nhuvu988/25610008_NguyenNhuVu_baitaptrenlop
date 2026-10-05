package com.example.businesscard

import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Email
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
    Column(modifier.fillMaxSize().background(Color(0xFFD2E8D4)).padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally) {
        Column(Modifier.weight(1f).verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
            Image(painterResource(R.drawable.android_logo), contentDescription = null,
                modifier = Modifier.size(120.dp).background(Color(0xFF073042)))
            Text(stringResource(R.string.full_name), fontSize = 36.sp, textAlign = TextAlign.Center,
                fontWeight = FontWeight.Light, modifier = Modifier.padding(top = 16.dp))
            Text(stringResource(R.string.title), color = Color(0xFF006D3B), fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center, modifier = Modifier.padding(top = 8.dp))
        }
        Column(Modifier.fillMaxWidth().padding(bottom = 24.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
            ContactRow(androidx.compose.material.icons.Icons.Default.Phone, stringResource(R.string.phone))
            ContactRow(androidx.compose.material.icons.Icons.Default.Share, stringResource(R.string.social))
            ContactRow(androidx.compose.material.icons.Icons.Default.Email, stringResource(R.string.email))
        }
    }
}

@Composable
fun ContactRow(icon: androidx.compose.ui.graphics.vector.ImageVector, value: String, modifier: Modifier = Modifier) {
    Row(modifier, verticalAlignment = Alignment.CenterVertically) {
        Icon(icon, contentDescription = null, tint = Color(0xFF006D3B), modifier = Modifier.size(24.dp))
        Spacer(Modifier.width(20.dp))
        Text(value)
    }
}
