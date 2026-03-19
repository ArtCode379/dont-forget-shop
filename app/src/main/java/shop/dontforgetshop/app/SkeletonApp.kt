package shop.dontforgetshop.app

import android.app.Application
import shop.dontforgetshop.app.di.dataModule
import shop.dontforgetshop.app.di.dispatcherModule
import shop.dontforgetshop.app.di.viewModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

class DTFGSApp : Application() {
    override fun onCreate() {
        super.onCreate()

        val appModules = dataModule + viewModule + dispatcherModule

        startKoin {
            androidLogger()
            androidContext(this@DTFGSApp)
            modules(appModules)
        }
    }
}