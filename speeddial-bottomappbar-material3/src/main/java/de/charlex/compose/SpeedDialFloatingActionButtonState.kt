package de.charlex.compose

import androidx.compose.animation.core.Transition
import androidx.compose.animation.core.updateTransition
import androidx.compose.runtime.*

@Composable
fun rememberSpeedDialFloatingActionButtonState(): SpeedDialFloatingActionButtonState {
    val state = remember {
        SpeedDialFloatingActionButtonState()
    }

    state.transition = updateTransition(targetState = state.currentState, label = "")

    return state
}

class SpeedDialFloatingActionButtonState {
    var currentState by mutableStateOf(SpeedDialState.COLLAPSED)

    var transition: Transition<SpeedDialState>? = null

    val stateChange: () -> Unit = {
        currentState = if (transition?.currentState == SpeedDialState.EXPANDED ||
            transition?.isRunning == true && transition?.targetState == SpeedDialState.EXPANDED
        ) {
            SpeedDialState.COLLAPSED
        } else {
            SpeedDialState.EXPANDED
        }
    }
}
