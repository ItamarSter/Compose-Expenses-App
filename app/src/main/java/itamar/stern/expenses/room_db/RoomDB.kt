package itamar.stern.expenses.room_db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import itamar.stern.expenses.models.Expenses

const val DB_VERSION = 2
const val DB_NAME = "ExpensesDatabase"

@Database(entities = [Expenses::class], version = DB_VERSION)
abstract class RoomDB : RoomDatabase() {
    companion object {
        fun create(context: Context): RoomDB {
            return Room.databaseBuilder(context, RoomDB::class.java, DB_NAME)
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build()
        }
    }
    abstract fun expensesDao(): ExpensesDao

}