package com.phngsapplication.app.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.phngsapplication.app.R
import com.phngsapplication.app.databinding.ActivityMainBinding
import kotlin.random.Random


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    lateinit var uri: String


    private lateinit var firebaseAuth: FirebaseAuth

    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        firebaseAuth = FirebaseAuth.getInstance()
        checkUser()
        val navHostFragment = supportFragmentManager.findFragmentById(
            R.id.frameBottombar
        ) as NavHostFragment
        navController = navHostFragment.navController

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNavigationView.setupWithNavController(navController)

        binding.fab.setOnClickListener {
            val navController = findNavController(R.id.frameBottombar)
            navController.navigate(R.id.fab)
        }
    }

    private fun checkUser() {
        //get current user
        val firebaseUser = firebaseAuth.currentUser
        if(firebaseUser == null){
            val destIntent = LoginScreenActivity.getIntent(this, null)
            startActivity(destIntent)
        }
        else{
            val email = firebaseUser.email
        }
    }


    companion object {
        const val TAG: String = "MAIN_ACTIVITY"

        private val PHOTO_REQUEST_CODE = Random.nextInt(0, 10000)
        fun getIntent(context: Context, bundle: Bundle?): Intent {
            val destIntent = Intent(context, MainActivity::class.java)
            destIntent.putExtra(TAG, bundle)
            return destIntent
        }
    }
}
