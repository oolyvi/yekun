package com.oolyvi.yekun.ui.settings.composables

import androidx.compose.foundation.clickable
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
import androidx.compose.material3.Switch
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

@Composable
fun SettingsSwitch(
    title: String,
    modifier: Modifier = Modifier,
    subtitle: String? = null,
    contentDescription: String?,
    value: Boolean,
    icon: Int,
    iconTint: Color,
    titleColor: Color,
    onClick: (Boolean) -> Unit
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
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .clickable {
                    onClick(!value)
                },
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier
                    .weight(8f)
                    .padding(10.dp),
                horizontalArrangement = Arrangement.spacedBy(20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(icon),
                    contentDescription = contentDescription,
                    modifier = Modifier.size(35.dp),
                    tint = iconTint
                )

                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = title,
                        color = titleColor,
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold
                    )

                    // if subtitle is not null, show this
                    if (subtitle != null) {
                        Text(
                            text = subtitle,
                            color = titleColor,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Light
                        )
                    }
                }
            }

            Switch(
                checked = value,
                onCheckedChange = {
                    onClick(it)
                },
                modifier = Modifier.weight(2f)
            )
        }
    }

}