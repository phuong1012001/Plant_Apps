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
        return binding.root
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
                                                Log.d("article id",idArticle.toString())
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

    private fun getDateTime(s: String): String? {
        try {
            val sdf = SimpleDateFormat("MM/dd/yyyy")
            val netDate = Date(s.toLong() * 1000)
            return sdf.format(netDate)
        } catch (e: Exception) {
            return e.toString()
        }
    }

    private fun getShortDate(ts:Long?):String{
        if(ts == null) return ""
        //Get instance of calendar
        val calendar = Calendar.getInstance(Locale.getDefault())
        //get current date from ts
        calendar.timeInMillis = ts
        //return formatted date
        return android.text.format.DateFormat.format("yyyy. MM. dd", calendar).toString()
    }

    companion object {
    }
}