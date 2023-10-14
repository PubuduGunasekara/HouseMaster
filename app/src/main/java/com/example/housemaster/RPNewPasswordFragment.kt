package com.example.housemaster

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.housemaster.databinding.FragmentResetPasswordBinding
import com.example.housemaster.databinding.FragmentRpNewPasswordBinding
import com.google.android.material.bottomnavigation.BottomNavigationView


class RPNewPasswordFragment : Fragment(R.layout.fragment_rp_new_password) {

    private lateinit var rpNewPasswordBinding: FragmentRpNewPasswordBinding


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rpNewPasswordBinding = FragmentRpNewPasswordBinding.bind(view)

        //this is just to hide ActionBar from the fragment
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
        //this is just to hide BottomNavBar from the fragment
        val view = requireActivity().findViewById<BottomNavigationView>(R.id.bottom_nav)
        view.visibility = View.GONE


        rpNewPasswordBinding.buttonConfirm.setOnClickListener {
            val username = rpNewPasswordBinding.editTextUserName.text.toString()
            val password = rpNewPasswordBinding.editTextPassword.text.toString()
            val action =
                LoginFragmentDirections.actionLoginFragmentToWelcomeFragment(username, password)
            findNavController().navigate(action)
        }


    }
}