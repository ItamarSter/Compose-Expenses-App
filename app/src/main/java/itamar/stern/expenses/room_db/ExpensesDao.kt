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

    @Query("SELECT * FROM Expenses")
    fun getAll(): LiveData<List<Expenses>>

}