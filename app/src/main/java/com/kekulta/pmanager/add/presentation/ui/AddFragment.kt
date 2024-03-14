package com.kekulta.pmanager.add.presentation.ui

import android.os.Bundle
import android.text.InputType
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.kekulta.pmanager.R
import com.kekulta.pmanager.add.domain.viewmodels.AddViewModel
import com.kekulta.pmanager.databinding.FragmentAddBinding
import com.kekulta.pmanager.list.presentation.nav.popBackStackOnBackPressed
import com.kekulta.pmanager.shared.events.Event
import org.koin.androidx.viewmodel.ext.android.viewModel

class AddFragment : Fragment(R.layout.fragment_add) {
    private val binding by viewBinding(FragmentAddBinding::bind)
    private val viewModel: AddViewModel by viewModel(ownerProducer = { requireActivity() })


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        popBackStackOnBackPressed()

        binding.addressEt.addTextChangedListener()

        binding.saveButton.setOnClickListener {
            val login = binding.loginEt.text?.toString()
            val password = binding.passwordEt.text?.toString()
            val address = binding.addressEt.text?.toString()

            val isPasswordValid = !password.isNullOrBlank()
            val isAddressValid = !address.isNullOrBlank()

            if (!isPasswordValid) {
                binding.passwordTif.error = "Password can't be empty!"
            }

            if (!isAddressValid) {
                binding.addressTif.error = "Address can't be empty!"
            }

            if (isAddressValid && isPasswordValid) {
                viewModel.saveSite(address!!, login!!, password!!)
                viewModel.dispatch(Event.ShowSnackBar("Account saved!"))

                findNavController().popBackStack()
            }
        }

        binding.passwordToggle.setOnClickListener {
            if (binding.passwordEt.inputType == InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD) {
                binding.passwordEt.inputType = InputType.TYPE_CLASS_TEXT
            } else {
                binding.passwordEt.inputType =
                    InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            }
        }
    }
}

