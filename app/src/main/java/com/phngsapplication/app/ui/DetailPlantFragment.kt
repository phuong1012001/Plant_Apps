package com.phngsapplication.app.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.phngsapplication.app.R
import com.phngsapplication.app.databinding.FragmentDetailPlantBinding
import com.phngsapplication.app.model.Plant
import org.koin.android.ext.android.bind

class DetailPlantFragment : Fragment() {

    private lateinit var binding: FragmentDetailPlantBinding
    private lateinit var mainActivity: MainActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mainActivity = getActivity() as MainActivity
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail_plant, container, false)

        var bundleReceive: Bundle? = getArguments()
        if(bundleReceive != null){
            val Plant: Plant = bundleReceive.get("Plant") as Plant
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
        }
        return binding.root
    }

    companion object {

    }
}