package itamar.stern.expenses.ui.view_models


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import itamar.stern.expenses.ExpensesApplication
import itamar.stern.expenses.models.Expenses

class FirstViewModel:ViewModel() {
    fun saveExpense(expense: Expenses){
        ExpensesApplication.roomDB.expensesDao().addExpense(expense)
    }

    var allExpenses: LiveData<List<Expenses>>
    init {
        allExpenses = ExpensesApplication.roomDB.expensesDao().getAll()
    }
}