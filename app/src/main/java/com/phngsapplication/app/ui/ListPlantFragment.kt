package com.phngsapplication.app.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.phngsapplication.app.R
import com.phngsapplication.app.adapter.PlantAdapter
import com.phngsapplication.app.databinding.FragmentListPlantBinding
import com.phngsapplication.app.model.Species

class ListPlantFragment : Fragment() {

    private lateinit var binding: FragmentListPlantBinding
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
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_list_plant, container, false)

        //Load danh sach plant
        val species = args.specie
        if(species != null){
            val adapter = PlantAdapter(species.plants)
            binding.recyclerListPlant.adapter = adapter

            //Thao tac chon de xem chi tiet ve cay
            adapter.onItemClick = {
                val action = ListPlantFragmentDirections.actionListPlantFragmentToDetailPlantFragment(it)
                val controller = findNavController()
                controller.navigate(action)
            }
        }

        //Thao tac voi button
        binding.btnBack.setOnClickListener{
            val controller = findNavController()
            controller.navigate(R.id.action_listPlantFragment_to_speciesFragment)
        }

        return binding.root
    }

    companion object {

    }
}