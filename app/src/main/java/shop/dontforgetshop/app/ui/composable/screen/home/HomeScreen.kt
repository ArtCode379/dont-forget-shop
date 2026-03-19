package shop.dontforgetshop.app.ui.composable.screen.home

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
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
import org.koin.androidx.compose.koinViewModel
import shop.dontforgetshop.app.R
import shop.dontforgetshop.app.data.model.Product
import shop.dontforgetshop.app.data.model.ProductCategory
import shop.dontforgetshop.app.ui.composable.shared.DataBasedContainer
import shop.dontforgetshop.app.ui.composable.shared.DataEmptyContent
import shop.dontforgetshop.app.ui.state.DataUiState
import shop.dontforgetshop.app.ui.viewmodel.ProductViewModel

private data class ArticleItem(
    @StringRes val titleRes: Int,
    @StringRes val descriptionRes: Int,
    @DrawableRes val imageRes: Int,
)

private val featuredArticles = listOf(
    ArticleItem(R.string.home_article_smart_home_title, R.string.home_article_smart_home_desc, R.drawable.img_onboarding_3),
    ArticleItem(R.string.home_article_kitchen_title, R.string.home_article_kitchen_desc, R.drawable.img_onboarding_1),
    ArticleItem(R.string.home_article_lighting_title, R.string.home_article_lighting_desc, R.drawable.img_table_lamp),
)

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: ProductViewModel = koinViewModel(),
    onNavigateToProductDetails: (productId: Int) -> Unit,
) {
    val productsState by viewModel.productsState.collectAsState()

    HomeContent(
        productsState = productsState,
        modifier = modifier,
        onNavigateToProductDetails = onNavigateToProductDetails,
        onAddProductToCart = viewModel::addToCart,
    )
}

@Composable
private fun HomeContent(
    productsState: DataUiState<List<Product>>,
    modifier: Modifier = Modifier,
    onNavigateToProductDetails: (productId: Int) -> Unit,
    onAddProductToCart: (productId: Int) -> Unit,
) {
    Column(modifier = modifier) {
        DataBasedContainer(
            dataState = productsState,

            dataPopulated = {
                val products = (productsState as DataUiState.Populated).data
                HomePopulatedContent(
                    products = products,
                    onNavigateToProductDetails = onNavigateToProductDetails,
                    onAddProductToCart = onAddProductToCart,
                )
            },

            dataEmpty = {
                DataEmptyContent(
                    primaryText = stringResource(R.string.products_state_empty_primary_text),
                    modifier = Modifier.fillMaxSize(),
                )
            },
        )
    }
}

@Composable
private fun HomePopulatedContent(
    products: List<Product>,
    onNavigateToProductDetails: (productId: Int) -> Unit,
    onAddProductToCart: (productId: Int) -> Unit,
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
    ) {
        // Featured product carousel
        item {
            FeaturedCarousel(
                products = products.take(4),
                onProductClick = onNavigateToProductDetails,
                onAddToCart = onAddProductToCart,
            )
        }

        // Category grid
        item {
            SectionHeader(title = stringResource(R.string.home_section_categories))
        }
        item {
            CategoryGrid()
        }

        // Featured products
        item {
            SectionHeader(title = stringResource(R.string.home_section_featured))
        }
        items(products) { product ->
            ProductCard(
                product = product,
                onClick = { onNavigateToProductDetails(product.id) },
                onAddToCart = { onAddProductToCart(product.id) },
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp),
            )
        }

        // Articles section
        item {
            SectionHeader(title = stringResource(R.string.home_section_articles))
            ArticlesRow(articles = featuredArticles)
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
private fun FeaturedCarousel(
    products: List<Product>,
    onProductClick: (Int) -> Unit,
    onAddToCart: (Int) -> Unit,
) {
    val pagerState = rememberPagerState(pageCount = { products.size })

    Column {
        HorizontalPager(
            state = pagerState,
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
            pageSpacing = 12.dp,
            modifier = Modifier.fillMaxWidth(),
        ) { page ->
            val product = products[page]
            Card(
                onClick = { onProductClick(product.id) },
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                modifier = Modifier.fillMaxWidth(),
            ) {
                Box {
                    Image(
                        painter = painterResource(product.imageRes),
                        contentDescription = product.title,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp),
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .background(
                                brush = androidx.compose.ui.graphics.Brush.verticalGradient(
                                    listOf(Color.Transparent, Color(0xCC1A1A1A)),
                                    startY = 80f,
                                )
                            ),
                    )
                    Column(
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .padding(16.dp),
                    ) {
                        Text(
                            text = product.title,
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            fontFamily = FontFamily.Serif,
                        )
                        Text(
                            text = "£%.2f".format(product.price),
                            color = Color(0xFFC9A961),
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 16.sp,
                        )
                    }
                }
            }
        }

        // Dots
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
        ) {
            repeat(products.size) { index ->
                Box(
                    modifier = Modifier
                        .padding(horizontal = 3.dp)
                        .size(if (index == pagerState.currentPage) 8.dp else 6.dp)
                        .clip(CircleShape)
                        .background(
                            if (index == pagerState.currentPage) Color(0xFFC9A961)
                            else Color(0xFFD0D0D0)
                        ),
                )
            }
        }
    }
}

@Composable
private fun SectionHeader(title: String) {
    Text(
        text = title,
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        fontFamily = FontFamily.Serif,
        color = Color(0xFF1A1A1A),
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
    )
}

@Composable
private fun CategoryGrid() {
    val categories = ProductCategory.entries
    val rows = categories.chunked(3)
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        rows.forEach { rowCategories ->
            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.fillMaxWidth(),
            ) {
                rowCategories.forEach { category ->
                    CategoryChip(
                        category = category,
                        modifier = Modifier.weight(1f),
                    )
                }
                // Fill empty slots if row is not full
                repeat(3 - rowCategories.size) {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
        Spacer(modifier = Modifier.height(4.dp))
    }
}

@Composable
private fun CategoryChip(
    category: ProductCategory,
    modifier: Modifier = Modifier,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0xFFF0EDE6))
            .padding(vertical = 14.dp, horizontal = 8.dp),
    ) {
        Text(
            text = stringResource(category.titleRes),
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            color = Color(0xFF1A1A1A),
            textAlign = androidx.compose.ui.text.style.TextAlign.Center,
            lineHeight = 16.sp,
        )
    }
}

@Composable
private fun ProductCard(
    product: Product,
    onClick: () -> Unit,
    onAddToCart: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        onClick = onClick,
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
            Image(
                painter = painterResource(product.imageRes),
                contentDescription = product.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(8.dp)),
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = stringResource(product.category.titleRes),
                    fontSize = 11.sp,
                    color = Color(0xFFC9A961),
                    fontWeight = FontWeight.Medium,
                    letterSpacing = 0.5.sp,
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = product.title,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF1A1A1A),
                    maxLines = 2,
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "£%.2f".format(product.price),
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF333333),
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Button(
                onClick = onAddToCart,
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF1A1A1A),
                    contentColor = Color(0xFFC9A961),
                ),
                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp),
                modifier = Modifier.height(36.dp),
            ) {
                Text(text = "+", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
private fun ArticlesRow(articles: List<ArticleItem>) {
    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        items(articles) { article ->
            ArticleCard(article = article)
        }
    }
}

@Composable
private fun ArticleCard(article: ArticleItem) {
    Card(
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = Modifier.width(240.dp),
    ) {
        Column {
            Image(
                painter = painterResource(article.imageRes),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(130.dp),
            )
            Column(modifier = Modifier.padding(12.dp)) {
                Text(
                    text = stringResource(article.titleRes),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF1A1A1A),
                    fontFamily = FontFamily.Serif,
                    maxLines = 1,
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = stringResource(article.descriptionRes),
                    fontSize = 12.sp,
                    color = Color(0xFF666666),
                    lineHeight = 18.sp,
                    maxLines = 2,
                    overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis,
                )
            }
        }
    }
}
