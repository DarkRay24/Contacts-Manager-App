package com.example.contactsmanager.viewmodel

import android.database.Observable
import androidx.databinding.Bindable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.contactsmanager.room.User
import com.example.contactsmanager.room.UserRepository
import kotlinx.coroutines.launch

class UserViewModel(private val repository : UserRepository)
    : ViewModel(), androidx.databinding.Observable {

    val users = repository.users
    private var isUpdateOrDelete = false
    private lateinit var userToUpdateOrDelete : User

    @Bindable
    val inputName = MutableLiveData<String?>()

    @Bindable
    val inputEmail = MutableLiveData<String?>()

    @Bindable
    val saveOrUpdateButtonText = MutableLiveData<String>()

    @Bindable
    val clearAllOrDeleteButtonText = MutableLiveData<String>()

    init {
        saveOrUpdateButtonText.value = "SAVE"
        clearAllOrDeleteButtonText.value = "CLEAR ALL"
    }

    private fun insert(user : User) = viewModelScope.launch {
        repository.insert(user)
    }
    private fun clearAll() = viewModelScope.launch{
        repository.deleteAll()
    }
    private fun update(user : User) = viewModelScope.launch {
        repository.update(user)

        // Resetting the Buttons and Fields
        inputName.value = null
        inputEmail.value = null
        isUpdateOrDelete = false
        saveOrUpdateButtonText.value = "SAVE"
        clearAllOrDeleteButtonText.value = "CLEAR ALL"

    }
    private fun delete(user : User) = viewModelScope.launch {
        repository.delete(user)

        // Resetting the Buttons and Fields
        inputName.value = null
        inputEmail.value = null
        isUpdateOrDelete = false
        saveOrUpdateButtonText.value = "SAVE"
        clearAllOrDeleteButtonText.value = "CLEAR ALL"
    }


    fun saveOrUpdate(){

        if (isUpdateOrDelete){
            // Make Update
            userToUpdateOrDelete.name = inputName.value!!
            userToUpdateOrDelete.email = inputEmail.value!!
            update(userToUpdateOrDelete)
        }
        else{
            // Insert New User
            val name = inputName.value!!
            val email = inputEmail.value!!

            insert(User(0, name, email))

            inputName.value = null
            inputEmail.value = null
        }
    }

    fun clearAllOrDelete(){
        if(isUpdateOrDelete){
            delete(userToUpdateOrDelete)
        }else{
            clearAll()
        }
    }

    fun initUpdateAndDelete(user : User){

        inputName.value = user.name
        inputEmail.value = user.email
        isUpdateOrDelete = true
        userToUpdateOrDelete = user
        saveOrUpdateButtonText.value = "UPDATE"
        clearAllOrDeleteButtonText.value = "DELETE"
    }


    override fun addOnPropertyChangedCallback(callback: androidx.databinding.Observable.OnPropertyChangedCallback?) {
        val x =0
    }

    override fun removeOnPropertyChangedCallback(callback: androidx.databinding.Observable.OnPropertyChangedCallback?) {
        val x = 0
    }


}