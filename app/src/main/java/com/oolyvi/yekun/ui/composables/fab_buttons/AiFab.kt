package com.oolyvi.yekun.ui.composables.fab_buttons

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.oolyvi.yekun.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun AiFab(
    listState: LazyListState,
    scope: CoroutineScope
) {

    FloatingActionButton(
        onClick = {
            scope.launch {
                listState.animateScrollToItem(0)
            }
        },
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.onPrimary,
        modifier = Modifier
            .width(60.dp)
            .height(100.dp)
            .navigationBarsPadding()
            .statusBarsPadding()
            .imePadding()
            .padding(bottom = 60.dp)
    ) {
        Icon(
            painter = painterResource(id = R.drawable.keyboard_arrow_down),
            tint = MaterialTheme.colorScheme.onPrimary,
            contentDescription = stringResource(R.string.cd_down_icon)
        )
    }

}