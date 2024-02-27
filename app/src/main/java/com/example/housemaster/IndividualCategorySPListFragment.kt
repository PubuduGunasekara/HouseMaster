package com.example.housemaster

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.housemaster.databinding.FragmentHomeBinding
import com.example.housemaster.databinding.FragmentIndividualCategoryBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class IndividualCategorySPListFragment : Fragment(R.layout.fragment_individual_category) {

    private lateinit var icViewBinding: FragmentIndividualCategoryBinding
    private val args: IndividualCategorySPListFragmentArgs by navArgs()
    private lateinit var firebaseFirestore: FirebaseFirestore


    private lateinit var spRecyclerView: RecyclerView
    private lateinit var spArrayList: ArrayList<ServiceProviderModel>
    lateinit var spName: Array<String>
    lateinit var spImage: Array<Int>
    lateinit var spRatings: Array<String>
    lateinit var spId: Array<String>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        firebaseFirestore = FirebaseFirestore.getInstance()
        super.onViewCreated(view, savedInstanceState)
        icViewBinding = FragmentIndividualCategoryBinding.bind(view)


        (activity as AppCompatActivity).supportActionBar?.title = args.categoryTitle

        getSpData()

    }

    private fun getSpData() {

        spRecyclerView = icViewBinding.individualCategoryRV
        spRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        spRecyclerView.setHasFixedSize(true)

        spArrayList = arrayListOf<ServiceProviderModel>()
        val ref = firebaseFirestore.collection("users")
            .whereEqualTo("selectedService", args.categoryTitle)
        ref.get().addOnSuccessListener { documentSnapshot ->
            if (!documentSnapshot.isEmpty) {
                val documents = documentSnapshot.documents
                for (spData in documents) {
                    val data = ServiceProviderModel(
                        spData.id,
                        spData["shopName"].toString(),
                        spData["image"].toString(),
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
                            val spImage = spArrayList[position].spImage
                            val action =
                                IndividualCategorySPListFragmentDirections.actionIndividualCategorySPListFragmentToServiceItemFragment(
                                    spId, spCategory, spName, spRatings, spImage
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