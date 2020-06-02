package com.example.askanything.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginViewModel() : ViewModel() {
    private var auth: FirebaseAuth = Firebase.auth

    var email = MutableLiveData<String>()
    var password = MutableLiveData<String>()

    var success = MutableLiveData<Boolean>()
    var error = MutableLiveData<String>()

    fun createAccount() {
        auth.createUserWithEmailAndPassword(email.value.toString(), password.value.toString())
            .addOnCompleteListener {
                success.value = it.isSuccessful
            }
            .addOnFailureListener {
                error.value = it.message
            }
        }
}
