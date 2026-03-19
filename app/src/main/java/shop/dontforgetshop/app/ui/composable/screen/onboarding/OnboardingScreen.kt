package shop.dontforgetshop.app.ui.composable.screen.onboarding

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import shop.dontforgetshop.app.R
import shop.dontforgetshop.app.ui.viewmodel.OnboardingViewModel

data class OnboardingContent(
    @field:StringRes val titleRes: Int,
    @field:StringRes val descriptionRes: Int,
    @field:DrawableRes val imageRes: Int,
)

private val onboardingPagesContent = listOf(
    OnboardingContent(
        titleRes = R.string.page_1_title,
        descriptionRes = R.string.page_1_description,
        imageRes = R.drawable.img_onboarding_1,
    ),
    OnboardingContent(
        titleRes = R.string.page_2_title,
        descriptionRes = R.string.page_2_description,
        imageRes = R.drawable.img_onboarding_2,
    ),
    OnboardingContent(
        titleRes = R.string.page_3_title,
        descriptionRes = R.string.page_3_description,
        imageRes = R.drawable.img_onboarding_3,
    ),
)

@Composable
fun OnboardingScreen(
    modifier: Modifier = Modifier,
    viewModel: OnboardingViewModel = koinViewModel(),
    onNavigateToHomeScreen: () -> Unit,
) {
    val onboardingSetState by viewModel.onboardingSetState.collectAsState()

    LaunchedEffect(onboardingSetState) {
        if (onboardingSetState) {
            onNavigateToHomeScreen()
        }
    }

    OnboardingContent(
        pages = onboardingPagesContent,
        modifier = modifier,
        onGetStarted = viewModel::setOnboarded,
    )
}

@Composable
private fun OnboardingContent(
    pages: List<OnboardingContent>,
    modifier: Modifier = Modifier,
    onGetStarted: () -> Unit,
) {
    val pagerState = rememberPagerState(pageCount = { pages.size })
    val coroutineScope = rememberCoroutineScope()
    val isLastPage = pagerState.currentPage == pages.size - 1

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFF8F8F8)),
    ) {
        // Skip button
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 12.dp),
        ) {
            if (!isLastPage) {
                Text(
                    text = stringResource(R.string.skip_button_title),
                    color = Color(0xFF666666),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .clickable { onGetStarted() }
                        .padding(8.dp),
                )
            }
        }

        // Pager
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
        ) { pageIndex ->
            OnboardingPage(
                content = pages[pageIndex],
                modifier = Modifier.fillMaxSize(),
            )
        }

        // Dot indicators
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 20.dp),
        ) {
            repeat(pages.size) { index ->
                val isSelected = index == pagerState.currentPage
                Box(
                    modifier = Modifier
                        .padding(horizontal = 4.dp)
                        .size(if (isSelected) 10.dp else 8.dp)
                        .clip(CircleShape)
                        .background(
                            if (isSelected) Color(0xFFC9A961) else Color(0xFFD0D0D0)
                        ),
                )
            }
        }

        // Action button
        Button(
            onClick = {
                if (isLastPage) {
                    onGetStarted()
                } else {
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(pagerState.currentPage + 1)
                    }
                }
            },
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF1A1A1A),
                contentColor = Color(0xFFC9A961),
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .padding(bottom = 40.dp)
                .height(54.dp),
        ) {
            Text(
                text = if (isLastPage)
                    stringResource(R.string.start_button_title)
                else
                    stringResource(R.string.next_button_title),
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                letterSpacing = 0.5.sp,
            )
        }
    }
}

@Composable
private fun OnboardingPage(
    content: OnboardingContent,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            painter = painterResource(content.imageRes),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(320.dp)
                .clip(RoundedCornerShape(16.dp)),
        )

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = stringResource(content.titleRes),
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Serif,
            color = Color(0xFF1A1A1A),
            textAlign = TextAlign.Center,
            lineHeight = 34.sp,
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = stringResource(content.descriptionRes),
            fontSize = 15.sp,
            color = Color(0xFF666666),
            textAlign = TextAlign.Center,
            lineHeight = 24.sp,
        )
    }
}
