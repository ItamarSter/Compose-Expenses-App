package itamar.stern.expenses.ui

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import itamar.stern.expenses.ui.nav_bar.NavBar
import itamar.stern.expenses.ui.screens.ListScreen
import itamar.stern.expenses.ui.screens.InfoScreen
import itamar.stern.expenses.ui.screens.HistoryScreen
import itamar.stern.expenses.ui.theme.ExpensesTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ExpensesTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    ExpensesApp()
                }
           }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ExpensesApp() {
    val navController = rememberNavController()
    Scaffold(bottomBar = { NavBar(navController) }) {
        NavHost(
            modifier = Modifier.padding(it),
            navController = navController,
            startDestination = "list_screen"
        ) {
            composable("list_screen") {
                ListScreen()
            }
            composable("info_screen") {
                InfoScreen()
            }
            composable("history_screen") {
                HistoryScreen()
            }
        }
    }
}



@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ExpensesTheme {
        ExpensesApp()
    }
}