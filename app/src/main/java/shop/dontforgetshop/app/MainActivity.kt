package shop.dontforgetshop.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import shop.dontforgetshop.app.ui.composable.approot.AppRoot
import shop.dontforgetshop.app.ui.theme.ProductAppDTFGSTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ProductAppDTFGSTheme {
                AppRoot()
            }
        }
    }
}