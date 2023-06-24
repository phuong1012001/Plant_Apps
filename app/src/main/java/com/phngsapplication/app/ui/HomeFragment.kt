package com.phngsapplication.app.ui

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.phngsapplication.app.R
import com.phngsapplication.app.adapter.PlantTypesAdapter
import com.phngsapplication.app.databinding.FragmentHomeBinding
import com.phngsapplication.app.model.Article
import com.phngsapplication.app.model.Species
import kotlin.random.Random

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var mainActivity: MainActivity
    private val PHOTO_REQUEST_CODE = Random.nextInt(0, 10000)

    var data1 = ArrayList<Species>() //Mang Species
    private var db = Firebase.firestore

    var articleList = ArrayList<Article>()
    private lateinit var set: ImageView
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var firebaseUser: FirebaseUser

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PHOTO_REQUEST_CODE && resultCode == Activity.RESULT_OK){
            val photoUri = data?.extras?.get(CameraConfiguration.IMAGE_URI) as Uri?
            photoUri?.let {
                Log.e("AAAAAAAAAAAA", photoUri.toString())

                val action = HomeFragmentDirections.actionHomeToAddingNewPlant2Fragment(photoUri.toString())
                val controller = findNavController()
                controller.navigate(action)
            }
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        firebaseAuth = FirebaseAuth.getInstance()
        firebaseUser = firebaseAuth.currentUser!!
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        mainActivity = getActivity() as MainActivity
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)

        val data = ArrayList<HomepageRowModel>()
        for(i in 1..5){
            data.add(HomepageRowModel("a", "b"))
        }

        val plantTypesAdapter = PlantTypesAdapter(data)

        binding.recyclerPlantTypes.adapter = plantTypesAdapter
        plantTypesAdapter.setOnItemClickListener(
            object : PlantTypesAdapter.OnItemClickListener {
                override fun onItemClick(view: View, position: Int, item: HomepageRowModel) {
                    onClickRecyclerHomepage(view, position, item)
                }
            }
        )

        set = binding.imageAvatar

        //Load User
        loadImageFromUser()

        //Load Articles
        loadArticlesFromFireStore()

        //Load Species
        loadSpeciesFromFireStore()

        setUpClicks()

        return binding.root
    }

    fun setUpClicks(): Unit {
        binding.bttonSpecies.setOnClickListener {
            var speciesArr : Array<Species> = data1.toTypedArray()
            Log.d("Size speciesArr", speciesArr.size.toString())
            val action = HomeFragmentDirections.actionHomeToSpeciesFragment(speciesArr)
            val controller = findNavController()
            controller.navigate(action)
        }
        binding.bttonArticles.setOnClickListener {
            var articlesArr : Array<Article> = articleList.toTypedArray()
            Log.d("Szie Article", articlesArr.size.toString())
            val action = HomeFragmentDirections.actionHomeFragmentToArticlesFragment(articlesArr)
            val controller = findNavController()
            controller.navigate(action)
        }
        binding.buttonAddingNew.setOnClickListener {
            val intent = Intent(activity, CameraActivity::class.java)
            startActivityForResult(intent, PHOTO_REQUEST_CODE)
        }
    }

    fun onClickRecyclerHomepage(
        view: View,
        position: Int,
        item: HomepageRowModel
    ): Unit {
        when(view.id) {
            R.id.plantTypes ->  {
//                mainActivity.goToDetailPlantTypes()
            }
        }
    }

    private fun loadImageFromUser() {
        db.collection("User").get().addOnSuccessListener {  }
            .addOnSuccessListener {
                if(!it.isEmpty){
                    for(data in it.documents){
                        val image = data.get("profileImage")
                        val uid = data.get("id")
                        val name = data.get("name")
                        if(uid.toString() == firebaseUser.uid)
                        {
                            Log.d("User", uid.toString())
                            binding.txtName.text = "Hello ${name.toString()}"
                            try {
                                Glide.with(requireContext()).load(image).into(set)
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                        }
                    }
                }
            }
            .addOnFailureListener {e->
                Toast.makeText(mainActivity, "Failed to load FireStore due to ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

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

    private fun setUpSearchViewGroupTwentyOneListener(): Unit {
//        binding.searchViewGroupTwentyOne.setOnQueryTextListener(object :
//            SearchView.OnQueryTextListener {
//            override fun onQueryTextSubmit(p0 : String) : Boolean {
//                // Performs search when user hit
//                // the search button on the keyboard
//                return false
//            }
//            override fun onQueryTextChange(p0 : String) : Boolean {
//                // Start filtering the list as user
//                // start entering the characters
//                return false
//            }
//        })
    }



    private fun loadSpeciesFromFireStore() {
        //data1 = ArrayList()
        db = FirebaseFirestore.getInstance()
        db.collection("User").get().addOnSuccessListener {  }
            .addOnSuccessListener {
                if(!it.isEmpty){
                    data1.clear()
                    for(data in it.documents){
                        val uid = data.get("id")
                        db.collection("User/$uid/Species").get().addOnSuccessListener {  }
                            .addOnSuccessListener {it1->
                                if(!it1.isEmpty){
                                    //data1.clear()
                                    for(data in it1.documents){
                                        val species = data.get("species")
                                        val id = data.get("id")

                                        data1.add(Species(id.toString(), species.toString(),"1", null))
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
        const val TAG: String = "HOMEPAGE_ACTIVITY"
    }
}