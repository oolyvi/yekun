package com.oolyvi.yekun.ui.home.composables

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.oolyvi.yekun.R
import com.oolyvi.yekun.ui.main.navigation.NavActionManager
import com.oolyvi.yekun.ui.theme.ColorBlack
import com.oolyvi.yekun.ui.theme.ColorWhite

@Composable
fun HomeSearchField(
    navActionManager: NavActionManager
) {

    Card(
        colors = CardDefaults.cardColors(
            containerColor = ColorWhite
        ),
        modifier = Modifier
            .padding(horizontal = 15.dp)
            .clip(RoundedCornerShape(15.dp))
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.onSurface,
                shape = RoundedCornerShape(15.dp)
            )
            .clickable {
                navActionManager.toSearch()
            },
        elevation = CardDefaults.cardElevation(
            defaultElevation = 5.dp
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Icon(
                painter = painterResource(id = R.drawable.search),
                contentDescription = stringResource(id = R.string.cd_search_icon),
                tint = ColorBlack
            )

            Spacer(modifier = Modifier.width(10.dp))

            Text(
                text = stringResource(R.string.search),
                color = ColorBlack
            )
        }
    }
}