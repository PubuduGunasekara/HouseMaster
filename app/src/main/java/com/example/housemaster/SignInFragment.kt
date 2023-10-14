package com.example.housemaster

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.housemaster.databinding.FragmentSigninBinding
import com.google.android.material.bottomnavigation.BottomNavigationView


class SignInFragment : Fragment(R.layout.fragment_signin) {

    private lateinit var signInBinding: FragmentSigninBinding


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        signInBinding = FragmentSigninBinding.bind(view)

        //this is just to hide ActionBar from the fragment
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
        //this is just to hide BottomNavBar from the fragment
        val view = requireActivity().findViewById<BottomNavigationView>(R.id.bottom_nav)
        view.visibility = View.GONE


        //sign in button action, signin screen to home screen
        signInBinding.btnSignIn.setOnClickListener {
            val action =
                SignInFragmentDirections.actionSignInFragmentToHomeFragment()
            findNavController().navigate(action)
        }

        //sign in button action, signin screen to sign up screen
        signInBinding.btnSignUp.setOnClickListener {
            val action =
                SignInFragmentDirections.actionSignInFragmentToSignUpFragment()
            findNavController().navigate(action)
        }

        //sign in button action, signin screen to reset password screen
        signInBinding.btnResetPw.setOnClickListener {
            val action =
                SignInFragmentDirections.actionSignInFragmentToResetPasswordFragment()
            findNavController().navigate(action)
        }


    }
}