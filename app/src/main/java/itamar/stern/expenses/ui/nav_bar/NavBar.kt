package itamar.stern.expenses.ui.nav_bar


import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.debugInspectorInfo
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import itamar.stern.expenses.R
import itamar.stern.expenses.ui.theme.Blue
import itamar.stern.expenses.ui.theme.Orange
import itamar.stern.expenses.ui.theme.Red


@Composable
fun NavBar(navController: NavHostController) {
    var tabIndex by remember { mutableStateOf(0) }
    val tabTitles = listOf(
        stringResource(id = R.string.list_tab),
        stringResource(id = R.string.info_tab),
        stringResource(id = R.string.history_tab)
    )
    val tabColors = listOf(Orange, Red, Blue)
    val tabAddresses = listOf("list_screen", "info_screen", "history_screen")
    TabRow(
        selectedTabIndex = tabIndex,
        backgroundColor = Color.Black,
        indicator = @Composable { tabPositions ->
            TabRowDefaults.Indicator(
                height = 4.dp,
                modifier = Modifier.myTabIndicatorOffset(tabPositions[tabIndex])
            )
        }
    ) {
        tabTitles.forEachIndexed { index, title ->
            Tab(
                modifier = Modifier
                    .background(tabColors[index]),
                selected = tabIndex == index,
                onClick = {
                    if(tabIndex != index){
                        navController.navigate(tabAddresses[index])
                    }
                    tabIndex = index
                },
                text = {
                    Text(
                        text = title,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            )
        }
    }
}

fun Modifier.myTabIndicatorOffset(
    currentTabPosition: TabPosition
): Modifier = composed(
    inspectorInfo = debugInspectorInfo {
        name = "tabIndicatorOffset"
        value = currentTabPosition
    }
) {
    val currentTabWidth by animateDpAsState(
        targetValue = currentTabPosition.width,
        animationSpec = tween(durationMillis = 250, easing = FastOutSlowInEasing)
    )
    val indicatorOffset by animateDpAsState(
        targetValue = currentTabPosition.left,
        animationSpec = tween(durationMillis = 250, easing = FastOutSlowInEasing)
    )
    fillMaxWidth()
        .wrapContentSize(Alignment.TopStart)
        .offset(x = indicatorOffset)
        .width(currentTabWidth)
}