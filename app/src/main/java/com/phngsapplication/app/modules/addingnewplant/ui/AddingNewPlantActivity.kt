package com.phngsapplication.app.modules.addingnewplant.ui

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.phngsapplication.app.R
import com.phngsapplication.app.appcomponents.base.BaseActivity
import com.phngsapplication.app.databinding.ActivityAddingNewPlantBinding
import com.phngsapplication.app.modules.addingnewplant.`data`.viewmodel.AddingNewPlantVM
import kotlin.String
import kotlin.Unit

class AddingNewPlantActivity :
    BaseActivity<ActivityAddingNewPlantBinding>(R.layout.activity_adding_new_plant) {
  private val viewModel: AddingNewPlantVM by viewModels<AddingNewPlantVM>()

  private lateinit var firebaseAuth: FirebaseAuth

  private lateinit var progressDialog: ProgressDialog

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    firebaseAuth = FirebaseAuth.getInstance()

    //init progress Dialog
    progressDialog = ProgressDialog(this)
    progressDialog.setTitle("Please wait !")
    progressDialog.setCanceledOnTouchOutside(false)

  }

  override fun onInitialized(): Unit {
    viewModel.navArguments = intent.extras?.getBundle("bundle")
    binding.addingNewPlantVM = viewModel
  }

  override fun setUpClicks(): Unit {
    binding.imageArrowleft.setOnClickListener {
      finish()
    }

    binding.imagePlus.setOnClickListener {
      validateData()
    }
  }

  private var species = ""
  private fun validateData() {
    // get data
    species = "Anh Túc"

    if(species.isEmpty()){
      Toast.makeText(this, "Enter Specie...", Toast.LENGTH_SHORT).show()
    }
    else{
      addSpeciesFirebase()
    }
  }

  private fun addSpeciesFirebase() {
    // show progress
    progressDialog.show()

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
        progressDialog.dismiss()
        Toast.makeText(this, "Add Successfully...", Toast.LENGTH_SHORT).show()
      }
      .addOnFailureListener{e->
        progressDialog.dismiss()
        Toast.makeText(this, "Failed to add due to ${e.message}", Toast.LENGTH_SHORT).show()
      }
  }

  companion object {
    const val TAG: String = "ADDING_NEW_PLANT_ACTIVITY"


    fun getIntent(context: Context, bundle: Bundle?): Intent {
      val destIntent = Intent(context, AddingNewPlantActivity::class.java)
      destIntent.putExtra("bundle", bundle)
      return destIntent
    }
  }
}
