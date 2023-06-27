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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.phngsapplication.app.R
import com.phngsapplication.app.adapter.ArticlesAdapter
import com.phngsapplication.app.databinding.FragmentArticlesBinding
import com.phngsapplication.app.model.Article
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class ArticlesFragment : Fragment() {
    private lateinit var binding: FragmentArticlesBinding
    private lateinit var mainActivity: MainActivity

    val args: ArticlesFragmentArgs by navArgs()
    var articlesList : Array<Article>? = null
    private lateinit var articleId : ArrayList<String>
    private var db = Firebase.firestore
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var searchView: androidx.appcompat.widget.SearchView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        firebaseAuth = FirebaseAuth.getInstance()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mainActivity = getActivity() as MainActivity
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_articles, container, false)

        articlesList = args.article

        val data = ArrayList<Article>()
        for (i in articlesList!!) {
            data.add(i)
        }
        val adapter = ArticlesAdapter(data)
        binding.recyclerArticles.adapter = adapter

        searchView = binding.searchForArticles

        articleId = ArrayList()
        loadLikeFromFireStore()

        //Thao tac buttion khong sua
        adapter.onItemClick = {
            var like: String = "dislike"
            for (ds in articleId) {
                if (ds == it.txtDate) {
                    like = "like"
                }
            }
            val action = ArticlesFragmentDirections.actionArticlesFragmentToDetailArticlesFragment(it, like)
            val controller = findNavController()
            controller.navigate(action)
        }

        binding.bttonPostArticles.setOnClickListener {
            var PostSpace = findNavController()
            PostSpace.navigate(R.id.action_articlesFragment_to_postArticles)
        }

        binding.toolbar.setNavigationOnClickListener{
            val controller = findNavController()
            controller.navigate(R.id.action_articlesFragment_to_home)
        }

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

        return binding.root
    }

    private fun filterList(query: String?) {
        if(query != null){
            var ftList = ArrayList<Article>()
            ftList = ArrayList()
            for (i in articlesList!!){
                //Log.d("filter", i.nameSpecies)
                if(i.titleArticle.lowercase(Locale.ROOT).contains(query) || i.titleArticle.uppercase(Locale.ROOT).contains(query)){
                    Log.d("filter 2", i.titleArticle)
                    ftList.add(i)
                }
            }
            if(ftList.isEmpty()){
                val adapter = ArticlesAdapter(ftList)
                binding.recyclerArticles.adapter = adapter
                Toast.makeText(requireContext(), "No data found", Toast.LENGTH_SHORT).show()
            }else{
                val adapter = ArticlesAdapter(ftList)
                binding.recyclerArticles.adapter = adapter

                searchView = binding.searchForArticles

                //Thao tac buttion khong sua
                adapter.onItemClick = {
                    var like: String = "dislike"
                    for (ds in articleId) {
                        if (ds == it.txtDate) {
                            like = "like"
                        }
                    }
                    val action = ArticlesFragmentDirections.actionArticlesFragmentToDetailArticlesFragment(it, like)
                    val controller = findNavController()
                    controller.navigate(action)
                }
            }
        }
    }

    private fun loadLikeFromFireStore() {
        db = FirebaseFirestore.getInstance()
        db.collection("User").get().addOnSuccessListener {  }
            .addOnSuccessListener {
                if(!it.isEmpty){
                    for(data in it.documents){
                        val uid = data.get("id")
                        if(uid.toString() == firebaseAuth.uid.toString()) {
                            db.collection("User/$uid/UserLikeArticle").get().addOnSuccessListener { }
                                .addOnSuccessListener { it1 ->
                                    if (!it1.isEmpty) {
                                        for (data in it1.documents) {
                                            val idArticle = data.get("articleId")
                                            val like = data.get("like")
                                            if(like.toString() == "true") {
                                                articleId.add(idArticle.toString())
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