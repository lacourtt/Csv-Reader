package com.dicane.csvreader.di

import com.dicane.csvreader.datasource.CsvDatasource
import com.dicane.csvreader.repository.ContactRepository
import com.dicane.csvreader.usecase.FetchContactsUseCase
import com.dicane.csvreader.usecase.UpdateContactUseCase
import com.dicane.csvreader.viewmodel.MainViewModel
import kotlinx.coroutines.Dispatchers
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val viewModelModule = module {
    single(named("IODispatcher")) {
        Dispatchers.IO
    }
    single {
        FetchContactsUseCase(get(), get(named("IODispatcher")))
    }
    single {
        UpdateContactUseCase(get())
    }
    single {
        CsvDatasource()
    }
    single {
        ContactRepository(get(), get())
    }
    viewModel {
        MainViewModel(get(), get())
    }
}