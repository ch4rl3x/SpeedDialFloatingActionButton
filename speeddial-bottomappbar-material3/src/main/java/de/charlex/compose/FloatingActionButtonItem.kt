package de.charlex.compose

import androidx.compose.ui.graphics.vector.ImageVector

open class FloatingActionButtonItem(
    val icon: ImageVector,
    val label: String,
    val onFabItemClicked: () -> Unit
)
