package com.phngsapplication.app.ui

import android.app.ProgressDialog
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.net.toUri
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.phngsapplication.app.R
import com.phngsapplication.app.databinding.FragmentAddingNewPlant2Binding
import com.phngsapplication.app.model.Plant
import com.phngsapplication.app.model.Species
import com.phngsapplication.app.ui.CameraActivity.Companion.TAG
import java.io.File

class AddingNewPlant2Fragment : Fragment() {

    private lateinit var binding: FragmentAddingNewPlant2Binding
    private lateinit var mainActivity: MainActivity

    private lateinit var firebaseAuth: FirebaseAuth

    private lateinit var progressDialog: ProgressDialog

    //array list to hold species
    private lateinit var speciesArrList: ArrayList<Species>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        firebaseAuth = FirebaseAuth.getInstance()

        loadSpecies()



    }

    private var selectSpeciesTitle = ""
    private var selectSpeciesId = ""
    private fun loadSpecies() {
        //init array list
        speciesArrList = ArrayList()

        //db reference to load species DF > Species
        val ref = FirebaseDatabase.getInstance().getReference("Species")
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                Log.d("TAG", "Start get data")
                speciesArrList.clear()
                for(ds in snapshot.children){
                    val species = ds.child("species").value
                    Log.d("Name get data: ", species.toString())
                    speciesArrList.add(Species(species.toString(), null))
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

    private fun speciesPickDialog() {
        Log.d("TAG", "Show pick Species")

        //get string array of species from arraylist
        val speciesArr = arrayOfNulls<String>(speciesArrList.size)
        for (i in speciesArrList.indices){
            speciesArr[i] = speciesArrList[i].nameSpecies
        }

        //alert dialog
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Pick Species")
            .setItems(speciesArr){dialog, which ->
                //handle item click
                // get clicked item
                selectSpeciesTitle = speciesArrList[which].nameSpecies

                // set species to textview
                binding.speciesTV.text = selectSpeciesTitle
                Log.d("TAG", "Select Species: $selectSpeciesTitle")
            }
            .show()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mainActivity = getActivity() as MainActivity
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_adding_new_plant_2, container, false)
        var a: String? = null
        var species: String? = null
        var bundleReceive: Bundle? = getArguments()
        //MT
        var uri: Uri? = null
        if(bundleReceive != null){
            species = bundleReceive.get("Species") as String

            a = bundleReceive.get("Uri") as String
            Log.d("uri", a)
            //MT
//            uri = a.toUri()
            uri = Uri.parse(a)

            Glide.with(this)
//                .load(File(uri.getPath()))
                    //MT
                .load(uri)
                .into(binding.imageNewPlant)
        }

        //handle click pick Species
        binding.speciesTV.setOnClickListener {
            speciesPickDialog()
        }

        binding.btnSubmit.setOnClickListener{
            var namePlant = binding.name.text.toString()
            var kingdom = binding.kingdom.text.toString()
            var family = binding.family.text.toString()
            var decreption = binding.decreption.text.toString()
            if(namePlant.isNotEmpty() && kingdom.isNotEmpty() && family.isNotEmpty() && decreption.isNotEmpty() && a != null){
                //Them
                var plant:Plant = Plant(a, namePlant, kingdom, family, decreption, "DisLike")
                //Species
                if (uri != null) {
                    validateData(uri)
                }
                mainActivity.goToHome()
            }else{
                Toast.makeText(mainActivity, "Not Valid !!", Toast.LENGTH_SHORT).show()
            }
        }

        return binding.root
    }

    private var plant = ""
    private var kingdom = ""
    private var family = ""
    private var description = ""
    private var species = ""

    private fun validateData(uri:Uri) {
        // get data
        species = binding.speciesTV.text.toString().trim()
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
        else if(species.isEmpty()){
            Toast.makeText(mainActivity, "Enter Pick Species...", Toast.LENGTH_SHORT).show()
        }
        else{
            //MT
            uploadImageToStorage(uri)
//            addPlantFirebase()
        }
    }

    // Upload image to Firebase Storage and add URL to Realtime Database
    private fun uploadImageToStorage(uri: Uri) {
        Log.d(TAG, "uploadImageToStorage: uploading to storage ...")
        val timestamp = System.currentTimeMillis()
        val filePathAndName = "Species/$timestamp"
        val storageReference = FirebaseStorage.getInstance().getReference(filePathAndName)

        storageReference.putFile(uri)
            .addOnSuccessListener { taskSnapshot ->
                storageReference.downloadUrl
                    .addOnSuccessListener { uri ->
                        val uploadedImageUrl = uri.toString()
                        Log.e("Hinh anh load: ", uploadedImageUrl)
                        addPlantFirebase(uploadedImageUrl, timestamp)
                    }
                    .addOnFailureListener { e ->
                        Log.d(TAG, "uploadImageToStorage: failed to get download URL due to ${e.message}")
                        Toast.makeText(mainActivity, "Failed to get download URL", Toast.LENGTH_SHORT).show()
                    }
            }
            .addOnFailureListener { e ->
                Log.d(TAG, "uploadImageToStorage: failed to upload due to ${e.message}")
                Toast.makeText(mainActivity, "Failed to upload due to ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun addPlantFirebase(uploadedImageUrl:String, timestamp:Long) {
        // show progress
        //progressDialog.show()

        //timestamp
//        val timestamp = System.currentTimeMillis()

        //setup data to add db
        val hashMap: HashMap<String, Any?> = HashMap()
        hashMap["id"] = "$timestamp"
        hashMap["species"] = "$species"
        hashMap["plant"] = "$plant"
        hashMap["kingdom"] = "$kingdom"
        hashMap["family"] = "$family"
        hashMap["description"] = "$description"
        hashMap["URL"] = "$uploadedImageUrl"

        hashMap["timestamp"] = timestamp
        hashMap["uid"] = "${firebaseAuth.uid}"

        //set data to db
        val ref = FirebaseDatabase.getInstance().getReference("Plants")
        ref.child("$timestamp")
            .setValue(hashMap)
            .addOnSuccessListener {
                //user info save, open user dashboard
                //progressDialog.dismiss()
                Toast.makeText(mainActivity, "Add Successfully...", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener{e->
                //progressDialog.dismiss()
                Toast.makeText(mainActivity, "Failed to add due to ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    companion object {

    }
}