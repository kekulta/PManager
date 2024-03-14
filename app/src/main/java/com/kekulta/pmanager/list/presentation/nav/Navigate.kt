package com.kekulta.pmanager.list.presentation.nav

import androidx.navigation.NavController

fun NavController.navigate(dest: Destination) {
    navigate(dest.route)
}