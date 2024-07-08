package com.example.acad.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.acad.R
import com.example.acad.models.Question
import com.example.acad.utils.format
import com.example.acad.utils.parseDate
import com.example.acad.viewholders.QuestionViewHolder

class QuestionAdapter (private var questions: List<Question>) : RecyclerView.Adapter<QuestionViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_question, parent, false)
        return QuestionViewHolder(view)
    }

    override fun onBindViewHolder(holder: QuestionViewHolder, position: Int) {
        val question = questions[position]
        holder.authorName.text = question.authorName
        holder.questionTime.text = question.questionTime.parseDate("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")?.format(" dd MMM yyyy à HH::mm")
        holder.questionTitle.text = question.questionTitle
        holder.questionDescription.text = question.questionDescription
        holder.viewsCount.text = question.viewsCount.toString()
        holder.commentsCount.text = question.commentsCount.toString()
        holder.votesCount.text = question.votesCount.toString()
        // Placeholder for profile image, update with actual image loading
        holder.profileImage.setImageResource(R.drawable.ic_profile)

        holder.tagsLayout.removeAllViews()
        question.tags.forEach { tag ->
            val tagView = LayoutInflater.from(holder.itemView.context)
                .inflate(R.layout.tag_item, holder.tagsLayout, false) as TextView
            tagView.text = tag
            holder.tagsLayout.addView(tagView)
        }

        holder.itemView.setOnClickListener {
            // Handle click event to navigate to question details
        }
    }

    override fun getItemCount(): Int = questions.size

    fun updateData(data: List<Question>) {
        questions = data
        notifyDataSetChanged()
    }
}