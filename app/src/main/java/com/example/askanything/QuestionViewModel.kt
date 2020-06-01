package com.example.askanything

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.askanything.database.QuestionRepository
import com.example.askanything.model.Question

class QuestionViewModel(application: Application) : AndroidViewModel(application) {
    var questionRepository =
        QuestionRepository(application)

    var currentQuestion = MutableLiveData<Question>()

    fun addQuestion(question: Question) {
        questionRepository.addQuestion(question)
    }
}