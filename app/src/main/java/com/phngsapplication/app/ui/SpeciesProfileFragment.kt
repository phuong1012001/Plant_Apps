package com.phngsapplication.app.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.phngsapplication.app.R
import com.phngsapplication.app.adapter.PlantAdapter
import com.phngsapplication.app.databinding.FragmentSpeciesProfileBinding
import com.phngsapplication.app.model.Plant

class SpeciesProfileFragment : Fragment() {

    private lateinit var binding: FragmentSpeciesProfileBinding
    private lateinit var mainActivity: MainActivity

    private lateinit var firebaseAuth: FirebaseAuth
    private var db = Firebase.firestore

    private lateinit var plantId : ArrayList<String>
    var plant = ArrayList<Plant>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        firebaseAuth = FirebaseAuth.getInstance()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mainActivity = getActivity() as MainActivity
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_species_profile, container, false)

        plant = ArrayList()
        plantId = ArrayList()

        db.collection("User").get().addOnSuccessListener {  }
            .addOnSuccessListener {
                if(!it.isEmpty){
                    for(data in it.documents){
                        val uid = data.get("id")
                        if(uid.toString() == firebaseAuth.uid.toString()) {
                            db.collection("User/$uid/UserLikePlant").get().addOnSuccessListener { }
                                .addOnSuccessListener { it1 ->
                                    if (!it1.isEmpty) {
                                        for (data in it1.documents) {
                                            val idPlant = data.get("plantId")
                                            val like = data.get("like")
                                            if(like.toString() == "true") {
                                                plantId.add(idPlant.toString())
                                                Log.d("plant id",idPlant.toString())
                                            }
//
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

        loadPlantFromFireStore()

        return binding.root
    }

    private fun loadPlantFromFireStore() {
        db = FirebaseFirestore.getInstance()

        db.collection("User").get().addOnSuccessListener {  }
            .addOnSuccessListener { it3 ->
                if (!it3.isEmpty) {
                    plant.clear()
                    for (data2 in it3.documents) {
                        val uid = data2.get("id")
                        db.collection("User/$uid/Species").get().addOnSuccessListener { }
                            .addOnSuccessListener { it1 ->
                                if (!it1.isEmpty) {
                                    //data1.clear()
                                    for (data3 in it1.documents) {
                                        val species = data3.get("species").toString()
                                        val id = data3.get("id").toString()
                                        db.collection("User/$uid/Species/$id/Plants")
                                            .get().addOnSuccessListener { }
                                            .addOnSuccessListener { it2 ->
                                                if (!it2.isEmpty) {
                                                    //data1.clear()
                                                    for (data1 in it2.documents) {

                                                        val plantName = data1.get("plant")
                                                        val idPlant = data1.get("id")
                                                        //val speciesId = data1.get("speciesId")
                                                        //val imagePlant = "img_rectangle_3"
                                                        val imagePlant = data1.get("URL")
                                                        val kingdom = data1.get("kingdom")
                                                        val family = data1.get("family")
                                                        val description =
                                                            data1.get("description")
                                                        val characterOne =
                                                            data1.get("characterOne")
                                                        val characterTwo =
                                                            data1.get("characterTwo")
                                                        val imageLike = "Like"
                                                        for (ds1 in plantId) {
                                                            if (idPlant.toString() == ds1) {
                                                                plant.add(
                                                                    Plant(
                                                                        idPlant.toString(),
                                                                        id,
                                                                        species,
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
                                                        }
                                                        if (plant != null) {
                                                            val adapter =
                                                                plant?.let { PlantAdapter(it) }
                                                            binding.recyclerSpecies.adapter =
                                                                adapter
                                                            if (adapter != null) {
                                                                adapter.onItemClick = {
//                                                                    val action = ProfileFragmentDirections.actionProfileToDetailPlantFragment(it, "like")
//                                                                    val controller = findNavController()
//                                                                    controller.navigate(action)
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