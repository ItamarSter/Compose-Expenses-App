package itamar.stern.expenses.ui.view_models


import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import itamar.stern.expenses.ExpensesApplication
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
class SecondViewModel:ViewModel() {

    var food: LiveData<Int>
    var fuel: LiveData<Int>
    var car: LiveData<Int>
    var rent: LiveData<Int>
    var clothing: LiveData<Int>
    var phone: LiveData<Int>
    var tools: LiveData<Int>
    var hobbies: LiveData<Int>
    var other: LiveData<Int>

    var sum: LiveData<Int>

    init {
        food = ExpensesApplication.roomDB.expensesDao().getCategory("אוכל", "%/${LocalDate.now().monthValue}/${LocalDate.now().year}")
        fuel = ExpensesApplication.roomDB.expensesDao().getCategory("דלק", "%/${LocalDate.now().monthValue}/${LocalDate.now().year}")
        car = ExpensesApplication.roomDB.expensesDao().getCategory("רכב", "%/${LocalDate.now().monthValue}/${LocalDate.now().year}")
        rent = ExpensesApplication.roomDB.expensesDao().getCategory("שכירות", "%/${LocalDate.now().monthValue}/${LocalDate.now().year}")
        clothing = ExpensesApplication.roomDB.expensesDao().getCategory("ביגוד", "%/${LocalDate.now().monthValue}/${LocalDate.now().year}")
        phone = ExpensesApplication.roomDB.expensesDao().getCategory("טלפון", "%/${LocalDate.now().monthValue}/${LocalDate.now().year}")
        tools = ExpensesApplication.roomDB.expensesDao().getCategory("כלים", "%/${LocalDate.now().monthValue}/${LocalDate.now().year}")
        hobbies = ExpensesApplication.roomDB.expensesDao().getCategory("תחביבים", "%/${LocalDate.now().monthValue}/${LocalDate.now().year}")
        other = ExpensesApplication.roomDB.expensesDao().getCategory("אחר", "%/${LocalDate.now().monthValue}/${LocalDate.now().year}")
        sum = ExpensesApplication.roomDB.expensesDao().getSumByMonthAndYear("%/${LocalDate.now().monthValue}/${LocalDate.now().year}")
    }

    fun updatePeriod(monthValue: Int, yearValue: String){
        val monthAndYear = "%/$monthValue/$yearValue"
        food = ExpensesApplication.roomDB.expensesDao().getCategory("אוכל", monthAndYear)
        fuel = ExpensesApplication.roomDB.expensesDao().getCategory("דלק", monthAndYear)
        car = ExpensesApplication.roomDB.expensesDao().getCategory("רכב", monthAndYear)
        rent = ExpensesApplication.roomDB.expensesDao().getCategory("שכירות", monthAndYear)
        clothing = ExpensesApplication.roomDB.expensesDao().getCategory("ביגוד", monthAndYear)
        phone = ExpensesApplication.roomDB.expensesDao().getCategory("טלפון", monthAndYear)
        tools = ExpensesApplication.roomDB.expensesDao().getCategory("כלים", monthAndYear)
        hobbies = ExpensesApplication.roomDB.expensesDao().getCategory("תחביבים", monthAndYear)
        other = ExpensesApplication.roomDB.expensesDao().getCategory("אחר", monthAndYear)
        sum = ExpensesApplication.roomDB.expensesDao().getSumByMonthAndYear(monthAndYear)
    }
}