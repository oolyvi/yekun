package com.oolyvi.yekun.ui.ai.composables

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.oolyvi.yekun.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatSheet(
    context: Context,
    clipboardManager: ClipboardManager,
    message: String,
    showBottomSheet: (Boolean) -> Unit,
    sheetState: SheetState,
    bottomPadding: Dp
) {

    ModalBottomSheet(
        onDismissRequest = {
            showBottomSheet(false)
        },
        shape = RoundedCornerShape(topEnd = 30.dp, topStart = 30.dp),
        sheetState = sheetState,
        windowInsets = WindowInsets(0, 0, 0, 0)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .padding(
                    bottom = 20.dp + bottomPadding,
                    top = 20.dp
                )
        ) {
            // copy title
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .clickable {
                        clipboardManager.setText(AnnotatedString((message)))
                        showBottomSheet(false)
                        Toast
                            .makeText(
                                context,
                                context.getString(R.string.copied), Toast.LENGTH_SHORT
                            )
                            .show()
                    }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.content_copy),
                    contentDescription = stringResource(R.string.cd_copy_icon),
                    modifier = Modifier.padding(
                        start = 25.dp,
                        end = 50.dp
                    )
                )

                Text(
                    text = stringResource(R.string.copy),
                    fontSize = 20.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }

            // share title
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .clickable {
                        val sharingText = message
                        val sendIntent: Intent = Intent().apply {
                            action = Intent.ACTION_SEND
                            putExtra(Intent.EXTRA_TEXT, sharingText)
                            type = "text/plain"
                        }
                        val shareIntent = Intent.createChooser(sendIntent, null)
                        context.startActivity(shareIntent)

                        showBottomSheet(false)
                    }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.share),
                    contentDescription = stringResource(R.string.cd_share_icon),
                    modifier = Modifier.padding(
                        start = 25.dp,
                        end = 50.dp
                    )
                )

                Text(
                    text = stringResource(R.string.share),
                    fontSize = 20.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }

}