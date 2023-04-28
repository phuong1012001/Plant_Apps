//package com.phngsapplication.app.modules.homepage.ui
//
//import android.content.Context
//import android.content.Intent
//import android.os.Bundle
//import android.view.View
//import androidx.activity.viewModels
//import androidx.appcompat.widget.SearchView
//import com.google.firebase.auth.FirebaseAuth
//import com.google.firebase.database.DatabaseReference
//import com.phngsapplication.app.R
//import com.phngsapplication.app.adapter.PlantTypesAdapter
//import com.phngsapplication.app.appcomponents.base.BaseActivity
//import com.phngsapplication.app.appcomponents.views.ImagePickerFragmentDialog
//import com.phngsapplication.app.databinding.ActivityHomepageBinding
////import com.phngsapplication.app.ui.ArticlesActivity
//import com.phngsapplication.app.ui.CameraActivity
//import com.phngsapplication.app.modules.homepage.data.model.HomepageRowModel
//import com.phngsapplication.app.modules.homepage.`data`.viewmodel.HomepageVM
//import com.phngsapplication.app.modules.profile.ui.ProfileActivity
//import com.phngsapplication.app.ui.LoginScreenActivity
//import kotlin.Boolean
//import kotlin.Int
//import kotlin.String
//import kotlin.Unit
//
//class HomepageActivity : BaseActivity<ActivityHomepageBinding>(R.layout.activity_homepage) {
//  private val viewModel: HomepageVM by viewModels<HomepageVM>()
//
//  private lateinit var firebaseAuth: FirebaseAuth
//  private lateinit var database: DatabaseReference
//
//  override fun onCreate(savedInstanceState: Bundle?) {
//    super.onCreate(savedInstanceState)
//    firebaseAuth = FirebaseAuth.getInstance()
//    checkUser()
//  }
//
//  private fun checkUser() {
//    //get current user
//    val firebaseUser = firebaseAuth.currentUser
//    if(firebaseUser == null){
//      val destIntent = LoginScreenActivity.getIntent(this, null)
//      startActivity(destIntent)
//    }
//    else{
//      //logged in, get and show user infor
//      val email = firebaseUser.email
////      val name = FirebaseDatabase.getInstance().
////      Log.d("Name", name.toString())
////      //set to textview
////      binding.txtName.text = "Hello $name"
//
//    }
//  }
//
//  override fun onInitialized(): Unit {
//    viewModel.navArguments = intent.extras?.getBundle("bundle")
//
//
//    val data = ArrayList<HomepageRowModel>()
//    for(i in 1..5){
//      data.add(HomepageRowModel("a", "b"))
//    }
//
//    val plantTypesAdapter = PlantTypesAdapter(data)
//
//    binding.recyclerHomepage.adapter = plantTypesAdapter
//    plantTypesAdapter.setOnItemClickListener(
//    object : PlantTypesAdapter.OnItemClickListener {
//      override fun onItemClick(view: View, position: Int, item: HomepageRowModel) {
//        onClickRecyclerHomepage(view, position, item)
//      }
//    }
//    )
//
//    //binding.txtName.text = "Hello" + " A"
//  }
//
//  override fun setUpClicks(): Unit {
//      binding.imageCamera.setOnClickListener {
//        ImagePickerFragmentDialog().show(supportFragmentManager)
//        { path ->//TODO HANDLE DATA
//        }
//      }
//
//    }
//
//    fun onClickRecyclerHomepage(
//      view: View,
//      position: Int,
//      item: HomepageRowModel
//    ): Unit {
//      when(view.id) {
//        R.id.plantTypes ->  {
//                  }
//      }
//    }
//
//    private fun setUpSearchViewGroupTwentyOneListener(): Unit {
//      binding.searchViewGroupTwentyOne.setOnQueryTextListener(object :
//      SearchView.OnQueryTextListener {
//        override fun onQueryTextSubmit(p0 : String) : Boolean {
//          // Performs search when user hit
//          // the search button on the keyboard
//          return false
//        }
//        override fun onQueryTextChange(p0 : String) : Boolean {
//          // Start filtering the list as user
//          // start entering the characters
//          return false
//        }
//        })
//      }
//
//      companion object {
//        const val TAG: String = "HOMEPAGE_ACTIVITY"
//
//          fun getIntent(context: Context, bundle: Bundle?): Intent {
//              val destIntent = Intent(context, HomepageActivity::class.java)
//              destIntent.putExtra("bundle", bundle)
//              return destIntent
//          }
//
//      }
//    }
