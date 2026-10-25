package com.oolyvi.yekun.ui.composables.top_bars

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.oolyvi.yekun.R
import com.oolyvi.yekun.ui.theme.ColorRed
import com.oolyvi.yekun.ui.theme.ColorSave
import com.oolyvi.yekun.ui.theme.ColorWhite
import com.oolyvi.yekun.utils.singleClick

@Composable
fun EditMediaTopBar(
    showDeleteDialog: (Boolean) -> Unit,
    bottomPadding: Dp,
    onApply: () -> Unit,
    isDeleteButtonShown: Boolean
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 15.dp, end = 15.dp, bottom = 20.dp + bottomPadding),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Bottom
    ) {
        Button(
            onClick = singleClick {
                onApply()
            },
            colors = ButtonColors(
                containerColor = ColorSave,
                contentColor = ColorWhite,
                disabledContainerColor = ColorSave,
                disabledContentColor = ColorWhite
            ),
            modifier = Modifier
                .height(50.dp)
                .weight(0.8f)
        ) {
            Text(
                text = stringResource(R.string.save),
                fontSize = 17.sp,
                fontWeight = FontWeight.ExtraBold
            )
        }

        // delete button shown, when it is not new entry
        if (isDeleteButtonShown) {
            IconButton(
                onClick = singleClick {
                    showDeleteDialog(true)
                },
                colors = IconButtonColors(
                    containerColor = ColorRed,
                    contentColor = ColorWhite,
                    disabledContainerColor = ColorRed,
                    disabledContentColor = ColorWhite
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .padding(horizontal = 10.dp)
                    .weight(0.2f)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.delete),
                    contentDescription = stringResource(id = R.string.cd_bin_icon)
                )
            }
        }
    }

    Spacer(modifier = Modifier.height(30.dp))

}