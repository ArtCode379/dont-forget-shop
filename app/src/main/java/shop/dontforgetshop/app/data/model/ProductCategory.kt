package shop.dontforgetshop.app.data.model

import androidx.annotation.StringRes
import shop.dontforgetshop.app.R

enum class ProductCategory(@field:StringRes val titleRes: Int) {
    KITCHEN_APPLIANCES(R.string.category_kitchen_appliances),
    SMART_HOME(R.string.category_smart_home),
    HOME_DECOR(R.string.category_home_decor),
    CLEANING(R.string.category_cleaning),
    BATHROOM(R.string.category_bathroom),
    LIGHTING(R.string.category_lighting),
}
