package com.example.housemaster

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.housemaster.databinding.FragmentEditProfileBinding
import com.example.housemaster.databinding.FragmentHomeBinding
import com.example.housemaster.databinding.FragmentListCardsBinding
import com.example.housemaster.databinding.FragmentProfileBinding
import com.example.housemaster.databinding.FragmentSearchBinding
import com.example.housemaster.databinding.FragmentSettingsBinding
import com.example.housemaster.databinding.FragmentTermsBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class ListCardsFragment : Fragment(R.layout.fragment_list_cards) {

    private lateinit var listCardsBinding: FragmentListCardsBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listCardsBinding = FragmentListCardsBinding.bind(view)

        listCardsBinding.addNewCardBtn.setOnClickListener {
            val action =
                ListCardsFragmentDirections.actionListCardsFragmentToAddCardsAndAccountsFragment()
            findNavController().navigate(action)
        }
    }


}