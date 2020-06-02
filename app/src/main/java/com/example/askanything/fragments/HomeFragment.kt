package com.example.askanything.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Button
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.askanything.QuestionViewModel
import com.example.askanything.R
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_question.*

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
            R.id.logout -> viewModel.logout()
        }

        return super.onOptionsItemSelected(item)
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this).get(QuestionViewModel::class.java)

        viewModel.getCurrentQuestion()

        viewModel.currentQuestion.observe(viewLifecycleOwner, Observer {
            tvCurrentQuestion.text = it.question
            btnOption1.text = it.options[0].option
            btnOption2.text = it.options[1].option
        })
    }

    private fun initViews() {
        btnOption1.setOnClickListener {
            viewModel.voteOnQuestion(0)
        }

        btnOption2.setOnClickListener {
            viewModel.voteOnQuestion(1)
        }

        btnNextQuestion.setOnClickListener {
            viewModel.getCurrentQuestion()
        }
    }
}

