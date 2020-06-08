package com.example.askanything.database

import android.content.Context
import android.widget.Toast
import com.example.askanything.model.Answered
import com.example.askanything.model.Question
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class QuestionRepository(var context: Context) {
    private var auth: FirebaseAuth = Firebase.auth

    private var questionsRef: DatabaseReference = Firebase.database.getReference("Questions")
    private var answeredRef: DatabaseReference = Firebase.database.getReference("Answered/${auth.uid}")


    fun getQuestion(onDataChange: (snapshot: DataSnapshot) -> Unit) {
        questionsRef.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                onDataChange(dataSnapshot)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                System.err.println(databaseError.message)
            }
        })
    }

    fun addQuestion(question: Question) {
        val questionRef = questionsRef.push()
        questionRef.setValue(question).addOnCompleteListener {
            Toast.makeText(context, "done did it", Toast.LENGTH_SHORT).show()
        }.addOnFailureListener {
            Toast.makeText(context, "done didn't do it", Toast.LENGTH_SHORT).show()
        }
    }

    fun voteOnQuestion(question: Question, questionId: String, option: Int) {
        // 1. add vote count to question
        val newVotes = question.options[option].votes + 1
        questionsRef.child(questionId).child("options/$option/votes").setValue(newVotes)

        // 2. log that user has voted on this question already.
        answeredRef.child(questionId).setValue(question.options[option].option)
    }

    // todo; functions like addquestion, bla bla bla

}