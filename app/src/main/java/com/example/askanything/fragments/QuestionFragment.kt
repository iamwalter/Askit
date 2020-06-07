package com.example.askanything.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.askanything.QuestionViewModel
import com.example.askanything.R
import kotlinx.android.synthetic.main.fragment_question.*
import kotlinx.android.synthetic.main.fragment_question.view.*

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class QuestionFragment : Fragment() {

    private lateinit var viewModel: QuestionViewModel

    private val questionAdapter = QuestionAdapter()

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_question, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewModel()
        initViews()
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this).get(QuestionViewModel::class.java)

        viewModel.getAllQuestions()

        viewModel.allQuestions.observe(viewLifecycleOwner, Observer {
            questionsProgressBar?.visibility = View.INVISIBLE
            questionAdapter.questions.clear()
            questionAdapter.questions.addAll(it)
            questionAdapter.notifyDataSetChanged()
        })
    }

    private fun initViews() {
        rvQuestions.adapter = questionAdapter
        rvQuestions.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        questionsProgressBar?.visibility = View.VISIBLE
        fabAddQuestion.setOnClickListener {
            findNavController().navigate(R.id.action_QuestionFragment_to_addQuestionFragment)
        }
    }

}
