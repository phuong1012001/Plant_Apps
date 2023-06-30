package com.phngsapplication.app.ui

import android.annotation.SuppressLint
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
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.phngsapplication.app.R
import com.phngsapplication.app.databinding.FragmentDetailArticlesBinding
import com.phngsapplication.app.model.Article
import java.util.*
import kotlin.collections.ArrayList

class DetailArticlesFragment : Fragment() {
    private lateinit var binding: FragmentDetailArticlesBinding
    private lateinit var mainActivity: MainActivity

    private var db = Firebase.firestore

    var articleList = ArrayList<Article>()

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

        //handle like
        val like = args.like
        if(like == "like")
            binding.btnLike.setBackgroundResource(R.drawable.rectangle_bg_red_a200_radius_28_5)
        else
            binding.btnLike.setBackgroundResource(R.drawable.rectangle_bg_white_a200_radius_28_5);
        //load data
        val Article = args.article
        if(Article != null){
            binding.txtAuthor.setText(Article.txtAuthor)
            binding.txtDate.setText(getShortDate(Article.txtDate.toLong()))
            binding.txtTitleArticle.setText(Article.titleArticle)
            binding.txtDescription.setText(Article.contentArticle)

            Glide.with(this)
                .load(Article.imageArticle)
                .into(binding.imageArticle)

            Glide.with(this)
                .load(Article.imageAuthor)
                .apply(RequestOptions().override(150,150))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(binding.imageAuthor)
        }

        var button_background : Int = 0
        button_background = if(like == "like")
            1
        else 2

        binding.btnLike.setOnClickListener {
            if(button_background==1){
                binding.btnLike.setBackgroundResource(R.drawable.rectangle_bg_white_a200_radius_28_5);
                button_background=2;
                updateUserLikeArticleFireStore(Article.txtDate, "false")
            } else if(button_background==2){
                binding.btnLike.setBackgroundResource(R.drawable.rectangle_bg_red_a200_radius_28_5);
                button_background=1;
                updateUserLikeArticleFireStore(Article.txtDate, "true")
            }
        }

        //Thao tac voi button
        loadArticlesFromFireStore()
        binding.toolbar.setNavigationOnClickListener{
            var articlesArr : Array<Article> = articleList.toTypedArray()
            val action = DetailArticlesFragmentDirections.actionDetailArticlesFragmentToArticlesFragment(articlesArr)
            val controller = findNavController()
            controller.navigate(action)
        }

        return binding.root
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
                                        articleList.add(Article(
                                            imgArticle.toString(),
                                            titleArticle.toString(),
                                            contentArticle.toString(),
                                            avatar.toString(),
                                            name.toString(),
                                            txtDate.toString()
                                        ))
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

    private fun getShortDate(ts:Long?):String{
        if(ts == null) return ""
        //Get instance of calendar
        val calendar = Calendar.getInstance(Locale.getDefault())
        //get current date from ts
        calendar.timeInMillis = ts
        //return formatted date
        return android.text.format.DateFormat.format("yyyy. MM. dd", calendar).toString()
    }

    private fun updateUserLikeArticleFireStore(articleId: String, like: String) {
        val timestamp = System.currentTimeMillis()
        val userLikeArticleMap = hashMapOf(
            "articleId" to articleId,
            "like" to like,
            "timestamp" to timestamp
        )

        val userId = FirebaseAuth.getInstance().currentUser!!.uid

        db.collection("User/$userId/UserLikeArticle").document("$articleId").set(userLikeArticleMap)
            .addOnSuccessListener {
                Toast.makeText(mainActivity, "Add FireStore Successfully...", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener{e->
                Toast.makeText(mainActivity, "Failed to add FireStore due to ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    companion object {

    }
}