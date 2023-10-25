package com.example.housemaster

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.housemaster.databinding.FragmentLoginBinding
import com.example.housemaster.databinding.FragmentSigninBinding
import com.example.housemaster.databinding.FragmentSignupBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
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

            if (validateEmail() && validateFirstName() && validateLastName() && validatePassword() && validateConPassword()) {
                if (validatePasswordAndConPassword()) {
                    firebaseAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener {
                            if (it.isSuccessful) {
                                MaterialAlertDialogBuilder(requireContext()).setTitle("Success")
                                    .setCancelable(false)
                                    .setMessage("You have successfully registered to the HouseMaster service!")
                                    .setPositiveButton("Done") { dialog_, which ->

                                        val action =
                                            SignUpFragmentDirections.actionSignUpFragmentToSignInFragment()
                                        findNavController().navigate(action)


                                    }.show()

                            } else {
                                Toast.makeText(
                                    requireContext(),
                                    it.exception?.message.toString(),
                                    Toast.LENGTH_LONG
                                )
                                    .show()
                            }
                        }
                } else {
                    validatePasswordAndConPassword()
                }
            } else {
                validateEmail()
                validateFirstName()
                validateLastName()
                validatePassword()
                validateConPassword()
            }
        }

        //if the user already have an account, then click the link to sign in
        signUpBinding.signupMoveToSigninp.setOnClickListener {
            val action = SignUpFragmentDirections.actionSignUpFragmentToSignInFragment()
            findNavController().navigate(action)
        }


    }

    private fun validateFirstName(): Boolean {
        var errorMessage: String? = null
        val value: String = signUpBinding.signupFname.text.toString()
        if (value.isEmpty()) {
            errorMessage = "First Name is Required"
        }
        if (errorMessage != null) {
            signUpBinding.signupFnameTil.apply {
                isErrorEnabled = true
                error = errorMessage
            }
        }
        return errorMessage == null
    }

    private fun validateLastName(): Boolean {
        var errorMessage: String? = null
        val value: String = signUpBinding.signupLname.text.toString()
        if (value.isEmpty()) {
            errorMessage = "Last Name is Required"
        }

        if (errorMessage != null) {
            signUpBinding.signupLnameTil.apply {
                isErrorEnabled = true
                error = errorMessage
            }
        }
        return errorMessage == null
    }

    //validate Email
    private fun validateEmail(): Boolean {
        var errorMessage: String? = null
        val value: String = signUpBinding.signupEmail.text.toString()
        if (value.isEmpty()) {
            errorMessage = "Email is required"
        } else if (!Patterns.EMAIL_ADDRESS.matcher(value).matches()) {
            errorMessage = "Invalid Email Address"
        }

        if (errorMessage != null) {
            signUpBinding.signupEmailTil.apply {
                isErrorEnabled = true
                error = errorMessage
            }
        }
        return errorMessage == null
    }

    private fun validatePassword(): Boolean {
        var errorMessage: String? = null
        val value: String = signUpBinding.signupPassword.text.toString()
        if (value.isEmpty()) {
            errorMessage = "Password is required"
        } else if (value.length < 6) {
            errorMessage = "Password must be six characters long"
        }
        if (errorMessage != null) {
            signUpBinding.signupPasswordTil.apply {
                isErrorEnabled = true
                error = errorMessage
            }
        }

        return errorMessage == null
    }

    private fun validateConPassword(): Boolean {
        var errorMessage: String? = null
        val value: String = signUpBinding.signupCpassword.text.toString()
        if (value.isEmpty()) {
            errorMessage = "Confirm Password is required"
        } else if (value.length < 6) {
            errorMessage = "Confirm Password must be six characters long"
        }
        if (errorMessage != null) {
            signUpBinding.signupCpasswordTil.apply {
                isErrorEnabled = true
                error = errorMessage
            }
        }

        return errorMessage == null
    }

    private fun validatePasswordAndConPassword(): Boolean {
        var errorMessage: String? = null
        val regPassword: String = signUpBinding.signupPassword.text.toString()
        val regConPassword: String = signUpBinding.signupCpassword.text.toString()

        if (regPassword != regConPassword) {
            errorMessage = "Confirm Password doesn't match with the Password"
        }

        if (errorMessage != null) {
            signUpBinding.signupCpasswordTil.apply {
                isErrorEnabled = true
                error = errorMessage
            }
        }

        return errorMessage == null
    }
}