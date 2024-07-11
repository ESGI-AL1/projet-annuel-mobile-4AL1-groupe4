package com.example.acad.viewmodels

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.acad.models.Program
import com.example.acad.repositories.DataStoreRepository
import com.example.acad.repositories.ProgramRepository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import retrofit2.HttpException

class ProgramViewModel(
    private val programRepository: ProgramRepository,
    private val dataStoreRepository: DataStoreRepository
) : ViewModel() {

    private val _programs = MutableLiveData<List<Program>>()
    val programs: LiveData<List<Program>> = _programs

    fun launchRequest() {
        viewModelScope.launch {
            dataStoreRepository.readAccessToken().collect {
                programRepository.publicListProgram(it)
                    .catch { exception ->
                        if (exception is HttpException) {
                            Log.e(TAG, "loginUser: ${exception.message()}", exception)
                        }
                    }
                    .collect { response ->
                        _programs.postValue(response)  // Update the LiveData with the new data
                    }
            }
        }
    }
}
