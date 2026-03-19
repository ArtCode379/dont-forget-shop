package shop.dontforgetshop.app.di

import shop.dontforgetshop.app.data.repository.CartRepository
import shop.dontforgetshop.app.data.repository.OnboardingRepository
import shop.dontforgetshop.app.data.repository.OrderRepository
import shop.dontforgetshop.app.data.repository.ProductRepository

import org.koin.core.qualifier.named
import org.koin.dsl.module

val dataModule = module {
    includes(databaseModule, dataStoreModule)

    single {
        OnboardingRepository(
            onboardingDataStoreManager = get(),
            coroutineDispatcher = get(named("IO"))
        )
    }

    single { ProductRepository() }

    single {
        CartRepository(
            cartItemDao = get(),
            coroutineDispatcher = get(named("IO"))
        )
    }

    single {
        OrderRepository(
            orderDao = get(),
            coroutineDispatcher = get(named("IO"))
        )
    }
}