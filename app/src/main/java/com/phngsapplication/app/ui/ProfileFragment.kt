package com.phngsapplication.app.ui

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.phngsapplication.app.R
import com.phngsapplication.app.databinding.FragmentProfileBinding


class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private lateinit var mainActivity: MainActivity

    lateinit var ArticlesFragment: ArticlesProfileFragment
    lateinit var SpeciesFragment: SpeciesProfileFragment


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ArticlesFragment = ArticlesProfileFragment();
        SpeciesFragment = SpeciesProfileFragment();

        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mainActivity = getActivity() as MainActivity
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false)

        binding.tablayout.setupWithViewPager(binding.viewPager)
        val fragmentManager = childFragmentManager
        val adapter = ViewPagerAdapter(childFragmentManager, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT)
        adapter.addFragment(ArticlesFragment, "Articles")
        adapter.addFragment(SpeciesFragment, "Species")
        binding.viewPager.setAdapter(adapter)

        var bundle: Bundle = Bundle()
        bundle.putString("A", "HOA")
        SpeciesFragment.setArguments(bundle)

        //Thong tin
//        var bundleReceive: Bundle? = getArguments()
//        if(bundleReceive != null){
//            val profile: Profile = bundleReceive.get("profile") as Profile
//            if(profile != null){
//                binding.txtName.setText(profile.name)
//                binding.txtLosAngelesCa.setText(profile.address)
//                val drawableResourceId1 = this.resources.getIdentifier(profile.imageAvatar,
//                    "drawable",
//                    mainActivity.packageName)
//
//
//                Glide.with(this)
//                    .load(drawableResourceId1)
//                    .into(binding.imageAvatar)
//            }
//        }


        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.bottom_navigation, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return super.onOptionsItemSelected(item)
    }

    companion object {

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