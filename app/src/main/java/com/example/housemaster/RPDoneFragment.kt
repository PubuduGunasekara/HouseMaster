package com.example.housemaster

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.housemaster.databinding.FragmentResetPasswordBinding
import com.example.housemaster.databinding.FragmentRpDoneBinding
import com.google.android.material.bottomnavigation.BottomNavigationView


class RPDoneFragment : Fragment(R.layout.fragment_rp_done) {

    private lateinit var rpDoneBinding: FragmentRpDoneBinding


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rpDoneBinding = FragmentRpDoneBinding.bind(view)

        //this is just to hide ActionBar from the fragment
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
        //this is just to hide BottomNavBar from the fragment
        val view = requireActivity().findViewById<BottomNavigationView>(R.id.bottom_nav)
        view.visibility = View.GONE


        rpDoneBinding.btnRpDoneSignIn.setOnClickListener {
            val action =
                RPDoneFragmentDirections.actionRPDoneFragmentToSignInFragment()
            findNavController().navigate(action)
        }


    }
}