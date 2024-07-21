package com.example.acad.data

import com.example.acad.models.Group
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GroupData @Inject constructor() {
    private var _group: MutableStateFlow<Group> = MutableStateFlow(Group())

    val group: MutableStateFlow<Group> get() = _group

    fun setGroup(group: Group) {
        _group.value = group
    }

}