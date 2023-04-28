package com.phngsapplication.app.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.phngsapplication.app.R
import com.phngsapplication.app.databinding.FragmentArticlesProfileBinding
import com.phngsapplication.app.databinding.FragmentListPlantBinding

class ArticlesProfileFragment : Fragment() {


    private lateinit var binding: FragmentArticlesProfileBinding
    private var title: String? = null
    private var page = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val fragmentFirst = ArticlesProfileFragment()
        val args: Bundle = Bundle()
        args.putInt("someInt", page)
        args.putString("someTitle", title)
        fragmentFirst.setArguments(args)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

//        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_list_plant, container, false)

//        var bundleReceive: Bundle? = getArguments()
//        if(bundleReceive != null){
//            var a: String = bundleReceive.get("A") as String
//            Log.d("Article", a)
//        }

//        page = getArguments()?.getInt("someInt", 0) ?:0;
//        title = getArguments()?.getString("someTitle")
//
//        title?.let { Log.d("2", it) }
        return inflater.inflate(R.layout.fragment_articles_profile, container, false)
    }

    companion object {
    }
}