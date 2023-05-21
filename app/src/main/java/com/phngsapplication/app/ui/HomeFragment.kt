package com.phngsapplication.app.ui

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.phngsapplication.app.R
import com.phngsapplication.app.adapter.PlantTypesAdapter
import com.phngsapplication.app.databinding.FragmentHomeBinding
import kotlin.random.Random

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var mainActivity: MainActivity
    private val PHOTO_REQUEST_CODE = Random.nextInt(0, 10000)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PHOTO_REQUEST_CODE && resultCode == Activity.RESULT_OK){
            val photoUri = data?.extras?.get(CameraConfiguration.IMAGE_URI) as Uri?
            photoUri?.let {
                val action = HomeFragmentDirections.actionHomeToAddPlant(photoUri.toString())
                val controller = findNavController()
                controller.navigate(action)
            }
        }

    }

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
            val controller = findNavController()
            controller.navigate(R.id.action_home_to_speciesFragment)
        }
        binding.bttonArticles.setOnClickListener {
            val controller = findNavController()
            controller.navigate(R.id.action_home_to_articlesFragment)
        }
        binding.buttonAddingNew.setOnClickListener {
//            val controller = findNavController()
//            controller.navigate(R.id.action_home_to_cameraActivity)

            val intent = Intent(activity, CameraActivity::class.java)
            startActivityForResult(intent, PHOTO_REQUEST_CODE)
        }
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

    private fun setUpSearchViewGroupTwentyOneListener(): Unit {
//        binding.searchViewGroupTwentyOne.setOnQueryTextListener(object :
//            SearchView.OnQueryTextListener {
//            override fun onQueryTextSubmit(p0 : String) : Boolean {
//                // Performs search when user hit
//                // the search button on the keyboard
//                return false
//            }
//            override fun onQueryTextChange(p0 : String) : Boolean {
//                // Start filtering the list as user
//                // start entering the characters
//                return false
//            }
//        })
    }

    companion object {
        const val TAG: String = "HOMEPAGE_ACTIVITY"
    }
}