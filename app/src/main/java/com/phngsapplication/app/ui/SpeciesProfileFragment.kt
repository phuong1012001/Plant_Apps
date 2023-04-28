package com.phngsapplication.app.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.phngsapplication.app.R

class SpeciesProfileFragment : Fragment() {


    private lateinit var profileFragment: ProfileFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //profileFragment = getFragmentM() as ProfileFragment

        var bundleReceive: Bundle? = getArguments()
        if(bundleReceive != null){
            var txt: String = bundleReceive.get("A") as String
            if(txt != null){
                Log.d("SPEC", txt)
            }
        }



        return inflater.inflate(R.layout.fragment_species_profile, container, false)
    }

    companion object {

    }
}