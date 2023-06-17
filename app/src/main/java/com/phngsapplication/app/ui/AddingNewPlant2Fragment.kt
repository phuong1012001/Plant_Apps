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
import androidx.appcompat.app.AlertDialog
import androidx.core.net.toUri
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.phngsapplication.app.R
import com.phngsapplication.app.databinding.FragmentAddingNewPlant2Binding
import com.phngsapplication.app.model.Plant
import com.phngsapplication.app.model.Species
import java.io.File

class AddingNewPlant2Fragment : Fragment() {

    private lateinit var binding: FragmentAddingNewPlant2Binding
    private lateinit var mainActivity: MainActivity

    private lateinit var firebaseAuth: FirebaseAuth

    private lateinit var progressDialog: ProgressDialog

    private var db = Firebase.firestore

    //array list to hold species
    private lateinit var speciesArrList: ArrayList<Species>

    private var selectSpeciesTitle = ""
    private var selectSpeciesId = ""
    private var selectUidOfSpecies = ""
    private var plant = ""
    private var kingdom = ""
    private var family = ""
    private var description = ""
    private var species = ""
    private var characteristicOne = ""
    private var characteristicTwo= ""

    val args: AddingNewPlant2FragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        firebaseAuth = FirebaseAuth.getInstance()

        loadSpeciesFromFireStore()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mainActivity = getActivity() as MainActivity
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_adding_new_plant_2, container, false)
        var a = args.uri
        var uri: Uri? = null

        if(a != null){
//            var uri = a.toUri()
            uri = Uri.parse(a)

//            Glide.with(this)
//                .load(File(uri.getPath()))
//                .into(binding.imageNewPlant)
            Glide.with(this)
                .load(File(uri.getPath()))
                .into(binding.imageNewPlant)
        }
        binding.speciesTV.setOnClickListener {
            speciesPickDialog()
        }

        binding.speciesTV.setOnClickListener {
            speciesPickDialog()
        }

        binding.btnSubmit.setOnClickListener{
            var name_plant = binding.name.text.toString()
            var kingdom = binding.kingdom.text.toString()
            var family = binding.family.text.toString()
            var decreption = binding.family.text.toString()
            if(name_plant.isNotEmpty() && kingdom.isNotEmpty() && family.isNotEmpty() && decreption.isNotEmpty() && a != null){
                if(uri != null){
                    validateData(uri)
                }
                val controller = findNavController()
                controller.navigate(R.id.action_addingNewPlant2Fragment_to_homeFragment2)
            }else{
                Toast.makeText(mainActivity, "Not Valid !!", Toast.LENGTH_SHORT).show()
            }
        }

        return binding.root
    }

    private fun loadSpeciesFromFireStore() {
        //init array list
        speciesArrList = ArrayList()
        db = FirebaseFirestore.getInstance()

//        db.collection("Species").get().addOnSuccessListener {  }
//            .addOnSuccessListener {
//                if(!it.isEmpty){
//                    speciesArrList.clear()
//                    for(data in it.documents){
//                        val species = data.get("species")
//                        val id = data.get("id")
//                        Log.d("Name get data: ", species.toString())
//                        speciesArrList.add(Species(id.toString(), species.toString(), null))
//                    }
//                }
//            }
        db.collection("User").get().addOnSuccessListener {  }
            .addOnSuccessListener {
                if(!it.isEmpty){
                    speciesArrList.clear()
                    for(data in it.documents){
                        val uid = data.get("id")
                        db.collection("User/$uid/Species").get().addOnSuccessListener {  }
                            .addOnSuccessListener {it1->
                                if(!it1.isEmpty){
                                    //data1.clear()
                                    for(data in it1.documents){
                                        val species = data.get("species")
                                        val id = data.get("id")

                                        speciesArrList.add(Species(id.toString(), species.toString(), uid.toString(), null))
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
                    val id = ds.child("id").value
                    Log.d("Name get data: ", species.toString())
                    speciesArrList.add(Species(id.toString(), species.toString(), "1" , null))
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
                selectSpeciesId = speciesArrList[which].id
                selectUidOfSpecies = speciesArrList[which].uid
                // set species to textview
                binding.speciesTV.text = selectSpeciesTitle
                Log.d("TAG", "Select Species: $selectSpeciesTitle")
            }
            .show()

    }

    private fun validateData(uri: Uri) {
        // get data
        species = binding.speciesTV.text.toString().trim()
        plant = binding.name.text.toString().trim()
        kingdom = binding.kingdom.text.toString().trim()
        family = binding.family.text.toString().trim()
        description = binding.decreption.text.toString().trim()
        characteristicOne = "CHARACTER 1"
        characteristicTwo = "CHARACTER 2"

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
            uploadImageToStorage(uri)
            //addPlantFirebase()
        }
    }

    // Upload image to Firebase Storage and add URL to Realtime Database
    private fun uploadImageToStorage(uri: Uri) {
        Log.d(CameraActivity.TAG, "uploadImageToStorage: uploading to storage ...")
        val timestamp = System.currentTimeMillis()
        val filePathAndName = "Species/$timestamp"
        val storageReference = FirebaseStorage.getInstance().getReference(filePathAndName)

        storageReference.putFile(uri)
            .addOnSuccessListener { taskSnapshot ->
                storageReference.downloadUrl
                    .addOnSuccessListener { uri ->
                        val uploadedImageUrl = uri.toString()
                        Log.e("Hinh anh load: ", uploadedImageUrl)
                        //addPlantFirebase(uploadedImageUrl, timestamp)
                        addPlantFireStore(uploadedImageUrl, timestamp)
                    }
                    .addOnFailureListener { e ->
                        Log.d(CameraActivity.TAG, "uploadImageToStorage: failed to get download URL due to ${e.message}")
                        Toast.makeText(mainActivity, "Failed to get download URL", Toast.LENGTH_SHORT).show()
                    }
            }
            .addOnFailureListener { e ->
                Log.d(CameraActivity.TAG, "uploadImageToStorage: failed to upload due to ${e.message}")
                Toast.makeText(mainActivity, "Failed to upload due to ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun addPlantFireStore(uploadedImageUrl: String, timestamp: Long) {
        val plantMap = hashMapOf(
            "id" to "$timestamp",
//            "species" to species,
//            "species" to "$species",
            "plant" to "$plant",
            "kingdom" to "$kingdom",
            "family" to "$family",
            "description" to "$description",
            "characterOne" to "$characteristicOne",
            "characterTwo" to "$characteristicTwo",
            "URL" to "$uploadedImageUrl",
            "timestamp" to timestamp,
            //"uid" to "${firebaseAuth.uid}"
        )

        val userId = FirebaseAuth.getInstance().currentUser!!.uid

        db.collection("User/$selectUidOfSpecies/Species/$selectSpeciesId/Plants").document("$timestamp").set(plantMap)
            .addOnSuccessListener {
                //user info save, open user dashboard
                //progressDialog.dismiss()
                Toast.makeText(mainActivity, "Add FireStore Successfully...", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener{e->
                //progressDialog.dismiss()
                Toast.makeText(mainActivity, "Failed to add FireStore due to ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    companion object {

    }
}