package com.example.housemaster

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.housemaster.databinding.FragmentHomeBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth

class HomeFragment : Fragment(R.layout.fragment_home) {

    private lateinit var homeBinding: FragmentHomeBinding
    private lateinit var firebaseAuth: FirebaseAuth

    private lateinit var categoryRecyclerView: RecyclerView
    private lateinit var categoryArrayList: ArrayList<ServiceCategoryModel>
    lateinit var categoryTitle: Array<String>
    lateinit var categoryImage: Array<Int>

    private lateinit var spRecyclerView: RecyclerView
    private lateinit var spArrayList: ArrayList<ServiceProviderModel>
    lateinit var spName: Array<String>
    lateinit var spImage: Array<Int>
    lateinit var spRatings: Array<String>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        firebaseAuth = FirebaseAuth.getInstance()
        if (firebaseAuth.currentUser == null) {
            val action = HomeFragmentDirections.actionHomeFragmentToSignInFragment()
            findNavController().navigate(action)
        }
        super.onViewCreated(view, savedInstanceState)
        homeBinding = FragmentHomeBinding.bind(view)


//adapter implementation for service provider category

        categoryTitle = arrayOf(
            "hello1",
            "hello1",
            "hello1",
            "hello1",
            "hello1",
            "hello1",
            "hello1",
            "hello1",
            "hello1",
            "hello1",
            "hello1",
            "hello1",
            "hello1",
            "hello1",
            "hello1",
            "hello1",
            "hello1",
            "hello1",
            "hello1",
            "hello1",
        )

        categoryImage = arrayOf(
            R.drawable.sample_saloon,
            R.drawable.sample_saloon,
            R.drawable.sample_saloon,
            R.drawable.sample_saloon,
            R.drawable.sample_saloon,
            R.drawable.sample_saloon,
            R.drawable.sample_saloon,
            R.drawable.sample_saloon,
            R.drawable.sample_saloon,

            R.drawable.sample_saloon,
            R.drawable.sample_saloon,
            R.drawable.sample_saloon,

            R.drawable.logo_house_master,
            R.drawable.sample_saloon,
            R.drawable.logo_house_master,
            R.drawable.sample_saloon,
            R.drawable.logo_house_master,
            R.drawable.sample_saloon,
            R.drawable.logo_house_master,
            R.drawable.sample_saloon,
        )

        categoryRecyclerView = homeBinding.homeCategoryRV
        categoryRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        categoryRecyclerView.setHasFixedSize(true)

        categoryArrayList = arrayListOf<ServiceCategoryModel>()
        getCategoryData()

        categoryRecyclerView.adapter = ServiceCategoryAdapter(categoryArrayList)
//end of adapter implementation


        //adapter implementation for service provider list

        spName = arrayOf(
            "hello1",
            "hello1",
            "hello1",
            "hello1",
            "hello1",
            "hello1",
            "hello1",
            "hello1",
            "hello1",
            "hello1",
            "hello1",
            "hello1",
            "hello1",
            "hello1",
            "hello1",
            "hello1",
            "hello1",
            "hello1",
            "hello1",
            "hello1",
        )


        spRatings = arrayOf(
            "hello1",
            "hello1",
            "hello1",
            "hello1",
            "hello1",
            "hello1",
            "hello1",
            "hello1",
            "hello1",
            "hello1",
            "hello1",
            "hello1",
            "hello1",
            "hello1",
            "hello1",
            "hello1",
            "hello1",
            "hello1",
            "hello1",
            "hello1",
        )

        spImage = arrayOf(
            R.drawable.sample_saloon,
            R.drawable.sample_saloon,
            R.drawable.sample_saloon,
            R.drawable.sample_saloon,
            R.drawable.sample_saloon,
            R.drawable.sample_saloon,
            R.drawable.sample_saloon,
            R.drawable.sample_saloon,
            R.drawable.sample_saloon,

            R.drawable.sample_saloon,
            R.drawable.sample_saloon,
            R.drawable.sample_saloon,

            R.drawable.logo_house_master,
            R.drawable.sample_saloon,
            R.drawable.logo_house_master,
            R.drawable.sample_saloon,
            R.drawable.logo_house_master,
            R.drawable.sample_saloon,
            R.drawable.logo_house_master,
            R.drawable.sample_saloon,
        )

        spRecyclerView = homeBinding.homeServiceProviderRV
        spRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        spRecyclerView.setHasFixedSize(true)

        spArrayList = arrayListOf<ServiceProviderModel>()
        getSpData()

        spRecyclerView.adapter = ServiceProviderAdapter(spArrayList)
//end of adapter implementation


        //this is just to hide ActionBar from the fragment
        (activity as AppCompatActivity?)!!.supportActionBar!!.show()
        //this is just to hide BottomNavBar from the fragment
        val view = requireActivity().findViewById<BottomNavigationView>(R.id.bottom_nav)
        view.visibility = View.VISIBLE

        homeBinding.buttonLogin.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToLoginFragment()
            findNavController().navigate(action)
        }


    }

    private fun getCategoryData() {
        for (i in categoryTitle.indices) {
            val title = ServiceCategoryModel(categoryTitle[i], categoryImage[i])
            categoryArrayList.add(title)
        }
    }

    private fun getSpData() {
        for (i in spName.indices) {
            val spModelData = ServiceProviderModel(spName[i], spImage[i], spRatings[i])
            spArrayList.add(spModelData)
        }
    }
}