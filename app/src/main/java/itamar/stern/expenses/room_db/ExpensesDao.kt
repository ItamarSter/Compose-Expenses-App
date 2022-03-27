package itamar.stern.expenses.room_db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import itamar.stern.expenses.models.Expenses

@Dao
interface ExpensesDao {
    @Insert
    fun addExpense(expense: Expenses)

    @Query("SELECT * FROM Expenses ORDER BY timeStamp DESC")
    fun getAll(): LiveData<List<Expenses>>

    @Query("SELECT SUM(sum) FROM Expenses WHERE category=:category AND date LIKE :monthAndYear")
    fun getCategory(category: String, monthAndYear: String): LiveData<Int>

    @Query("SELECT SUM(sum) FROM Expenses WHERE date LIKE :monthAndYear")
    fun getSumByMonthAndYear(monthAndYear: String): LiveData<Int>

}