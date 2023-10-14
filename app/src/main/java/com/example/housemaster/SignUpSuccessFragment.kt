package com.example.housemaster

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.housemaster.databinding.FragmentSignupSuccessBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class SignUpSuccessFragment : Fragment(R.layout.fragment_signup_success) {

    private lateinit var signUpSuccessBinding: FragmentSignupSuccessBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        signUpSuccessBinding = FragmentSignupSuccessBinding.bind(view)

        //this is just to hide ActionBar from the fragment
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
        //this is just to hide BottomNavBar from the fragment
        val view = requireActivity().findViewById<BottomNavigationView>(R.id.bottom_nav)
        view.visibility = View.GONE

        //sign in button action, sign up success screen to sign in screen
        signUpSuccessBinding.btnDone.setOnClickListener {
            val action =
                SignUpSuccessFragmentDirections.actionSignUpSuccessFragmentToSignInFragment()
            findNavController().navigate(action)
        }


    }
}