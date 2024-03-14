package com.kekulta.pmanager.change.presentation.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.kekulta.pmanager.R
import com.kekulta.pmanager.change.domain.viewmodels.ChangeViewModel
import com.kekulta.pmanager.databinding.FragmentChangeBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class ChangeFragment : Fragment(R.layout.fragment_change) {
    private val binding by viewBinding(FragmentChangeBinding::bind)
    private val viewModel: ChangeViewModel by viewModel(ownerProducer = { requireActivity() })

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}