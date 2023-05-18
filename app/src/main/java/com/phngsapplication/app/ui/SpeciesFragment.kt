package com.phngsapplication.app.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.phngsapplication.app.R
import com.phngsapplication.app.adapter.AlphabetAdapter
import com.phngsapplication.app.adapter.SpeciesAdapter
import com.phngsapplication.app.databinding.FragmentSpeciesBinding
import com.phngsapplication.app.model.Plant
import com.phngsapplication.app.model.Species
import com.phngsapplication.app.model.SpeciesAlphabet

class SpeciesFragment : Fragment() {
    private lateinit var binding: FragmentSpeciesBinding
    private lateinit var mainActivity: MainActivity

    private lateinit var firebaseAuth: FirebaseAuth

    val alphabet = ArrayList<String>()

    val data = ArrayList<Plant>()
    val data1 = ArrayList<Species>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        alphabet.add("A")
        alphabet.add("B")
        alphabet.add("C")
        alphabet.add("D")
        alphabet.add("E")
        alphabet.add("F")
        alphabet.add("G")
        alphabet.add("H")
        alphabet.add("I")
        alphabet.add("J")
        alphabet.add("K")
        alphabet.add("L")
        alphabet.add("M")
        alphabet.add("N")
        alphabet.add("O")
        alphabet.add("P")
        alphabet.add("Q")
        alphabet.add("R")
        alphabet.add("S")
        alphabet.add("T")
        alphabet.add("U")
        alphabet.add("V")
        alphabet.add("W")
        alphabet.add("X")
        alphabet.add("Y")
        alphabet.add("Z")

        for(i in 10..20){
            data.add(Plant("img_rectangle_3", "Item" + i, "KINGDOM" + i, "Family" + i, "Des" + i, "Like"))
        }
        loadSpecies(data1, data)


    }

    private fun loadSpecies(data1: ArrayList<Species>, data: ArrayList<Plant>) {
        val ref = FirebaseDatabase.getInstance().getReference("Species")
        ref.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                Log.d("TAG", "Start get data")
                data1.clear()
                for(ds in snapshot.children){
                    val species = ds.child("species").value
                    Log.d("Name get data: ", species.toString())
                    data1.add(Species(species.toString(), data))
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

        Log.d("TAG", "End get data")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mainActivity = getActivity() as MainActivity
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_species, container, false)

        val adapter2 = AlphabetAdapter(alphabet)

        binding.recyclerView2.adapter = adapter2

//        val data = ArrayList<Plant>()
//        for(i in 10..20){
//            data.add(Plant("img_rectangle_3", "Item" + i, "KINGDOM" + i, "Family" + i, "Des" + i, "Like"))
//        }

//        val data1 = ArrayList<Species>()

//        loadSpecies(data1, data)
        //get data species

        val data2 = ArrayList<SpeciesAlphabet>()
        data2.add(SpeciesAlphabet("A", data1))

        //Them vao adapter
        val adapter1 = SpeciesAdapter(data2)
        binding.recyclerView1.adapter = adapter1
        adapter1.onItemClick = {
            mainActivity.goToListPlant(it)
        }

        binding.btnBack.setOnClickListener{
            getFragmentManager()?.popBackStack()
        }

        return binding.root
    }
    companion object {

    }
}