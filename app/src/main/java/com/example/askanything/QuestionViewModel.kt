package com.example.askanything

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.askanything.database.QuestionRepository
import com.example.askanything.model.Question
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class QuestionViewModel(application: Application) : AndroidViewModel(application) {
    private var questionRepository = QuestionRepository(application)

    private var auth: FirebaseAuth = Firebase.auth

    private var currentQuestionId: String = ""
    var currentQuestion = MutableLiveData<Question>()
    var allQuestions = MutableLiveData<List<Question>>()

    fun logout() {
        auth.signOut()
    }

    fun getAllQuestions() {
        questionRepository.getQuestion { it ->
            val questionList = arrayListOf<Question>()
            for (q in it.children) {
                val question = q.getValue(Question::class.java)
                question?.let {
                    questionList.add(it)
                }
            }

            allQuestions.value = questionList
        }
    }

    fun getCurrentQuestion() {
        questionRepository.getQuestion {
            for (q in it.children) {
                val question = q.getValue(Question::class.java)

                if (question != null && question.authorId != "Walter") { // TODO: AUTH ID
                    currentQuestion.value = q.getValue(Question::class.java)
                    currentQuestionId = q.key.toString()
                }
            }
        }
    }

    fun voteOnQuestion(option: Int) {
        currentQuestion.value?.let { questionRepository.voteOnQuestion(it, currentQuestionId, option) }
    }

    fun addQuestion(question: Question) {
        questionRepository.addQuestion(question)
    }
}