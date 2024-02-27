package com.example.housemaster

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.housemaster.databinding.FragmentAddCardsAndAccountsBinding
import com.example.housemaster.databinding.FragmentEditProfileBinding
import com.example.housemaster.databinding.FragmentHomeBinding
import com.example.housemaster.databinding.FragmentListCardsBinding
import com.example.housemaster.databinding.FragmentProfileBinding
import com.example.housemaster.databinding.FragmentSearchBinding
import com.example.housemaster.databinding.FragmentSettingsBinding
import com.example.housemaster.databinding.FragmentTermsBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class AddCardsAndAccountsFromRBCFragment : Fragment(R.layout.fragment_add_cards_and_accounts_from_rbc) {

    private lateinit var addCardsAndAccountsBinding: FragmentAddCardsAndAccountsBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var authId: String
    private var userAccCardDetails: userAccCardModel? = null
    private lateinit var database: DatabaseReference
    private var dbDataRetreive = Firebase.firestore

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addCardsAndAccountsBinding = FragmentAddCardsAndAccountsBinding.bind(view)

        auth = FirebaseAuth.getInstance()


        val months = resources.getStringArray(R.array.months)
        val monthsArrayAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_list_item, months)
        addCardsAndAccountsBinding.expirationMonth.setAdapter(monthsArrayAdapter)

        val years = resources.getStringArray(R.array.years)
        val yearsArrayAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_list_item, years)
        addCardsAndAccountsBinding.expirationYear.setAdapter(yearsArrayAdapter)


        addCardsAndAccountsBinding.addCardBtn.setOnClickListener {
            val action =
                AddCardsAndAccountsFragmentDirections.actionAddCardsAndAccountsFragmentToListCardsFragment()
            findNavController().navigate(action)
        }


        addCardsAndAccountsBinding.addCardBtn.setOnClickListener {
            val nameOnCard = addCardsAndAccountsBinding.cardName.text.toString()
            val cardNumber = addCardsAndAccountsBinding.cardNumber.text.toString()
            val expMonth = addCardsAndAccountsBinding.expirationMonth.text.toString()
            val expYear = addCardsAndAccountsBinding.expirationYear.text.toString()
            val cvv = addCardsAndAccountsBinding.secNumber.text.toString()




            if (validateNameOnCard(addCardsAndAccountsBinding.cardName.text.toString()) && validateCardNumber(
                    addCardsAndAccountsBinding.cardNumber.text.toString()
                ) && validateExpMonth(addCardsAndAccountsBinding.expirationMonth.text.toString()) && validateExpYear(
                    addCardsAndAccountsBinding.expirationYear.text.toString()
                ) && validateCVV(addCardsAndAccountsBinding.secNumber.text.toString())
            ) {


                if (auth.currentUser != null) {

                    //create a record
                    authId = auth.currentUser!!.uid



                    userAccCardDetails =
                        userAccCardModel(
                            nameOnCard, cardNumber, expMonth, expYear, cvv
                        )

                    val cardDetailMap = hashMapOf(
                        "userId" to authId,
                        "nameOnCard" to nameOnCard,
                        "cardNumber" to cardNumber,
                        "expMonth" to expMonth,
                        "expYear" to expYear,
                        "cvv" to cvv
                    )

                    dbDataRetreive.collection("CustomerCardAccountDetails").document(authId)
                        .set(cardDetailMap)
                        .addOnSuccessListener {

                            MaterialAlertDialogBuilder(requireContext()).setTitle("Success")
                                .setCancelable(false)
                                .setMessage("Save changes successfully")
                                .setPositiveButton("Done") { dialog_, which ->

                                    val action =
                                        AddCardsAndAccountsFragmentDirections.actionAddCardsAndAccountsFragmentToListCardsFragment()
                                    findNavController().navigate(action)


                                }.show()


                            addCardsAndAccountsBinding.cardName.text?.clear()
                            addCardsAndAccountsBinding.cardNumber.text?.clear()
                            addCardsAndAccountsBinding.expirationYear.text?.clear()
                            addCardsAndAccountsBinding.expirationMonth.text?.clear()

                            addCardsAndAccountsBinding.secNumber.text?.clear()


                        }
                        .addOnFailureListener {
                            Toast.makeText(requireContext(), "Failed", Toast.LENGTH_LONG).show()

                        }


                } else {
//please log in again
                }


            } else {
                validateCardNumber(addCardsAndAccountsBinding.cardNumber.text.toString())
                validateNameOnCard(addCardsAndAccountsBinding.cardName.text.toString())
                validateExpMonth(addCardsAndAccountsBinding.expirationMonth.text.toString())
                validateExpYear(addCardsAndAccountsBinding.expirationYear.text.toString())
                validateCVV(addCardsAndAccountsBinding.secNumber.text.toString())
            }
        }


    }

    /*public fun validateNameOnCard(valPara: String): Boolean {
        var errorMessage: String? = null
        val value: String = valPara
        if (value.isEmpty()) {
            errorMessage = "Name is Required"

            return false
        }
        if (errorMessage != null) {
            addCardsAndAccountsBinding.cardNameTil.apply {
                isErrorEnabled = true
                error = errorMessage
            }
        }
        return true
    }
*/
    public fun validateNameOnCard(valPara: String): Boolean {
        var errorMessage: String? = null
        val value: String = valPara
        if (value.isEmpty()) {
            errorMessage = "Name is Required"
            addCardsAndAccountsBinding.cardNameTil.apply {
                isErrorEnabled = true
                error = errorMessage
            }
            return false
        }

        return true
    }

   /* public fun validateCardNumber(valPara: String): Boolean {
        var errorMessage: String? = null
        val value: String = valPara
        if (value.isEmpty()) {
            errorMessage = "Card Number is Required"

            return false
        }
        if (errorMessage != null) {
            addCardsAndAccountsBinding.cardNumberTil.apply {
                isErrorEnabled = true
                error = errorMessage
            }
        }
        return true
    }
*/
    public fun validateCardNumber(valPara: String): Boolean {
        var errorMessage: String? = null
        val value: String = valPara
        if (value.isEmpty()) {
            errorMessage = "Card Number is Required"
            addCardsAndAccountsBinding.cardNumberTil.apply {
                isErrorEnabled = true
                error = errorMessage
            }
            return false
        }
        return true
    }

   /* public fun validateCVV(valPara: String): Boolean {
        var errorMessage: String? = null
        val value: String = valPara
        if (value.isEmpty()) {
            errorMessage = "CVV is Required"

            return false
        }

        if (errorMessage != null) {
            addCardsAndAccountsBinding.secNumberTil.apply {
                isErrorEnabled = true
                error = errorMessage
            }
        }
        return true
    }*/

    public fun validateCVV(valPara: String): Boolean {
         var errorMessage: String? = null
         val value: String = valPara
         if (value.isEmpty()) {
             errorMessage = "CVV is Required"
             addCardsAndAccountsBinding.secNumberTil.apply {
                 isErrorEnabled = true
                 error = errorMessage
             }
             return false
         }
         return true
     }

   /* public fun validateExpMonth(valPara: String): Boolean {
        var errorMessage: String? = null
        val value: String = valPara
        if (value.isEmpty()) {
            errorMessage = "Required"

            return false
        }
        if (errorMessage != null) {
            addCardsAndAccountsBinding.expirationMonthTil.apply {
                isErrorEnabled = true
                error = errorMessage
            }
        }
        return true
    }*/

    public fun validateExpMonth(valPara: String): Boolean {
        var errorMessage: String? = null
        val value: String = valPara
        if (value.isEmpty()) {
            errorMessage = "Required"
            addCardsAndAccountsBinding.expirationMonthTil.apply {
                isErrorEnabled = true
                error = errorMessage
            }
            return false
        }
        return true
    }

    public fun validateExpYear(valPara: String): Boolean {
        var errorMessage: String? = null
        val value: String = valPara
        if (value.isEmpty()) {
            errorMessage = "Required"
            addCardsAndAccountsBinding.expirationYearTil.apply {
                isErrorEnabled = true
                error = errorMessage
            }
            return false
        }
        return true
    }

   /* public fun validateExpYear(valPara: String): Boolean {
        var errorMessage: String? = null
        val value: String = valPara
        if (value.isEmpty()) {
            errorMessage = "Required"
            return false
        }

        if (errorMessage != null) {
            addCardsAndAccountsBinding.expirationYearTil.apply {
                isErrorEnabled = true
                error = errorMessage
            }
        }
        return true
    }*/


}