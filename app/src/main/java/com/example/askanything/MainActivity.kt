package com.example.askanything

import android.content.Intent
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.example.askanything.login.LoginActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        // check if user is logged in
        auth = Firebase.auth
        val currentUser = auth.currentUser

        if (currentUser == null) {
            // navigate to create user/login
            startActivity(Intent(this, LoginActivity::class.java))
        } else {
            initNavigation()
        }
    }

    private fun initNavigation() {
        val navController = findNavController(R.id.nav_host_fragment)
        NavigationUI.setupWithNavController(navView, navController)
        toolbar.setupWithNavController(navController, AppBarConfiguration(navController.graph))
    }


}
