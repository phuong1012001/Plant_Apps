package com.phngsapplication.app.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.phngsapplication.app.R
import com.phngsapplication.app.adapter.PlantAdapter
import com.phngsapplication.app.databinding.FragmentSpeciesProfileBinding
import com.phngsapplication.app.model.Plant

class SpeciesProfileFragment : Fragment() {


    private lateinit var binding: FragmentSpeciesProfileBinding
    private lateinit var mainActivity: MainActivity

    val args: ListPlantFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        mainActivity = getActivity() as MainActivity
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_species_profile, container, false)

        val plant = ArrayList<Plant>()
        for(i in 10..20){
            plant.add(Plant("img_rectangle_3", "Item" + i, "KINGDOM" + i, "Family" + i, "Des" + i, "Like"))
        }

        //Load danh sach plant
//        val species = args.specie
        if(plant != null){
            val adapter = PlantAdapter(plant)
            binding.recyclerSpecies.adapter = adapter
        }

        return binding.root

//        //profileFragment = getFragmentM() as ProfileFragment
//
//        var bundleReceive: Bundle? = getArguments()
//        if(bundleReceive != null){
//            var txt: String = bundleReceive.get("A") as String
//            if(txt != null){
//                Log.d("SPEC", txt)
//            }
//        }
//
//
//
//        return inflater.inflate(R.layout.fragment_species_profile, container, false)
    }

    companion object {

    }
}