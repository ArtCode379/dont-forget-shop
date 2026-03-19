package shop.dontforgetshop.app.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import shop.dontforgetshop.app.data.dao.CartItemDao
import shop.dontforgetshop.app.data.dao.OrderDao
import shop.dontforgetshop.app.data.database.converter.Converters
import shop.dontforgetshop.app.data.entity.CartItemEntity
import shop.dontforgetshop.app.data.entity.OrderEntity

@Database(
    entities = [CartItemEntity::class, OrderEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class DTFGSDatabase : RoomDatabase() {

    abstract fun cartItemDao(): CartItemDao

    abstract fun orderDao(): OrderDao
}