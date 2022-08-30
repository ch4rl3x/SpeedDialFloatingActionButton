package de.charlex.compose.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import de.charlex.compose.FloatingActionButtonItem
import de.charlex.compose.BottomAppBarSpeedDialFloatingActionButton
import de.charlex.compose.SubSpeedDialFloatingActionButtons
import de.charlex.compose.example.ui.theme.SpeedDialFloatingActionButtonTheme
import de.charlex.compose.rememberSpeedDialFloatingActionButtonState

class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SpeedDialFloatingActionButtonTheme {
                // A surface container using the 'background' color from the theme
                val fabState = rememberSpeedDialFloatingActionButtonState()
                Scaffold(
                    bottomBar = {
                        BottomAppBar(
                            actions = {

                            },
                            floatingActionButton = {
                                BottomAppBarSpeedDialFloatingActionButton(
                                    state = fabState
                                ) {
                                    Icon(Icons.Default.Add, contentDescription = null)
                                }
                            }
                        )
                    },
                    floatingActionButton = {
                        SubSpeedDialFloatingActionButtons(
                            state = fabState,
                            items = listOf(
                                FloatingActionButtonItem(
                                    icon = Icons.Default.Person,
                                    label = "Person"
                                ) {

                                },
                                FloatingActionButtonItem(
                                    icon = Icons.Default.Home,
                                    label = "Home"
                                ) {

                                }
                            )
                        )
                    }
                ) {
                    Surface(
                        modifier = Modifier
                            .padding(it)
                            .fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        Text( text = "Hello")
                    }
                }
            }
        }
    }
}