package com.phngsapplication.app.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.phngsapplication.app.R
import com.phngsapplication.app.databinding.FragmentProfileBinding


class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private lateinit var mainActivity: MainActivity

    lateinit var ArticlesFragment: ArticlesProfileFragment
    lateinit var SpeciesFragment: SpeciesProfileFragment

    private var db = Firebase.firestore
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ArticlesFragment = ArticlesProfileFragment();
        SpeciesFragment = SpeciesProfileFragment();

        firebaseAuth = FirebaseAuth.getInstance()

        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mainActivity = getActivity() as MainActivity
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false)
        //Thong tin
        loadUserFromFireStore()

        binding.toolbar.setOnMenuItemClickListener() {
            when(it.itemId){
                R.id.logout->{
                    firebaseAuth.signOut()
                    val action = ProfileFragmentDirections.actionProfileToLoginScreenActivity()
                    val controller = findNavController()
                    controller.navigate(action)
                    mainActivity.finish()
                }
                R.id.changePassword->{
                    val controller = findNavController()
                    controller.navigate(R.id.action_profile_to_editProfileFragment)
                }
            }
            true
        }

        binding.tablayout.setupWithViewPager(binding.viewPager)
        val fragmentManager = childFragmentManager
        val adapter = ViewPagerAdapter(childFragmentManager, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT)
        adapter.addFragment(ArticlesFragment, "Articles")
        adapter.addFragment(SpeciesFragment, "Species")
        binding.viewPager.setAdapter(adapter)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.bottom_navigation, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    @SuppressLint("SuspiciousIndentation")
    private fun loadUserFromFireStore() {
        db = FirebaseFirestore.getInstance()
        db.collection("User").get().addOnSuccessListener {  }
            .addOnSuccessListener {
                if(!it.isEmpty){
                    for(data in it.documents){
                        val uid = data.get("id").toString()
                        if(firebaseAuth.uid.toString() == uid){
                            val name = data.get("name").toString()
                            val image = data.get("profileImage").toString()
                            val address = data.get("local").toString()
                            Log.d("Name", name)
                            binding.txtName.text = name
                            Glide.with(this)
                                .load(image)
                                .into(binding.imageAvatar)
                            binding.txtLosAngelesCa.text = address
                            break
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