package com.oolyvi.yekun.ui.anime_list.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyListState
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.oolyvi.yekun.R
import com.oolyvi.yekun.ui.anime_list.AnimeListUiState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnimeListSheet(
    scope: CoroutineScope,
    listState: LazyListState,
    uiState: AnimeListUiState,
    getFilteredUserAnimeList: () -> Unit,
    onChangeStatus: (String?) -> Unit,
    showBottomSheet: (Boolean) -> Unit,
    sheetState: SheetState,
    bottomPadding: Dp
) {

    /**
     * status items list
     */
    val statusItemsList: List<String> = listOf(
        "watching",
        "completed",
        "on_hold",
        "dropped",
        "plan_to_watch"
    )
    val statusTitleMap = mapOf(
        "watching" to stringResource(id = R.string.watching),
        "completed" to stringResource(id = R.string.completed),
        "on_hold" to stringResource(id = R.string.on_hold),
        "dropped" to stringResource(id = R.string.dropped),
        "plan_to_watch" to stringResource(id = R.string.plan_to_watch)
    )

    /**
     * status icon mapping
     */
    val statusIconMap = mapOf(
        "watching" to R.drawable.play_arrow,
        "completed" to R.drawable.done,
        "on_hold" to R.drawable.pause,
        "dropped" to R.drawable.delete,
        "plan_to_watch" to R.drawable.watch_later
    )



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
                    bottom = 8.dp + bottomPadding,
                    top = 20.dp
                )
        ) {
            statusItemsList.forEach {

                // this will avoid nonsense screen loading while clicking status items multiple times
                val isSelected = it == uiState.selectedStatus

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(45.dp)
                        .clickable {
                            if (!isSelected) {
                                onChangeStatus(it)
                                getFilteredUserAnimeList()
                                scope.launch {
                                    listState.scrollToItem(0)
                                }
                            }
                        }
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        modifier = Modifier
                            .padding(start = 15.dp)
                    ) {
                        val statusIcon = statusIconMap[it]
                        val seasonTitle = statusTitleMap[it]

                        if (statusIcon != null) {
                            Icon(
                                painter = painterResource(id = statusIcon),
                                tint = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface,
                                contentDescription = stringResource(R.string.cd_selected_status_icon),
                                modifier = Modifier.size(25.dp)
                            )
                        }

                        if (seasonTitle != null) {
                            Text(
                                text = seasonTitle,
                                fontSize = 20.sp,
                                color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                            )
                        }
                    }
                }
            }
        }
    }

}