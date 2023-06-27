package com.phngsapplication.app.ui

import android.annotation.SuppressLint
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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.phngsapplication.app.R
import com.phngsapplication.app.adapter.PlantAdapter
import com.phngsapplication.app.databinding.FragmentListPlantBinding
import com.phngsapplication.app.model.Plant
import com.phngsapplication.app.model.Species
import java.util.*
import kotlin.collections.ArrayList

class ListPlantFragment : Fragment() {

    private lateinit var binding: FragmentListPlantBinding
    private lateinit var mainActivity: MainActivity

    var data = ArrayList<Plant>()
    var data1 = ArrayList<Species>()
    private lateinit var plantId : ArrayList<String>

    private var db = Firebase.firestore
    private lateinit var firebaseAuth: FirebaseAuth

    private lateinit var searchView: androidx.appcompat.widget.SearchView

    val args: ListPlantFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        firebaseAuth = FirebaseAuth.getInstance()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        mainActivity = getActivity() as MainActivity
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_list_plant, container, false)

        //Load danh sach plant
        val nameSpecies = args.species
        val idSpecies = args.idSpecies

        data = ArrayList()
        plantId = ArrayList()

        loadPlantFromFireStore(idSpecies, nameSpecies)
        loadLikeFromFireStore()
        loadSpeciesFromFireStore()

        searchView = binding.searchForCactus

        searchView.setOnQueryTextListener(object :androidx.appcompat.widget.SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                Log.d("text new", newText.toString())
                filterList(newText)
                return true
            }

        })

        //Thao tac voi button
        binding.toolbar.setNavigationOnClickListener{
            var speciesArr : Array<Species> = data1.toTypedArray() //Danh sach loai
            val action = ListPlantFragmentDirections.actionListPlantFragmentToSpeciesFragment(speciesArr)
            val controller = findNavController()
            controller.navigate(action)
        }

        return binding.root
    }

    private fun filterList(query: String?) {
        if(query != null){
            var ftList = ArrayList<Plant>()
            ftList = ArrayList()
            for (i in data){
                //Log.d("filter", i.nameSpecies)
                if(i.txtPlant.lowercase(Locale.ROOT).contains(query)){
                    Log.d("filter 2", i.txtPlant)
                    ftList.add(i)
                }
            }
            if(ftList.isEmpty()){
                val adapter = ftList?.let { PlantAdapter(it) }
                binding.recyclerListPlant.adapter = adapter
                Toast.makeText(requireContext(), "No data found", Toast.LENGTH_SHORT).show()
            }else{
                val adapter = ftList?.let { PlantAdapter(it) }
                binding.recyclerListPlant.adapter = adapter
                if (adapter != null) {
                    var like : String = "dislike"
                    adapter.onItemClick = {
                        for(ds in plantId){
                            if(ds == it.plantId){
                                like = "like"
                            }
                        }
                        val action = ListPlantFragmentDirections.actionListPlantFragmentToDetailPlantFragment(it, like)
                        val controller = findNavController()
                        controller.navigate(action)
                    }
                }
            }
        }
    }

    @SuppressLint("SuspiciousIndentation")
    private fun loadLikeFromFireStore() {
        db = FirebaseFirestore.getInstance()
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

    private fun loadSpeciesFromFireStore() {
        db = FirebaseFirestore.getInstance()
        db.collection("User").get().addOnSuccessListener {  }
            .addOnSuccessListener {
                if(!it.isEmpty){
                    data1.clear()
                    for(data in it.documents){
                        val uid = data.get("id")
                        db.collection("User/$uid/Species").get().addOnSuccessListener {  }
                            .addOnSuccessListener {it1->
                                if(!it1.isEmpty){
                                    //data1.clear()
                                    for(data in it1.documents){
                                        val species = data.get("species")
                                        val id = data.get("id")

                                        data1.add(Species(id.toString(), species.toString(),"1", null))
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

    @SuppressLint("SuspiciousIndentation")
    private fun loadPlantFromFireStore(idSpecies: String, nameSpecies: String) {
        data = ArrayList()
        db = FirebaseFirestore.getInstance()

        binding.txtPlant.setText(nameSpecies)

        db.collection("User").get().addOnSuccessListener {  }
            .addOnSuccessListener {it3->
                if(!it3.isEmpty){
                    data.clear()
                    for(data2 in it3.documents){
                        val uid = data2.get("id")
                        db.collection("User/$uid/Species").get().addOnSuccessListener {  }
                            .addOnSuccessListener {it1->
                                if(!it1.isEmpty){
                                    //data1.clear()
                                    for(data3 in it1.documents){
                                        val species = data3.get("species")
                                        val id = data3.get("id")
                                        if(id.toString() == idSpecies){
                                            db.collection("User/$uid/Species/${id.toString()}/Plants").get().addOnSuccessListener {  }
                                                .addOnSuccessListener {it2->
                                                    if(!it2.isEmpty){
                                                        //data1.clear()
                                                        for(data1 in it2.documents){

                                                            val plantName = data1.get("plant")
                                                            val plantId = data1.get("id")
                                                            //val speciesId = data1.get("speciesId")
                                                            //val imagePlant = "img_rectangle_3"
                                                            val imagePlant = data1.get("URL")
                                                            val kingdom = data1.get("kingdom")
                                                            val family = data1.get("family")
                                                            val description = data1.get("description")
                                                            val characterOne = data1.get("characterOne")
                                                            val characterTwo = data1.get("characterTwo")
                                                            val imageLike = "Like"
                                                            data.add(
                                                                Plant(
                                                                    plantId.toString(),
                                                                    idSpecies,
                                                                    nameSpecies,
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
                                                        if(data != null){
                                                            val adapter = data?.let { PlantAdapter(it) }
                                                            binding.recyclerListPlant.adapter = adapter
                                                            if (adapter != null) {
                                                                var like : String = "dislike"
                                                                adapter.onItemClick = {
                                                                    for(ds in plantId){
                                                                        if(ds == it.plantId){
                                                                            like = "like"
                                                                        }
                                                                    }
                                                                    val action = ListPlantFragmentDirections.actionListPlantFragmentToDetailPlantFragment(it, like)
                                                                    val controller = findNavController()
                                                                    controller.navigate(action)
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