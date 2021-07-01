package com.bae.roompractice.view.viewmodel

import androidx.lifecycle.*
import com.bae.roompractice.model.database.JSUserRepository
import com.bae.roompractice.model.entities.JSUser
import kotlinx.coroutines.launch

class JSUserViewModel(private val repository: JSUserRepository): ViewModel()
{
    val allUserList: LiveData<List<JSUser>> = repository.allUserList.asLiveData()

    fun insert(jsUser: JSUser) = viewModelScope.launch {
        repository.insertUserData(jsUser)
    }
}

class JSUserViewModelFactory(private val repository: JSUserRepository): ViewModelProvider.Factory
{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(JSUserViewModel::class.java)) {
            return JSUserViewModel(repository) as T
        }
        throw IllegalAccessException("Unknown ViewModel Class")
    }
}