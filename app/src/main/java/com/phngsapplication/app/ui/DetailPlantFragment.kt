package com.phngsapplication.app.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.phngsapplication.app.R
import com.phngsapplication.app.databinding.FragmentDetailPlantBinding
import com.phngsapplication.app.model.Plant
import org.koin.android.ext.android.bind

class DetailPlantFragment : Fragment() {

    private lateinit var binding: FragmentDetailPlantBinding
    private lateinit var mainActivity: MainActivity

    val args: DetailPlantFragmentArgs by navArgs()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mainActivity = getActivity() as MainActivity
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail_plant, container, false)

        val Plant = args.plant
        if(Plant != null){

            val drawableResourceId1 = this.resources.getIdentifier(Plant.imagePlant,
                "drawable",
                mainActivity.packageName)


            Glide.with(this)
                .load(drawableResourceId1)
                .into(binding.image)

//                val drawableResourceId2 = this.resources.getIdentifier(Plant.imagePlant,
//                    "drawable",
//                    mainActivity.packageName)
//
//
//                Glide.with(this)
//                    .load(drawableResourceId2)
//                    .into(binding.like)

            binding.txtPlant.setText(Plant.txtPlant)
            binding.txtKINGDOM.setText(Plant.txtKINGDOM)
            binding.txtFAMILY.setText(Plant.txtFAMILY)
            binding.txtDescription.setText(Plant.txtDescription)

        }

        binding.btnBack.setOnClickListener{
            val controller = findNavController()
            controller.navigate(R.id.action_detailPlantFragment_to_listPlantFragment)
        }
        return binding.root
    }

    companion object {

    }
}