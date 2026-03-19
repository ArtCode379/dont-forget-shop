package shop.dontforgetshop.app.ui.composable.screen.splash

import androidx.compose.animation.core.Easing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.delay
import org.koin.androidx.compose.koinViewModel
import shop.dontforgetshop.app.R
import shop.dontforgetshop.app.ui.viewmodel.SplashViewModel

@Composable
fun SplashScreen(
    modifier: Modifier = Modifier,
    viewModel: SplashViewModel = koinViewModel(),
    onNavigateToHomeScreen: () -> Unit,
    onNavigateToOnboarding: () -> Unit,
) {
    val onboardedState by viewModel.onboardedState.collectAsStateWithLifecycle()
    var animationStarted by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        animationStarted = true
        delay(1800)
        if (onboardedState) {
            onNavigateToHomeScreen()
        } else {
            onNavigateToOnboarding()
        }
    }

    SplashContent(
        animationStarted = animationStarted,
        modifier = modifier,
    )
}

@Composable
private fun SplashContent(
    animationStarted: Boolean,
    modifier: Modifier = Modifier,
) {
    val alpha by animateFloatAsState(
        targetValue = if (animationStarted) 1f else 0f,
        animationSpec = tween(durationMillis = 900),
        label = "splash_alpha",
    )
    val scale by animateFloatAsState(
        targetValue = if (animationStarted) 1f else 0.75f,
        animationSpec = tween(durationMillis = 900, easing = Easing { it * it * (3 - 2 * it) }),
        label = "splash_scale",
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF1A1A1A),
                        Color(0xFF2A2A2A),
                    ),
                ),
            ),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .alpha(alpha)
                .scale(scale),
        ) {
            Image(
                painter = painterResource(R.drawable.icon),
                contentDescription = "App icon",
                modifier = Modifier.size(120.dp),
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Don't Forget Shop",
                color = Color(0xFFC9A961),
                fontSize = 28.sp,
                fontWeight = FontWeight.SemiBold,
                fontFamily = FontFamily.Serif,
                textAlign = TextAlign.Center,
                letterSpacing = 0.5.sp,
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "PREMIUM HOME GOODS",
                color = Color(0xFF9E9E9E),
                fontSize = 11.sp,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center,
                letterSpacing = 3.sp,
            )
        }

        Text(
            text = "PLEASE DO NOT FORGET LTD",
            color = Color(0xFF555555),
            fontSize = 10.sp,
            fontWeight = FontWeight.Normal,
            letterSpacing = 2.sp,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 36.dp)
                .alpha(alpha),
        )
    }
}
