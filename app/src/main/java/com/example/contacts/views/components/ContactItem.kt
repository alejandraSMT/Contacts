package com.example.contacts.views.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemColors
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import com.example.contacts.data.event.ContactEvent
import com.example.contacts.data.state.ContactState
import com.example.contacts.model.Contact
import com.example.contacts.ui.theme.buttonsColor
import com.example.contacts.ui.theme.containerColor
import com.example.contacts.ui.theme.favoriteColor
import com.example.contacts.ui.theme.topBarColor

@Composable
fun ContactItem(
    contact: Contact,
    lastItem : Boolean
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(containerColor)
    ) {
        ListItem(
            headlineContent = {
                Text(
                    text = contact.name,
                    color = Color.Black,
                    style = MaterialTheme.typography.bodyLarge
                )
            },
            supportingContent = {
                Text(
                    text = contact.number,
                    color = Color.Gray,
                    style = MaterialTheme.typography.bodyMedium
                )
            } ,
            leadingContent = {
                Icon(
                    imageVector = Icons.Rounded.AccountCircle,
                    contentDescription = "",
                    tint = buttonsColor,
                    modifier = Modifier
                        .size(40.dp)
                        .alpha(0.7f)
                )
            },
            trailingContent = {
                if (contact.favorite!!) {
                    Icon(
                        imageVector = Icons.Rounded.Star,
                        contentDescription = "",
                        tint = buttonsColor,
                        modifier = Modifier
                            .size(30.dp)
                    )
                }
            },
            colors = ListItemDefaults.colors(
                containerColor = containerColor
            ),
            modifier = Modifier
        )
        if(!lastItem){
            Divider(
                thickness = 1.dp,
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .padding(end = 15.dp)
                    .align(Alignment.End),
                color = buttonsColor
            )
        }
    }
}