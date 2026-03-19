package shop.dontforgetshop.app.di

import androidx.room.Room
import shop.dontforgetshop.app.data.database.DTFGSDatabase
import org.koin.dsl.module

private const val DB_NAME = "skeleton_db"

val databaseModule = module {
    single {
        Room.databaseBuilder(
            context = get(),
            klass = DTFGSDatabase::class.java,
            name = DB_NAME
        ).build()
    }

    single { get<DTFGSDatabase>().cartItemDao() }

    single { get<DTFGSDatabase>().orderDao() }
}