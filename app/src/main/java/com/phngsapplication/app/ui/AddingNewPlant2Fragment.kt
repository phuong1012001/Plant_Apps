package com.phngsapplication.app.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.net.toUri
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.phngsapplication.app.R
import com.phngsapplication.app.databinding.FragmentAddingNewPlant2Binding
import com.phngsapplication.app.model.Plant
import java.io.File

class AddingNewPlant2Fragment : Fragment() {

    private lateinit var binding: FragmentAddingNewPlant2Binding
    private lateinit var mainActivity: MainActivity

    val args: AddingNewPlant2FragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mainActivity = getActivity() as MainActivity
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_adding_new_plant_2, container, false)
        var a = args.uri
        var species = args.nameSpecie
        if(species != null){
            var uri = a.toUri()

            Glide.with(this)
                .load(File(uri.getPath()))
                .into(binding.imageNewPlant)
        }

        binding.btnSubmit.setOnClickListener{
            var name_plant = binding.name.text.toString()
            var kingdom = binding.kingdom.text.toString()
            var family = binding.family.text.toString()
            var decreption = binding.family.text.toString()
            if(name_plant.isNotEmpty() && kingdom.isNotEmpty() && family.isNotEmpty() && decreption.isNotEmpty() && a != null){
                //Them
                var plant:Plant = Plant(a, name_plant, kingdom, family, decreption, "DisLike")
                //Species

                mainActivity.goToHome()
            }else{
                Toast.makeText(mainActivity, "Not Valid !!", Toast.LENGTH_SHORT).show()
            }
        }

        return binding.root
    }

    companion object {

    }
}