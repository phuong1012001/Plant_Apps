package com.phngsapplication.app.ui

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.phngsapplication.app.R
import com.phngsapplication.app.appcomponents.base.BaseActivity
import com.phngsapplication.app.databinding.ActivityMainBinding
import com.phngsapplication.app.model.Article
import com.phngsapplication.app.model.Plant
import com.phngsapplication.app.model.Species
import kotlin.random.Random


class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {
    var HomeFragment = HomeFragment()
    var SpeciesFragment = SpeciesFragment()
    var ArticlesProfileFragment = ArticlesProfileFragment()
    var AddingNewPlant1Fragment = AddingNewPlant1Fragment()
    var AddingNewPlant2Fragment = AddingNewPlant2Fragment()
    lateinit var uri: String


    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var database: DatabaseReference

    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        firebaseAuth = FirebaseAuth.getInstance()
        checkUser()




        val navHostFragment = supportFragmentManager.findFragmentById(
            R.id.frameBottombar
        ) as NavHostFragment
        navController = navHostFragment.navController

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNavigationView.setupWithNavController(navController)
    }

    private fun checkUser() {
        //get current user
        val firebaseUser = firebaseAuth.currentUser
        if(firebaseUser == null){
            val destIntent = LoginScreenActivity.getIntent(this, null)
            startActivity(destIntent)
        }
        else{
            //logged in, get and show user infor
            val email = firebaseUser.email
//      val name = FirebaseDatabase.getInstance().
//      Log.d("Name", name.toString())
//      //set to textview
//      binding.txtName.text = "Hello $name"

        }
    }

    override fun onInitialized(): Unit {
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
//        Log.e("AAAAAA", "BBBBbbbbbbbbbbbbb")
//        if (requestCode == PHOTO_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
//            val photoUri = data?.extras?.get(CameraConfiguration.IMAGE_URI) as Uri?
//            photoUri?.let {
//                Log.e("AAAAAA", "BBBB")
////                photo_view.load(it)
//
////                goToAddingNewPlant(photoUri.toString())
//            }
//        }
    }

    public fun goToHome(){
        replaceFragment(HomeFragment)

//        val destIntent = CameraActivity.getIntent(this, null)
//        startActivityForResult(destIntent, PHOTO_REQUEST_CODE)
    }

    public fun goToDetailPlantTypes(){
        var bundle: Bundle = Bundle()
        bundle.putSerializable("a", "a")
        SpeciesFragment.setArguments(bundle)
        replaceFragment(SpeciesFragment)
    }

    public fun goToAddingNewPlant(uri: String){
        var bundle: Bundle = Bundle()
        bundle.putString("Uri", uri)
        AddingNewPlant1Fragment.setArguments(bundle)
        replaceFragment(AddingNewPlant1Fragment)
    }

    public fun goToAddingNewPlant2(uri: String, species:String){
        var bundle: Bundle = Bundle()
        bundle.putString("Uri", uri)
        bundle.putString("Species", species)
        AddingNewPlant2Fragment.setArguments(bundle)
        replaceFragment(AddingNewPlant2Fragment)
    }

    public fun a(){
        var bundle: Bundle = Bundle()
        bundle.putString("A", "HOA")
        ArticlesProfileFragment.setArguments(bundle)
    }

    private fun replaceFragment(fragment: Fragment){
        if(fragment!=null){
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.frameBottombar, fragment)
            transaction.addToBackStack(fragment.javaClass.getName())
            transaction.commit()
        }
    }

    override fun setUpClicks(): Unit {
    }

    companion object {
        const val TAG: String = "MAIN_ACTIVITY"

        private val PHOTO_REQUEST_CODE = Random.nextInt(0, 10000)
        fun getIntent(context: Context, bundle: Bundle?): Intent {
            val destIntent = Intent(context, MainActivity::class.java)
            destIntent.putExtra(TAG, bundle)
            return destIntent
        }
    }
}
