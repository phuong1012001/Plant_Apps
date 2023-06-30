package com.phngsapplication.app.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.phngsapplication.app.R
import com.phngsapplication.app.databinding.FragmentAddingNewPlant1Binding

class AddingNewPlant1Fragment : Fragment() {

    private lateinit var binding: FragmentAddingNewPlant1Binding
    private lateinit var mainActivity: MainActivity

    private lateinit var firebaseAuth: FirebaseAuth

    private var db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        firebaseAuth = FirebaseAuth.getInstance()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mainActivity = getActivity() as MainActivity
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_adding_new_plant_1, container, false)

        binding.btnNext.setOnClickListener{
            var name = binding.name.text.toString()
            if(name.isNotEmpty()){
                validateData()
            }else{
                Toast.makeText(mainActivity, "Species not Valid !!", Toast.LENGTH_SHORT).show()
            }
        }

        binding.toolbar.setNavigationOnClickListener{
            val controller = findNavController()
            controller.navigate(R.id.action_fab_to_homeFragment)
        }
        return binding.root
    }

    private var species = ""

    private fun validateData() {
        // get data
        species = binding.name.text.toString().trim()

        if(species.isEmpty()){
            Toast.makeText(mainActivity, "Enter Specie...", Toast.LENGTH_SHORT).show()
        }
        else{
            addSpeciesFirebase()
            addSpeciesFireStore()
        }
    }

    private fun addSpeciesFireStore() {
        val timestamp = System.currentTimeMillis()
        val speciesMap = hashMapOf(
            "id" to "$timestamp",
            "species" to species,
            "timestamp" to timestamp,
            "uid" to "${firebaseAuth.uid}"
        )

        val userId = FirebaseAuth.getInstance().currentUser!!.uid

        db.collection("User/$userId/Species").document("$timestamp").set(speciesMap)
            .addOnSuccessListener {
                Toast.makeText(mainActivity, "Add FireStore Successfully...", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener{e->
                Toast.makeText(mainActivity, "Failed to add FireStore due to ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun addSpeciesFirebase() {
        //timestamp
        val timestamp = System.currentTimeMillis()

        //setup data to add db
        val hashMap: HashMap<String, Any?> = HashMap()
        hashMap["id"] = "$timestamp"
        hashMap["species"] = species
        hashMap["timestamp"] = timestamp
        hashMap["uid"] = "${firebaseAuth.uid}"

        //set data to db
        val ref = FirebaseDatabase.getInstance().getReference("Species")
        ref.child("$timestamp")
            .setValue(hashMap)
            .addOnSuccessListener {
                Toast.makeText(mainActivity, "Add Successfully...", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener{e->
                Toast.makeText(mainActivity, "Failed to add due to ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    companion object {
    }
}