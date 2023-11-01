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

class AddCardsAndAccountsFragment : Fragment(R.layout.fragment_add_cards_and_accounts) {

    private lateinit var addCardsAndAccountsBinding: FragmentAddCardsAndAccountsBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addCardsAndAccountsBinding = FragmentAddCardsAndAccountsBinding.bind(view)


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
    }


}