package com.example.housemaster

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.housemaster.databinding.FragmentResetPasswordBinding
import com.google.android.material.bottomnavigation.BottomNavigationView


class ResetPasswordFragment : Fragment(R.layout.fragment_reset_password) {

    private lateinit var resetPasswordBinding: FragmentResetPasswordBinding


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        resetPasswordBinding = FragmentResetPasswordBinding.bind(view)

        //this is just to hide ActionBar from the fragment
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
        //this is just to hide BottomNavBar from the fragment
        val view = requireActivity().findViewById<BottomNavigationView>(R.id.bottom_nav)
        view.visibility = View.GONE


        resetPasswordBinding.btnResetLinkSend.setOnClickListener {

        }


    }
}