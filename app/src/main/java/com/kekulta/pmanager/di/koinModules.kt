package com.kekulta.pmanager.di

import com.kekulta.pmanager.SitesRepository
import org.koin.dsl.module

val koinModule = module {
    single { SitesRepository() }
}