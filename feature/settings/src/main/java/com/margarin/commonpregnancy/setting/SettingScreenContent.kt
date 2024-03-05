package com.margarin.commonpregnancy.setting

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.margarin.settings.R

@Composable
fun SettingScreenContent(component: SettingComponent) {

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
    ) {
        Text(
            text = stringResource(R.string.settings),
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.onBackground
        )
        Spacer(modifier = Modifier.height(16.dp))
        TextIcon(
            text = stringResource(R.string.change_term),
            onClickOption = { component.onClickTerms() }
        )
        HorizontalDivider(Modifier.padding(vertical = 8.dp))
        TextIcon(
            text = stringResource(R.string.support),
            onClickOption = { }
        )
Text(
    modifier = Modifier.padding(end = 32.dp, bottom = 16.dp),
    text = stringResource(R.string.support_desc),
    color = Color.Gray
)
        HorizontalDivider(Modifier.padding(vertical = 8.dp))
        TextIcon(
            text = stringResource(R.string.privacy_policy),
            onClickOption = { }
        )
    }
}

@Composable
private fun TextIcon(
text: String,
onClickOption: () -> Unit
) {
    Row(
        modifier = Modifier
            .clickable { onClickOption() }
            .padding(vertical = 16.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge
        )
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
            contentDescription = null,
            tint = Color.Gray
        )
    }
}