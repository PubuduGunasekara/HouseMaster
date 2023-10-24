package com.example.housemaster

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.housemaster.databinding.FragmentSigninBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import java.lang.Exception


class SignInFragment : Fragment(R.layout.fragment_signin) {

    private lateinit var signInBinding: FragmentSigninBinding
    private lateinit var firebaseAuth: FirebaseAuth


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        signInBinding = FragmentSigninBinding.bind(view)

        firebaseAuth = FirebaseAuth.getInstance()

        signInBinding.signinMoveToSignup.setOnClickListener {
            val action = SignInFragmentDirections.actionSignInFragmentToSignUpFragment()
            findNavController().navigate(action)
        }




        signInBinding.btnSignIn.setOnClickListener {
            val email = signInBinding.signinEmail.text.toString()
            val password = signInBinding.signinPassword.text.toString()



            if (validateEmail() && validatePassword()) {

                firebaseAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            val action =
                                SignInFragmentDirections.actionSignInFragmentToHomeFragment()
                            findNavController().navigate(action)
                        } else {
                            /*signInBinding.firebaseErrorTil.apply {
                                isErrorEnabled = true
                                if (it.exception?.message.toString() == "ERROR_INVALID_CREDENTIAL")
                                    error = "INVALID LOGIN CREDENTIAL";
                            }*/
                            Toast.makeText(
                                requireContext(),
                                "" + it.exception?.message.toString(),
                                Toast.LENGTH_LONG
                            )
                                .show()
                        }
                    }

            } else {
                validateEmail()
                validatePassword()
            }
        }

        //this function will check if the user is already logged into the app, and authenticate the user and navigate to the relevent screen
        fun onStart() {
            super.onStart()
            if (firebaseAuth.currentUser != null) {
                val action = SignInFragmentDirections.actionSignInFragmentToHomeFragment()
                findNavController().navigate(action)
            }
        }

        //navigate to forgot password page
        signInBinding.signinForgotPassword.setOnClickListener {
            val action = SignInFragmentDirections.actionSignInFragmentToResetPasswordFragment()
            findNavController().navigate(action)
        }


        //this is just to hide ActionBar from the fragment
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
        //this is just to hide BottomNavBar from the fragment
        val view = requireActivity().findViewById<BottomNavigationView>(R.id.bottom_nav)
        view.visibility = View.GONE


    }

    //validate Email
    private fun validateEmail(): Boolean {
        var errorMessage: String? = null
        val value: String = signInBinding.signinEmail.text.toString()
        if (value.isEmpty()) {
            errorMessage = "Email is required"
        } else if (!Patterns.EMAIL_ADDRESS.matcher(value).matches()) {
            errorMessage = "Invalid Email Address"
        }

        if (errorMessage != null) {
            signInBinding.signinEmailTil.apply {
                isErrorEnabled = true
                error = errorMessage
            }
        }
        return errorMessage == null
    }

    private fun validatePassword(): Boolean {
        var errorMessage: String? = null
        val value: String = signInBinding.signinPassword.text.toString()
        if (value.isEmpty()) {
            errorMessage = "Password is required"
        }
        if (errorMessage != null) {
            signInBinding.signinPasswordTil.apply {
                isErrorEnabled = true
                error = errorMessage
            }
        }

        return errorMessage == null
    }

}