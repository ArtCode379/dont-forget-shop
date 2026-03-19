package shop.dontforgetshop.app.ui.composable.screen.cart

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.compose.viewmodel.koinViewModel
import shop.dontforgetshop.app.R
import shop.dontforgetshop.app.ui.composable.shared.DataBasedContainer
import shop.dontforgetshop.app.ui.composable.shared.DataEmptyContent
import shop.dontforgetshop.app.ui.state.CartItemUiState
import shop.dontforgetshop.app.ui.state.DataUiState
import shop.dontforgetshop.app.ui.viewmodel.CartViewModel

@Composable
fun CartScreen(
    modifier: Modifier = Modifier,
    viewModel: CartViewModel = koinViewModel(),
    onNavigateToCheckoutScreen: () -> Unit,
) {
    val cartItemsState by viewModel.cartItemsState.collectAsStateWithLifecycle()
    val totalPrice by viewModel.totalPrice.collectAsStateWithLifecycle()

    CartScreenContent(
        cartItemsState = cartItemsState,
        modifier = modifier,
        totalPrice = totalPrice,
        onPlusItemClick = viewModel::incrementProductInCart,
        onMinusItemClick = viewModel::decrementItemInCart,
        onCompleteOrderButtonClick = onNavigateToCheckoutScreen,
    )
}

@Composable
private fun CartScreenContent(
    cartItemsState: DataUiState<List<CartItemUiState>>,
    modifier: Modifier = Modifier,
    totalPrice: Double,
    onPlusItemClick: (Int) -> Unit,
    onMinusItemClick: (Int) -> Unit,
    onCompleteOrderButtonClick: () -> Unit,
) {
    Column(modifier = modifier) {
        DataBasedContainer(
            dataState = cartItemsState,

            dataPopulated = {
                val items = (cartItemsState as DataUiState.Populated).data
                CartPopulatedContent(
                    items = items,
                    totalPrice = totalPrice,
                    onPlusItemClick = onPlusItemClick,
                    onMinusItemClick = onMinusItemClick,
                    onCompleteOrderButtonClick = onCompleteOrderButtonClick,
                )
            },

            dataEmpty = {
                DataEmptyContent(
                    primaryText = stringResource(R.string.cart_state_empty_primary_text),
                    modifier = Modifier.fillMaxSize(),
                )
            },
        )
    }
}

@Composable
private fun CartPopulatedContent(
    items: List<CartItemUiState>,
    totalPrice: Double,
    onPlusItemClick: (Int) -> Unit,
    onMinusItemClick: (Int) -> Unit,
    onCompleteOrderButtonClick: () -> Unit,
) {
    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 90.dp),
        ) {
            items(items) { item ->
                CartItemRow(
                    item = item,
                    onPlusClick = { onPlusItemClick(item.productId) },
                    onMinusClick = { onMinusItemClick(item.productId) },
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp),
                )
            }

            item {
                HorizontalDivider(
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                    color = Color(0xFFE0E0E0),
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = stringResource(R.string.cart_total_label),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF666666),
                    )
                    Text(
                        text = "£%.2f".format(totalPrice),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1A1A1A),
                        fontFamily = FontFamily.Serif,
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
            }
        }

        Button(
            onClick = onCompleteOrderButtonClick,
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
                text = stringResource(R.string.button_place_order_label, totalPrice),
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
            )
        }
    }
}

@Composable
private fun CartItemRow(
    item: CartItemUiState,
    onPlusClick: () -> Unit,
    onMinusClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = modifier.fillMaxWidth(),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            if (item.productImageRes != null) {
                Image(
                    painter = painterResource(item.productImageRes),
                    contentDescription = item.productTitle,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(72.dp)
                        .clip(RoundedCornerShape(8.dp)),
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = item.productTitle,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF1A1A1A),
                    maxLines = 2,
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "£%.2f".format(item.productPrice),
                    fontSize = 14.sp,
                    color = Color(0xFF666666),
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            // Quantity controls
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                IconButton(
                    onClick = onMinusClick,
                    modifier = Modifier.size(32.dp),
                ) {
                    Icon(
                        painter = painterResource(R.drawable.minus_svgrepo_com),
                        contentDescription = stringResource(R.string.decrease_quantity_icon_description),
                        tint = Color(0xFF1A1A1A),
                        modifier = Modifier.size(16.dp),
                    )
                }
                Text(
                    text = item.quantity.toString(),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1A1A1A),
                    modifier = Modifier.width(28.dp),
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                )
                IconButton(
                    onClick = onPlusClick,
                    modifier = Modifier.size(32.dp),
                ) {
                    Icon(
                        painter = painterResource(R.drawable.plus_svgrepo_com),
                        contentDescription = stringResource(R.string.increase_quantity_icon_description),
                        tint = Color(0xFF1A1A1A),
                        modifier = Modifier.size(16.dp),
                    )
                }
            }
        }
    }
}
