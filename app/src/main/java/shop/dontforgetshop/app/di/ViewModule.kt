package shop.dontforgetshop.app.di

import shop.dontforgetshop.app.ui.viewmodel.AppViewModel
import shop.dontforgetshop.app.ui.viewmodel.CartViewModel
import shop.dontforgetshop.app.ui.viewmodel.CheckoutViewModel
import shop.dontforgetshop.app.ui.viewmodel.OnboardingViewModel
import shop.dontforgetshop.app.ui.viewmodel.OrderViewModel
import shop.dontforgetshop.app.ui.viewmodel.ProductDetailsViewModel
import shop.dontforgetshop.app.ui.viewmodel.ProductViewModel
import shop.dontforgetshop.app.ui.viewmodel.SplashViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModule = module {
    viewModel {
        AppViewModel(
            cartRepository = get()
        )
    }

    viewModel {
        SplashViewModel(
            onboardingRepository = get()
        )
    }

    viewModel {
        OnboardingViewModel(
            onboardingRepository = get()
        )
    }

    viewModel {
        ProductViewModel(
            productRepository = get(),
            cartRepository = get(),
        )
    }

    viewModel {
        ProductDetailsViewModel(
            productRepository = get(),
            cartRepository = get(),
        )
    }

    viewModel {
        CheckoutViewModel(
            cartRepository = get(),
            productRepository = get(),
            orderRepository = get(),
        )
    }

    viewModel {
        CartViewModel(
            cartRepository = get(),
            productRepository = get(),
        )
    }

    viewModel {
        OrderViewModel(
            orderRepository = get(),
        )
    }
}