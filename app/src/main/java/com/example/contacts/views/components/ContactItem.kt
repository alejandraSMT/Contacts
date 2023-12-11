package com.example.contacts.views.components

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.example.contacts.model.Contact
import com.example.contacts.ui.theme.buttonsColor
import com.example.contacts.ui.theme.containerColor
import com.example.contacts.ui.theme.deleteRed
import com.example.contacts.ui.theme.favoriteColor
import com.example.contacts.utils.Converters
import com.example.contacts.views.viewmodel.ContactViewModel
import me.saket.swipe.SwipeAction
import me.saket.swipe.SwipeableActionsBox

@Composable
fun ContactItem(
    contact: Contact,
    lastItem : Boolean,
    contactViewModel: ContactViewModel
) {

    val removeFavorite = SwipeAction(
        onSwipe = {
            contactViewModel.removeFavorite(contact)
        },
        icon = {
            Icon(
                imageVector = Icons.Rounded.FavoriteBorder,
                contentDescription = "remove favorite",
                tint = Color.White,
                modifier = Modifier
                    .padding(end = 100.dp)
            )
        },
        background = Color.LightGray
    )

    val favorite = SwipeAction(
        onSwipe = {
            contactViewModel.addFavorite(contact)
        },
        icon = {
            Icon(
                imageVector = Icons.Rounded.Favorite,
                contentDescription = "favorite icon",
                tint = Color.White,
                modifier = Modifier
                    .padding(end = 100.dp)
            )
        },
        background = buttonsColor
    )

    val delete = SwipeAction(
        onSwipe = {
            contactViewModel.deleteContact(contact)
        },
        icon = {
            Icon(
                imageVector = Icons.Rounded.Delete,
                contentDescription = "delete icon",
                tint = Color.White,
                modifier = Modifier
                    .padding(start = 100.dp)
            )
        },
        background = deleteRed
    )

    SwipeableActionsBox (
        swipeThreshold = 200.dp,
        startActions = if(contact.favorite!!) listOf(removeFavorite) else listOf(favorite),
        endActions = listOf(delete),
        modifier = Modifier
    ){

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
                    if(contact.contactImage == null){
                        Icon(
                            imageVector = Icons.Rounded.AccountCircle,
                            contentDescription = "",
                            tint = buttonsColor,
                            modifier = Modifier
                                .size(40.dp)
                                .alpha(0.7f)
                        )
                    }else{
                        Image(
                            bitmap = Converters().base64ToBitmap(contact.contactImage).asImageBitmap(),
                            contentDescription = "contact photo",
                            modifier = Modifier
                                .size(40.dp),
                            contentScale = ContentScale.Fit
                        )
                    }
                },
                trailingContent = {
                    if (contact.favorite!!) {
                        Icon(
                            imageVector = Icons.Rounded.Favorite,
                            contentDescription = "",
                            tint = buttonsColor,
                            modifier = Modifier
                                .size(25.dp)
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
}