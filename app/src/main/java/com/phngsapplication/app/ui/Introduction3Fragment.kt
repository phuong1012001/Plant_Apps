package com.phngsapplication.app.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.phngsapplication.app.R
import com.phngsapplication.app.databinding.FragmentIntroduction3Binding

class Introduction3Fragment : Fragment() {
    private lateinit var binding: FragmentIntroduction3Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_introduction_3, container, false)

        binding.btnSignIn.setOnClickListener {
            val controller = findNavController()
            controller.navigate(R.id.action_introduction3Fragment_to_loginScreenActivity)
        }

        return binding.root
    }
}