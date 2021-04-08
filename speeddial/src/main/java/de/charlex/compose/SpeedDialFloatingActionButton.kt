package de.charlex.compose

import androidx.annotation.DrawableRes
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.FloatingActionButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.contentColorFor
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEachIndexed

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun SpeedDialFloatingActionButton(
    modifier: Modifier = Modifier,
    initialExpanded: Boolean = false,
    onClick: (SpeedDialData?) -> Unit,
    animationDuration: Int = 300,
    animationDelayPerSelection: Int = 100,
    speedDialData: List<SpeedDialData>,
    showLabels: Boolean = false,
    fabBackgroundColor: Color = MaterialTheme.colors.secondary,
    fabContentColor: Color = contentColorFor(fabBackgroundColor),
    speedDialBackgroundColor: Color = MaterialTheme.colors.secondaryVariant,
    speedDialContentColor: Color = contentColorFor(speedDialBackgroundColor),
) {
    var expanded by remember { mutableStateOf(initialExpanded) }

    val transition = updateTransition(label = "multiSelectionExpanded", targetState = expanded)

    val speedDialAlpha = mutableListOf<State<Float>>()
    val speedDialScale = mutableListOf<State<Float>>()

    speedDialData.fastForEachIndexed { index, _ ->

        speedDialAlpha.add(transition.animateFloat(
            label = "multiSelectionAlpha",
            transitionSpec = {
                tween(
                    delayMillis = index * animationDelayPerSelection,
                    durationMillis = animationDuration
                )
            }
        ) {
            if (it) 1f else 0f
        })

        speedDialScale.add(transition.animateFloat(
            label = "multiSelectionScale",
            transitionSpec = {
                tween(
                    delayMillis = index * animationDelayPerSelection,
                    durationMillis = animationDuration
                )
            }
        ) {
            if (it) 1f else 0f
        })
    }

    val fabIconRotation by transition.animateFloat(
        label = "fabIconRotation",
        transitionSpec = {
            tween(durationMillis = animationDuration)
        }
    ) {
        if (it) 45f else 0f
    }
    val fabBackgroundColorAnimated by transition.animateColor(
        label = "fabBackgroundColor",
        transitionSpec = {
            tween(durationMillis = animationDuration)
        }
    ) {
        if (it) Color.LightGray else fabBackgroundColor
    }

    val fabContentColorAnimated by transition.animateColor(
        label = "fabContentColor",
        transitionSpec = {
            tween(durationMillis = animationDuration)
        }
    ) {
        if (it) Color.Black else fabContentColor
    }

    Layout(
        modifier = modifier,
        content = {
            FloatingActionButton(
                onClick = {
                    expanded = !expanded

                    if (speedDialData.isEmpty()) {
                        onClick(null)
                    }
                },
                backgroundColor = fabBackgroundColorAnimated,
                contentColor = fabContentColorAnimated
            ) {
                Icon(
                    modifier = Modifier.rotate(fabIconRotation),
                    imageVector = Icons.Default.Add,
                    contentDescription = null,
                )
            }

            speedDialData.fastForEachIndexed { index, data ->

                val correctIndex =
                    if (expanded) index else speedDialData.size - 1 - index

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    val interactionSource = remember { MutableInteractionSource() }
                    if (showLabels) {

                        Surface(
                            modifier = Modifier.clickable(
                                    interactionSource = interactionSource,
                                    indication = rememberRipple()
                                ) {
                                    onClick(data)
                                }
                                .alpha(speedDialAlpha[correctIndex].value)
                                .scale(speedDialScale[correctIndex].value),
                            shape = MaterialTheme.shapes.small,
                            color = speedDialBackgroundColor,
                            contentColor = speedDialContentColor
                        ) {
                            Text(
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                                text = data.label,
                                color = speedDialContentColor,
                                maxLines = 1,
                                fontWeight = FontWeight.Medium
                            )
                        }
                        Spacer(modifier = Modifier.requiredWidth(10.dp))
                    }

                    Box(
                        modifier = Modifier
                            .requiredSize(56.dp)
                            .padding(8.dp)
                            .alpha(speedDialAlpha[correctIndex].value)
                            .scale(speedDialScale[correctIndex].value)
                    ) {
                        FloatingActionButton(
                            elevation = FloatingActionButtonDefaults.elevation(0.dp, 0.dp),
                            interactionSource = interactionSource,
                            onClick = {
                                onClick(data)
                            },
                            backgroundColor = speedDialBackgroundColor,
                            contentColor = speedDialContentColor
                        ) {
                            if (data.painter != null) {
                                Icon(
                                    painter = data.painter,
                                    tint = speedDialContentColor,
                                    contentDescription = null
                                )
                            } else if (data.painterResource != null) {
                                Icon(
                                    painter = painterResource(id = data.painterResource),
                                    tint = speedDialContentColor,
                                    contentDescription = null
                                )
                            }
                        }
                    }
                }
            }
        }
    ) { measurables, constraints ->

        val fab = measurables[0]
        val subFabs = measurables.subList(1, measurables.count())

        val fabPlacable = fab.measure(constraints)

        val subFabPlacables = subFabs.map {
            it.measure(constraints)
        }

        layout(
            width = fabPlacable.width,
            height = fabPlacable.height
        ) {
            fabPlacable.placeRelative(0, 0)

            subFabPlacables.forEachIndexed { index, placeable ->

                if (transition.isRunning or transition.currentState) {
                    placeable.placeRelative(
                        x = fabPlacable.width - placeable.width,
                        y = -index * placeable.height - (fabPlacable.height * 1.25f).toInt()
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun SpeedDialPreview() {
    MaterialTheme {
        Box(
            modifier = Modifier
                .height(500.dp)
                .width(200.dp),
            contentAlignment = Alignment.BottomEnd
        ) {
            SpeedDialFloatingActionButton(
                modifier = Modifier.padding(20.dp),
                showLabels = true,
                onClick = {
                },
                speedDialData = listOf(
                    SpeedDialData(
                        name = "Test 1",
                        painter = painterResource(id = R.drawable.ic_add_white_24dp)
                    ),
                    SpeedDialData(
                        name = "Test 2",
                        painter = painterResource(id = R.drawable.ic_add_white_24dp)
                    ),
                    SpeedDialData(
                        name = "Test 3",
                        painter = painterResource(id = R.drawable.ic_add_white_24dp)
                    ),
                    SpeedDialData(
                        name = "Test 4",
                        painterResource = R.drawable.ic_add_white_24dp
                    )
                )
            )
        }
    }
}

data class SpeedDialData(
    val name: String,
    val label: String = name,
    val painter: Painter? = null,
    @DrawableRes
    val painterResource: Int? = null
)
