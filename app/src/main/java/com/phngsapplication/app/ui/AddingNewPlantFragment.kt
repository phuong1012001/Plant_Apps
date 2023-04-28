package com.phngsapplication.app.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import coil.api.load
import com.bumptech.glide.Glide
import com.phngsapplication.app.R
import com.phngsapplication.app.databinding.FragmentAddingNewPlantBinding
import kotlinx.android.synthetic.main.fragment_adding_new_plant.*
import java.io.File

class AddingNewPlantFragment : Fragment() {

    private lateinit var binding: FragmentAddingNewPlantBinding
    private lateinit var mainActivity: MainActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mainActivity = getActivity() as MainActivity
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_adding_new_plant, container, false)

        var bundleReceive: Bundle? = getArguments()
        if(bundleReceive != null){
            var a: String = bundleReceive.get("Uri") as String
            Log.d("uri", a)

            var uri = a.toUri()

            Glide.with(this)
                .load(File(uri.getPath()))
                .into(binding.imageNewPlant)
        }

        return binding.root
    }

    companion object {
    }
}