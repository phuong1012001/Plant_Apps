package com.phngsapplication.app.ui

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.phngsapplication.app.R

class Introduction : AppCompatActivity() {
    private lateinit var firebaseAuth: FirebaseAuth

    private lateinit var sharedPreferences: SharedPreferences
    private val SHARED_PREFERENCE_NAME = "time_start_activity"
    var time1: Long = 0
    var time2: Long = 0
    var first: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_introduction)
        firebaseAuth = FirebaseAuth.getInstance()
        firebaseAuth.uid.toString()

        sharedPreferences = this.getSharedPreferences(
            SHARED_PREFERENCE_NAME,
            Context.MODE_PRIVATE
        )
        first = sharedPreferences.getString("fist", "")
    }

    override fun onStart() {
        super.onStart()
        if (first == "true") {
            if (firebaseAuth.uid != null){
                val destIntent = MainActivity.getIntent(this, null)
                startActivity(destIntent)
                this.finish()
            }
            else {
                val destIntent = LoginScreenActivity.getIntent(this, null)
                startActivity(destIntent)
                this.finish()
            }
        }
    }

    companion object {
        const val TAG: String = "INTRO_ACTIVITY"
        fun getIntent(context: Context, bundle: Bundle?): Intent {
            val destIntent = Intent(context, Introduction::class.java)
            destIntent.putExtra(TAG, bundle)
            return destIntent
        }
    }
}