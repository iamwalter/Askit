package com.example.askanything.fragments

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.askanything.R
import com.example.askanything.model.Question
import kotlinx.android.synthetic.main.question_item.view.*
import kotlin.math.roundToInt

class QuestionAdapter() : RecyclerView.Adapter<QuestionAdapter.ViewHolder>() {
    var questions = arrayListOf<Question>()

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(question: Question) {
            itemView.tvQuestion.text = question.question
            itemView.tvOption1.text = question.options[0].option
            itemView.tvOption2.text = question.options[1].option
            val totalVotes = question.options[0].votes + question.options[1].votes
            itemView.tvTotalVotesNum.text = totalVotes.toString()
            if (totalVotes != 0) {
                itemView.tvOption1Percentage.text = (question.options[0].votes.toDouble() / totalVotes * 100).roundToInt().toString() + "%"
                itemView.tvOption2Percentage.text = (question.options[1].votes.toDouble() / totalVotes * 100).roundToInt().toString() + "%"
            } else {
                itemView.tvOption1Percentage.text = "0%"
                itemView.tvOption2Percentage.text = "0%"
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.question_item, parent, false)
        )
    }

    override fun getItemCount(): Int = questions.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(questions[position])
    }

}