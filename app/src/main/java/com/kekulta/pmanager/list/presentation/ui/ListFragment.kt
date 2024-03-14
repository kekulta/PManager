package com.kekulta.pmanager.list.presentation.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.kekulta.pmanager.R
import com.kekulta.pmanager.databinding.FragmentListBinding
import com.kekulta.pmanager.list.domain.viewmodels.ListViewModel
import com.kekulta.pmanager.list.presentation.nav.exitOnBackPressed
import com.kekulta.pmanager.list.presentation.recycler.RecyclerViewMarginDecorator
import com.kekulta.pmanager.list.presentation.recycler.SitesAdapter
import com.kekulta.pmanager.shared.utils.dip
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class ListFragment : Fragment(R.layout.fragment_list) {
    private val binding by viewBinding(FragmentListBinding::bind)
    private val viewModel: ListViewModel by viewModel(ownerProducer = { requireActivity() })

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        exitOnBackPressed()

        val rvAdapter = SitesAdapter()

        binding.sitesRv.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = rvAdapter
            addItemDecoration(RecyclerViewMarginDecorator(dip(16)))
        }

        binding.addSiteFab.setOnClickListener {
            //findNavController().navigate(Destination.ADD)
        }

        lifecycleScope.launch {
            viewModel.observeSites().collect { vo ->
                rvAdapter.submitList(vo)
            }
        }
    }
}