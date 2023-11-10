package com.example.housemaster

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.housemaster.databinding.FragmentHomeBinding
import com.example.housemaster.databinding.FragmentIndividualCategoryBinding
import com.example.housemaster.databinding.FragmentServiceItemBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class ServiceItemFragment : Fragment(R.layout.fragment_service_item) {
    private val args: ServiceItemFragmentArgs by navArgs()
    private lateinit var serviceItemBinding: FragmentServiceItemBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        serviceItemBinding = FragmentServiceItemBinding.bind(view)

        (activity as AppCompatActivity).supportActionBar?.title = args.serviceItemId

        //this is just to hide ActionBar from the fragment
        (activity as AppCompatActivity?)!!.supportActionBar!!.show()
        //this is just to hide BottomNavBar from the fragment
        val view = requireActivity().findViewById<BottomNavigationView>(R.id.bottom_nav)
        view.visibility = View.VISIBLE

        //serviceItemBinding.serviceItemId.text = args.serviceItemId

        serviceItemBinding.btnBookApt.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToLoginFragment()
            findNavController().navigate(action)
        }


    }
}