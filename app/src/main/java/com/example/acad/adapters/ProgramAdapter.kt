package com.example.acad.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.acad.R
import com.example.acad.models.Program
import com.example.acad.viewholders.ProgramViewHolder

class ProgramAdapter(private val programs: List<Program>, private val onClick: (Program) -> Unit) :
    RecyclerView.Adapter<ProgramViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProgramViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_program, parent, false)
        return ProgramViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProgramViewHolder, position: Int) {
        val program = programs[position]
        holder.profileImage.setImageResource(program.profileImage)
        holder.programTitle.text = program.title
        holder.programDate.text = program.date
        holder.programDescription.text = program.description

        // Clear existing tags
        holder.tagsLayout.removeAllViews()

        // Add tags to the layout
        program.tags?.forEach { tag ->
            val tagView = LayoutInflater.from(holder.itemView.context)
                .inflate(R.layout.tag_view, holder.tagsLayout, false) as TextView
            tagView.text = tag
            holder.tagsLayout.addView(tagView)
        }

        holder.itemView.setOnClickListener {
            onClick(program)
        }
    }

    override fun getItemCount(): Int = programs.size
}
