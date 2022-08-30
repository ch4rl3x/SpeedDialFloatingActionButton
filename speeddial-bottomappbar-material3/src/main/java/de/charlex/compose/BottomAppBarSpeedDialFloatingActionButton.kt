package de.charlex.compose

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.spring
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.FloatingActionButtonElevation
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape

/**
 * Place this component in the material 3 BottomAppBar
 */
@Composable
fun BottomAppBarSpeedDialFloatingActionButton(
    modifier: Modifier = Modifier,
    state: SpeedDialFloatingActionButtonState,
    shape: Shape = FloatingActionButtonDefaults.shape,
    containerColor: Color = FloatingActionButtonDefaults.containerColor,
    contentColor: Color = contentColorFor(containerColor),
    elevation: FloatingActionButtonElevation = FloatingActionButtonDefaults.bottomAppBarFabElevation(),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    rotateIcon: Boolean = true,
    content: @Composable () -> Unit,
) {
    val rotation = if (rotateIcon) {
        state.transition?.animateFloat(
            transitionSpec = {
                if (targetState == SpeedDialState.EXPANDED) {
                    spring(stiffness = Spring.StiffnessLow)
                } else {
                    spring(stiffness = Spring.StiffnessMedium)
                }
            },
            label = "",
            targetValueByState = {
                if (it == SpeedDialState.EXPANDED) 45f else 0f
            }
        )
    } else null

    FloatingActionButton(
        modifier = modifier,
        shape = shape,
        containerColor = containerColor,
        contentColor = contentColor,
        elevation = elevation,
        interactionSource = interactionSource,
        onClick = {
            state.stateChange()
        }
    ) {
        Box(
            modifier = Modifier.rotate(rotation?.value ?: 0f),
        ) {
            content.invoke()
        }
    }
}
