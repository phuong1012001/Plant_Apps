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
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.phngsapplication.app.R
import com.phngsapplication.app.databinding.FragmentAddingNewPlant2Binding
import com.phngsapplication.app.model.Plant
import com.phngsapplication.app.model.Species
import com.phngsapplication.app.ui.CameraActivity.Companion.TAG
import org.koin.android.ext.android.bind
import java.io.File
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class AddingNewPlant2Fragment : Fragment() {

    private lateinit var binding: FragmentAddingNewPlant2Binding
    private lateinit var mainActivity: MainActivity

    private lateinit var firebaseAuth: FirebaseAuth

    private lateinit var progressDialog: ProgressDialog

    private lateinit var database: DatabaseReference
    private lateinit var nameSpecies: String
    //array list to hold species
    private lateinit var speciesArrList: ArrayList<Species>
    private var ImageUri: Uri? = null
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
        var uri: Uri? = null
        if (bundleReceive != null) {
            species = bundleReceive.get("Species") as String

            a = bundleReceive.get("Uri") as String
            Log.d("uri", a)

            uri = Uri.parse(a)

            Glide.with(this)
                .load(uri)
                .into(binding.imageNewPlant)
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

    private var name = ""
    private var plant = ""
    private var kingdom = ""
    private var family = ""
    private var description = ""
    private fun validateData(uri:Uri) {
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
            uploadImageToStorage(uri)
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


    private fun addPlantFirebase(uploadedImageUrl: String, timestamp: Long) {
        // show progress
        //progressDialog.show()

        //timestamp
//        val timestamp = System.currentTimeMillis()

        //setup data to add db
        val hashMap: HashMap<String, Any?> = HashMap()
        hashMap["id"] = "$timestamp"
        hashMap["name"] = "$name"
        hashMap["plant"] = "$plant"
        hashMap["kingdom"] = "$kingdom"
        hashMap["family"] = "$family"
        hashMap["description"] = "$description"
        hashMap["url"] = "$uploadedImageUrl"
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