# SpeedDialFloatingActionButton
## Current Compose Version: 1.0.0-beta09
Compose MultiSelection / SpeedDial - FloatingActionButton

Usable docked in BottomAppBar

<a href="https://github.com/ch4rl3x/SpeedDialFloatingActionButton/actions?query=workflow%3ALint"><img src="https://github.com/ch4rl3x/SpeedDialFloatingActionButton/workflows/Lint/badge.svg" alt="Lint"></a>
<a href="https://github.com/ch4rl3x/SpeedDialFloatingActionButton/actions?query=workflow%3AKtlint"><img src="https://github.com/ch4rl3x/SpeedDialFloatingActionButton/workflows/Ktlint/badge.svg" alt="Ktlint"></a>

<a href="https://www.codefactor.io/repository/github/ch4rl3x/SpeedDialFloatingActionButton"><img src="https://www.codefactor.io/repository/github/ch4rl3x/SpeedDialFloatingActionButton/badge" alt="CodeFactor" /></a>
<a href="https://repo1.maven.org/maven2/de/charlex/compose/speeddial/"><img src="https://img.shields.io/maven-central/v/de.charlex.compose/speeddial" alt="Maven Central" /></a>



# Add to your project

Add actual SpeedDialFloatingActionButton library:

```groovy
dependencies {
    implementation 'de.charlex.compose:speeddial:1.0.0-beta03'
}
```

# How does it work?

Use it instead of a normal FloatingActionButton

```kotlin
SpeedDialFloatingActionButton(
    modifier = Modifier,
    initialExpanded = false,
    onClick = { speedDialData: SpeedDialData? ->
        //TODO
    },
    animationDuration = 300,
    animationDelayPerSelection = 100,
    showLabels = true,
    fabBackgroundColor: Color = MaterialTheme.colors.secondary,
    fabContentColor: Color = contentColorFor(fabBackgroundColor),
    speedDialBackgroundColor: Color = MaterialTheme.colors.secondaryVariant,
    speedDialContentColor: Color = contentColorFor(speedDialBackgroundColor),
    speedDialData = listOf(
        SpeedDialData(
            name = "test1",
            label = "Test 1"
            painter = painterResource(id = R.drawable.ic_add_white_24dp)
        ),
        SpeedDialData(
            name = "Test 2",
            painter = painterResource(id = R.drawable.ic_add_white_24dp)
        ),
        SpeedDialData(
            name = "Test 3",
            painterResource = R.drawable.ic_add_white_24dp
        ),
        SpeedDialData(
            name = "Test 4",
            painterResource = R.drawable.ic_add_white_24dp
        )
    )
)
```


# Examples

### Standalone
![SpeedDialFloatingActionButton demonstration](https://github.com/ch4rl3x/SpeedDialFloatingActionButton/blob/main/art/expand.gif)

### Docked in BottomAppBar with labels
![SpeedDialFloatingActionButton demonstration](https://github.com/ch4rl3x/SpeedDialFloatingActionButton/blob/main/art/expand_labeled_docked.gif)




That's it!

License
--------

    Copyright 2020 Alexander Karkossa

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
