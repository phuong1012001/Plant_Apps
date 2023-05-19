package com.phngsapplication.app.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.phngsapplication.app.R
import com.phngsapplication.app.databinding.FragmentIntroduction1Binding

class Introduction1Fragment : Fragment() {
    private lateinit var binding: FragmentIntroduction1Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_introduction_1, container, false)

        binding.btnNext.setOnClickListener {
            val controller = findNavController()
            controller.navigate(R.id.action_introduction1Fragment_to_introduction2Fragment)
        }

        return binding.root
    }
}