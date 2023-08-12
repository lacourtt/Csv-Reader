package com.dicane.csvreader.di

import com.dicane.csvreader.datasource.CsvDatasource
import com.dicane.csvreader.repository.ContactRepository
import com.dicane.csvreader.repository.ContactRepositoryImpl
import com.dicane.csvreader.usecase.FetchContactsUseCase
import com.dicane.csvreader.usecase.UpdateContactUseCase
import com.dicane.csvreader.viewmodel.MainViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val viewModelModule = module {
    single(named("IODispatcher")) {
        Dispatchers.IO
    }
    single {
        FetchContactsUseCase(get())
    }
    single {
        UpdateContactUseCase(get())
    }
    single {
        CsvDatasource()
    }
    factory<ContactRepository> {
        ContactRepositoryImpl(get(), get())
    }
    viewModel {
        MainViewModel(get(), get(), get<CoroutineDispatcher>(named("IODispatcher")))
    }
}