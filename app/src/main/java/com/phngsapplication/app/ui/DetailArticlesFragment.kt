package com.phngsapplication.app.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.phngsapplication.app.R
import com.phngsapplication.app.databinding.FragmentDetailArticlesBinding
import com.phngsapplication.app.model.Article

class DetailArticlesFragment : Fragment() {
    private lateinit var binding: FragmentDetailArticlesBinding
    private lateinit var mainActivity: MainActivity

    val args: DetailArticlesFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mainActivity = getActivity() as MainActivity
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail_articles, container, false)

        //load data
        val Article = args.article
        if(Article != null){
            binding.txtAuthor.setText(Article.txtAuthor)
            binding.txtDate.setText(Article.txtDate)


            val drawableResourceId1 = this.resources.getIdentifier(Article.imageArticle,
                "drawable",
                mainActivity.packageName)


            Glide.with(this)
                .load(drawableResourceId1)
                .into(binding.imageArticle)

            val drawableResourceId2 = this.resources.getIdentifier(Article.imageAuthor,
                "drawable",
                mainActivity.packageName)


            Glide.with(this)
                .load(drawableResourceId2)
                .apply(RequestOptions().override(150,150))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(binding.imageAuthor)
        }

        //Thao tac voi button
        binding.btnBack.setOnClickListener{
            getFragmentManager()?.popBackStack()
        }

        return binding.root
    }

    companion object {

    }
}