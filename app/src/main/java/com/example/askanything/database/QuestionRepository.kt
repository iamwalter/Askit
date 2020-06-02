package com.example.askanything.database

import android.content.Context
import android.widget.Toast
import com.example.askanything.model.Question
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class QuestionRepository(var context: Context) {
    private var reference: DatabaseReference = Firebase.database.getReference("Questions")

    fun getQuestions(onDataChange: (snapshot: DataSnapshot) -> Unit) {
        reference.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                onDataChange(dataSnapshot)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                System.err.println(databaseError.message)
            }
        })
    }

    fun addQuestion(question: Question) {
        val questionRef = reference.push()
        question.authorId = "Walter"
        questionRef.setValue(question).addOnCompleteListener {
            Toast.makeText(context, "done did it", Toast.LENGTH_SHORT).show()
        }.addOnFailureListener {
            Toast.makeText(context, "done didn't do it", Toast.LENGTH_SHORT).show()
        }
    }

    fun voteOnQuestion(question: Question, questionId: String, option: Int) {
        val newVotes = question.options[option].votes + 1
        reference.child(questionId).child("options/$option/votes").setValue(newVotes)
    }

    // todo; functions like addquestion, bla bla bla

}