package com.example.housemaster

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.marginBottom
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.housemaster.databinding.FragmentHomeBinding
import com.example.housemaster.databinding.FragmentIndividualCategoryBinding
import com.example.housemaster.databinding.FragmentServiceItemBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.picasso.Picasso

class ServiceItemFragment : Fragment(R.layout.fragment_service_item) {
    private val args: ServiceItemFragmentArgs by navArgs()
    private lateinit var serviceItemBinding: FragmentServiceItemBinding
    private lateinit var firebaseFirestore: FirebaseFirestore
    lateinit var serviceType: Array<String>
    lateinit var servicePrice: Array<String>
    lateinit var serviceTypeId: Array<String>
    var sTypeIdGlobal: String = ""
    var sTypeTitleGlobal: String = ""

    private var serviceTypeSharedPre: String? = null
    private lateinit var sharedPreferences: SharedPreferences

    private lateinit var serviceTypeRecyclerView: RecyclerView
    private lateinit var serviceTypeArrayList: ArrayList<ServiceItemTypeModel>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        firebaseFirestore = FirebaseFirestore.getInstance()
        super.onViewCreated(view, savedInstanceState)
        serviceItemBinding = FragmentServiceItemBinding.bind(view)

        (activity as AppCompatActivity).supportActionBar?.title = args.spName

        //this is just to hide ActionBar from the fragment
        (activity as AppCompatActivity?)!!.supportActionBar!!.show()
        //this is just to hide BottomNavBar from the fragment
        val view = requireActivity().findViewById<BottomNavigationView>(R.id.bottom_nav)
        view.visibility = View.VISIBLE
        serviceItemBinding.loadingSpinner.setVisibility(View.VISIBLE)

        //shared preference
        sharedPreferences = requireContext().getSharedPreferences(
            "com.example.housemaster",
            Context.MODE_PRIVATE
        )




        serviceItemBinding.siCategory.text = args.serviceCategory
        serviceItemBinding.siRatings.text = args.spRatings + "/5"
        serviceItemBinding.siTitle.text = args.spName
        Picasso.get().load(args.spImage).into(serviceItemBinding.siCoverImage)

        serviceItemBinding.loadingSpinner.setVisibility(View.GONE)

        /*
                serviceType = arrayOf(
                    "id1",
                    "hello2",
                    "hello3",
                    "hello4",
                    "hello5",
                    "hello6",
                    "hello7",
                    "hello8",
                    "hello9",
                    "hello10",
                    "hello11",
                    "hello12",
                    "hello13",
                    "hello14",
                    "hello15",
                    "hello16",
                    "hello17",
                    "hello18",
                    "hello19",
                    "hello20",
                )

                servicePrice = arrayOf(
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

                serviceTypeId = arrayOf(
                    "id1",
                    "hello2",
                    "hello3",
                    "hello4",
                    "hello5",
                    "hello6",
                    "hello7",
                    "hello8",
                    "hello9",
                    "hello10",
                    "hello11",
                    "hello12",
                    "hello13",
                    "hello14",
                    "hello15",
                    "hello16",
                    "hello17",
                    "hello18",
                    "hello19",
                    "hello20",
                )

        */
        getServiceTypeData()

        serviceItemBinding.btnContinue.setOnClickListener {

            if (sTypeIdGlobal.isNotEmpty() && sTypeIdGlobal != "" && sTypeTitleGlobal != "" && sTypeTitleGlobal.isNotEmpty()) {
                navigateToNextPage()
            } else {
                Toast.makeText(
                    requireContext(),
                    "Select a service to continue",
                    Toast.LENGTH_LONG
                ).show()
            }

        }


    }

    private fun navigateToNextPage() {
        val action =
            ServiceItemFragmentDirections.actionServiceItemFragmentToBookAppointmentFragment(
                sTypeIdGlobal, sTypeTitleGlobal
            )
        findNavController().navigate(action)
    }

    private fun getServiceTypeData() {
        serviceItemBinding.loadingSpinner.setVisibility(View.VISIBLE)
        serviceTypeRecyclerView = serviceItemBinding.siServiceTypesRV
        serviceTypeRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        serviceTypeRecyclerView.setHasFixedSize(true)

        serviceTypeArrayList = arrayListOf<ServiceItemTypeModel>()

        val ref = firebaseFirestore.collection("services").document(args.serviceItemId)

        ref.get().addOnSuccessListener { documentSnapshot ->

            if (documentSnapshot.exists()) {

                val dataArray = documentSnapshot.get("myServices") as? ArrayList<Any>

                if (dataArray != null) {

                    for (item in dataArray.indices) {

                        val dataArray2 = dataArray[item] as HashMap<*, *>

                        val services = ServiceItemTypeModel(
                            dataArray2["price"].toString(),
                            dataArray2["serviceName"].toString(),
                            dataArray2["price"].toString().toDouble(),
                        )
                        serviceTypeArrayList.add(services)


                    }
                }


                var adapter = ServiceItemTypeAdapter(serviceTypeArrayList)
                serviceTypeRecyclerView.adapter = adapter
                serviceItemBinding.loadingSpinner.setVisibility(View.GONE)
                adapter.setOnItemClickListener(object :
                    ServiceItemTypeAdapter.onItemClickListener {
                    override fun onItemClick(position: Int, clickedView: View) {
                        val sTypeId = serviceTypeArrayList[position].serviceTypeId
                        val sTypeTitle = serviceTypeArrayList[position].typeTitle
                        val sTypePrice = serviceTypeArrayList[position].typePrice
                        sharedPreferences.edit()
                            .putString("service_provider_id", args.serviceItemId)
                            .apply()
                        sharedPreferences.edit()
                            .putString("service_provider_name", args.spName)
                            .apply()
                        sharedPreferences.edit().putString("service_type_id", sTypeId).apply()
                        sharedPreferences.edit().putString("service_type_title", sTypeTitle)
                            .apply()
                        sharedPreferences.edit()
                            .putString("service_item_category", args.serviceCategory)
                            .apply()
                        sharedPreferences.edit().putFloat(
                            "service_type_price",
                            sTypePrice.toFloat()
                        )
                            .apply()
                        if (sTypeTitle != "" && sTypeId != "") {
                            sTypeTitleGlobal = sTypeTitle
                            sTypeIdGlobal = sTypeId


                            //clickedView.

                            //serviceItemBinding.btnContinue.setText("$sTypeTitle")
                        }


                    }
                }
                )


            } else {

            }

        }.addOnFailureListener {

            Log.e("firebase", "Error getting data", it)
        }
    }
}