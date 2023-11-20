package com.example.housemaster

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.housemaster.databinding.FragmentHomeBinding
import com.example.housemaster.databinding.FragmentIndividualCategoryBinding
import com.example.housemaster.databinding.FragmentServiceItemBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class ServiceItemFragment : Fragment(R.layout.fragment_service_item) {
    private val args: ServiceItemFragmentArgs by navArgs()
    private lateinit var serviceItemBinding: FragmentServiceItemBinding
    lateinit var serviceType: Array<String>
    lateinit var servicePrice: Array<String>
    lateinit var serviceTypeId: Array<String>

    private var serviceTypeSharedPre: String? = null
    private lateinit var sharedPreferences: SharedPreferences

    private lateinit var serviceTypeRecyclerView: RecyclerView
    private lateinit var serviceTypeArrayList: ArrayList<ServiceItemTypeModel>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        serviceItemBinding = FragmentServiceItemBinding.bind(view)

        (activity as AppCompatActivity).supportActionBar?.title = args.serviceItemId

        //this is just to hide ActionBar from the fragment
        (activity as AppCompatActivity?)!!.supportActionBar!!.show()
        //this is just to hide BottomNavBar from the fragment
        val view = requireActivity().findViewById<BottomNavigationView>(R.id.bottom_nav)
        view.visibility = View.VISIBLE


        //shared preference
        sharedPreferences = requireContext().getSharedPreferences(
            "com.example.housemaster",
            Context.MODE_PRIVATE
        )


        //serviceItemBinding.serviceItemId.text = args.serviceItemId


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

        serviceTypeRecyclerView = serviceItemBinding.siServiceTypesRV
        serviceTypeRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        serviceTypeRecyclerView.setHasFixedSize(true)

        serviceTypeArrayList = arrayListOf<ServiceItemTypeModel>()
        getServiceTypeData()

        var adapter = ServiceItemTypeAdapter(serviceTypeArrayList)
        serviceTypeRecyclerView.adapter = adapter
        adapter.setOnItemClickListener(object : ServiceItemTypeAdapter.onItemClickListener {
            override fun onItemClick(position: Int) {
                val sTypeId = serviceTypeArrayList[position].serviceTypeId
                val sTypeTitle = serviceTypeArrayList[position].typeTitle

                sharedPreferences.edit().putString("service_type_id", sTypeId).apply()
                sharedPreferences.edit().putString("service_type_title", sTypeTitle).apply()

                val action =
                    ServiceItemFragmentDirections.actionServiceItemFragmentToBookAppointmentFragment(
                        sTypeId, sTypeTitle
                    )
                findNavController().navigate(action)
            }
        }
        )

        serviceItemBinding.btnBookApt.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToLoginFragment()
            findNavController().navigate(action)
        }


    }

    private fun getServiceTypeData() {
        for (i in serviceTypeId.indices) {
            val data = ServiceItemTypeModel(serviceTypeId[i], serviceType[i], servicePrice[i])
            serviceTypeArrayList.add(data)
        }
    }
}