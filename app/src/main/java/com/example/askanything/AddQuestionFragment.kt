package com.example.askanything

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.askanything.model.Question
import kotlinx.android.synthetic.main.fragment_add_question.*

/**
 * A simple [Fragment] subclass.
 */
class AddQuestionFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_question, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnSubmit.setOnClickListener {
            handleSubmitQuestion()
        }
    }

    private fun handleSubmitQuestion() {
        val question = etQuestion.text.toString()
        val option1 = etOption1.text.toString()
        val option2 = etOption2.text.toString()

        if (question.isBlank() || option1.isBlank() || option2.isBlank()) {
            Toast.makeText(context, "Please fill in all fields.", Toast.LENGTH_SHORT).show()
        } else {
            val questionObj = Question(question, listOf(option1, option2))

            // todo, push question object


        }
    }

}
