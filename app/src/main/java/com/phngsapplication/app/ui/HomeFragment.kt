package com.phngsapplication.app.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.phngsapplication.app.R
import com.phngsapplication.app.databinding.FragmentHomeBinding
import com.phngsapplication.app.modules.homepage.data.model.HomepageRowModel
import com.phngsapplication.app.adapter.PlantTypesAdapter
import com.phngsapplication.app.appcomponents.views.ImagePickerFragmentDialog
import com.phngsapplication.app.modules.camera.ui.CameraActivity
import com.phngsapplication.app.modules.profile.ui.ProfileActivity

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var mainActivity: MainActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        mainActivity = getActivity() as MainActivity
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)

        val data = ArrayList<HomepageRowModel>()
        for(i in 1..5){
            data.add(HomepageRowModel("a", "b"))
        }

        val plantTypesAdapter = PlantTypesAdapter(data)

        binding.recyclerPlantTypes.adapter = plantTypesAdapter
        plantTypesAdapter.setOnItemClickListener(
            object : PlantTypesAdapter.OnItemClickListener {
                override fun onItemClick(view: View, position: Int, item: HomepageRowModel) {
                    onClickRecyclerHomepage(view, position, item)
                }
            }
        )

        setUpClicks()

        return binding.root
    }

    fun setUpClicks(): Unit {
        binding.bttonSpecies.setOnClickListener {
            mainActivity.goToSpecies()
        }
        binding.bttonArticles.setOnClickListener {
            mainActivity.goToArticles()
        }
//        binding.buttonAddingNew.setOnClickListener {
//            val destIntent = CameraActivity.getIntent(this, null)
//            startActivity(destIntent)
//        }
    }

    fun onClickRecyclerHomepage(
        view: View,
        position: Int,
        item: HomepageRowModel
    ): Unit {
        when(view.id) {
            R.id.plantTypes ->  {
                mainActivity.goToDetailPlantTypes()
            }
        }
    }

    companion object {

    }
}