package com.example.acad.repositories

import com.example.acad.services.ProgramService
import javax.inject.Inject

class ProgramRepository @Inject constructor(
    private val service: ProgramService
) {

}