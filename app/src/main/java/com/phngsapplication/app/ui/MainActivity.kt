package com.phngsapplication.app.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.phngsapplication.app.R
import com.phngsapplication.app.appcomponents.base.BaseActivity
import com.phngsapplication.app.databinding.ActivityMainBinding
import com.phngsapplication.app.model.Article
import com.phngsapplication.app.model.Plant
import com.phngsapplication.app.model.Species


class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {
    var HomeFragment = HomeFragment()
    var SpeciesFragment = SpeciesFragment()
    var ListPlantFragment = ListPlantFragment()
    var DetailPlantFragment = DetailPlantFragment()
    var ArticlesFragment = ArticlesFragment()
    var DetailArticlesFragment = DetailArticlesFragment()

    override fun onInitialized(): Unit {
        replaceFragment(HomeFragment)
        binding.bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.home ->replaceFragment(HomeFragment)
                R.id.profile->replaceFragment(SpeciesFragment)
            }
            true
        }

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
        const val TAG: String = "PROFILE_ACTIVITY"

        fun getIntent(context: Context, bundle: Bundle?): Intent {
            val destIntent = Intent(context, MainActivity::class.java)
            destIntent.putExtra("bundle", bundle)
            return destIntent
        }
    }
}
