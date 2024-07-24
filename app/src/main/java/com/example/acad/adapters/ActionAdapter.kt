package com.example.acad.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.acad.R
import com.example.acad.models.Action
import com.example.acad.viewholders.ActionViewHolder

class ActionAdapter(private var actions: List<Action>) : RecyclerView.Adapter<ActionViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActionViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_comment, parent, false)
        return ActionViewHolder(view)
    }

    override fun getItemCount(): Int = actions.size

    override fun onBindViewHolder(holder: ActionViewHolder, position: Int) {
        val action = actions[position]

    }
}