package shop.dontforgetshop.app.ui.composable.screen.productdetails

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.koin.compose.viewmodel.koinViewModel
import shop.dontforgetshop.app.R
import shop.dontforgetshop.app.data.model.Product
import shop.dontforgetshop.app.ui.composable.shared.DataBasedContainer
import shop.dontforgetshop.app.ui.composable.shared.DataEmptyContent
import shop.dontforgetshop.app.ui.state.DataUiState
import shop.dontforgetshop.app.ui.viewmodel.ProductDetailsViewModel

@Composable
fun ProductDetailsScreen(
    productId: Int,
    modifier: Modifier = Modifier,
    viewModel: ProductDetailsViewModel = koinViewModel(),
) {
    val productState by viewModel.productDetailsState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.observeProductDetails(productId)
    }

    ProductDetailsScreenContent(
        productState = productState,
        modifier = modifier,
        onAddToCart = viewModel::addProductToCart,
    )
}

@Composable
private fun ProductDetailsScreenContent(
    productState: DataUiState<Product>,
    modifier: Modifier = Modifier,
    onAddToCart: () -> Unit,
) {
    Column(modifier = modifier) {
        DataBasedContainer(
            dataState = productState,

            dataPopulated = {
                val product = (productState as DataUiState.Populated).data
                ProductDetailView(
                    product = product,
                    onAddToCart = onAddToCart,
                )
            },

            dataEmpty = {
                DataEmptyContent(
                    primaryText = stringResource(R.string.product_details_state_empty_primary_text),
                    modifier = Modifier.fillMaxSize(),
                )
            },
        )
    }
}

@Composable
private fun ProductDetailView(
    product: Product,
    onAddToCart: () -> Unit,
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(bottom = 96.dp),
        ) {
            // Product image
            Image(
                painter = painterResource(product.imageRes),
                contentDescription = stringResource(R.string.product_image_description),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp),
            )

            Column(modifier = Modifier.padding(20.dp)) {
                // Category badge
                Box(
                    modifier = Modifier
                        .background(Color(0xFFF0EDE6), RoundedCornerShape(6.dp))
                        .padding(horizontal = 10.dp, vertical = 4.dp),
                ) {
                    Text(
                        text = stringResource(product.category.titleRes),
                        fontSize = 11.sp,
                        color = Color(0xFFC9A961),
                        fontWeight = FontWeight.SemiBold,
                        letterSpacing = 0.5.sp,
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = product.title,
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Serif,
                    color = Color(0xFF1A1A1A),
                    lineHeight = 34.sp,
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "£%.2f".format(product.price),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1A1A1A),
                )

                Spacer(modifier = Modifier.height(20.dp))

                HorizontalDivider(color = Color(0xFFE0E0E0))

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = "About this product",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF666666),
                    letterSpacing = 0.5.sp,
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = product.description,
                    fontSize = 15.sp,
                    color = Color(0xFF444444),
                    lineHeight = 24.sp,
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Pickup info card
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFFF5EDD8), RoundedCornerShape(10.dp))
                        .padding(14.dp),
                ) {
                    Text(text = "🏪", fontSize = 20.sp)
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = "In-store pickup ready within 24 hours",
                        fontSize = 13.sp,
                        color = Color(0xFF5A4000),
                        fontWeight = FontWeight.Medium,
                    )
                }
            }
        }

        // Sticky Add to Cart button
        Button(
            onClick = onAddToCart,
            shape = RoundedCornerShape(14.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF1A1A1A),
                contentColor = Color(0xFFC9A961),
            ),
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .navigationBarsPadding()
                .padding(horizontal = 20.dp, vertical = 16.dp)
                .height(54.dp),
        ) {
            Text(
                text = stringResource(R.string.button_add_to_cart_label),
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                letterSpacing = 0.5.sp,
            )
        }
    }
}
