package com.example.housemaster

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.housemaster.databinding.FragmentLoginBinding
import com.example.housemaster.databinding.FragmentSigninBinding
import com.example.housemaster.databinding.FragmentSignupBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class SignUpFragment : Fragment(R.layout.fragment_signup) {

    private lateinit var signUpBinding: FragmentSignupBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        signUpBinding = FragmentSignupBinding.bind(view)

        //this is just to hide ActionBar from the fragment
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
        //this is just to hide BottomNavBar from the fragment
        val view = requireActivity().findViewById<BottomNavigationView>(R.id.bottom_nav)
        view.visibility = View.GONE

        //sign in button action, signin screen to sign up success screen
        signUpBinding.btnSignUp.setOnClickListener {
            val action = SignUpFragmentDirections.actionSignUpFragmentToSignUpSuccessFragment()
            findNavController().navigate(action)
        }


    }
}