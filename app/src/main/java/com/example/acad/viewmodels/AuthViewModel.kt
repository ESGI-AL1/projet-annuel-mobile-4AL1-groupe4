package com.example.acad.viewmodels

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.acad.repositories.AuthRepository
import com.example.acad.requests.UserRequest
import com.example.acad.utils.enums.HttpStatus
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

class AuthViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {

    private val _state = MutableStateFlow(HttpStatus.INITIAL)

    val state: StateFlow<HttpStatus> get() = _state

    fun initialize() {
        viewModelScope.launch {
            _state.value = HttpStatus.INITIAL
        }
    }

    fun registerUser(request: UserRequest) {
        viewModelScope.launch {
            _state.value = HttpStatus.LOADING
            repository.registerUser(request)
                .catch {exception->
                    if (exception is HttpException) {
                        Log.e(TAG, "registerUser: ${exception.message()}", exception)
                    }
                    _state.value = HttpStatus.ERROR
                }
                .collect {
                    Log.d(TAG, "registerUser: $it")
                    _state.value = HttpStatus.LOADED
                }
        }
    }
}