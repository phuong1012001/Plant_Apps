package com.phngsapplication.app.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.phngsapplication.app.R
import com.phngsapplication.app.adapter.PlantAdapter
import com.phngsapplication.app.databinding.FragmentListPlantBinding
import com.phngsapplication.app.model.Species

class ListPlantFragment : Fragment() {

    private lateinit var binding: FragmentListPlantBinding
    private lateinit var mainActivity: MainActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        mainActivity = getActivity() as MainActivity
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_list_plant, container, false)

        var bundleReceive: Bundle? = getArguments()
        if(bundleReceive != null){
            val species: Species = bundleReceive.get("listPlant") as Species
            if(species != null){
                val adapter = species.plants?.let { PlantAdapter(it) }
                binding.recyclerListPlant.adapter = adapter
                if (adapter != null) {
                    adapter.onItemClick = {
                        mainActivity.goToDetailPlant(it)
                    }
                }
            }
        }

        binding.btnBack.setOnClickListener{
            getFragmentManager()?.popBackStack()
        }

        return binding.root
    }

    companion object {

    }
}