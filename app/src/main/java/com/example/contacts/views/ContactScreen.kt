package com.example.contacts.views

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.AddCircle
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.contacts.data.event.ContactEvent
import com.example.contacts.data.state.ContactState
import com.example.contacts.model.Contact
import com.example.contacts.ui.theme.PurpleGrey40
import com.example.contacts.ui.theme.PurpleGrey80
import com.example.contacts.ui.theme.buttonsColor
import com.example.contacts.ui.theme.containerColor
import com.example.contacts.ui.theme.favoriteColor
import com.example.contacts.ui.theme.searchbar
import com.example.contacts.ui.theme.topBarColor
import com.example.contacts.views.components.AddContactDialog
import com.example.contacts.views.components.ContactItem
import com.example.contacts.views.viewmodel.ContactViewModel
import dagger.hilt.android.AndroidEntryPoint
import me.saket.swipe.SwipeAction

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactScreen(
    contactViewModel: ContactViewModel = viewModel()
) {
    val allContacts = contactViewModel.allContacts.observeAsState().value
    val context= LocalContext.current

    val openAddDialog =  remember{ mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Contactos",
                        color = Color.White,
                        fontWeight = FontWeight.SemiBold
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = topBarColor
                )
            )
        },
        content = { padding ->
            val searchText = remember{ mutableStateOf("") }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
                    .padding(top = 75.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(15.dp)
            ) {
                TextField(
                    modifier = Modifier
                        .fillMaxWidth(0.9f),
                    value = searchText.value,
                    onValueChange = {searchText.value = it},
                    placeholder = {
                        Text(
                            text = "Buscar contacto...",
                            color = Color.Gray
                        )
                    },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = containerColor,
                        unfocusedContainerColor = containerColor,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedTextColor = Color.Black
                    ),
                    shape = RoundedCornerShape(50.dp),
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Rounded.Search,
                            contentDescription = "search icon",
                            tint = Color.LightGray
                        )
                    },
                    singleLine = true
                )
                if(contactViewModel.allContacts.isInitialized){
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth(0.9f)
                            .clip(shape = RoundedCornerShape(20.dp))
                            .background(Color.White),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        val list = contactViewModel.allContacts
                        val filteredContacts = list.value?.filter { it.name.contains(searchText.value, true) }
                        item{
                            if(filteredContacts?.isEmpty() == true){
                                Text(
                                    text = "Actualmente no tiene contactos",
                                    modifier = Modifier
                                        .align(Alignment.CenterHorizontally)
                                        .padding(top = 10.dp),
                                    textAlign = TextAlign.Center,
                                    color = Color.LightGray
                                )
                            }else{
                                filteredContacts?.forEachIndexed { index, contact ->
                                    ContactItem(
                                        contact = contact,
                                        lastItem = index == filteredContacts.size-1,
                                        contactViewModel = contactViewModel
                                    )
                                }
                            }
                        }

                    }
                }else{
                    Column(
                        modifier = Modifier
                            .fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ){
                        CircularProgressIndicator(
                            strokeWidth = 5.dp,
                            color = Color.LightGray,
                            trackColor = buttonsColor
                        )
                    }
                }
            }
        },
        floatingActionButton = {
            Button(
                onClick = {
                   openAddDialog.value = true
                },
                shape = CircleShape,
                colors = ButtonDefaults.buttonColors(
                    containerColor = buttonsColor,
                    contentColor = Color.White
                ),
                modifier = Modifier
                    .size(50.dp),
                contentPadding = PaddingValues(15.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ){
                    Icon(
                        imageVector = Icons.Rounded.Add,
                        contentDescription = "",
                        modifier = Modifier
                            .size(20.dp)
                    )
                }
            }
        }
    )

    if(openAddDialog.value){
        AddContactDialog(
            openAddDialog = openAddDialog,
            contactViewModel = contactViewModel
        )
    }

}