package com.kekulta.pmanager.di

import com.kekulta.pmanager.CacheDataStore
import com.kekulta.pmanager.CacheDataStoreImpl
import com.kekulta.pmanager.IconRepository
import com.kekulta.pmanager.NetworkIconDataStore
import com.kekulta.pmanager.NetworkIconDataStoreImpl
import com.kekulta.pmanager.SitesRepository
import org.koin.dsl.module

val koinModule = module {
    single<CacheDataStore> { CacheDataStoreImpl(get()) }
    single<NetworkIconDataStore> { NetworkIconDataStoreImpl(get()) }
    single { IconRepository(get(), get()) }
    single { SitesRepository() }
}