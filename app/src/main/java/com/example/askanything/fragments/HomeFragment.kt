package com.example.askanything.fragments

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.askanything.MainActivity
import com.example.askanything.QuestionViewModel
import com.example.askanything.R
import com.example.askanything.login.LoginActivity
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.question_item.*
import kotlin.math.roundToInt


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class HomeFragment : Fragment() {

    private lateinit var viewModel: QuestionViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewModel()
        initViews()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {

        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.home_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.logout -> {
                viewModel.logout()
                requireActivity().finish()
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this).get(QuestionViewModel::class.java)

        viewModel.getCurrentQuestion()

        viewModel.currentQuestion.observe(viewLifecycleOwner, Observer { question ->

            if (question == null) {
                tvCurrentQuestion.text = "No Questions Left to answer"
            } else {
               tvCurrentQuestion.text = question.question
                btnOption1.text = question.options[0].option
                btnOption2.text = question.options[1].option
                val totalVotes = question.options[0].votes + question.options[1].votes
                if (totalVotes != 0) {
                    tvAnswer1Percentage.text = (question.options[0].votes.toDouble() / totalVotes * 100).roundToInt().toString() + "%"
                    tvAnswer2Percentage.text = (question.options[1].votes.toDouble() / totalVotes * 100).roundToInt().toString() + "%"
                } else {
                    tvAnswer1Percentage.text = "0%"
                    tvAnswer2Percentage.text = "0%"
                }
            }
        })
    }

    private fun initViews() {
        btnNextQuestion.visibility = View.INVISIBLE

        btnOption1.setOnClickListener {
            handleVote(0)
        }

        btnOption2.setOnClickListener {
            handleVote(1)
        }


    }

    private fun handleVote(option: Int) {
        btnOption1.isEnabled = false
        btnOption2.isEnabled = false
        btnNextQuestion.visibility = View.VISIBLE

        tvAnswer1Percentage.visibility = View.VISIBLE
        tvAnswer2Percentage.visibility = View.VISIBLE

        btnNextQuestion.setOnClickListener {
            btnOption1.isEnabled = true
            btnOption2.isEnabled = true
            btnNextQuestion.visibility = View.INVISIBLE

            tvAnswer1Percentage.visibility = View.INVISIBLE
            tvAnswer2Percentage.visibility = View.INVISIBLE

            viewModel.voteOnQuestion(option)
        }
    }
}

