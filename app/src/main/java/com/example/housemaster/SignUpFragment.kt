package com.example.housemaster

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.housemaster.databinding.FragmentLoginBinding
import com.example.housemaster.databinding.FragmentSigninBinding
import com.example.housemaster.databinding.FragmentSignupBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth

class SignUpFragment : Fragment(R.layout.fragment_signup) {

    private lateinit var signUpBinding: FragmentSignupBinding
    private lateinit var firebaseAuth: FirebaseAuth
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        signUpBinding = FragmentSignupBinding.bind(view)

        //initiate the firebase object
        firebaseAuth = FirebaseAuth.getInstance()

        //this is just to hide ActionBar from the fragment
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
        //this is just to hide BottomNavBar from the fragment
        val view = requireActivity().findViewById<BottomNavigationView>(R.id.bottom_nav)
        view.visibility = View.GONE

        //signup btn on click listener to implement the signup authentication
        signUpBinding.btnSignup.setOnClickListener {
            val email = signUpBinding.signupEmail.text.toString()
            val password = signUpBinding.signupPassword.text.toString()
            val cPassword = signUpBinding.signupCpassword.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty() && cPassword.isNotEmpty()) {
                if (password == cPassword) {
                    firebaseAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener {
                            if (it.isSuccessful) {
                                val action =
                                    SignUpFragmentDirections.actionSignUpFragmentToSignUpSuccessFragment()
                                findNavController().navigate(action)
                            } else {
                                Toast.makeText(
                                    requireContext(),
                                    it.exception.toString(),
                                    Toast.LENGTH_LONG
                                )
                                    .show()
                            }
                        }
                } else {
                    Toast.makeText(requireContext(), "Password is not matching", Toast.LENGTH_LONG)
                        .show()
                }
            } else {
                Toast.makeText(requireContext(), "Empty fields are not allowed", Toast.LENGTH_LONG)
                    .show()
            }
        }

        //if the user already have an account, then click the link to sign in
        signUpBinding.signupMoveToSigninp.setOnClickListener {
            val action = SignUpFragmentDirections.actionSignUpFragmentToSignInFragment()
            findNavController().navigate(action)
        }


    }
}