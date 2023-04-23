package com.phngsapplication.app.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.phngsapplication.app.R
import com.phngsapplication.app.adapter.ArticlesAdapter
import com.phngsapplication.app.databinding.FragmentArticlesBinding
import com.phngsapplication.app.model.Article

class ArticlesFragment : Fragment() {
    private lateinit var binding: FragmentArticlesBinding
    private lateinit var mainActivity: MainActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mainActivity = getActivity() as MainActivity
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_articles, container, false)

        val data = ArrayList<Article>()
        for(i in 1..5){
            data.add(Article("tomato", "Item" + i, "img_rectangle_125x125", "Name" + i, "Date" + i))
        }
        val adapter = ArticlesAdapter(data)

        binding.recyclerArticles.adapter = adapter
        adapter.onItemClick = {
            mainActivity.goToDetailArticles(it)
        }

        binding.btnBack.setOnClickListener{
            getFragmentManager()?.popBackStack()
        }
        return binding.root
    }
    companion object {
    }
}