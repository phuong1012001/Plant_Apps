package com.phngsapplication.app.modules.profile.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.phngsapplication.app.ArticlesFragment
import com.phngsapplication.app.R
import com.phngsapplication.app.SpeciesFragment
import com.phngsapplication.app.appcomponents.base.BaseActivity
import com.phngsapplication.app.databinding.ActivityProfileBinding
import org.koin.android.ext.android.bind


class ProfileActivity : BaseActivity<ActivityProfileBinding>(R.layout.activity_profile) {
  lateinit var ArticlesFragment: ArticlesFragment
  lateinit var SpeciesFragment: SpeciesFragment

  override fun onInitialized(): Unit {
    ArticlesFragment = ArticlesFragment();
    SpeciesFragment = SpeciesFragment();

    binding.tablayout.setupWithViewPager(binding.viewPager)


    val fragmentManager = supportFragmentManager
    val adapter = ViewPagerAdapter(supportFragmentManager, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT)
    adapter.addFragment(ArticlesFragment, "Articles")
    adapter.addFragment(SpeciesFragment, "Species")
    binding.viewPager.setAdapter(adapter)

  }

  override fun setUpClicks(): Unit {
  }

  companion object {
    const val TAG: String = "PROFILE_ACTIVITY"

    fun getIntent(context: Context, bundle: Bundle?): Intent {
      val destIntent = Intent(context, ProfileActivity::class.java)
      destIntent.putExtra("bundle", bundle)
      return destIntent
    }
  }
}

internal class ViewPagerAdapter(manager: FragmentManager?, behaviorResumeOnlyCurrentFragment: Int) :
  FragmentPagerAdapter(manager!!) {
  private val mFragmentList: MutableList<Fragment> = ArrayList()
  private val mFragmentTitleList: MutableList<String> = ArrayList()
  override fun getItem(position: Int): Fragment {
    return mFragmentList[position]
  }

  override fun getCount(): Int {
    return mFragmentList.size
  }

  fun addFragment(fragment: Fragment, title: String) {
    mFragmentList.add(fragment)
    mFragmentTitleList.add(title)
  }

  override fun getPageTitle(position: Int): CharSequence? {
    return mFragmentTitleList[position]
  }
}