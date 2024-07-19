package com.example.acad.data

import com.example.acad.models.Program
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProgramData @Inject constructor() {
    private var _program: MutableStateFlow<Program> = MutableStateFlow(Program())
    private var _listProgram: MutableList<Program> = mutableListOf()

    val program: StateFlow<Program> get() = _program
    val listProgram: List<Program> get() = _listProgram

    fun save(program: Program) {
        _program.value = program
    }

    fun saveList(listProgram: List<Program>) {
        _listProgram.clear()
        _listProgram.addAll(listProgram)
    }

    fun find(id: Long): Program? {
        return _listProgram.find { it.id == id }
    }
}