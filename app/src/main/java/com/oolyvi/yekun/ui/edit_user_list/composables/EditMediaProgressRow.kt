package com.oolyvi.yekun.ui.edit_user_list.composables

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.oolyvi.yekun.utils.toStringOrEmpty

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun EditMediaProgressRow(
    label: String,
    progress: Int?,
    modifier: Modifier,
    totalProgress: Int?,
    onValueChange: (String) -> Unit,
    onMinusClick: () -> Unit,
    onPlusClick: () -> Unit,
) {

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // decreasing count by 1
        OutlinedButton(
            onClick = onMinusClick,
            shape = CircleShape,
            modifier = Modifier.width(70.dp)
        ) {
            Text(
                text = "-1",
                color = MaterialTheme.colorScheme.onSurface
            )
        }

        // text field
        OutlinedTextField(
            value = progress.toStringOrEmpty(),
            onValueChange = onValueChange,
            modifier = Modifier
                .weight(2f)
                .padding(horizontal = 16.dp),
            label = { Text(text = label) },
            suffix = {
                totalProgress?.let { Text(text = "/$it") }
            },
            shape = CircleShape,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        // increasing count by 1
        OutlinedButton(
            onClick = onPlusClick,
            shape = CircleShape,
            modifier = Modifier.width(70.dp)
        ) {
            Text(
                text = "+1",
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }

}