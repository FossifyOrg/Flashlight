package com.simplemobiletools.flashlight.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.simplemobiletools.commons.compose.extensions.AdjustNavigationBarColors
import com.simplemobiletools.commons.compose.extensions.MyDevices
import com.simplemobiletools.commons.compose.extensions.rememberMutableInteractionSource
import com.simplemobiletools.commons.compose.lists.*
import com.simplemobiletools.commons.compose.menus.ActionItem
import com.simplemobiletools.commons.compose.menus.ActionMenu
import com.simplemobiletools.commons.compose.menus.OverflowMode
import com.simplemobiletools.commons.compose.theme.AppThemeSurface
import com.simplemobiletools.commons.compose.theme.SimpleTheme
import com.simplemobiletools.flashlight.R
import com.simplemobiletools.flashlight.helpers.AppDimensions
import com.simplemobiletools.flashlight.views.AnimatedSleepTimer
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

@Composable
internal fun MainScreen(
    flashlightButton: @Composable () -> Unit,
    brightDisplayButton: @Composable () -> Unit,
    sosButton: @Composable () -> Unit,
    stroboscopeButton: @Composable () -> Unit,
    slidersSection: @Composable () -> Unit,
    sleepTimer: @Composable () -> Unit,
    showMoreApps: Boolean,
    openSettings: () -> Unit,
    openAbout: () -> Unit,
    openSleepTimer: () -> Unit,
    moreAppsFromUs: () -> Unit,
) {
    AdjustNavigationBarColors()
    SimpleScaffold(
        customTopBar = { scrolledColor: Color, _: MutableInteractionSource, scrollBehavior: TopAppBarScrollBehavior, statusBarColor: Int, colorTransitionFraction: Float, contrastColor: Color ->
            TopAppBar(
                title = {},
                actions = {
                    val actionMenus = remember { buildActionMenu(showMoreApps, openSettings, openAbout, openSleepTimer, moreAppsFromUs) }
                    var isMenuVisible by remember { mutableStateOf(false) }
                    ActionMenu(
                        items = actionMenus,
                        numIcons = 2,
                        isMenuVisible = isMenuVisible,
                        onMenuToggle = { isMenuVisible = it },
                        iconsColor = scrolledColor
                    )
                },
                scrollBehavior = scrollBehavior,
                colors = simpleTopAppBarColors(statusBarColor, colorTransitionFraction, contrastColor),
                modifier = Modifier.topAppBarPaddings(),
                windowInsets = topAppBarInsets()
            )
        }) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            flashlightButton()
            brightDisplayButton()
            sosButton()
            stroboscopeButton()
            slidersSection()
            Spacer(
                modifier = Modifier.size(SimpleTheme.dimens.padding.extraLarge)
            )
        }

        Box(
            modifier = Modifier.align(Alignment.BottomEnd)
                .navigationBarsPadding(),
        ) {
            sleepTimer()
        }
    }
}

@Composable
internal fun FlashlightButton(
    flashlightActive: Boolean,
    onFlashlightPress: () -> Unit,
) {
    Icon(
        modifier = Modifier
            .size(AppDimensions.mainButtonSize)
            .padding(vertical = SimpleTheme.dimens.padding.large)
            .clickable(
                indication = null,
                interactionSource = rememberMutableInteractionSource(),
                onClick = onFlashlightPress
            ),
        painter = painterResource(id = R.drawable.ic_flashlight_vector),
        contentDescription = stringResource(id = com.simplemobiletools.commons.R.string.flashlight_short),
        tint = if (flashlightActive) SimpleTheme.colorScheme.primary else SimpleTheme.colorScheme.onSurface
    )
}

@Composable
internal fun BrightDisplayButton(
    onBrightDisplayPress: () -> Unit,
) {
    SmallButton(
        painter = painterResource(id = R.drawable.ic_bright_display_vector),
        contentDescription = stringResource(id = R.string.bright_display),
        isActive = false,
        onPress = onBrightDisplayPress
    )
}

@Composable
internal fun SosButton(
    sosActive: Boolean,
    onSosButtonPress: () -> Unit,
) {
    Text(
        modifier = Modifier
            .padding(vertical = SimpleTheme.dimens.padding.large)
            .clickable(
                indication = null,
                interactionSource = rememberMutableInteractionSource(),
                onClick = onSosButtonPress
            ),
        text = stringResource(id = R.string.sos),
        fontSize = AppDimensions.sosTextSize,
        fontWeight = FontWeight.Bold,
        color = if (sosActive) SimpleTheme.colorScheme.primary else SimpleTheme.colorScheme.onSurface
    )
}

@Composable
internal fun StroboscopeButton(
    stroboscopeActive: Boolean,
    onStroboscopeButtonPress: () -> Unit,
) {
    SmallButton(
        painter = painterResource(id = R.drawable.ic_stroboscope_vector),
        contentDescription = stringResource(id = R.string.stroboscope),
        isActive = stroboscopeActive,
        onPress = onStroboscopeButtonPress
    )
}

@Composable
internal fun MainScreenSlidersSection(
    showBrightnessBar: Boolean,
    brightnessBarValue: Float,
    onBrightnessBarValueChange: (Float) -> Unit,
    showStroboscopeBar: Boolean,
    stroboscopeBarValue: Float,
    onStroboscopeBarValueChange: (Float) -> Unit,
) {
    val dimens = SimpleTheme.dimens
    val sliderModifier = remember {
        Modifier
            .padding(horizontal = dimens.padding.extraLarge)
            .padding(top = 24.dp, bottom = 40.dp)
            .size(width = AppDimensions.seekbarWidth, height = AppDimensions.seekbarHeight)
    }

    if (showBrightnessBar) {
        Slider(
            modifier = sliderModifier,
            value = brightnessBarValue,
            onValueChange = onBrightnessBarValueChange
        )
    }

    if (showStroboscopeBar) {
        Slider(
            modifier = sliderModifier,
            value = stroboscopeBarValue,
            onValueChange = onStroboscopeBarValueChange
        )
    }

    if (!showBrightnessBar && !showStroboscopeBar) {
        Spacer(
            modifier = sliderModifier,
        )
    }
}

@Composable
private fun SmallButton(
    painter: Painter,
    contentDescription: String,
    isActive: Boolean,
    onPress: () -> Unit
) {
    Icon(
        modifier = Modifier
            .size(AppDimensions.smallerButtonSize)
            .padding(vertical = SimpleTheme.dimens.padding.large)
            .clickable(
                indication = null,
                interactionSource = rememberMutableInteractionSource(),
                onClick = onPress
            ),
        painter = painter,
        contentDescription = contentDescription,
        tint = if (isActive) SimpleTheme.colorScheme.primary else SimpleTheme.colorScheme.onSurface
    )
}

private fun buildActionMenu(
    showMoreApps: Boolean,
    openSettings: () -> Unit,
    openAbout: () -> Unit,
    openSleepTimer: () -> Unit,
    moreAppsFromUs: () -> Unit,
): ImmutableList<ActionItem> {
    val settings =
        ActionItem(com.simplemobiletools.commons.R.string.settings, icon = Icons.Filled.Settings, doAction = openSettings, overflowMode = OverflowMode.NEVER_OVERFLOW)
    val about = ActionItem(com.simplemobiletools.commons.R.string.about, icon = Icons.Outlined.Info, doAction = openAbout, overflowMode = OverflowMode.NEVER_OVERFLOW)
    val sleepTimer = ActionItem(com.simplemobiletools.commons.R.string.sleep_timer, doAction = openSleepTimer, overflowMode = OverflowMode.ALWAYS_OVERFLOW)
    val list = mutableListOf(settings, about, sleepTimer)
    if (showMoreApps) {
        list += ActionItem(com.simplemobiletools.commons.R.string.more_apps_from_us, doAction = moreAppsFromUs, overflowMode = OverflowMode.ALWAYS_OVERFLOW)
    }
    return list.toImmutableList()
}

@Composable
@MyDevices
internal fun MainScreenPreview() {
    AppThemeSurface {
        MainScreen(
            flashlightButton = {
                FlashlightButton(
                    onFlashlightPress = {},
                    flashlightActive = true,
                )
            },
            brightDisplayButton = {
                BrightDisplayButton(
                    onBrightDisplayPress = {}
                )
            },
            sosButton = {
                SosButton(
                    sosActive = false,
                    onSosButtonPress = {},
                )
            },
            stroboscopeButton = {
                StroboscopeButton(
                    stroboscopeActive = false,
                    onStroboscopeButtonPress = {},
                )
            },
            slidersSection = {
                MainScreenSlidersSection(
                    showBrightnessBar = false,
                    brightnessBarValue = 0f,
                    onBrightnessBarValueChange = {},
                    showStroboscopeBar = false,
                    stroboscopeBarValue = 0f,
                    onStroboscopeBarValueChange = {},
                )
            },
            sleepTimer = {
                AnimatedSleepTimer(
                    timerText = "00:00",
                    timerVisible = true,
                    onTimerClosePress = {},
                )
            },
            showMoreApps = true,
            openSettings = {},
            openAbout = {},
            moreAppsFromUs = {},
            openSleepTimer = {}
        )
    }
}
