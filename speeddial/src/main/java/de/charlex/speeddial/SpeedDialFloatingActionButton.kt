package de.charlex.speeddial

import androidx.annotation.DrawableRes
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEach

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun SpeedDialFloatingActionButton(
    modifier: Modifier = Modifier,
    initialExpanded: Boolean = false,
    onClick: (MultiSelectionData?) -> Unit,
    animationDuration: Int = 300,
    multiSelectionData: List<MultiSelectionData>,
    showMultiSelectionLabel: Boolean = false,
    multiSelectionBackgroundColor: Color = MaterialTheme.colors.secondaryVariant,
    multiSelectionContentColor: Color = contentColorFor(multiSelectionBackgroundColor),
) {
    var multiSelectionExpanded by remember { mutableStateOf(initialExpanded) }

    val transition = updateTransition(label = "multiSelectionExpanded", targetState = multiSelectionExpanded)

    val multiSelectionAlpha by transition.animateFloat(
        label = "multiSelectionAlpha",
        transitionSpec = {
            tween(durationMillis = animationDuration)
        }
    ) {
        if (it) 1f else 0f
    }
    val fabIconRotation by transition.animateFloat(
        label = "fabIconRotation",
        transitionSpec = {
            tween(durationMillis = animationDuration)
        }
    ) {
        if (it) 45f else 0f
    }
    val fabBackgroundColor by transition.animateColor(
        label = "fabBackgroundColor",
        transitionSpec = {
            tween(durationMillis = animationDuration)
        }
    ) {
        if (it) Color.LightGray else MaterialTheme.colors.secondary
    }

    Layout(
        modifier = modifier,
        content = {
            FloatingActionButton(
                onClick = {
                    multiSelectionExpanded = !multiSelectionExpanded

                    if (multiSelectionData.isEmpty()) {
                        onClick(null)
                    }
                },
                backgroundColor = fabBackgroundColor,
            ) {
                Icon(
                    modifier = Modifier.rotate(fabIconRotation),
                    imageVector = Icons.Default.Add,
                    contentDescription = null,
                )
            }

            multiSelectionData.fastForEach {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    val interactionSource = remember { MutableInteractionSource() }
                    if (showMultiSelectionLabel) {
                        Surface(
                            modifier = Modifier.clickable(
                                interactionSource = interactionSource,
                                indication = rememberRipple()
                            ) {
                                onClick(it)
                            },
                            shape = MaterialTheme.shapes.small,
                            color = multiSelectionBackgroundColor,
                            contentColor = multiSelectionContentColor
                        ) {
                            Text(
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                                text = it.label,
                                color = multiSelectionContentColor,
                                fontWeight = FontWeight.Medium
                            )
                        }

                        Spacer(modifier = Modifier.requiredWidth(10.dp))
                    }

                    Box(
                        modifier = Modifier
                            .requiredSize(56.dp)
                            .padding(8.dp)
                    ) {
                        FloatingActionButton(
                            interactionSource = interactionSource,
                            onClick = {
                                onClick(it)
                            },
                            backgroundColor = multiSelectionBackgroundColor,
                            contentColor = multiSelectionContentColor
                        ) {
                            Icon(
                                painter = painterResource(id = it.iconResource),
                                contentDescription = null
                            )
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

        layout(fabPlacable.width, fabPlacable.height) {
            fabPlacable.placeRelative(0, 0)

            subFabPlacables.forEachIndexed { index, placeable ->
                placeable.placeRelativeWithLayer(
                    x = fabPlacable.width - placeable.width,
                    y = - index * placeable.height - (fabPlacable.height * 1.25f).toInt()
                ) {
                    alpha = multiSelectionAlpha
                }
            }
        }
    }
}

data class MultiSelectionData(
    val name: String,
    val label: String = name,
    @DrawableRes
    val iconResource: Int
)
