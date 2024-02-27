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

            if (validateEmail(signUpBinding.signupEmail.text.toString()) && validateFirstName(
                    signUpBinding.signupFname.text.toString()
                ) && validateLastName(
                    signUpBinding.signupLname.text.toString()
                ) && validatePassword(signUpBinding.signupPassword.text.toString()) && validateConPassword(
                    signUpBinding.signupCpassword.text.toString()
                )
            ) {
                if (validatePasswordAndConPassword(
                        signUpBinding.signupPassword.text.toString(),
                        signUpBinding.signupCpassword.text.toString()
                    )
                ) {
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
                    validatePasswordAndConPassword(
                        signUpBinding.signupPassword.text.toString(),
                        signUpBinding.signupCpassword.text.toString()
                    )
                }
            } else {
                validateEmail(signUpBinding.signupEmail.text.toString())
                validateFirstName(signUpBinding.signupFname.text.toString())
                validateLastName(signUpBinding.signupLname.text.toString())
                validatePassword(signUpBinding.signupPassword.text.toString())
                validateConPassword(signUpBinding.signupCpassword.text.toString())
            }
        }

        //if the user already have an account, then click the link to sign in
        signUpBinding.signupMoveToSigninp.setOnClickListener {
            val action = SignUpFragmentDirections.actionSignUpFragmentToSignInFragment()
            findNavController().navigate(action)
        }


    }
/*
    public fun validateFirstName(valPara: String): Boolean {
        var errorMessage: String? = null
        val value: String = valPara
        if (value.isEmpty()) {
            errorMessage = "First Name is Required"

            return false
        }

        if (errorMessage != null) {
            signUpBinding.signupFnameTil.apply {
                isErrorEnabled = true
                error = errorMessage
            }
        }

        return true
    }

    public fun validateLastName(valPar: String): Boolean {
        var errorMessage: String? = null
        val value: String = valPar
        if (value.isEmpty()) {
            errorMessage = "Last Name is Required"

            return false
        }
        if (errorMessage != null) {
            signUpBinding.signupLnameTil.apply {
                isErrorEnabled = true
                error = errorMessage
            }
        }
        return true
    }

    //validate Email
    public fun validateEmail(valPara: String): Boolean {
        var errorMessage: String? = null
        val value: String = valPara
        if (value.isEmpty()) {
            errorMessage = "Email is required"

            return false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(value).matches()) {
            errorMessage = "Invalid Email Address"

            return false
        }
        if (errorMessage != null) {
            signUpBinding.signupEmailTil.apply {
                isErrorEnabled = true
                error = errorMessage
            }
        }

        return true
    }

    public fun validatePassword(valPara: String): Boolean {
        var errorMessage: String? = null
        val value: String = valPara
        if (value.isEmpty()) {
            errorMessage = "Password is required"

            return false
        } else if (value.length < 6) {
            errorMessage = "Password must be six characters long"

            return false
        }
        if (errorMessage != null) {
            signUpBinding.signupPasswordTil.apply {
                isErrorEnabled = true
                error = errorMessage
            }
        }

        return true
    }

    public fun validateConPassword(valPara: String): Boolean {
        var errorMessage: String? = null
        val value: String = valPara
        if (value.isEmpty()) {
            errorMessage = "Confirm Password is required"
            return false
        } else if (value.length < 6) {
            errorMessage = "Confirm Password must be six characters long"

            return false
        }
        if (errorMessage != null) {
            signUpBinding.signupCpasswordTil.apply {
                isErrorEnabled = true
                error = errorMessage
            }
        }

        return true
    }

    public fun validatePasswordAndConPassword(valPara1: String, valPar2: String): Boolean {
        var errorMessage: String? = null
        val regPassword: String = valPara1
        val regConPassword: String = valPar2

        if (regPassword != regConPassword) {
            errorMessage = "Confirm Password doesn't match with the Password"

            return false
        }
        if (errorMessage != null) {
            signUpBinding.signupCpasswordTil.apply {
                isErrorEnabled = true
                error = errorMessage
            }
        }

        return true
    }

*/





    public fun validateFirstName(valPara: String): Boolean {
        var errorMessage: String? = null
        val value: String = valPara
        if (value.isEmpty()) {
            errorMessage = "First Name is Required"
            signUpBinding.signupFnameTil.apply {
                isErrorEnabled = true
                error = errorMessage
            }
            return false
        }

        return true
    }

    public fun validateLastName(valPar: String): Boolean {
        var errorMessage: String? = null
        val value: String = valPar
        if (value.isEmpty()) {
            errorMessage = "Last Name is Required"
            signUpBinding.signupLnameTil.apply {
                isErrorEnabled = true
                error = errorMessage
            }
            return false
        }

        return true
    }

    //validate Email
    public fun validateEmail(valPara: String): Boolean {
        var errorMessage: String? = null
        val value: String = valPara
        if (value.isEmpty()) {
            errorMessage = "Email is required"
            signUpBinding.signupEmailTil.apply {
                isErrorEnabled = true
                error = errorMessage
            }
            return false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(value).matches()) {
            errorMessage = "Invalid Email Address"
            signUpBinding.signupEmailTil.apply {
                isErrorEnabled = true
                error = errorMessage
            }
            return false
        }


        return true
    }

    public fun validatePassword(valPara: String): Boolean {
        var errorMessage: String? = null
        val value: String = valPara
        if (value.isEmpty()) {
            errorMessage = "Password is required"
            signUpBinding.signupPasswordTil.apply {
                isErrorEnabled = true
                error = errorMessage
            }
            return false
        } else if (value.length < 6) {
            errorMessage = "Password must be six characters long"
            signUpBinding.signupPasswordTil.apply {
                isErrorEnabled = true
                error = errorMessage
            }
            return false
        }

        return true
    }

    public fun validateConPassword(valPara: String): Boolean {
        var errorMessage: String? = null
        val value: String = valPara
        if (value.isEmpty()) {
            errorMessage = "Confirm Password is required"
            return false
        } else if (value.length < 6) {
            errorMessage = "Confirm Password must be six characters long"
            signUpBinding.signupCpasswordTil.apply {
                isErrorEnabled = true
                error = errorMessage
            }
            return false
        }


        return true
    }

    public fun validatePasswordAndConPassword(valPara1: String, valPar2: String): Boolean {
        var errorMessage: String? = null
        val regPassword: String = valPara1
        val regConPassword: String = valPar2

        if (regPassword != regConPassword) {
            errorMessage = "Confirm Password doesn't match with the Password"
            signUpBinding.signupCpasswordTil.apply {
                isErrorEnabled = true
                error = errorMessage
            }
            return false
        }


        return true
    }





}