package shop.dontforgetshop.app.ui.composable.screen.order

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.koin.androidx.compose.koinViewModel
import shop.dontforgetshop.app.R
import shop.dontforgetshop.app.data.entity.OrderEntity
import shop.dontforgetshop.app.ui.composable.shared.DataBasedContainer
import shop.dontforgetshop.app.ui.composable.shared.DataEmptyContent
import shop.dontforgetshop.app.ui.state.DataUiState
import shop.dontforgetshop.app.ui.viewmodel.OrderViewModel
import java.time.format.DateTimeFormatter

@Composable
fun OrdersScreen(
    modifier: Modifier = Modifier,
    viewModel: OrderViewModel = koinViewModel(),
) {
    val ordersState by viewModel.ordersState.collectAsState()

    OrdersContent(
        ordersState = ordersState,
        modifier = modifier,
    )
}

@Composable
private fun OrdersContent(
    ordersState: DataUiState<List<OrderEntity>>,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        DataBasedContainer(
            dataState = ordersState,

            dataPopulated = {
                val orders = (ordersState as DataUiState.Populated).data
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    items(orders) { order ->
                        OrderCard(
                            order = order,
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp),
                        )
                    }
                    item { Spacer(modifier = Modifier.height(16.dp)) }
                }
            },

            dataEmpty = {
                DataEmptyContent(
                    primaryText = stringResource(R.string.orders_state_empty_primary_text),
                    modifier = Modifier.fillMaxSize(),
                )
            },
        )
    }
}

@Composable
private fun OrderCard(
    order: OrderEntity,
    modifier: Modifier = Modifier,
) {
    val dateFormatter = DateTimeFormatter.ofPattern("d MMM yyyy")

    Card(
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = modifier.fillMaxWidth(),
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Header row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = stringResource(R.string.order_number, order.orderNumber),
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Serif,
                    color = Color(0xFF1A1A1A),
                )
                Text(
                    text = order.timestamp.format(dateFormatter),
                    fontSize = 12.sp,
                    color = Color(0xFF999999),
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = stringResource(R.string.order_customer, order.customerFirstName, order.customerLastName),
                fontSize = 13.sp,
                color = Color(0xFF666666),
            )

            Spacer(modifier = Modifier.height(10.dp))

            HorizontalDivider(color = Color(0xFFEEEEEE))

            Spacer(modifier = Modifier.height(10.dp))

            // Order description
            Text(
                text = order.description,
                fontSize = 13.sp,
                color = Color(0xFF444444),
                lineHeight = 20.sp,
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Total + status
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .background(Color(0xFFF0EDE6), RoundedCornerShape(6.dp))
                        .padding(horizontal = 8.dp, vertical = 4.dp),
                ) {
                    Text(text = "⏳", fontSize = 12.sp)
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = stringResource(R.string.order_status_processing),
                        fontSize = 11.sp,
                        color = Color(0xFF5A4000),
                        fontWeight = FontWeight.Medium,
                    )
                }
                Text(
                    text = stringResource(R.string.order_total_label, order.price),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1A1A1A),
                )
            }
        }
    }
}
