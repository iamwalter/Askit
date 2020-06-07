package com.example.askanything.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.askanything.QuestionViewModel
import com.example.askanything.R
import com.example.askanything.model.Option
import com.example.askanything.model.Question
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_add_question.*

/**
 * A simple [Fragment] subclass.
 */
class AddQuestionFragment : Fragment() {

    private lateinit var viewModel : QuestionViewModel
    private var auth = Firebase.auth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_question, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewModel()
        initViews()
    }

    private fun initViews() {
        btnSubmit.setOnClickListener {
            handleSubmitQuestion()
        }
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this).get(QuestionViewModel::class.java)
    }

    private fun handleSubmitQuestion() {
        val option1 = Option(etOption1.text.toString(), 0)
        val option2 = Option(etOption2.text.toString(), 0)
        val question = Question(etQuestion.text.toString(), listOf(option1, option2), auth.uid)

        if (question.question.isBlank() || option1.option.isBlank() || option2.option.isBlank()) {
            Toast.makeText(context, "Please fill in all fields.", Toast.LENGTH_SHORT).show()
        } else {
            viewModel.addQuestion(question)
            findNavController().navigateUp()
        }
    }

}
