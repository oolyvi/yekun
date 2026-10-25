package com.oolyvi.yekun.ui.edit_user_list

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SelectableChipColors
import androidx.compose.material3.SheetState
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.oolyvi.yekun.R
import com.oolyvi.yekun.data.model.manga.MangaDetail
import com.oolyvi.yekun.ui.composables.top_bars.EditMediaTopBar
import com.oolyvi.yekun.ui.edit_user_list.composables.EditMediaDatePicker
import com.oolyvi.yekun.ui.edit_user_list.composables.EditMediaProgressRow
import com.oolyvi.yekun.ui.manga_detail.MangaDetailViewModel
import com.oolyvi.yekun.ui.theme.ColorTitleNo
import com.oolyvi.yekun.ui.theme.ColorTitleYes
import com.oolyvi.yekun.utils.singleClick
import com.oolyvi.yekun.utils.getLocalDateFromMillis
import com.oolyvi.yekun.utils.toMilliseconds
import java.util.Calendar
import kotlin.math.roundToInt

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun EditMangaListSheet(
    mangaDetail: MangaDetail,
    myListStatus: MangaDetail.MyListStatus?,
    onApply: () -> Unit,
    onDelete: () -> Unit,
    showDeleteDialog: (Boolean) -> Unit,
    showBottomSheet: (Boolean) -> Unit,
    sheetState: SheetState,
    bottomPadding: Dp
) {

    val viewModel: MangaDetailViewModel = hiltViewModel()
    val uiState by viewModel.uiState.collectAsState()

    val startDateMillis =
        myListStatus?.startDate?.toMilliseconds() ?: Calendar.getInstance().timeInMillis
    val endDateMillis = myListStatus?.finishDate?.toMilliseconds()
        ?: Calendar.getInstance().timeInMillis

    val pickerStateStartDate = rememberDatePickerState(
        initialDisplayedMonthMillis = startDateMillis,
        initialSelectedDateMillis = startDateMillis
    )

    val pickerStateEndDate = rememberDatePickerState(
        initialDisplayedMonthMillis = endDateMillis,
        initialSelectedDateMillis = endDateMillis
    )

    LaunchedEffect(mangaDetail) {
        viewModel.setMediaInfo(mangaDetail)
    }

    LaunchedEffect(myListStatus) {
        if (myListStatus != null)
            viewModel.setEditVariables(myListStatus)
    }

    /**
     * status icon mapping
     */
    val formattedStatusItems = mapOf(
        "completed" to stringResource(id = R.string.completed),
        "reading" to stringResource(id = R.string.reading),
        "on_hold" to stringResource(id = R.string.on_hold),
        "dropped" to stringResource(id = R.string.dropped),
        "plan_to_read" to stringResource(id = R.string.plan_to_read)
    )
    val statusIconMap = mapOf(
        "reading" to R.drawable.book,
        "completed" to R.drawable.done,
        "on_hold" to R.drawable.pause,
        "dropped" to R.drawable.delete,
        "plan_to_read" to R.drawable.watch_later
    )

    /**
     * status items list
     */
    val statusItemsList: List<String> = listOf(
        "reading",
        "completed",
        "plan_to_read",
        "on_hold",
        "dropped"
    )

    /**
     * priority mapping
     */
    val priorityTitle = when (uiState.priority.toFloat()) {
        0f -> stringResource(id = R.string.low)
        1f -> stringResource(id = R.string.medium)
        2f -> stringResource(id = R.string.high)
        else -> {
            stringResource(id = R.string.low)
        }
    }

    /**
     * reread mapping
     */
    val rereadValueTitle = when (uiState.rereadValue.toFloat()) {
        0f -> "-"
        1f -> stringResource(id = R.string.very_low)
        2f -> stringResource(id = R.string.low)
        3f -> stringResource(id = R.string.medium)
        4f -> stringResource(id = R.string.high)
        5f -> stringResource(id = R.string.very_high)
        else -> {
            "-"
        }
    }



    ModalBottomSheet(
        onDismissRequest = {
            showBottomSheet(false)
        },
        shape = RoundedCornerShape(topEnd = 30.dp, topStart = 30.dp),
        sheetState = sheetState,
        windowInsets = WindowInsets(0, 0, 0, 0),
        modifier = Modifier.fillMaxHeight(0.9f)
    ) {
        Scaffold(
            topBar = {
                EditMediaTopBar(
                    showDeleteDialog = showDeleteDialog,
                    bottomPadding = bottomPadding,
                    onApply = onApply,
                    isDeleteButtonShown = !uiState.isNewEntry
                )
            }
        ) {
            Column(
                modifier = Modifier
                    .padding(
                        bottom = 10.dp + bottomPadding,
                        top = 60.dp
                    )
                    .verticalScroll(rememberScrollState())
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Spacer(modifier = Modifier.height(20.dp))

                /**
                 * status
                 */
                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth(),
                    contentPadding = PaddingValues(horizontal = 15.dp)
                ) {
                    items(statusItemsList) {
                        FilterChip(
                            modifier = Modifier.padding(horizontal = 6.dp),
                            selected = (it == uiState.status),
                            onClick = {
                                viewModel.onChangeStatus(it)
                            },
                            label = {
                                formattedStatusItems[it]?.let {
                                    Text(
                                        text = it,
                                        fontSize = 14.sp
                                    )
                                }
                            },
                            leadingIcon = {
                                statusIconMap[it]?.let { it1 ->
                                    Icon(
                                        painter = painterResource(id = it1),
                                        contentDescription = stringResource(id = R.string.cd_status_icon)
                                    )
                                }
                            },
                            colors = SelectableChipColors(
                                labelColor = MaterialTheme.colorScheme.onSurface,
                                selectedLabelColor = MaterialTheme.colorScheme.surface,
                                disabledLabelColor = MaterialTheme.colorScheme.onSurface,

                                leadingIconColor = MaterialTheme.colorScheme.onSurface,
                                selectedLeadingIconColor = MaterialTheme.colorScheme.surface,
                                disabledLeadingIconColor = MaterialTheme.colorScheme.onSurface,

                                trailingIconColor = MaterialTheme.colorScheme.onSurface,
                                selectedTrailingIconColor = MaterialTheme.colorScheme.surface,
                                disabledTrailingIconColor = MaterialTheme.colorScheme.onSurface,

                                containerColor = MaterialTheme.colorScheme.surface,
                                selectedContainerColor = MaterialTheme.colorScheme.primary,
                                disabledContainerColor = MaterialTheme.colorScheme.surface,
                                disabledSelectedContainerColor = MaterialTheme.colorScheme.onSurface
                            )
                        )
                    }
                }

                Spacer(modifier = Modifier.height(40.dp))


                /**
                 * chapters
                 */
                EditMediaProgressRow(
                    label = stringResource(R.string.chapters),
                    progress = uiState.numChaptersRead,
                    modifier = Modifier.padding(horizontal = 15.dp),
                    totalProgress = mangaDetail.numChapters,
                    onValueChange = {
                        viewModel.onChangeChaptersCount(it.toIntOrNull())
                    },
                    onMinusClick = {
                        viewModel.onChangeChaptersCount(uiState.numChaptersRead?.minus(1))
                    },
                    onPlusClick = {
                        viewModel.onChangeChaptersCount(uiState.numChaptersRead?.plus(1))
                    }
                )

                Spacer(modifier = Modifier.height(40.dp))


                /**
                 * volumes
                 */
                EditMediaProgressRow(
                    label = stringResource(R.string.volumes),
                    progress = uiState.numVolumesRead,
                    modifier = Modifier.padding(horizontal = 15.dp),
                    totalProgress = mangaDetail.numVolumes,
                    onValueChange = {
                        viewModel.onChangeVolumesCount(it.toIntOrNull())
                    },
                    onMinusClick = {
                        viewModel.onChangeVolumesCount(uiState.numVolumesRead?.minus(1))
                    },
                    onPlusClick = {
                        viewModel.onChangeVolumesCount(uiState.numVolumesRead?.plus(1))
                    }
                )

                Spacer(modifier = Modifier.height(40.dp))


                /**
                 * score
                 */
                Column(
                    modifier = Modifier.padding(horizontal = 15.dp)
                ) {
                    Text(
                        text = stringResource(R.string.score, uiState.score)
                    )
                    Slider(
                        value = uiState.score.toFloat(),
                        onValueChange = {
                            viewModel.onChangeScore(it.roundToInt())
                        },
                        steps = 9,
                        valueRange = 0f..10f
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))


                /**
                 * priority
                 */
                Column(
                    modifier = Modifier.padding(horizontal = 15.dp)
                ) {
                    Text(
                        text = stringResource(R.string.priority, priorityTitle)
                    )
                    Slider(
                        value = uiState.priority.toFloat(),
                        onValueChange = {
                            viewModel.onChangePriority(it.roundToInt())
                        },
                        steps = 1,
                        valueRange = 0f..2f
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))


                /**
                 * start date
                 */
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface,
                    ),
                    modifier = Modifier
                        .padding(horizontal = 15.dp)
                        .clip(RoundedCornerShape(15.dp))
                        .border(
                            1.dp,
                            MaterialTheme.colorScheme.onSurface,
                            RoundedCornerShape(15.dp)
                        )
                        .clickable {
                            viewModel.openStartDatePicker()
                        }
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(10.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.date_range),
                            contentDescription = stringResource(id = R.string.cd_date_range_icon)
                        )

                        Spacer(modifier = Modifier.width(5.dp))

                        if (!uiState.startDateTitle.isNullOrEmpty()) {
                            Text(
                                text = uiState.startDateTitle,
                                fontWeight = FontWeight.Normal
                            )
                        } else {
                            Text(
                                text = "-_-",
                                fontWeight = FontWeight.Normal
                            )
                        }
                    }
                }

                // show data picker when user clicks box
                if (uiState.openStartDatePicker) {
                    EditMediaDatePicker(
                        datePickerState = pickerStateStartDate,
                        onDateSelected = {
                            viewModel.onChangeStartDate(getLocalDateFromMillis(it))
                        },
                        onDismiss = {
                            viewModel.closeDatePickers()
                        }
                    )
                }

                Spacer(modifier = Modifier.height(5.dp))

                Icon(
                    painter = painterResource(id = R.drawable.keyboard_arrow_down),
                    tint = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.size(30.dp),
                    contentDescription = stringResource(id = R.string.cd_down_icon)
                )

                Spacer(modifier = Modifier.height(5.dp))


                /**
                 * end date
                 */
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface,
                    ),
                    modifier = Modifier
                        .padding(horizontal = 15.dp)
                        .clip(RoundedCornerShape(15.dp))
                        .border(
                            1.dp,
                            MaterialTheme.colorScheme.onSurface,
                            RoundedCornerShape(15.dp)
                        )
                        .clickable {
                            viewModel.openEndDatePicker()
                        }
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(10.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.date_range),
                            contentDescription = stringResource(id = R.string.cd_date_range_icon)
                        )

                        Spacer(modifier = Modifier.width(5.dp))

                        if (!uiState.endDateTitle.isNullOrEmpty()) {
                            Text(
                                text = uiState.endDateTitle,
                                fontWeight = FontWeight.Normal
                            )
                        } else {
                            Text(
                                text = "-_-",
                                fontWeight = FontWeight.Normal
                            )
                        }
                    }
                }

                // show data picker when user clicks box
                if (uiState.openFinishDatePicker) {
                    EditMediaDatePicker(
                        datePickerState = pickerStateEndDate,
                        onDateSelected = {
                            viewModel.onChangeEndDate(getLocalDateFromMillis(it))
                        },
                        onDismiss = {
                            viewModel.closeDatePickers()
                        }
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))


                /**
                 * is re-reading
                 */
                Row(
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(horizontal = 15.dp)
                ) {
                    Checkbox(
                        checked = uiState.isRereading,
                        onCheckedChange = {
                            viewModel.onChangeIsRereading(it)
                        }
                    )
                    Text(
                        text = stringResource(R.string.rereading),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))


                /**
                 * total re-reads
                 */
                EditMediaProgressRow(
                    label = stringResource(R.string.total_rereads),
                    progress = uiState.numTimesRereads,
                    totalProgress = null,
                    modifier = Modifier.padding(horizontal = 15.dp),
                    onValueChange = {
                        viewModel.onChangeRepeatCount(it.toIntOrNull())
                    },
                    onMinusClick = {
                        viewModel.onChangeRepeatCount(uiState.numTimesRereads?.minus(1))
                    },
                    onPlusClick = {
                        viewModel.onChangeRepeatCount(uiState.numTimesRereads?.plus(1))
                    }
                )

                Spacer(modifier = Modifier.height(10.dp))


                /**
                 * re-read value
                 */
                Column(
                    modifier = Modifier.padding(horizontal = 15.dp)
                ) {
                    Text(
                        text = stringResource(R.string.reread_value, rereadValueTitle)
                    )
                    Slider(
                        value = uiState.rereadValue.toFloat(),
                        onValueChange = {
                            viewModel.onChangeRereadValue(it.roundToInt())
                        },
                        steps = 4,
                        valueRange = 0f..5f
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))


                /**
                 * tags
                 */
                OutlinedTextField(
                    value = uiState.tags ?: "",
                    label = {
                        Text(
                            text = stringResource(id = R.string.tags),
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    },
                    onValueChange = {
                        viewModel.onChangeTags(it)
                    },
                    shape = CircleShape,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                )

                Spacer(modifier = Modifier.height(25.dp))


                /**
                 * notes
                 */
                OutlinedTextField(
                    value = uiState.comments ?: "",
                    onValueChange = {
                        viewModel.onChangeComments(it)
                    },
                    shape = CircleShape,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    label = {
                        Text(
                            text = stringResource(id = R.string.notes),
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                )

                Spacer(modifier = Modifier.height(90.dp))

            }
        }
    }


    /**
     * show delete dialog
     */
    if (uiState.showDeleteDialog) {
        AlertDialog(
            icon = {
                Text(
                    text = "༼☯﹏☯༽",
                    color = MaterialTheme.colorScheme.onSurface,
                    fontSize = 40.sp,
                    fontWeight = FontWeight.ExtraBold
                )
            },
            title = {
                Text(
                    text = stringResource(id = R.string.delete_dialog_title),
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.Bold
                )
            },
            onDismissRequest = {
                showDeleteDialog(false)
            },
            confirmButton = {
                TextButton(
                    onClick = singleClick {
                        onDelete()
                        showDeleteDialog(false)
                    }
                ) {
                    Text(
                        text = stringResource(id = R.string.yes),
                        color = ColorTitleYes
                    )
                }
            },
            dismissButton = {
                TextButton(
                    onClick = singleClick {
                        showDeleteDialog(false)
                    }
                ) {
                    Text(
                        text = stringResource(id = R.string.no),
                        color = ColorTitleNo
                    )
                }
            }
        )
    }

}