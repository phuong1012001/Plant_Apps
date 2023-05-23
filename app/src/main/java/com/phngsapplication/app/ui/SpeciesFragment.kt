package com.phngsapplication.app.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.phngsapplication.app.R
import com.phngsapplication.app.adapter.SpeciesAdapter
import com.phngsapplication.app.databinding.FragmentSpeciesBinding
import com.phngsapplication.app.model.Plant
import com.phngsapplication.app.model.Species
import com.phngsapplication.app.model.SpeciesAlphabet
import `in`.myinnos.alphabetsindexfastscrollrecycler.IndexFastScrollRecyclerView
import java.util.*
import kotlin.collections.ArrayList

class SpeciesFragment : Fragment() {
    private lateinit var binding: FragmentSpeciesBinding
    private lateinit var mainActivity: MainActivity

    val alphabet = ArrayList<String>()
    var mRecyclerView: IndexFastScrollRecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mainActivity = getActivity() as MainActivity
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_species, container, false)

        mRecyclerView = binding.fastscrollerrecycler
        val adapter = initialiseUI()

        adapter.onItemClick = {
            val action = SpeciesFragmentDirections.actionSpeciesFragmentToListPlantFragment(it)
            val controller = findNavController()
            controller.navigate(action)
        }

        binding.btnBack.setOnClickListener{
            val controller = findNavController()
            controller.navigate(R.id.action_speciesFragment_to_home)
        }

        return binding.root
    }

    private fun initialiseUI(): SpeciesAdapter{
        val data = ArrayList<Plant>()
        for(i in 10..20){
            data.add(Plant("img_rectangle_3", "Item" + i, "KINGDOM" + i, "Family" + i, "Des" + i, "Like"))
        }

        val data1 = ArrayList<Species>()
        data1.add(Species("B", data))
        data1.add(Species("C", data))
        data1.add(Species("D", data))

        val data2 = ArrayList<SpeciesAlphabet>()
        data2.add(SpeciesAlphabet("A", data1))
        data2.add(SpeciesAlphabet("B", data1))
        data2.add(SpeciesAlphabet("C", data1))
        data2.add(SpeciesAlphabet("D", data1))
        data2.add(SpeciesAlphabet("E", data1))
        data2.add(SpeciesAlphabet("F", data1))
        data2.add(SpeciesAlphabet("G", data1))
        data2.add(SpeciesAlphabet("H", data1))
        data2.add(SpeciesAlphabet("I", data1))
        data2.add(SpeciesAlphabet("J", data1))
        data2.add(SpeciesAlphabet("K", data1))
        data2.add(SpeciesAlphabet("L", data1))
        data2.add(SpeciesAlphabet("M", data1))
        data2.add(SpeciesAlphabet("N", data1))
        data2.add(SpeciesAlphabet("O", data1))
        data2.add(SpeciesAlphabet("P", data1))
        data2.add(SpeciesAlphabet("Q", data1))
        data2.add(SpeciesAlphabet("R", data1))
        data2.add(SpeciesAlphabet("S", data1))
        data2.add(SpeciesAlphabet("T", data1))
        data2.add(SpeciesAlphabet("U", data1))

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