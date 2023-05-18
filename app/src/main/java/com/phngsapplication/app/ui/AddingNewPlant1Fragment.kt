package com.phngsapplication.app.ui

import android.app.ProgressDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.net.toUri
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.phngsapplication.app.R
import com.phngsapplication.app.databinding.FragmentAddingNewPlant1Binding
import java.io.File

class AddingNewPlant1Fragment : Fragment() {

    private lateinit var binding: FragmentAddingNewPlant1Binding
    private lateinit var mainActivity: MainActivity

    private lateinit var firebaseAuth: FirebaseAuth

    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        firebaseAuth = FirebaseAuth.getInstance()

        //init progress Dialog
//        progressDialog = ProgressDialog(mainActivity)
//        progressDialog.setTitle("Please wait !")
//        progressDialog.setCanceledOnTouchOutside(false)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mainActivity = getActivity() as MainActivity
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_adding_new_plant_1, container, false)
        var a: String? = null
        var bundleReceive: Bundle? = getArguments()
        if(bundleReceive != null){
            a = bundleReceive.get("Uri") as String
            Log.d("uri", a)

            var uri = a.toUri()

            Glide.with(this)
                .load(File(uri.getPath()))
                .into(binding.imageNewPlant)
        }
        binding.btnNext.setOnClickListener{
            var name = binding.name.text.toString()
            if(name.isNotEmpty() && a != null){
                validateData()
                mainActivity.goToAddingNewPlant2(a, name)
            }else{
                Toast.makeText(mainActivity, "Species not Valid !!", Toast.LENGTH_SHORT).show()
            }
        }
        return binding.root
    }


    private var species = ""
    private var plant = ""
    private var description = ""
    private fun validateData() {
        // get data
        species = binding.name.text.toString().trim()

        if(species.isEmpty()){
            Toast.makeText(mainActivity, "Enter Specie...", Toast.LENGTH_SHORT).show()
        }
        else{
            addSpeciesFirebase()
        }
    }

    private fun addSpeciesFirebase() {
        // show progress
        //progressDialog.show()

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