package com.phngsapplication.app.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
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

    private lateinit var speciesRecyclerView: RecyclerView
    private lateinit var speciesList: ArrayList<SpeciesAlphabet>
    val alphabet = ArrayList<String>()
    //MT
    //arraylist to hold categories
    private lateinit var categoryArrayList:ArrayList<SpeciesAlphabet>
//    adapter
    private lateinit var adapterSpecies: SpeciesAdapter

    private var databaseReference: DatabaseReference? = null
    var plant = ArrayList<Plant>()


    //==========================
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //============
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
        Log.e("ERROR1: ", "Toi cho getDATA, but it's not link")
        getData()

    }
    private fun getData() {
        databaseReference = FirebaseDatabase.getInstance().getReference("Species")
        databaseReference?.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val speciesAlphabets = ArrayList<SpeciesAlphabet>()
                    for (ds in snapshot.children) {
                        Log.e("ERROR0: ", "Toi cho getDATA, but it's not link")
                        val speciesAlphabet = ds.getValue(SpeciesAlphabet::class.java)
                        if (speciesAlphabet != null) {
                            speciesAlphabets.add(speciesAlphabet)
                        }
                    }

                    val plantNames = ArrayList<String>() // List to store plant names
                    for (speciesAlphabet in speciesAlphabets) {
                        for (species in speciesAlphabet.species) {
                            species.plants?.forEach { plant ->
                                plant.txtname?.let { plantNames.add(it) } // Add plant name to the list
                            }
                        }
                    }

                    // Use the plantNames list to display or process the names as required
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.i("Error", error.message)
            }
        })
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mainActivity = getActivity() as MainActivity
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_species, container, false)



        val adapter2 = AlphabetAdapter(alphabet)

        binding.recyclerView2.adapter = adapter2

        val data = ArrayList<Plant>()
        for(i in 10..20){
            data.add(Plant("img_rectangle_3", "Name" + i, "Item" + i, "KINGDOM" + i, "Family" + i, "Des" + i, "Like"))
        }

        val data1 = ArrayList<Species>()
        data1.add(Species("B", data))
        data1.add(Species("C", data))
        data1.add(Species("D", data))

        val data2 = ArrayList<SpeciesAlphabet>()
        data2.add(SpeciesAlphabet("A", data1))

        //Them vao adapter
        val adapter1 = SpeciesAdapter(data2)
        binding.recyclerView1.adapter = adapter1
        adapter1.onItemClick = {
            mainActivity.goToListPlant(it)
        }

//        //MT
//        //init arraylist
//        categoryArrayList = ArrayList<SpeciesAlphabet>()
//        //get all categories from firebase database ... Firebase DB > Categories
//        val ref = FirebaseDatabase.getInstance().getReference("Species/")
//        ref.addValueEventListener(object : ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
//                categoryArrayList.clear()
//                for (ds in snapshot.children) {
//                    //get data as model
//                    val model = ds.child().getValue(String::class.java)
//                    //add to arraylist
//                    categoryArrayList.add(model!!)
//                }
//                //setup adapter
//                adapterSpecies = SpeciesAdapter(categoryArrayList)
//                //set adapter to recyclerview
//                binding.recyclerView1.adapter = adapterSpecies
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//
//            }
//        })


        //=====================================
        binding.btnBack.setOnClickListener {
            getFragmentManager()?.popBackStack()
        }

        return binding.root
    }

    companion object {

    }
}