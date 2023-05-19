package com.phngsapplication.app.ui

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.phngsapplication.app.R
import com.phngsapplication.app.appcomponents.base.BaseActivity
import com.phngsapplication.app.appcomponents.ui.loadImageFromNetwork
import com.phngsapplication.app.databinding.ActivityMainBinding
import com.phngsapplication.app.model.Article
import com.phngsapplication.app.model.Plant
import com.phngsapplication.app.model.Species
import kotlin.random.Random


class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {
    var HomeFragment = HomeFragment()
    var SpeciesFragment = SpeciesFragment()
    var ListPlantFragment = ListPlantFragment()
    var DetailPlantFragment = DetailPlantFragment()
    var ArticlesFragment = ArticlesFragment()
    var DetailArticlesFragment = DetailArticlesFragment()
    var ArticlesProfileFragment = ArticlesProfileFragment()
    var ProfileFragment = ProfileFragment()
    var AddingNewPlant1Fragment = AddingNewPlant1Fragment()
    var AddingNewPlant2Fragment = AddingNewPlant2Fragment()
    lateinit var uri: String


    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        firebaseAuth = FirebaseAuth.getInstance()
        checkUser()

        replaceFragment(HomeFragment)
        binding.bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.home ->replaceFragment(HomeFragment)
                R.id.profile->replaceFragment(ProfileFragment)
            }
            true
        }
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
        if (requestCode == PHOTO_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val photoUri = data?.extras?.get(CameraConfiguration.IMAGE_URI) as Uri?
            photoUri?.let {
//                photo_view.load(it)

                goToAddingNewPlant(photoUri.toString())
                Log.e(TAG, "CHECK TAKE IMAGE: " + photoUri.toString());

            }
        }
        // MinhTuyen
       else if (requestCode == REQUEST_SELECT_IMAGE_IN_ALBUM && resultCode == Activity.RESULT_OK) {
            val selectedImageUri = data?.data
            // Handle the selected image here
            if (selectedImageUri != null) {
                goToAddingNewPlant(selectedImageUri.toString())
               Log.e(TAG, "CHECK LOAD IMAGE: " + selectedImageUri.toString());
            }
        }
    }

    public fun goToHome(){
        replaceFragment(HomeFragment)
    }

    public fun goToDetailPlantTypes(){
        var bundle: Bundle = Bundle()
        bundle.putSerializable("a", "a")
        SpeciesFragment.setArguments(bundle)
        replaceFragment(SpeciesFragment)
    }

    public fun goToArticles(){
        var bundle: Bundle = Bundle()
        bundle.putSerializable("a", "a")
        ArticlesFragment.setArguments(bundle)
        replaceFragment(ArticlesFragment)
    }

    public fun goToCamere(){
        val destIntent = CameraActivity.getIntent(this, null)
        startActivityForResult(destIntent, PHOTO_REQUEST_CODE)
    }

    public fun goToDetailArticles(article: Article){
        var bundle: Bundle = Bundle()
        bundle.putParcelable("article", article)
        DetailArticlesFragment.setArguments(bundle)
        replaceFragment(DetailArticlesFragment)
    }

    public fun goToSpecies(){
        var bundle: Bundle = Bundle()
        bundle.putSerializable("a", "a")
        SpeciesFragment.setArguments(bundle)
        replaceFragment(SpeciesFragment)
    }

    public fun goToListPlant(listPlant: Species){
        var bundle: Bundle = Bundle()
        bundle.putParcelable("listPlant", listPlant)
        ListPlantFragment.setArguments(bundle)
        replaceFragment(ListPlantFragment)
    }

    public fun goToDetailPlant(plant: Plant){
        var bundle: Bundle = Bundle()
        bundle.putParcelable("Plant", plant)
        DetailPlantFragment.setArguments(bundle)
        replaceFragment(DetailPlantFragment)
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
        private val REQUEST_SELECT_IMAGE_IN_ALBUM = 1001
        fun getIntent(context: Context, bundle: Bundle?): Intent {
            val destIntent = Intent(context, MainActivity::class.java)
            destIntent.putExtra(TAG, bundle)
            return destIntent
        }
    }
}
