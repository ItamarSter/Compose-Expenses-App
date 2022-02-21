package itamar.stern.expenses.ui.screens

import android.app.DatePickerDialog
import android.content.Context
import android.os.Build
import android.widget.DatePicker
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.expandHorizontally
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.android.material.datepicker.MaterialDatePicker
import itamar.stern.expenses.R
import itamar.stern.expenses.models.Expenses
import itamar.stern.expenses.ui.view_models.FirstViewModel
import java.time.LocalDate
import java.time.LocalDateTime


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun FirstScreen() {
    val context = LocalContext.current
    val savedString = stringResource(id = R.string.saved)
    val viewModel: FirstViewModel = viewModel()
    val expensesList = viewModel.allExpenses.observeAsState(listOf())

    var openDialog by remember { mutableStateOf(false) }
    if (openDialog) {
        OpenAddDialog({
            viewModel.saveExpense(it)
            Toast.makeText(context, savedString, Toast.LENGTH_SHORT).show()
        }) {
            openDialog = false //onDismiss callback
        }
    }
    Box() {
        LazyColumn {
            items(expensesList.value) { expense ->
                ExpenseItem(expense)
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            FloatingActionButton(
                modifier = Modifier
                    .padding(16.dp),
                onClick = {
                    openDialog = true
                },
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "")
            }
        }

    }


}

@Composable
fun ExpenseItem(expense: Expenses) {
    Card(
        elevation = 4.dp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp)
            .padding(top = 4.dp, bottom = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(8.dp)
        ) {
            Row(
                verticalAlignment = Alignment.Bottom
            ) {
                Text(
                    text = expense.sum.toString(),
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                    modifier = Modifier
                        .fillMaxWidth(0.33f)
                )
                Text(text = expense.category)
                if (expense.title != "") Text(text = ": ${expense.title}")
            }
            Divider(color = Color.Black)
            Row(
                verticalAlignment = Alignment.Bottom
            ) {
                Text(
                    text = expense.time,
                    modifier = Modifier
                        .fillMaxWidth(0.33f)
                )
                Text(text = expense.date)
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun OpenAddDialog(saveExpense: (Expenses) -> Unit, onDismiss: () -> Unit) {
    val context = LocalContext.current
    var grayOrRedTextField by remember { mutableStateOf(Color.Gray) }
    var sumValue by remember { mutableStateOf("") }
    var titleValue by remember { mutableStateOf("") }
    var dateValue by remember { mutableStateOf("${LocalDateTime.now().dayOfMonth}/${LocalDateTime.now().monthValue}") }
    var dropDownExpanded by remember { mutableStateOf(false) }
    val dropItems = listOf(
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
    var selectedItem by remember { mutableStateOf(dropItems[0]) }
    AlertDialog(
        onDismissRequest = { onDismiss() },
        buttons = {},
        text = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(id = R.string.add_expense),
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    OutlinedTextField(
                        modifier = Modifier
                            .fillMaxWidth(0.78f)
                            .padding(end = 8.dp),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            unfocusedBorderColor = grayOrRedTextField,
                            focusedBorderColor = grayOrRedTextField,
                            focusedLabelColor = grayOrRedTextField,
                            unfocusedLabelColor = grayOrRedTextField
                        ),
                        value = sumValue,
                        onValueChange = { newText ->
                            sumValue = newText.replace("\n", "")
                            grayOrRedTextField = Color.Gray
                        },
                        label = { Text(text = stringResource(id = R.string.sum)) },
                        maxLines = 1,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                    OutlinedButton(
                        onClick = {
                            showDatePicker(context) {
                                dateValue = it
                            }
                        },
                        modifier = Modifier
                            .padding(top = 8.dp)
                            .border(1.dp, Color.Gray, RoundedCornerShape(4.dp))
                            .align(Alignment.Top),
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                imageVector = Icons.Default.DateRange,
                                contentDescription = "",
                                Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 2.dp)
                            )
                            Text(text = dateValue, fontSize = 10.sp, letterSpacing = 0.sp)
                        }

                    }

                }

                OutlinedTextField(
                    value = titleValue,
                    onValueChange = { newText ->
                        titleValue = newText
                    },
                    label = { Text(text = stringResource(id = R.string.details)) },
                    maxLines = 1
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth()
                ) {
                    OutlinedButton(
                        onClick = { dropDownExpanded = true },
                        border = BorderStroke(1.dp, Color.DarkGray),
                        modifier = Modifier
                            .size(80.dp, 40.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Text(
                                text = selectedItem
                            )
                            Icon(
                                imageVector = Icons.Default.ArrowDropDown,
                                contentDescription = ""
                            )
                        }
                    }
                    OutlinedButton(
                        onClick = {
                            if (sumValue != "") {
                                saveExpense(saveExpense, selectedItem, sumValue, titleValue)
                                onDismiss()
                            } else {
                                grayOrRedTextField = Color.Red
                            }
                        },
                        border = BorderStroke(1.dp, Color.DarkGray),
                        modifier = Modifier
                            .size(80.dp, 40.dp)
                    ) {
                        Text(text = stringResource(id = R.string.save_expense))
                    }
                    OutlinedButton(
                        onClick = { onDismiss() },
                        border = BorderStroke(1.dp, Color.DarkGray),
                        modifier = Modifier
                            .size(60.dp, 40.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "",
                        )
                    }
                }

                DropdownMenu(
                    expanded = dropDownExpanded,
                    onDismissRequest = { dropDownExpanded = false }
                ) {
                    dropItems.forEachIndexed { index, str ->
                        DropdownMenuItem(
                            onClick = {
                                selectedItem = dropItems[index]
                                dropDownExpanded = false
                            },
                        ) {
                            Text(text = str)
                        }
                    }
                }
            }
        }
    )
}

@RequiresApi(Build.VERSION_CODES.O)
private fun showDatePicker(context: Context, updateDate: (String) -> Unit) {
    DatePickerDialog(context,{ _, year, monthOfYear, dayOfMonth ->
        updateDate("$dayOfMonth/$monthOfYear/$year")
    }, LocalDate.now().year, LocalDate.now().monthValue, LocalDate.now().dayOfMonth).show()
}



@RequiresApi(Build.VERSION_CODES.O)
private fun saveExpense(
    saveExpense: (Expenses) -> Unit,
    selectedItem: String,
    sumValue: String,
    titleValue: String
) {
    val date = LocalDateTime.now()
    saveExpense(
        Expenses(
            System.currentTimeMillis(),
            selectedItem,
            "${date.dayOfMonth}/${date.monthValue}/${date.year}",
            sumValue.toDouble(),
            titleValue,
            "${date.hour}:${if (date.minute.toString().length > 1) date.minute else "0${date.minute}"}"
        )
    )
}
