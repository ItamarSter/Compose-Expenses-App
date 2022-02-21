package itamar.stern.expenses.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Expenses(
    @PrimaryKey
    val timeStamp: Long,
    val category: String,
    val date: String,
    val sum: Double,
    val title: String,
    val time: String
)

