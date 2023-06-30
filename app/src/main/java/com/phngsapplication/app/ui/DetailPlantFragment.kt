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

class DetailPlantFragment : Fragment() {

    private lateinit var binding: FragmentDetailPlantBinding
    private lateinit var mainActivity: MainActivity

    private var db = Firebase.firestore
    private lateinit var firebaseAuth: FirebaseAuth

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

        //Xet bieu thuong LIKE_DISLIKE
        val like = args.like
        if(like == "like")
            binding.btnLike.setBackgroundResource(R.drawable.rectangle_bg_red_a200_radius_28_5)
        else
            binding.btnLike.setBackgroundResource(R.drawable.rectangle_bg_white_a200_radius_28_5);

        val Plant = args.plant
        if(Plant != null){
            Glide.with(this)
                .load(Plant.imagePlant)
                .into(binding.image)

            binding.txtPlant.setText(Plant.txtPlant)
            binding.txtKINGDOM.setText(Plant.txtKINGDOM)
            binding.txtFAMILY.setText(Plant.txtFAMILY)
            binding.txtDescription.setText(Plant.txtDescription)
            binding.btnDanger.text = Plant.txtCharacterOne
            binding.btnDecoration.text = Plant.txtCharacterTwo
        }

        var button_background : Int = 0
        button_background = if(like == "like")
            2
        else 1

        binding.btnLike.setOnClickListener {
            if(button_background==2){
                binding.btnLike.setBackgroundResource(R.drawable.rectangle_bg_white_a200_radius_28_5);
                button_background=1;
                updateUserLikePlantFireStore(Plant.plantId, "false")
            } else if(button_background==1){
                binding.btnLike.setBackgroundResource(R.drawable.rectangle_bg_red_a200_radius_28_5);
                button_background=2;
                updateUserLikePlantFireStore(Plant.plantId, "true")
            }
        }

        binding.toolbar.setNavigationOnClickListener{
            val action = DetailPlantFragmentDirections.actionDetailPlantFragmentToListPlantFragment(Plant.speciesName, Plant.speciesId)
            val controller = findNavController()
            controller.navigate(action)
        }
        return binding.root
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
                Toast.makeText(mainActivity, "Add FireStore Successfully...", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener{e->
                Toast.makeText(mainActivity, "Failed to add FireStore due to ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    companion object {

    }
}
