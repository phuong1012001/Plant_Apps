package com.phngsapplication.app.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.net.toUri
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.phngsapplication.app.R
import com.phngsapplication.app.databinding.FragmentAddingNewPlant1Binding
import java.io.File

class AddingNewPlant1Fragment : Fragment() {

    private lateinit var binding: FragmentAddingNewPlant1Binding
    private lateinit var mainActivity: MainActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mainActivity = getActivity() as MainActivity
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_adding_new_plant_1, container, false)
        var a: String? = null
        var bundleReceive: Bundle? = getArguments()
        if(bundleReceive != null){
            a = bundleReceive.get("Uri") as String
            Log.d("uri", a)

            var uri = a.toUri()

            Glide.with(this)
                .load(File(uri.getPath()))
                .into(binding.imageNewPlant)
        }
        binding.btnNext.setOnClickListener{
            var name = binding.name.text.toString()
            if(name.isNotEmpty() && a != null){
                mainActivity.goToAddingNewPlant2(a, name)
            }else{
                Toast.makeText(mainActivity, "Species not Valid !!", Toast.LENGTH_SHORT).show()
            }
        }
        return binding.root
    }

    companion object {
    }
}