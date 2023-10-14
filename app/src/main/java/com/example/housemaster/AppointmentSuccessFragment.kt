package com.example.housemaster

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.housemaster.databinding.FragmentAppointmentPayBinding
import com.example.housemaster.databinding.FragmentAppointmentSuccessBinding
import com.example.housemaster.databinding.FragmentBookAppointmentBinding
import com.example.housemaster.databinding.FragmentHomeBinding
import com.example.housemaster.databinding.FragmentIndividualCategoryBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class AppointmentSuccessFragment : Fragment(R.layout.fragment_appointment_success) {

    private lateinit var appointmentSuccessBinding: FragmentAppointmentSuccessBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        appointmentSuccessBinding = FragmentAppointmentSuccessBinding.bind(view)

        //this is just to hide ActionBar from the fragment
        (activity as AppCompatActivity?)!!.supportActionBar!!.show()
        //this is just to hide BottomNavBar from the fragment
        val view = requireActivity().findViewById<BottomNavigationView>(R.id.bottom_nav)
        view.visibility = View.VISIBLE

        appointmentSuccessBinding.btnAptDone.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToLoginFragment()
            findNavController().navigate(action)
        }


    }
}