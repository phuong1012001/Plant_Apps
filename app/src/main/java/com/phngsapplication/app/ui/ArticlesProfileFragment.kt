package com.phngsapplication.app.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.phngsapplication.app.R
import com.phngsapplication.app.adapter.ArticlesAdapter
import com.phngsapplication.app.databinding.FragmentArticlesProfileBinding
import com.phngsapplication.app.model.Article

class ArticlesProfileFragment : Fragment() {
    private lateinit var binding: FragmentArticlesProfileBinding
    private lateinit var mainActivity: MainActivity

    private lateinit var articleId : ArrayList<String>
    var articleList = ArrayList<Article>()

    private var db = Firebase.firestore
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        firebaseAuth = FirebaseAuth.getInstance()

        articleList = ArrayList()
        articleId = ArrayList()

        loadArticlesLike()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mainActivity = getActivity() as MainActivity
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_articles_profile, container, false)

        loadArticlesFromFireStore()

        return binding.root
    }

    @SuppressLint("SuspiciousIndentation")
    private fun loadArticlesLike() {
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
                                                Log.d("article id 1",idArticle.toString())
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

    @SuppressLint("SuspiciousIndentation")
    private fun loadArticlesFromFireStore() {
        db = FirebaseFirestore.getInstance()
        db.collection("User").get().addOnSuccessListener {  }
            .addOnSuccessListener {
                if(!it.isEmpty){
                    articleList.clear()
                    for(data in it.documents){
                        val uid = data.get("id")
                        val name = data.get("name")
                        val avatar = data.get("profileImage")
                        db.collection("User/$uid/Articles").get().addOnSuccessListener {  }
                            .addOnSuccessListener {it1->
                                if(!it1.isEmpty){
                                    //data1.clear()
                                    for(data in it1.documents){
                                        val imgArticle = data.get("URL")
                                        val titleArticle = data.get("caption")
                                        val contentArticle = data.get("content")
                                        val txtDate = data.get("timestamp")
                                        for(ds in articleId)
                                            if(ds == txtDate.toString()) {
                                                articleList.add(
                                                    Article(
                                                        imgArticle.toString(),
                                                        titleArticle.toString(),
                                                        contentArticle.toString(),
                                                        avatar.toString(),
                                                        name.toString(),
                                                        txtDate.toString()
                                                    )
                                                )
                                            }
                                        if (articleList != null) {
                                            val adapter = articleList?.let { ArticlesAdapter(it) }
                                            binding.recyclerArticles.adapter = adapter
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