package shop.dontforgetshop.app.ui.composable.screen.settings

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import shop.dontforgetshop.app.R

@Composable
fun SettingsScreen(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val supportUrl = stringResource(R.string.customer_support_link)

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFF8F8F8))
            .verticalScroll(rememberScrollState()),
    ) {
        // Brand header
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF1A1A1A))
                .padding(horizontal = 20.dp, vertical = 28.dp),
        ) {
            Text(
                text = "Don't Forget Shop",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Serif,
                color = Color(0xFFC9A961),
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Premium Home Goods",
                fontSize = 13.sp,
                color = Color(0xFF9E9E9E),
                letterSpacing = 1.sp,
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        // About section
        SettingsSectionLabel(label = stringResource(R.string.settings_screen_about_label))

        SettingsCard {
            SettingsRow(
                label = stringResource(R.string.settings_screen_company_label),
                value = stringResource(R.string.company_name),
            )
            HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp), color = Color(0xFFEEEEEE))
            SettingsRow(
                label = stringResource(R.string.settings_screen_version_label),
                value = stringResource(R.string.app_version),
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Support section
        SettingsSectionLabel(label = stringResource(R.string.settings_screen_customer_support_label))

        SettingsCard {
            SettingsActionRow(
                label = stringResource(R.string.settings_open_website),
                value = stringResource(R.string.customer_support_link),
                onClick = {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(supportUrl))
                    context.startActivity(intent)
                },
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "© 2024 PLEASE DO NOT FORGET LTD\nAll rights reserved.",
            fontSize = 11.sp,
            color = Color(0xFFAAAAAA),
            lineHeight = 18.sp,
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp),
        )
    }
}

@Composable
private fun SettingsSectionLabel(label: String) {
    Text(
        text = label.uppercase(),
        fontSize = 11.sp,
        fontWeight = FontWeight.SemiBold,
        color = Color(0xFF999999),
        letterSpacing = 1.sp,
        modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp),
    )
}

@Composable
private fun SettingsCard(content: @Composable () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(Color.White),
    ) {
        content()
    }
}

@Composable
private fun SettingsRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = label,
            fontSize = 14.sp,
            color = Color(0xFF333333),
            fontWeight = FontWeight.Medium,
            modifier = Modifier.weight(1f),
        )
        Text(
            text = value,
            fontSize = 13.sp,
            color = Color(0xFF888888),
        )
    }
}

@Composable
private fun SettingsActionRow(label: String, value: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = label,
                fontSize = 14.sp,
                color = Color(0xFF333333),
                fontWeight = FontWeight.Medium,
            )
            Text(
                text = value,
                fontSize = 12.sp,
                color = Color(0xFFC9A961),
            )
        }
        Spacer(modifier = Modifier.width(8.dp))
        Icon(
            painter = painterResource(R.drawable.link_svgrepo_com),
            contentDescription = null,
            tint = Color(0xFFC9A961),
            modifier = Modifier.size(18.dp),
        )
    }
}
