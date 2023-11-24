package com.example.housemaster

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.housemaster.databinding.FragmentHomeBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class HomeFragment : Fragment(R.layout.fragment_home) {

    private lateinit var homeBinding: FragmentHomeBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var firebaseFirestore: FirebaseFirestore

    private lateinit var categoryRecyclerView: RecyclerView
    private lateinit var categoryArrayList: ArrayList<ServiceCategoryModel>


    lateinit var categoryTitle: Array<String>
    lateinit var categoryImage: Array<Int>
    lateinit var categoryId: Array<String>

    private lateinit var spRecyclerView: RecyclerView
    private lateinit var spArrayList: ArrayList<ServiceProviderModel>
    lateinit var spName: Array<String>
    lateinit var spImage: Array<Int>
    lateinit var spRatings: Array<String>
    lateinit var spId: Array<String>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        firebaseAuth = FirebaseAuth.getInstance()
        firebaseFirestore = FirebaseFirestore.getInstance()
        if (firebaseAuth.currentUser == null) {
            val action = HomeFragmentDirections.actionHomeFragmentToSignInFragment()
            findNavController().navigate(action)
        }
        super.onViewCreated(view, savedInstanceState)
        homeBinding = FragmentHomeBinding.bind(view)

        getCategoryData()

        getSpData()


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

        categoryRecyclerView = homeBinding.homeCategoryRV
        categoryRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        categoryRecyclerView.setHasFixedSize(true)

        categoryArrayList = arrayListOf<ServiceCategoryModel>()
        val ref = firebaseFirestore.collection("serviceCategories")
        ref.get().addOnSuccessListener { documentSnapshot ->
            if (!documentSnapshot.isEmpty) {
                val documents = documentSnapshot.documents
                for (category in documents) {
                    val data = ServiceCategoryModel(
                        category.id,
                        category["categoryName"].toString(),
                        R.drawable.sample_saloon
                    )
                    categoryArrayList.add(data)

                    var adapter = ServiceCategoryAdapter(categoryArrayList)
                    categoryRecyclerView.adapter = adapter
                    adapter.setOnItemClickListener(object :
                        ServiceCategoryAdapter.onItemClickListener {
                        override fun onItemClick(position: Int) {
                            val categoryId = categoryArrayList[position].categoryId
                            val categoryTitle = categoryArrayList[position].categoryName
                            val action =
                                HomeFragmentDirections.actionHomeFragmentToIndividualCategorySPListFragment(
                                    categoryId,
                                    categoryTitle
                                )
                            findNavController().navigate(action)
                        }
                    }
                    )
                }
            } else {

            }

        }.addOnFailureListener {

            Log.e("firebase", "Error getting data", it)
        }


    }

    private fun getSpData() {
        spRecyclerView = homeBinding.homeServiceProviderRV
        spRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        spRecyclerView.setHasFixedSize(true)

        spArrayList = arrayListOf<ServiceProviderModel>()


        val ref = firebaseFirestore.collection("users")
        ref.get().addOnSuccessListener { documentSnapshot ->
            if (!documentSnapshot.isEmpty) {

                val documents = documentSnapshot.documents
                for (spData in documents) {
                    val data = ServiceProviderModel(
                        spData.id,
                        spData["shopName"].toString(),
                        R.drawable.sample_saloon,
                        spData["serviceCategory"].toString(),
                        spData["shopRatings"].toString(),
                    )
                    spArrayList.add(data)

                    var adapter2 = ServiceProviderAdapter(spArrayList)
                    spRecyclerView.adapter = adapter2
                    adapter2.setOnItemClickListener(object :
                        ServiceProviderAdapter.onItemClickListener {
                        override fun onItemClick(position: Int) {
                            val spId = spArrayList[position].spId
                            val spName = spArrayList[position].spName
                            val spRatings = spArrayList[position].spRatings
                            val spCategory = spArrayList[position].spCategory
                            val action =
                                HomeFragmentDirections.actionHomeFragmentToServiceItemFragment(
                                    spId, spCategory, spName, spRatings
                                )
                            findNavController().navigate(action)
                        }
                    }
                    )
                }
            } else {

            }

        }.addOnFailureListener {

            Log.e("firebase", "Error getting data", it)
        }
    }
}