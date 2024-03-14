package com.kekulta.pmanager.di

import com.kekulta.pmanager.add.domain.viewmodels.AddViewModel
import com.kekulta.pmanager.change.domain.viewmodels.ChangeViewModel
import com.kekulta.pmanager.list.data.db.AppDatabase
import com.kekulta.pmanager.list.data.db.api.SiteDataStore
import com.kekulta.pmanager.list.data.db.impl.SiteDataStoreImpl
import com.kekulta.pmanager.list.data.db.mappers.SiteDmMapper
import com.kekulta.pmanager.list.data.db.mappers.SiteEntityMapper
import com.kekulta.pmanager.list.domain.formatters.SiteVoFormatter
import com.kekulta.pmanager.list.domain.repos.SitesRepository
import com.kekulta.pmanager.list.domain.viewmodels.ListViewModel
import com.kekulta.pmanager.list.domain.viewmodels.MainViewModel
import com.kekulta.pmanager.list.presentation.ui.AuthManager
import com.kekulta.pmanager.list.presentation.ui.EncryptionManager
import com.kekulta.pmanager.shared.events.Dispatcher
import com.kekulta.pmanager.shared.events.EventDispatcher
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val koinModule = module {
    single<SiteDataStore> { SiteDataStoreImpl(get()) }
    single { AppDatabase.createDatabase(get()) }
    single { get<AppDatabase>().getSiteDao() }
    single { SitesRepository(get(), get(), get()) }
    single<EventDispatcher> { Dispatcher() }
    single { AuthManager() }
    single { EncryptionManager() }

    factory { SiteVoFormatter() }
    factory { SiteDmMapper() }
    factory { SiteEntityMapper() }
}

val viewModels = module {
    viewModel { MainViewModel(get(), get()) }
    viewModel { ListViewModel(get(), get()) }
    viewModel { AddViewModel(get(), get()) }
    viewModel { ChangeViewModel() }
}