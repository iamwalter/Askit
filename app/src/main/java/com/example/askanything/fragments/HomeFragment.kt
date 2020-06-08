package com.example.askanything.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.askanything.QuestionViewModel
import com.example.askanything.R
import com.example.askanything.model.Option
import com.example.askanything.model.Question
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_no_questions.*
import kotlinx.android.synthetic.main.fragment_yes_questions.*
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
            hideLoadingBar()

            if (question == null) {
                updateLayoutNoQuestions()
            } else {
                updateLayoutYesQuestions(question)
            }
        })
    }


    private fun initViews() {
        showLoadingBar()

        btnNoQuestions.setOnClickListener {
            findNavController().navigate(R.id.action_HomeFragment_to_QuestionFragment)
        }

        btnOption1.setOnClickListener {
            handleVote(0)
        }

        btnOption2.setOnClickListener {
            handleVote(1)
        }
    }

    private fun handleVote(option: Int) {
        updateLayoutVoted()

        btnNextQuestion.setOnClickListener {
            updateLayoutWaitingOnVote()

            viewModel.voteOnQuestion(option)
        }
    }

    private fun updateLayoutVoted() {
        btnOption1.isEnabled = false
        btnOption2.isEnabled = false
        btnNextQuestion.visibility = View.VISIBLE
        tvAnswer1Percentage.visibility = View.VISIBLE
        tvAnswer2Percentage.visibility = View.VISIBLE
    }

    private fun updateLayoutWaitingOnVote() {
        btnOption1.isEnabled = true
        btnOption2.isEnabled = true
        btnNextQuestion.visibility = View.INVISIBLE
        tvAnswer1Percentage.visibility = View.INVISIBLE
        tvAnswer2Percentage.visibility = View.INVISIBLE
    }

    private fun updateLayoutNoQuestions() {
        no_layout.visibility = View.VISIBLE
        yes_layout.visibility = View.GONE
    }

    private fun updateLayoutYesQuestions(question: Question) {
        if (yes_layout.visibility != View.VISIBLE) {
            no_layout.visibility = View.GONE
            yes_layout.visibility = View.VISIBLE
        }

        val option1 = question.options[0]
        val option2 = question.options[1]

        tvCurrentQuestion.text = question.question
        btnOption1.text = option1.option
        btnOption2.text = option2.option

        val totalVotes = option1.votes + option2.votes

        val firstVotes = getPercentageVotes(totalVotes, option1)
        val secondVotes = getPercentageVotes(totalVotes, option2)

        tvAnswer1Percentage.text = String.format(getString(R.string.answerpercentage), firstVotes)
        tvAnswer2Percentage.text = String.format(getString(R.string.answerpercentage), secondVotes)

    }

    private fun showLoadingBar() {
        yes_layout.visibility = View.GONE
        no_layout.visibility = View.GONE

        homeProgressBar.visibility = View.VISIBLE
    }

    private fun hideLoadingBar() {
        homeProgressBar.visibility = View.INVISIBLE
    }
}

private fun getPercentageVotes(
    totalVotes: Int,
    option: Option
) = if (totalVotes != 0) (option.votes.toDouble() / totalVotes * 100).roundToInt() else 0

