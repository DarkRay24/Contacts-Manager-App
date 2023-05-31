package com.example.contactsmanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.contactsmanager.databinding.ActivityMainBinding
import com.example.contactsmanager.room.User
import com.example.contactsmanager.room.UserDatabase
import com.example.contactsmanager.room.UserRepository
import com.example.contactsmanager.viewUI.MyRecyclerViewAdapter
import com.example.contactsmanager.viewmodel.UserViewModel
import com.example.contactsmanager.viewmodel.UserViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var userViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        // Room database
        val dao = UserDatabase.getInstance(application).userDAO
        val repository = UserRepository(dao)
        val factory = UserViewModelFactory(repository)

        userViewModel = ViewModelProvider(
            this,
            factory
        )[UserViewModel::class.java]

        // Assigning this ViewModel Object to Layout Object
        binding.userViewModel = userViewModel

        binding.lifecycleOwner = this

        initiateRecyclerView()
    }

    private fun initiateRecyclerView() {
        binding.rvContacts.layoutManager = LinearLayoutManager(this)
        displayUsersList()
    }

    private fun displayUsersList() {
        userViewModel.users.observe(this, Observer {
            binding.rvContacts.adapter = MyRecyclerViewAdapter(
                it, { selectedItem: User -> listItemClicked(selectedItem) }
            )
        })
    }

    private fun listItemClicked(selectedItem: User) {
        Toast.makeText(
            this,
            "Selected name is ${selectedItem.name}",
            Toast.LENGTH_LONG
        ).show()

        userViewModel.initUpdateAndDelete(selectedItem)
    }
}