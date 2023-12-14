package com.example.contacts.views.components

import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.core.content.ContextCompat
import androidx.room.TypeConverters
import com.example.contacts.R
import com.example.contacts.data.event.ContactEvent
import com.example.contacts.data.state.ContactState
import com.example.contacts.model.Contact
import com.example.contacts.ui.theme.buttonsColor
import com.example.contacts.ui.theme.containerColor
import com.example.contacts.utils.Converters
import com.example.contacts.views.viewmodel.ContactViewModel

@Composable
fun AddContactDialog(
    openAddDialog: MutableState<Boolean>,
    contactViewModel: ContactViewModel
) {
    val openErrorDialog = remember { mutableStateOf(false) }

    val newName = remember{ mutableStateOf("") }
    val newNumber = remember{ mutableStateOf("") }

    var imageUri = remember {
        mutableStateOf<Uri?>(null)
    }
    val context = LocalContext.current
    val bitmap =  remember {
        mutableStateOf<Bitmap?>(null)
    }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()){
        uri: Uri? ->
        imageUri.value = uri
    }

    imageUri?.let {
        if(Build.VERSION.SDK_INT < 28){
            bitmap.value = MediaStore.Images.Media.getBitmap(
                context.contentResolver, it.value
            )
        }else{
            val source = it.value?.let { it1 ->
                ImageDecoder
                    .createSource(context.contentResolver, it1)
            }
            val aux = source?.let { it1 -> ImageDecoder.decodeBitmap(it1) }
            bitmap.value = aux?.copy(Bitmap.Config.ARGB_8888,false)
        }
    }

    Dialog(
        onDismissRequest = {
            openAddDialog.value = false
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
                        .clickable {
                            launcher.launch("image/*")
                        }
                ){
                    if(bitmap.value == null){
                        Icon(
                            imageVector = Icons.Rounded.Person,
                            contentDescription = "add foto",
                            modifier = Modifier
                                .align(Alignment.Center)
                                .size(40.dp)
                        )
                    }else{
                        Image(
                            bitmap = bitmap.value!!.asImageBitmap(),
                            contentDescription = "image uploading",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(200.dp)
                        )
                    }
                }
                TextField(
                    value = newName.value,
                    onValueChange = {
                        newName.value = it
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
                    value = newNumber.value,
                    onValueChange = {
                        newNumber.value = it
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
                       .padding(top = 15.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ){
                    Button(
                        onClick = {
                            openAddDialog.value = false
                            newName.value = ""
                            newNumber.value = ""
                            //onEvent(ContactEvent.HideDialog)
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
                            if(newName.value != "" && newNumber.value != ""){
                                val contact = Contact(
                                    name = newName.value,
                                    number = newNumber.value,
                                    contactImage = if(bitmap.value == null) null else Converters().encodeImage(bitmap.value!!)
                                )
                                contactViewModel.addContact(contact)
                                openAddDialog.value = false
                                newName.value = ""
                                newNumber.value = ""

                            }else{
                                openErrorDialog.value = true
                            }
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

    if(openErrorDialog.value){
        ErrorDialog(
            openErroDialog = openErrorDialog
        )
    }
}