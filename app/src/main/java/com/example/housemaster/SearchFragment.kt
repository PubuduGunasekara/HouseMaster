package com.example.housemaster

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.housemaster.databinding.FragmentHomeBinding
import com.example.housemaster.databinding.FragmentSearchBinding
import com.example.housemaster.databinding.FragmentSettingsBinding
import com.example.housemaster.databinding.FragmentTermsBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class SearchFragment : Fragment(R.layout.fragment_search) {
    private var recyclerView: RecyclerView? = null
    private var searchCategoryRecyclerViewAdapter: SearchCategoryAdapter? = null
    private var categoryList = ArrayList<ServiceCategoryModel>()
    private lateinit var searchBinding: FragmentSearchBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var firebaseFirestore: FirebaseFirestore

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        categoryList = ArrayList<ServiceCategoryModel>()
        firebaseAuth = FirebaseAuth.getInstance()
        firebaseFirestore = FirebaseFirestore.getInstance()
        super.onViewCreated(view, savedInstanceState)
        searchBinding = FragmentSearchBinding.bind(view)
        getCategoryData()


    }

    private fun getCategoryData() {

        recyclerView = searchBinding.categoryLists
        searchCategoryRecyclerViewAdapter = SearchCategoryAdapter(categoryList)
        val layoutManager: RecyclerView.LayoutManager = GridLayoutManager(requireContext(), 2)

        recyclerView!!.layoutManager = layoutManager
        recyclerView!!.adapter = searchCategoryRecyclerViewAdapter

        recyclerView!!.setHasFixedSize(true)

        categoryList = arrayListOf<ServiceCategoryModel>()
        val ref = firebaseFirestore.collection("serviceCategories")
        ref.get().addOnSuccessListener { documentSnapshot ->
            if (!documentSnapshot.isEmpty) {
                val documents = documentSnapshot.documents
                for (category in documents) {
                    val data = ServiceCategoryModel(
                        category.id,
                        category["categoryName"].toString(),
                        category["categoryImage"].toString()
                    )
                    categoryList.add(data)

                    searchCategoryRecyclerViewAdapter!!.notifyDataSetChanged()

                    var adapter = ServiceCategoryAdapter(categoryList)
                    recyclerView!!.adapter = adapter
                    adapter.setOnItemClickListener(object :
                        ServiceCategoryAdapter.onItemClickListener {
                        override fun onItemClick(position: Int) {
                            val categoryId = categoryList[position].categoryId
                            val categoryTitle = categoryList[position].categoryName
                            val action =
                                SearchFragmentDirections.actionSearchFragmentToIndividualCategorySPListFragment(
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

}