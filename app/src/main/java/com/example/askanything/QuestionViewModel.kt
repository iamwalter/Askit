package com.example.askanything

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.askanything.database.QuestionRepository
import com.example.askanything.model.Option
import com.example.askanything.model.Question
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class QuestionViewModel(application: Application) : AndroidViewModel(application) {
    private var questionRepository = QuestionRepository(application)

    private var answeredRef: DatabaseReference = Firebase.database.getReference("Answered")

    private var auth: FirebaseAuth = Firebase.auth

    private var currentQuestionId: String = ""
    var currentQuestion = MutableLiveData<Question?>()
    var allQuestions = MutableLiveData<List<Question>>()

    fun logout() {
        auth.signOut()
    }

    fun getAllQuestions() {
        questionRepository.getQuestion { it ->
            val questionList = arrayListOf<Question>()
            for (q in it.children) {
                val question = q.getValue(Question::class.java)
                if (question != null) {
                    if (question.authorId == auth.uid) {
                        questionList.add(question)
                    }
                }
            }

            allQuestions.value = questionList
        }
    }

    fun getCurrentQuestion() {
        questionRepository.getQuestion {
            for (q in it.children) {
                var question = q.getValue(Question::class.java)
                if (question != null && question.authorId != auth.uid) {
                    // check if question is already answered
                    answeredRef.child(auth.uid!!).child(q.key!!)
                        .addValueEventListener(object :
                            ValueEventListener {
                            override fun onDataChange(dataSnapshot: DataSnapshot) {
                                val answered = dataSnapshot.exists()

                                if (!answered) {
                                    currentQuestion.value = question
                                    currentQuestionId = q.key.toString()
                                    return
                                }

                            }

                            override fun onCancelled(databaseError: DatabaseError) {
                                System.err.println(databaseError.message)
                            }
                        })
                }
            }


            currentQuestion.value = null
        }
    }

    fun voteOnQuestion(option: Int) {
        currentQuestion.value?.let {
            questionRepository.voteOnQuestion(
                it,
                currentQuestionId,
                option
            )
        }
    }

    fun addQuestion(question: Question) {
        questionRepository.addQuestion(question)
    }
}