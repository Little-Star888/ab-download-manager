package com.abdownloadmanager.shared.ui.widget

import ir.amirab.util.compose.IconSource
import com.abdownloadmanager.shared.utils.ui.widget.MyIcon
import com.abdownloadmanager.shared.utils.ui.myColors
import ir.amirab.util.ifThen
import com.abdownloadmanager.shared.utils.div
import androidx.compose.animation.core.*
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp

@Composable
fun alphaFlicker(): Float {
    val t = rememberInfiniteTransition()
    return t.animateFloat(1f, 0f, infiniteRepeatable(tween(1000), repeatMode = RepeatMode.Reverse)).value
}

@Composable
fun IconActionButton(
    icon: IconSource,
    contentDescription: String,
    modifier: Modifier = Modifier,
    indicateActive: Boolean = false,
    requiresAttention: Boolean = false,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    onClick: () -> Unit,
) {
    val isFocused by interactionSource.collectIsFocusedAsState()
    val isActiveOrFocused = indicateActive || isFocused
    val shape = RoundedCornerShape(6.dp)
    Box(
        modifier
            .ifThen(!enabled) {
                alpha(0.5f)
            }
            .border(
                1.dp,
                myColors.onBackground / 10,
                shape
            )
            .ifThen(isActiveOrFocused || requiresAttention) {
                border(
                    1.dp,
                    myColors.focusedBorderColor / if (isActiveOrFocused) 1f else alphaFlicker(),
                    shape
                )
            }
            .clip(shape)
            .background(myColors.surface)
            .clickable(
                enabled = enabled,
                indication = LocalIndication.current,
                interactionSource = interactionSource,
                role = Role.Button,
                onClick = onClick,
            )
            .padding(6.dp)
    ) {
        MyIcon(
            icon,
            contentDescription,
            Modifier
                .size(16.dp)
        )
    }
}
