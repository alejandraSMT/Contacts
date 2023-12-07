package com.example.contacts.views.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Face
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.contacts.R
import com.example.contacts.data.event.ContactEvent
import com.example.contacts.data.state.ContactState
import com.example.contacts.model.Contact
import com.example.contacts.ui.theme.buttonsColor
import com.example.contacts.ui.theme.containerColor
import com.example.contacts.views.viewmodel.ContactViewModel

@Composable
fun AddContactDialog(
    openAddDialog: MutableState<Boolean>,
    state : ContactState,
    onEvent : (ContactEvent) -> Unit
) {

    val newName = remember{ mutableStateOf("") }
    val newNumber = remember{ mutableStateOf("") }

    Dialog(
        onDismissRequest = {
            //openAddDialog.value = false
            onEvent(ContactEvent.HideDialog)
        }
    ) {
        Card(
            shape = RoundedCornerShape(20.dp),
            modifier = Modifier
                .fillMaxHeight(0.7f)
                .fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            )
        ) {
            Column(
                modifier = Modifier
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(15.dp),
            ) {
                Text(
                    text = "Añadir contacto",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.Black,
                    fontWeight = FontWeight.SemiBold
                )

                Box(
                    modifier = Modifier
                        .clip(shape = CircleShape)
                        .background(buttonsColor)
                        .size(90.dp)
                        .align(Alignment.CenterHorizontally)
                ){
                    Icon(
                        imageVector = Icons.Rounded.Person,
                        contentDescription = "add foto",
                        modifier = Modifier
                            .align(Alignment.Center)
                            .size(40.dp)
                    )
                }

                TextField(
                    value = state.name,
                    onValueChange = {
                        onEvent(ContactEvent.setName(it))
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Rounded.Person,
                            contentDescription = "person",
                            tint = buttonsColor,
                            modifier = Modifier
                                .alpha(0.7f)
                        )
                    },
                    shape = RoundedCornerShape(20.dp),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = containerColor,
                        unfocusedContainerColor = containerColor,
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.Black,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedLabelColor = Color.Black,
                        unfocusedLabelColor = Color.Black
                    ),
                    singleLine = true,
                    placeholder = {
                        Text(
                            text = "Nombre",
                            color = Color.Gray
                        )
                    }
                )

                TextField(
                    value = state.number,
                    onValueChange = {
                        onEvent(ContactEvent.setPhone(it))
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Rounded.Phone,
                            contentDescription = "number",
                            tint = buttonsColor,
                            modifier = Modifier
                                .alpha(0.7f)
                        )
                    },
                    shape = RoundedCornerShape(20.dp),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = containerColor,
                        unfocusedContainerColor = containerColor,
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.Black,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedLabelColor = Color.Black,
                        unfocusedLabelColor = Color.Black
                    ),
                    singleLine = true,
                    placeholder = {
                        Text(
                            text = "Número",
                            color = Color.Gray
                        )
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number
                    )
                )

                Row(
                   modifier = Modifier
                       .align(Alignment.CenterHorizontally)
                       .padding(top=15.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ){
                    Button(
                        onClick = {
                            //openAddDialog.value = false
                            onEvent(ContactEvent.HideDialog)
                            //newName.value = ""
                            //newNumber.value = ""
                        },
                        shape = RoundedCornerShape(20.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.White
                        ),
                        border = BorderStroke(width = 1.dp, color = buttonsColor)
                    ) {
                        Text(
                            text = "Cancelar",
                            color = Color.Black
                        )
                    }

                    Button(
                        onClick = {
                            onEvent(ContactEvent.SaveContact)
                            onEvent(ContactEvent.HideDialog)
                            /*openAddDialog.value = false
                            newName.value = ""
                            newNumber.value = ""
                            onEvent(ContactEvent.setName(newName.value))
                            onEvent(ContactEvent.setPhone(newNumber.value))*/
                        },
                        shape = RoundedCornerShape(20.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = buttonsColor
                        ),
                        modifier = Modifier
                    ) {
                        Text(
                            text = "Guardar",
                            color = Color.White
                        )
                    }
                }

            }
        }
    }
}