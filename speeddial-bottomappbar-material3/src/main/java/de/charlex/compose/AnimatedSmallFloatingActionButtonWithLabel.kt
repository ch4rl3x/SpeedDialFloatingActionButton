package de.charlex.compose

import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale

@Composable
internal fun AnimatedSmallFloatingActionButtonWithLabel(
    modifier: Modifier = Modifier,
    showLabel: Boolean,
    labelContent: @Composable () -> Unit,
    fabContent: @Composable () -> Unit,
    state: SpeedDialFloatingActionButtonState
) {
    val alpha: State<Float>? = state.transition?.animateFloat(
        transitionSpec = {
            tween(durationMillis = 50)
        },
        label = "",
        targetValueByState = {
            if (it == SpeedDialState.EXPANDED) 1f else 0f
        }
    )
    val scale: State<Float>? = state.transition?.animateFloat(
        label = "",
        targetValueByState = {
            if (it == SpeedDialState.EXPANDED) 1.0f else 0f
        }
    )
    Row(
        modifier = modifier
            .alpha(animateFloatAsState((alpha?.value ?: 0f)).value)
            .scale(animateFloatAsState(targetValue = scale?.value ?: 0f).value),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        if (showLabel) {
            labelContent.invoke()
        }
        fabContent.invoke()
    }
}
