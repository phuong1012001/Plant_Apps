package com.phngsapplication.app.ui

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.phngsapplication.app.R
import com.phngsapplication.app.databinding.FragmentIntroduction3Binding
import kotlinx.android.synthetic.main.activity_introduction.*

class Introduction3Fragment : Fragment() {
    private lateinit var binding: FragmentIntroduction3Binding
    private lateinit var introduction: Introduction
    private lateinit var sharedPreferences: SharedPreferences
    private val SHARED_PREFERENCE_NAME = "time_start_activity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_introduction_3, container, false)
        introduction = getActivity() as Introduction

        sharedPreferences = introduction.getSharedPreferences(
            SHARED_PREFERENCE_NAME,
            Context.MODE_PRIVATE
        )

        binding.btnSignIn.setOnClickListener {
            sharedPreferences.edit().putString("fist", "true")
                .apply()
            val controller = findNavController()
            controller.navigate(R.id.action_introduction3Fragment_to_loginScreenActivity)
            introduction.finish()
        }

        return binding.root
    }
}