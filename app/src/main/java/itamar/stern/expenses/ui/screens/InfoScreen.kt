package itamar.stern.expenses.ui.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import itamar.stern.expenses.R
import itamar.stern.expenses.ui.view_models.SecondViewModel
import java.time.LocalDate


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun InfoScreen() {
    val viewModel: SecondViewModel = viewModel()
    var monthValue by remember { mutableStateOf("${LocalDate.now().monthValue-1}") }
    var dropDownExpandedMonths by remember { mutableStateOf(false) }
    val dropItemsMonths = listOf(
        stringResource(id = R.string.january),
        stringResource(id = R.string.february),
        stringResource(id = R.string.march),
        stringResource(id = R.string.april),
        stringResource(id = R.string.may),
        stringResource(id = R.string.june),
        stringResource(id = R.string.july),
        stringResource(id = R.string.august),
        stringResource(id = R.string.september),
        stringResource(id = R.string.october),
        stringResource(id = R.string.november),
        stringResource(id = R.string.december),
    )
    var selectedItemMonths by remember { mutableStateOf(dropItemsMonths[LocalDate.now().monthValue-1]) }
    var yearValue by remember { mutableStateOf("${LocalDate.now().year}") }
    var dropDownExpandedYears by remember { mutableStateOf(false) }
    val dropItemsYears = listOf(
        stringResource(id = R.string.y2022),
        stringResource(id = R.string.y2023),
        stringResource(id = R.string.y2024),
        stringResource(id = R.string.y2025),
        stringResource(id = R.string.y2026),
        stringResource(id = R.string.y2027),
    )
    var selectedItemYears by remember { mutableStateOf(dropItemsYears[0]) }
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row() {
            OutlinedButton(
                onClick = { dropDownExpandedYears = true },
                border = BorderStroke(1.dp, Color.DarkGray),
                modifier = Modifier
                    .size(80.dp, 40.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = selectedItemYears
                    )
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = ""
                    )
                }
            }
            DropdownMenu(
                expanded = dropDownExpandedYears,
                onDismissRequest = { dropDownExpandedYears = false }
            ) {
                dropItemsYears.forEachIndexed { index, str ->
                    DropdownMenuItem(
                        onClick = {
                            selectedItemYears = dropItemsYears[index]
                            yearValue = selectedItemYears
                            dropDownExpandedYears = false
                        },
                    ) {
                        Text(text = str)
                    }
                }
            }
            OutlinedButton(
                onClick = { dropDownExpandedMonths = true },
                border = BorderStroke(1.dp, Color.DarkGray),
                modifier = Modifier
                    .size(80.dp, 40.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = selectedItemMonths
                    )
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = ""
                    )
                }
            }
            DropdownMenu(
                expanded = dropDownExpandedMonths,
                onDismissRequest = { dropDownExpandedMonths = false }
            ) {
                dropItemsMonths.forEachIndexed { index, str ->
                    DropdownMenuItem(
                        onClick = {
                            selectedItemMonths = dropItemsMonths[index]
                            monthValue = selectedItemMonths
                            viewModel.updatePeriod(dropItemsMonths.indexOf(monthValue)+1, yearValue)
                            dropDownExpandedMonths = false
                        },
                    ) {
                        Text(text = str)
                    }
                }
            }
        }
        val typesItems = listOf(
            stringResource(id = R.string.food),
            stringResource(id = R.string.fuel),
            stringResource(id = R.string.car),
            stringResource(id = R.string.rent),
            stringResource(id = R.string.clothing),
            stringResource(id = R.string.phone),
            stringResource(id = R.string.tools),
            stringResource(id = R.string.hobbies),
            stringResource(id = R.string.other),
        )
        val values = listOf(
            viewModel.food.observeAsState(),
            viewModel.fuel.observeAsState(),
            viewModel.car.observeAsState(),
            viewModel.rent.observeAsState(),
            viewModel.clothing.observeAsState(),
            viewModel.phone.observeAsState(),
            viewModel.tools.observeAsState(),
            viewModel.hobbies.observeAsState(),
            viewModel.other.observeAsState(),
        )
        val sum = viewModel.sum.observeAsState()
        for (i in 0 until typesItems.size-1){
            Row() {
                Text(text = typesItems[i])
                Text(text = "${values[i].value}")
            }
        }
        Row() {
            Text(text = "סה\"כ")
            Text(text = "${sum.value}")
        }

    }
}

