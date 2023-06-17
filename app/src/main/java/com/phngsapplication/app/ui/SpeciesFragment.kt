package com.phngsapplication.app.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.phngsapplication.app.R
import com.phngsapplication.app.adapter.SpeciesAdapter
import com.phngsapplication.app.databinding.FragmentSpeciesBinding
import com.phngsapplication.app.model.Species
import com.phngsapplication.app.model.SpeciesAlphabet
import `in`.myinnos.alphabetsindexfastscrollrecycler.IndexFastScrollRecyclerView
import java.util.*
import kotlin.collections.ArrayList

class SpeciesFragment : Fragment() {
    private lateinit var binding: FragmentSpeciesBinding
    private lateinit var mainActivity: MainActivity

    var mRecyclerView: IndexFastScrollRecyclerView? = null

    val args: SpeciesFragmentArgs by navArgs()

    private var db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mainActivity = getActivity() as MainActivity
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_species, container, false)

        var speciesList = args.listSpecies
        Log.d("listSpecies", speciesList.size.toString())

        mRecyclerView = binding.fastscrollerrecycler
        var adapter = initialiseUI(speciesList)

        adapter.onItemClick = {
            val action = SpeciesFragmentDirections.actionSpeciesFragmentToListPlantFragment(it.nameSpecies, it.id)
            val controller = findNavController()
            controller.navigate(action)
        }

        binding.btnBack.setOnClickListener{
            val controller = findNavController()
            controller.navigate(R.id.action_speciesFragment_to_home)
        }

        return binding.root
    }
    private fun initialiseUI(data1 : Array<Species>): SpeciesAdapter {
        var data2 = ArrayList<SpeciesAlphabet>()
        var data3 = ArrayList<Species>()

        var c: Char = 'A'
        data2 = ArrayList()
        while (c <= 'Z') {

            data3 = ArrayList()
            for(sp in data1){
                if(sp.nameSpecies[0] == c)
                {
                    data3.add(sp)
                }
            }
            data2.add(SpeciesAlphabet(c.toString(), data3))
            ++c
        }
        val adapter1 = SpeciesAdapter(data2)
        mRecyclerView?.apply {
            layoutManager = LinearLayoutManager(context)
//            adapter = RecyclerViewAdapter(mData)
            adapter = adapter1
            setIndexTextSize(12)
            setIndexBarCornerRadius(0)
            setIndexBarTransparentValue(0.4.toFloat())
            setPreviewPadding(0)
            setPreviewTextSize(60)
            setPreviewTransparentValue(0.6f)
            setIndexBarVisibility(true)
            setIndexBarStrokeVisibility(true)
            setIndexBarStrokeWidth(1)
            setIndexBarHighLightTextVisibility(true)
            setIndexBarColor("#EFF7FF")
            setIndexBarTextColor("#A1A8B9")
            setIndexBarStrokeColor("#EFF7FF")
        }
        Objects.requireNonNull<RecyclerView.LayoutManager>(mRecyclerView?.layoutManager)
            .scrollToPosition(0)

        return adapter1
    }
    companion object {

    }
}