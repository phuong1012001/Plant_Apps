package com.phngsapplication.app.ui

import android.app.ProgressDialog
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.net.toUri
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.phngsapplication.app.R
import com.phngsapplication.app.databinding.FragmentAddingNewPlant2Binding
import com.phngsapplication.app.model.Plant
import kotlinx.android.synthetic.main.fragment_adding_new_plant_2.*
import org.koin.android.ext.android.bind
import java.io.File

class AddingNewPlant2Fragment : Fragment() {

    private lateinit var binding: FragmentAddingNewPlant2Binding
    private lateinit var mainActivity: MainActivity

    private lateinit var firebaseAuth: FirebaseAuth

    private lateinit var progressDialog: ProgressDialog

    private lateinit var database: DatabaseReference
    private lateinit var nameSpecies: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        firebaseAuth = FirebaseAuth.getInstance()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mainActivity = getActivity() as MainActivity
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_adding_new_plant_2,
            container,
            false
        )
        var a: String? = null
        var species: String? = null
        var bundleReceive: Bundle? = getArguments()
        if (bundleReceive != null) {
            species = bundleReceive.get("Species") as String

            a = bundleReceive.get("Uri") as String
            Log.d("uri", a)

            var uri = Uri.parse(a)

            Glide.with(this)
                .load(uri)
                .into(binding.imageNewPlant)
//            nameSpecies = binding.namespecies.setText(species).toString()
        }

        binding.btnSubmit.setOnClickListener {
            nameSpecies = binding.namespecies.setText(species).toString()
            Log.d("BBB", nameSpecies)
            var name_plant = binding.name.text.toString()
            var kingdom = binding.kingdom.text.toString()
            var family = binding.family.text.toString()
            var decreption = binding.family.text.toString()
            if(name_plant.isNotEmpty() && kingdom.isNotEmpty() && family.isNotEmpty() && decreption.isNotEmpty() && a != null){
                //Them
                val plant =
                    Plant(a, nameSpecies, name_plant, kingdom, family, decreption, "DisLike")
                //Species
                validateData()
                mainActivity.goToHome()
            }else{
                Toast.makeText(mainActivity, "Not Valid !!", Toast.LENGTH_SHORT).show()
            }
        }

        return binding.root
    }

    private var name = ""
    private var plant = ""
    private var kingdom = ""
    private var family = ""
    private var description = ""
    private fun validateData() {
        // get data
        name = binding.namespecies.text.toString().trim()
        plant = binding.name.text.toString().trim()
        kingdom = binding.name.text.toString().trim()
        family = binding.name.text.toString().trim()
        description = binding.name.text.toString().trim()

        if(plant.isEmpty()){
            Toast.makeText(mainActivity, "Enter Plant...", Toast.LENGTH_SHORT).show()
        }
        else if(kingdom.isEmpty()){
            Toast.makeText(mainActivity, "Enter Kingdom...", Toast.LENGTH_SHORT).show()
        }
        else if(family.isEmpty()){
            Toast.makeText(mainActivity, "Enter Family...", Toast.LENGTH_SHORT).show()
        }
        else if(description.isEmpty()){
            Toast.makeText(mainActivity, "Enter Description...", Toast.LENGTH_SHORT).show()
        }
        else{
            addPlantFirebase()
        }
    }

    private fun addPlantFirebase() {
        // show progress
        //progressDialog.show()

        //timestamp
        val timestamp = System.currentTimeMillis()

        //setup data to add db
        val hashMap: HashMap<String, Any?> = HashMap()
        hashMap["id"] = "$timestamp"
        hashMap["name"] = name
        hashMap["plant"] = plant
        hashMap["kingdom"] = kingdom
        hashMap["family"] = family
        hashMap["description"] = description

        hashMap["timestamp"] = timestamp
        hashMap["uid"] = "${firebaseAuth.uid}"

        //set data to db
        val ref = FirebaseDatabase.getInstance().getReference("Species")
        ref.child(name)
            .setValue(hashMap)
            .addOnSuccessListener {
                //user info save, open user dashboard
                //progressDialog.dismiss()
//                binding.namespecies.text.clear()
                binding.name.text.clear()
                binding.kingdom.text.clear()
                binding.family.text.clear()
                binding.decreption.text.clear()
                Toast.makeText(mainActivity, "Add Successfully...", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener{e->
                //progressDialog.dismiss()
                Toast.makeText(mainActivity, "Failed to add due to ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    companion object {

    }




//            var name_species = binding.namespecies.text.toString()
//            var name_plant = binding.name.text.toString()
//            var kingdom = binding.kingdom.text.toString()
//            var family = binding.family.text.toString()
//            var decreption = binding.decreption.text.toString()
//            database = FirebaseDatabase.getInstance().getReference("Species")
//            if (name_plant.isNotEmpty() && kingdom.isNotEmpty() && family.isNotEmpty() && decreption.isNotEmpty() && a != null) {
//                //Them
//                val plant =
//                    Plant(a, name_species, name_plant, kingdom, family, decreption, "DisLike")
//                //Species
//                database.child(name_species).setValue(plant).addOnSuccessListener {
//                    binding.namespecies.text.clear()
//                    binding.name.text.clear()
//                    binding.kingdom.text.clear()
//                    binding.family.text.clear()
//                    binding.decreption.text.clear()
//                    Toast.makeText(mainActivity, "Add Successfully...", Toast.LENGTH_SHORT).show()
//                }.addOnFailureListener {
//                    Toast.makeText(mainActivity, "Failed", Toast.LENGTH_SHORT).show()
//                }
//                mainActivity.goToHome()
//            } else {
//                Toast.makeText(mainActivity, "Not Valid !!", Toast.LENGTH_SHORT).show()
//            }
//        }
//
//
//        return binding.root
//    }




}