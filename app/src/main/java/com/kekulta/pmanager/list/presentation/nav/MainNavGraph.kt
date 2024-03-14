package com.kekulta.pmanager.list.presentation.nav

import androidx.navigation.createGraph
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.fragment
import com.kekulta.pmanager.add.presentation.ui.AddFragment
import com.kekulta.pmanager.list.presentation.ui.ListFragment

object MainNavGraph {
    fun setup(navHostFragment: NavHostFragment) {
        val navController = navHostFragment.navController

        navController.graph = navController.createGraph(
            startDestination = Destination.LIST.route
        ) {
            fragment<ListFragment>(Destination.LIST.route) {
                label = "List"
            }

            fragment<AddFragment>(Destination.ADD.route) {
                label = "Add"
            }
        }
    }
}

