package de.charlex.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Place this component in the scaffold FloatingActionButton slot
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T : FloatingActionButtonItem> SubSpeedDialFloatingActionButtons(
    items: List<T>,
    showLabels: Boolean = true,
    state: SpeedDialFloatingActionButtonState,
    verticalArrangement: Arrangement.Vertical = Arrangement.Top,
    labelContent: @Composable (T) -> Unit = {
        val backgroundColor = MaterialTheme.colorScheme.primaryContainer
        Surface(
            color = backgroundColor,
            shape = RoundedCornerShape(12.0.dp),
            shadowElevation = 2.dp,
            onClick = { it.onFabItemClicked() }
        ) {
            Text(
                text = it.label,
                color = contentColorFor(backgroundColor = backgroundColor),
                modifier = Modifier
                    .padding(start = 6.dp, end = 6.dp, top = 4.dp, bottom = 4.dp)
            )
        }
    },
    fabContent: @Composable (T) -> Unit = {
        SmallFloatingActionButton(
            modifier = Modifier
                .padding(4.dp),
            onClick = { it.onFabItemClicked() },
            elevation = FloatingActionButtonDefaults.elevation(
                defaultElevation = 2.dp,
                hoveredElevation = 4.dp
            )
        ) {
            Icon(
                imageVector = it.icon,
                contentDescription = it.label
            )
        }
    }
) {
    Column(
        horizontalAlignment = Alignment.End,
        verticalArrangement = verticalArrangement
    ) {
        items.forEach { item ->
            AnimatedSmallFloatingActionButtonWithLabel(
                state = state,
                showLabel = showLabels,
                labelContent = {
                    labelContent(item)
                },
                fabContent = {
                    fabContent(item)
                }
            )
        }
    }
}
