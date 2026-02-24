package com.example.settings_screen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.settings_screen.ui.theme.Settings_ScreenTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Settings_ScreenTheme {
                SettingsScreen()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen() {
    val snackbarHostState = remember { SnackbarHostState() }
    var showSnackbar by remember { mutableStateOf(false) }
    var darkMode by remember { mutableStateOf(false) }
    var notifications by remember { mutableStateOf(true) }
    var locationAccess by remember { mutableStateOf(false) }
    var autoUpdate by remember { mutableStateOf(true) }
    var fontSize by remember { mutableFloatStateOf(16f) }

    LaunchedEffect(showSnackbar) {
        if (showSnackbar) {
            snackbarHostState.showSnackbar("Cache cleared", duration = SnackbarDuration.Short)
            showSnackbar = false
        }
    }

    Scaffold(
        //fillMaxSize modifier req.
        modifier = Modifier.fillMaxSize(),
        topBar = {
            //Material3 Req. 1
            TopAppBar(
                title = { Text("Settings") },
                navigationIcon = {
                    //Material3 Req. 2
                    IconButton(onClick = { }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        //Material3 Req. 3
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                //padding modifier req.
                .padding(horizontal = 16.dp)
        ) {
            Text("Appearance", style = MaterialTheme.typography.labelLarge, color = MaterialTheme.colorScheme.primary)

            SettingRow(label = "Dark Mode", desc = "Use dark theme", onClick = { darkMode = !darkMode }) {
                //Material3 Req. 1
                Switch(checked = darkMode, onCheckedChange = { darkMode = it })
            }

            //Material3 Req. 4
            HorizontalDivider()

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    //border modifier req.
                    .border(1.dp, MaterialTheme.colorScheme.outlineVariant, RoundedCornerShape(8.dp))
                    //clip modifier req.
                    .clip(RoundedCornerShape(8.dp))
                    .padding(12.dp)
                    .heightIn(min = 56.dp),
                //alignment modifier req.
                verticalAlignment = Alignment.CenterVertically
            ) {
                //weight modifier req.
                Column(modifier = Modifier.weight(1f)) {
                    Text("Font Size", style = MaterialTheme.typography.bodyLarge)
                    Text("${fontSize.toInt()} sp", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
                //Material3 Req. 5
                Slider(
                    value = fontSize,
                    onValueChange = { fontSize = it },
                    valueRange = 12f..28f,
                    //sizeIn modifier req.
                    modifier = Modifier.weight(1f).sizeIn(maxHeight = 48.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
            Text("Notifications", style = MaterialTheme.typography.labelLarge, color = MaterialTheme.colorScheme.primary)

            SettingRow(label = "Push Notifications", desc = "Receive alerts", onClick = { notifications = !notifications }) {
                Switch(checked = notifications, onCheckedChange = { notifications = it })
            }

            HorizontalDivider()

            SettingRow(label = "Location Access", desc = "Allow location-based alerts", onClick = { locationAccess = !locationAccess }) {
                Checkbox(checked = locationAccess, onCheckedChange = { locationAccess = it })
            }

            Spacer(modifier = Modifier.height(16.dp))
            Text("Data & Storage", style = MaterialTheme.typography.labelLarge, color = MaterialTheme.colorScheme.primary)

            SettingRow(label = "Auto-Update", desc = "Download updates over Wi-Fi", onClick = { autoUpdate = !autoUpdate }) {
                Checkbox(checked = autoUpdate, onCheckedChange = { autoUpdate = it })
            }

            HorizontalDivider()

            Row(
                //heightIn modifier req.
                modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp).heightIn(min = 56.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text("Clear Cache", style = MaterialTheme.typography.bodyLarge)
                    Text("Free up storage space", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
                FilledTonalButton(onClick = { showSnackbar = true }) { Text("Clear") }
            }

            Spacer(modifier = Modifier.height(16.dp))

            //Material3 Req. 6
            Card(
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Settings Screen App", style = MaterialTheme.typography.titleMedium)
                    Text("Version 1.0.0", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
fun SettingRow(label: String, desc: String, onClick: () -> Unit, control: @Composable () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            //background modifier req.
            .background(MaterialTheme.colorScheme.surface)
            //clickable modifier req.
            .clickable { onClick() }
            .padding(vertical = 12.dp, horizontal = 4.dp)
            .heightIn(min = 56.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(label, style = MaterialTheme.typography.bodyLarge)
            Text(desc, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
        control()
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SettingsScreenPreview() {
    Settings_ScreenTheme {
        SettingsScreen()
    }
}