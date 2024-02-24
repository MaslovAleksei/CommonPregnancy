package com.margarin.commonpregnancy.presentation.details

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.margarin.commonpregnancy.presentation.utils.ContentType

@Composable
fun DetailsContent(component: DetailsComponent) {

    val state by component.model.collectAsState()
    val title = when (state.contentType) {
        ContentType.ChildDetails -> {
            "ChildDetails"
        }

        ContentType.AdviceDetails -> {
            "AdviceDetails"
        }

        ContentType.MotherDetails -> {
            "MotherDetails 111"
        }


    }
    Text(text = title)


}