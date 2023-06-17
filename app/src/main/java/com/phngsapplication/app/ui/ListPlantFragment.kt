package com.phngsapplication.app.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.phngsapplication.app.R
import com.phngsapplication.app.adapter.PlantAdapter
import com.phngsapplication.app.databinding.FragmentListPlantBinding
import com.phngsapplication.app.model.Plant
import com.phngsapplication.app.model.Species

class ListPlantFragment : Fragment() {

    private lateinit var binding: FragmentListPlantBinding
    private lateinit var mainActivity: MainActivity

    var data = ArrayList<Plant>()
    private var db = Firebase.firestore

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
        val species = args.species
        val idSpecies = args.idSpecies
        Log.d("AAA", species)
        data = ArrayList()
        loadPlantFromFireStore(idSpecies, species)

        //Thao tac voi button
        binding.btnBack.setOnClickListener{
            val controller = findNavController()
            controller.navigate(R.id.action_listPlantFragment_to_speciesFragment)
        }

        return binding.root
    }

    @SuppressLint("SuspiciousIndentation")
    private fun loadPlantFromFireStore(idSpecies: String, speciesDaTa: String) {
        //init array list
        data = ArrayList()
        db = FirebaseFirestore.getInstance()

        binding.txtPlant.setText(speciesDaTa)

        db.collection("User").get().addOnSuccessListener {  }
            .addOnSuccessListener {it3->
                if(!it3.isEmpty){
                    data.clear()
                    for(data2 in it3.documents){
                        val uid = data2.get("id")
                        db.collection("User/$uid/Species").get().addOnSuccessListener {  }
                            .addOnSuccessListener {it1->
                                if(!it1.isEmpty){
                                    //data1.clear()
                                    for(data3 in it1.documents){
                                        val species = data3.get("species")
                                        val id = data3.get("id")
                                        if(id.toString() == idSpecies){
                                            db.collection("User/$uid/Species/${id.toString()}/Plants").get().addOnSuccessListener {  }
                                                .addOnSuccessListener {it2->
                                                    if(!it2.isEmpty){
                                                        //data1.clear()
                                                        for(data1 in it2.documents){

                                                            val plantName = data1.get("plant")
                                                            val plantId = data1.get("id")
                                                            //val speciesId = data1.get("speciesId")
                                                            //val imagePlant = "img_rectangle_3"
                                                            val imagePlant = data1.get("URL")
                                                            val kingdom = data1.get("kingdom")
                                                            val family = data1.get("family")
                                                            val description = data1.get("description")
                                                            val characterOne = data1.get("characterOne")
                                                            val characterTwo = data1.get("characterTwo")
                                                            val imageLike = "Like"
                                                            data.add(
                                                                Plant(
                                                                    plantId.toString(),
                                                                    idSpecies,
                                                                    imagePlant.toString(),
                                                                    plantName.toString(),
                                                                    kingdom.toString(),
                                                                    family.toString(),
                                                                    description.toString(),
                                                                    characterOne.toString(),
                                                                    characterTwo.toString(),
                                                                    imageLike
                                                                )
                                                            )
                                                        }
                                                        if(speciesDaTa != null){
                                                            val adapter = data?.let { PlantAdapter(it) }
                                                            binding.recyclerListPlant.adapter = adapter
                                                            if (adapter != null) {
                                                                adapter.onItemClick = {
                                                                    val action = ListPlantFragmentDirections.actionListPlantFragmentToDetailPlantFragment(it)
                                                                    val controller = findNavController()
                                                                    controller.navigate(action)
                                                                }
                                                            }
                                                        }

                                                    }
                                                }
                                        }
                                    }
                                }
                            }
                    }
                }
            }
            .addOnFailureListener {e->
                Toast.makeText(mainActivity, "Failed to load FireStore due to ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    companion object {

    }
}