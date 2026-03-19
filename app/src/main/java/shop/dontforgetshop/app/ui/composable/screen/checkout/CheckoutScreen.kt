package shop.dontforgetshop.app.ui.composable.screen.checkout

import androidx.compose.foundation.background
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
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.androidx.compose.koinViewModel
import shop.dontforgetshop.app.R
import shop.dontforgetshop.app.data.entity.OrderEntity
import shop.dontforgetshop.app.ui.state.DataUiState
import shop.dontforgetshop.app.ui.viewmodel.CheckoutViewModel

@Composable
fun CheckoutScreen(
    modifier: Modifier = Modifier,
    viewModel: CheckoutViewModel = koinViewModel(),
    onNavigateToOrdersScreen: () -> Unit,
) {
    val focusManager = LocalFocusManager.current
    val orderState by viewModel.orderState.collectAsStateWithLifecycle()
    val emailInvalidState by viewModel.emailInvalidState.collectAsStateWithLifecycle()

    val isButtonEnabled by remember {
        derivedStateOf {
            viewModel.customerFirstName.isNotEmpty() &&
                    viewModel.customerLastName.isNotEmpty() &&
                    viewModel.customerEmail.isNotEmpty()
        }
    }

    if (orderState is DataUiState.Populated) {
        CheckoutDialog(
            order = (orderState as DataUiState.Populated<OrderEntity>).data,
            onConfirm = onNavigateToOrdersScreen,
        )
    }

    CheckoutContent(
        customerFirstName = viewModel.customerFirstName,
        customerLastName = viewModel.customerLastName,
        customerEmail = viewModel.customerEmail,
        isEmailInvalid = emailInvalidState,
        modifier = modifier,
        focusManager = focusManager,
        isButtonEnabled = isButtonEnabled,
        onFirstNameChanged = viewModel::updateCustomerFirstName,
        onLastNameChanged = viewModel::updateCustomerLastName,
        onEmailChanged = viewModel::updateCustomerEmail,
        onPlaceOrderButtonClick = viewModel::placeOrder,
    )
}

@Composable
private fun CheckoutContent(
    customerFirstName: String,
    customerLastName: String,
    customerEmail: String,
    isEmailInvalid: Boolean,
    modifier: Modifier = Modifier,
    focusManager: FocusManager,
    isButtonEnabled: Boolean,
    onFirstNameChanged: (String) -> Unit,
    onLastNameChanged: (String) -> Unit,
    onEmailChanged: (String) -> Unit,
    onPlaceOrderButtonClick: () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFF8F8F8))
            .verticalScroll(rememberScrollState()),
    ) {
        // Header
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF1A1A1A))
                .padding(horizontal = 20.dp, vertical = 24.dp),
        ) {
            Text(
                text = stringResource(R.string.checkout_title),
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Serif,
                color = Color(0xFFC9A961),
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = stringResource(R.string.checkout_subtitle),
                fontSize = 13.sp,
                color = Color(0xFF9E9E9E),
                lineHeight = 20.sp,
            )
        }

        Column(modifier = Modifier.padding(20.dp)) {
            // Name row
            Row(modifier = Modifier.fillMaxWidth()) {
                CheckoutTextField(
                    input = customerFirstName,
                    onInputChange = onFirstNameChanged,
                    labelText = stringResource(R.string.checkout_text_field_first_name),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                    keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(androidx.compose.ui.focus.FocusDirection.Next) }),
                    modifier = Modifier.weight(1f),
                )
                Spacer(modifier = Modifier.width(12.dp))
                CheckoutTextField(
                    input = customerLastName,
                    onInputChange = onLastNameChanged,
                    labelText = stringResource(R.string.checkout_text_field_last_name),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                    keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(androidx.compose.ui.focus.FocusDirection.Next) }),
                    modifier = Modifier.weight(1f),
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            CheckoutTextField(
                input = customerEmail,
                onInputChange = onEmailChanged,
                labelText = stringResource(R.string.checkout_text_field_email),
                isError = isEmailInvalid,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Done,
                ),
                keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
                modifier = Modifier.fillMaxWidth(),
            )
            if (isEmailInvalid) {
                Text(
                    text = stringResource(R.string.checkout_email_error),
                    color = MaterialTheme.colorScheme.error,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(start = 4.dp, top = 4.dp),
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Pickup info
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFF5EDD8), RoundedCornerShape(10.dp))
                    .padding(14.dp),
            ) {
                Text(text = "🏪", fontSize = 18.sp)
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = stringResource(R.string.checkout_pickup_info),
                    fontSize = 13.sp,
                    color = Color(0xFF5A4000),
                    fontWeight = FontWeight.Medium,
                    lineHeight = 20.sp,
                )
            }

            Spacer(modifier = Modifier.height(28.dp))

            Button(
                onClick = onPlaceOrderButtonClick,
                enabled = isButtonEnabled,
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF1A1A1A),
                    contentColor = Color(0xFFC9A961),
                    disabledContainerColor = Color(0xFFCCCCCC),
                    disabledContentColor = Color(0xFF888888),
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .navigationBarsPadding()
                    .height(54.dp),
            ) {
                Text(
                    text = stringResource(R.string.button_confirm_order_label),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    letterSpacing = 0.5.sp,
                )
            }
        }
    }
}

@Composable
fun CheckoutTextField(
    input: String,
    onInputChange: (String) -> Unit,
    labelText: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    isError: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
) {
    OutlinedTextField(
        value = input,
        onValueChange = onInputChange,
        modifier = modifier,
        enabled = enabled,
        label = {
            Text(
                text = labelText,
                style = MaterialTheme.typography.titleSmall,
            )
        },
        isError = isError,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        singleLine = true,
        shape = RoundedCornerShape(10.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.surface,
            unfocusedContainerColor = MaterialTheme.colorScheme.surface,
            focusedTextColor = MaterialTheme.colorScheme.onSurface,
            unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
            focusedBorderColor = Color(0xFFC9A961),
            unfocusedBorderColor = MaterialTheme.colorScheme.outline,
            focusedLabelColor = Color(0xFFC9A961),
            unfocusedLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
            cursorColor = Color(0xFFC9A961),
        ),
    )
}
