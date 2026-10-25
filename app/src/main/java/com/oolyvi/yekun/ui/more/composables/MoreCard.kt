package com.oolyvi.yekun.ui.more.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.oolyvi.yekun.ui.theme.ColorTransparent
import com.oolyvi.yekun.utils.debouncedClickable

@Composable
fun MoreCard(
    title: String?,
    subtitle: String?,
    modifier: Modifier = Modifier,
    contentDescription: String?,
    icon: Int?,
    iconTint: Color,
    titleColor: Color,
    subTitleColor: Color,
    titleFontWeight: FontWeight,
    subTitleFontWeight: FontWeight,
    onClick: () -> Unit
) {

    Card(
        colors = CardColors(
            containerColor = ColorTransparent,
            contentColor = MaterialTheme.colorScheme.onSurface,
            disabledContainerColor = ColorTransparent,
            disabledContentColor = MaterialTheme.colorScheme.onSurface
        ),
        modifier = modifier
            .clip(RoundedCornerShape(20.dp))
            .debouncedClickable(500) {
                onClick()
            }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            horizontalArrangement = Arrangement.spacedBy(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // card icon
            icon?.let { painterResource(id = it) }?.let {
                Icon(
                    painter = it,
                    contentDescription = contentDescription,
                    tint = iconTint,
                    modifier = Modifier.size(35.dp)
                )
            }

            // main title and subtitle
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start
            ) {
                if (title != null) {
                    Text(
                        text = title,
                        color = titleColor,
                        fontSize = 17.sp,
                        fontWeight = titleFontWeight
                    )
                }

                if (subtitle != null) {
                    Text(
                        text = subtitle,
                        color = subTitleColor,
                        fontSize = 15.sp,
                        fontWeight = subTitleFontWeight
                    )
                }
            }
        }
    }

}