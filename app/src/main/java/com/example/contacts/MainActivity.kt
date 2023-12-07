package com.example.contacts

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.example.contacts.data.dao.ContactDatabase
import com.example.contacts.ui.theme.ContactsTheme
import com.example.contacts.views.ContactScreen
import com.example.contacts.views.viewmodel.ContactViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        private val db by lazy {
            Room.databaseBuilder(
                applicationContext,
                ContactDatabase::class.java,
                "contacts.db"
            ).build()
        }

        private val contactViewModel by viewModels<ContactViewModel>(
            factoryProducer = {
                object : ViewModelProvider.Factory{
                    override fun <T : ViewModel> create(modelClass: Class<T>): T {
                        return ContactViewModel(db.daoContact()) as T
                    }
                }
            }
        )

        setContent {
            ContactsTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    ContactScreen(
                        state = ,
                        onEvent = contactViewModel::onEvent
                    )
                }
            }
        }
    }
}