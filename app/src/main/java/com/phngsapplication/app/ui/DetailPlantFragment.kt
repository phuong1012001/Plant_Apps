package com.phngsapplication.app.ui

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
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.phngsapplication.app.R
import com.phngsapplication.app.databinding.FragmentDetailPlantBinding
import com.phngsapplication.app.model.Plant
import org.koin.android.ext.android.bind

class DetailPlantFragment : Fragment() {

    private lateinit var binding: FragmentDetailPlantBinding
    private lateinit var mainActivity: MainActivity

    private lateinit var firebaseAuth: FirebaseAuth

    private var like: Boolean = false
    var like1:String = ""
    private var db = Firebase.firestore

    val args: DetailPlantFragmentArgs by navArgs()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        firebaseAuth = FirebaseAuth.getInstance()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mainActivity = getActivity() as MainActivity
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail_plant, container, false)

        val Plant = args.plant
        if(Plant != null){
            Log.d("AAAAAAAA", Plant.toString())
//            val drawableResourceId1 = this.resources.getIdentifier(Plant.imagePlant,
//                "drawable",
//                mainActivity.packageName)

            Glide.with(this)
                .load(Plant.imagePlant)
                .into(binding.image)

//                val drawableResourceId2 = this.resources.getIdentifier(Plant.imagePlant,
//                    "drawable",
//                    mainActivity.packageName)
//
//
//                Glide.with(this)
//                    .load(drawableResourceId2)
//                    .into(binding.like)

            binding.txtPlant.setText(Plant.txtPlant)
            binding.txtKINGDOM.setText(Plant.txtKINGDOM)
            binding.txtFAMILY.setText(Plant.txtFAMILY)
            binding.txtDescription.setText(Plant.txtDescription)
            binding.btnDanger.text = Plant.txtCharacterOne
            binding.btnDecoration.text = Plant.txtCharacterTwo

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
                                                Log.d("id", idPlant.toString())
                                                like1 = data.get("like").toString()
                                                if(idPlant.toString() == Plant.plantId && like1 == "true") {
                                                    Log.d("like1", like1)
                                                    binding.btnLike.setBackgroundResource(R.drawable.rectangle_bg_red_a200_radius_28_5)
                                                }
//                                                else if(idPlant.toString() == Plant.plantId && like1 == "true")
//                                                    binding.btnLike.setBackgroundResource(R.drawable.rectangle_bg_red_a200_radius_28_5)
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

        var button_background : Int = 0
        button_background = if(like1 == "true")
            2
        else 1


        binding.btnLike.setOnClickListener {
            if(button_background==2){
                Log.d("AAAA", "1")
                binding.btnLike.setBackgroundResource(R.drawable.rectangle_bg_white_a200_radius_28_5);
                button_background=1;
                updateUserLikePlantFireStore(Plant.plantId, "false")
            } else if(button_background==1){
                Log.d("AAAA", "2")
                binding.btnLike.setBackgroundResource(R.drawable.rectangle_bg_red_a200_radius_28_5);
                button_background=2;
                updateUserLikePlantFireStore(Plant.plantId, "true")
            }
        }

        binding.btnBack.setOnClickListener{
            val controller = findNavController()
            controller.navigate(R.id.action_detailPlantFragment_to_listPlantFragment)
        }
        return binding.root
    }

    private fun updateUserLikePlantFirebase(plantId: String, like: String) {
        val editMap = mapOf(
            "like" to like
        )
        val ref = FirebaseDatabase.getInstance().getReference("UserLikePlant")
        ref.child("${firebaseAuth.uid}$plantId").updateChildren(editMap)
    }

    private fun updateUserLikePlantFireStore(plantId: String, like: String) {
        val timestamp = System.currentTimeMillis()
        val userLikePlantMap = hashMapOf(
            "plantId" to plantId,
            "like" to like,
            "timestamp" to timestamp
        )

        val userId = FirebaseAuth.getInstance().currentUser!!.uid

        db.collection("User/$userId/UserLikePlant").document("$plantId").set(userLikePlantMap)
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

    private fun addUserLikePlantFirebase(plantId: String, toString: String) {
        // show progress
        //progressDialog.show()

        //timestamp
        val timestamp = System.currentTimeMillis()

        //setup data to add db
        val hashMap: HashMap<String, Any?> = HashMap()
        hashMap["id"] = "${firebaseAuth.uid}$plantId"
        hashMap["plantId"] = plantId
        hashMap["uid"] = "${firebaseAuth.uid}"
        hashMap["like"] = like
        hashMap["timestamp"] = timestamp

        //set data to db
        val ref = FirebaseDatabase.getInstance().getReference("UserLikePlant")
        ref.child("${firebaseAuth.uid}$plantId")
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
