package com.example.housemaster

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.housemaster.databinding.FragmentAppointmentItemBinding
import com.example.housemaster.databinding.FragmentHomeBinding
import com.example.housemaster.databinding.FragmentIndividualCategoryBinding
import com.example.housemaster.databinding.FragmentServiceItemBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.firestore.FirebaseFirestore

class AppointmentItemFragment : Fragment(R.layout.fragment_appointment_item) {

    private lateinit var fragmentApptItemFragment: FragmentAppointmentItemBinding
    private lateinit var firebaseFirestore: FirebaseFirestore
    lateinit var serviceType: Array<String>
    lateinit var servicePrice: Array<String>
    lateinit var serviceTypeId: Array<String>

    private var serviceTypeSharedPre: String? = null
    private lateinit var sharedPreferences: SharedPreferences

    private lateinit var serviceTypeRecyclerView: RecyclerView
    private lateinit var serviceTypeArrayList: ArrayList<ServiceItemTypeModel>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        firebaseFirestore = FirebaseFirestore.getInstance()
        super.onViewCreated(view, savedInstanceState)
        fragmentApptItemFragment = FragmentAppointmentItemBinding.bind(view)

        //this is just to hide ActionBar from the fragment
        (activity as AppCompatActivity?)!!.supportActionBar!!.show()
        //this is just to hide BottomNavBar from the fragment
        val view = requireActivity().findViewById<BottomNavigationView>(R.id.bottom_nav)
        view.visibility = View.VISIBLE


        fragmentApptItemFragment.btnBookApt.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToLoginFragment()
            findNavController().navigate(action)
        }

    }
}